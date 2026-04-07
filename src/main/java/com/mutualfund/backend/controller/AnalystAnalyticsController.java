package com.mutualfund.backend.controller;

import com.mutualfund.backend.service.AnalystAnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analyst")
public class AnalystAnalyticsController {

    private final AnalystAnalyticsService analystAnalyticsService;

    public AnalystAnalyticsController(AnalystAnalyticsService analystAnalyticsService) {
        this.analystAnalyticsService = analystAnalyticsService;
    }

    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Object>> getAnalytics() {
        return ResponseEntity.ok(analystAnalyticsService.getAnalytics());
    }
}
