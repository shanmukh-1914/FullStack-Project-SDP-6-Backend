package com.mutualfund.backend.service;

import com.mutualfund.backend.dto.InvestmentDTO;
import com.mutualfund.backend.dto.InvestRequest;
import java.util.List;

public interface InvestmentService {
    InvestmentDTO invest(Long userId, InvestRequest request);
    List<InvestmentDTO> getInvestmentsByUser(Long userId);
    InvestmentDTO getInvestmentById(Long id);
    void redeemInvestment(Long id);
}