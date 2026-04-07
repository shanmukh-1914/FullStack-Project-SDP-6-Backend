package com.mutualfund.backend.service;

import com.mutualfund.backend.dto.MutualFundDTO;
import com.mutualfund.backend.entity.MutualFund;
import com.mutualfund.backend.exception.ResourceNotFoundException;
import com.mutualfund.backend.repo.MutualFundRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

//Service layer handling business logic for User operations
// Connects controller with repository layer
@Service
public class MutualFundServiceImpl implements MutualFundService {

    private final MutualFundRepo mutualFundRepo;

    public MutualFundServiceImpl(MutualFundRepo mutualFundRepo) {
        this.mutualFundRepo = mutualFundRepo;
    }

    @Override
    public List<MutualFundDTO> getAllFunds() {
        return mutualFundRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MutualFundDTO getFundById(Long id) {
        MutualFund fund = mutualFundRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fund not found with id: " + id));
        return mapToDTO(fund);
    }

    @Override
    public List<MutualFundDTO> getFundsByCategory(String category) {
        return mutualFundRepo.findByCategory(category)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MutualFundDTO> getFundsByRisk(String risk) {
        MutualFund.RiskLevel riskLevel = MutualFund.RiskLevel.valueOf(risk);
        return mutualFundRepo.findByRisk(riskLevel)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MutualFundDTO> searchFunds(String name) {
        return mutualFundRepo.searchByName(name)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MutualFundDTO createFund(MutualFundDTO dto) {
        if (mutualFundRepo.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Fund already exists: " + dto.getName());
        }
        MutualFund fund = mapToEntity(dto);
        return mapToDTO(mutualFundRepo.save(fund));
    }

    @Override
    public MutualFundDTO updateFund(Long id, MutualFundDTO dto) {
        MutualFund fund = mutualFundRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fund not found with id: " + id));

        fund.setName(dto.getName());
        fund.setCategory(dto.getCategory());
        fund.setRisk(MutualFund.RiskLevel.valueOf(dto.getRisk()));
        fund.setNav(dto.getNav());
        fund.setAum(dto.getAum());
        fund.setExpenseRatio(dto.getExpenseRatio());
        fund.setMinInvest(dto.getMinInvest());
        fund.setDescription(dto.getDescription());
        fund.setReturns1yr(dto.getReturns1yr());
        fund.setReturns3yr(dto.getReturns3yr());
        fund.setReturns5yr(dto.getReturns5yr());
        fund.setFundManager(dto.getFundManager());

        return mapToDTO(mutualFundRepo.save(fund));
    }

    @Override
    public MutualFundDTO updateNav(Long id, Double newNav) {
        MutualFund fund = mutualFundRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fund not found with id: " + id));
        fund.setNav(newNav);
        return mapToDTO(mutualFundRepo.save(fund));
    }

    public MutualFundDTO mapToDTO(MutualFund fund) {
        MutualFundDTO dto = new MutualFundDTO();
        dto.setId(fund.getId());
        dto.setName(fund.getName());
        dto.setCategory(fund.getCategory());
        dto.setRisk(fund.getRisk().name());
        dto.setNav(fund.getNav());
        dto.setAum(fund.getAum());
        dto.setExpenseRatio(fund.getExpenseRatio());
        dto.setMinInvest(fund.getMinInvest());
        dto.setDescription(fund.getDescription());
        dto.setReturns1yr(fund.getReturns1yr());
        dto.setReturns3yr(fund.getReturns3yr());
        dto.setReturns5yr(fund.getReturns5yr());
        dto.setFundManager(fund.getFundManager());
        return dto;
    }

    private MutualFund mapToEntity(MutualFundDTO dto) {
        MutualFund fund = new MutualFund();
        fund.setName(dto.getName());
        fund.setCategory(dto.getCategory());
        fund.setRisk(MutualFund.RiskLevel.valueOf(dto.getRisk()));
        fund.setNav(dto.getNav());
        fund.setAum(dto.getAum());
        fund.setExpenseRatio(dto.getExpenseRatio());
        fund.setMinInvest(dto.getMinInvest());
        fund.setDescription(dto.getDescription());
        fund.setReturns1yr(dto.getReturns1yr());
        fund.setReturns3yr(dto.getReturns3yr());
        fund.setReturns5yr(dto.getReturns5yr());
        fund.setFundManager(dto.getFundManager());
        return fund;
    }
}