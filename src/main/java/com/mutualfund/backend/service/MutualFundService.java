package com.mutualfund.backend.service;

import com.mutualfund.backend.dto.MutualFundDTO;
import java.util.List;

public interface MutualFundService {
    List<MutualFundDTO> getAllFunds();
    MutualFundDTO getFundById(Long id);
    List<MutualFundDTO> getFundsByCategory(String category);
    List<MutualFundDTO> getFundsByRisk(String risk);
    List<MutualFundDTO> searchFunds(String name);
    MutualFundDTO createFund(MutualFundDTO dto);
    MutualFundDTO updateFund(Long id, MutualFundDTO dto);
    MutualFundDTO updateNav(Long id, Double newNav);
}