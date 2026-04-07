package com.mutualfund.backend.service;

import com.mutualfund.backend.dto.InvestmentDTO;
import com.mutualfund.backend.dto.InvestRequest;
import com.mutualfund.backend.entity.Investment;
import com.mutualfund.backend.entity.MutualFund;
import com.mutualfund.backend.entity.User;
import com.mutualfund.backend.exception.ResourceNotFoundException;
import com.mutualfund.backend.repo.InvestmentRepo;
import com.mutualfund.backend.repo.MutualFundRepo;
import com.mutualfund.backend.repo.UserRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepo investmentRepo;
    private final UserRepo userRepo;
    private final MutualFundRepo mutualFundRepo;

    public InvestmentServiceImpl(InvestmentRepo investmentRepo,
                                  UserRepo userRepo,
                                  MutualFundRepo mutualFundRepo) {
        this.investmentRepo = investmentRepo;
        this.userRepo = userRepo;
        this.mutualFundRepo = mutualFundRepo;
    }

    @Override
    public InvestmentDTO invest(Long userId, InvestRequest request) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        MutualFund fund = mutualFundRepo.findById(request.getFundId())
                .orElseThrow(() -> new ResourceNotFoundException("Fund not found: " + request.getFundId()));

        double newUnits = Math.round((request.getAmount() / fund.getNav()) * 100.0) / 100.0;

        Optional<Investment> existing = investmentRepo.findByUserIdAndFundId(userId, fund.getId());

        if (existing.isPresent()) {
            Investment inv = existing.get();
            inv.setAmountInvested(inv.getAmountInvested() + request.getAmount());
            inv.setUnits(Math.round((inv.getUnits() + newUnits) * 100.0) / 100.0);
            return mapToDTO(investmentRepo.save(inv));
        }

        Investment investment = new Investment();
        investment.setUser(user);
        investment.setFund(fund);
        investment.setAmountInvested(request.getAmount());
        investment.setUnits(newUnits);
        investment.setNavAtPurchase(fund.getNav());
        investment.setStatus(Investment.Status.ACTIVE);

        return mapToDTO(investmentRepo.save(investment));
    }

    @Override
    public List<InvestmentDTO> getInvestmentsByUser(Long userId) {
        return investmentRepo.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InvestmentDTO getInvestmentById(Long id) {
        Investment inv = investmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment not found: " + id));
        return mapToDTO(inv);
    }

    @Override
    public void redeemInvestment(Long id) {
        Investment inv = investmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investment not found: " + id));
        inv.setStatus(Investment.Status.REDEEMED);
        investmentRepo.save(inv);
    }

    private InvestmentDTO mapToDTO(Investment inv) {
        InvestmentDTO dto = new InvestmentDTO();
        dto.setId(inv.getId());
        dto.setUserId(inv.getUser().getId());
        dto.setFundId(inv.getFund().getId());
        dto.setFundName(inv.getFund().getName());
        dto.setCategory(inv.getFund().getCategory());
        dto.setRisk(inv.getFund().getRisk().name());
        dto.setAmountInvested(inv.getAmountInvested());
        dto.setUnits(inv.getUnits());
        dto.setNavAtPurchase(inv.getNavAtPurchase());
        dto.setStatus(inv.getStatus().name());
        dto.setInvestmentDate(inv.getInvestmentDate());

        double currentValue = Math.round((inv.getFund().getNav() * inv.getUnits()) * 100.0) / 100.0;
        double gains = Math.round((currentValue - inv.getAmountInvested()) * 100.0) / 100.0;
        double gainPercent = Math.round(((gains / inv.getAmountInvested()) * 100) * 100.0) / 100.0;

        dto.setCurrentValue(currentValue);
        dto.setGains(gains);
        dto.setGainPercent(gainPercent);

        return dto;
    }
}