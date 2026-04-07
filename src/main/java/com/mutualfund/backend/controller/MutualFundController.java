package com.mutualfund.backend.controller;

import com.mutualfund.backend.dto.MutualFundDTO;
import com.mutualfund.backend.service.MutualFundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MutualFundController {

    private final MutualFundService mutualFundService;

    public MutualFundController(MutualFundService mutualFundService) {
        this.mutualFundService = mutualFundService;
    }

    @GetMapping("/investor/funds")
    public ResponseEntity<List<MutualFundDTO>> getAllFunds() {
        return ResponseEntity.ok(mutualFundService.getAllFunds());
    }

    @GetMapping("/investor/funds/{id}")
    public ResponseEntity<MutualFundDTO> getFundById(@PathVariable Long id) {
        return ResponseEntity.ok(mutualFundService.getFundById(id));
    }

    @GetMapping("/investor/funds/category/{category}")
    public ResponseEntity<List<MutualFundDTO>> getFundsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(mutualFundService.getFundsByCategory(category));
    }

    @GetMapping("/investor/funds/risk/{risk}")
    public ResponseEntity<List<MutualFundDTO>> getFundsByRisk(@PathVariable String risk) {
        return ResponseEntity.ok(mutualFundService.getFundsByRisk(risk));
    }

    @GetMapping("/investor/funds/search")
    public ResponseEntity<List<MutualFundDTO>> searchFunds(@RequestParam String name) {
        return ResponseEntity.ok(mutualFundService.searchFunds(name));
    }

    @PostMapping("/admin/funds")
    public ResponseEntity<MutualFundDTO> createFund(@RequestBody MutualFundDTO dto) {
        return ResponseEntity.ok(mutualFundService.createFund(dto));
    }

    @PutMapping("/admin/funds/{id}")
    public ResponseEntity<MutualFundDTO> updateFund(@PathVariable Long id,
                                                     @RequestBody MutualFundDTO dto) {
        return ResponseEntity.ok(mutualFundService.updateFund(id, dto));
    }

    @PutMapping("/admin/funds/{id}/nav")
    public ResponseEntity<MutualFundDTO> updateNav(@PathVariable Long id,
                                                    @RequestBody Map<String, Double> body) {
        return ResponseEntity.ok(mutualFundService.updateNav(id, body.get("nav")));
    }
}