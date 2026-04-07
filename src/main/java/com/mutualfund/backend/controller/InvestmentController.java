package com.mutualfund.backend.controller;

import com.mutualfund.backend.dto.InvestmentDTO;
import com.mutualfund.backend.dto.InvestRequest;
import com.mutualfund.backend.service.InvestmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/investor")
public class InvestmentController {

    private final InvestmentService investmentService;

    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @PostMapping("/invest/{userId}")
    public ResponseEntity<InvestmentDTO> invest(@PathVariable Long userId,
                                                 @RequestBody InvestRequest request) {
        return ResponseEntity.ok(investmentService.invest(userId, request));
    }

    @GetMapping("/portfolio/{userId}")
    public ResponseEntity<List<InvestmentDTO>> getPortfolio(@PathVariable Long userId) {
        return ResponseEntity.ok(investmentService.getInvestmentsByUser(userId));
    }

    @GetMapping("/investments/{id}")
    public ResponseEntity<InvestmentDTO> getInvestmentById(@PathVariable Long id) {
        return ResponseEntity.ok(investmentService.getInvestmentById(id));
    }

    @PutMapping("/investments/{id}/redeem")
    public ResponseEntity<String> redeem(@PathVariable Long id) {
        investmentService.redeemInvestment(id);
        return ResponseEntity.ok("Investment redeemed successfully");
    }
}