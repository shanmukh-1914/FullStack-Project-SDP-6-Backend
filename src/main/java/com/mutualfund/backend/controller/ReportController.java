package com.mutualfund.backend.controller;

import com.mutualfund.backend.dto.ReportDTO;
import com.mutualfund.backend.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/analyst/reports/{analystId}")
    public ResponseEntity<ReportDTO> createReport(
            @PathVariable Long analystId,
            @RequestBody ReportDTO dto) {
        return ResponseEntity.ok(reportService.createReport(dto, analystId));
    }

    @GetMapping("/analyst/reports")
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/analyst/reports/my/{analystId}")
    public ResponseEntity<List<ReportDTO>> getMyReports(@PathVariable Long analystId) {
        return ResponseEntity.ok(reportService.getReportsByAnalyst(analystId));
    }

    @GetMapping("/analyst/reports/{id}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @DeleteMapping("/analyst/reports/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok("Report deleted successfully");
    }

    @GetMapping("/admin/reports")
    public ResponseEntity<List<ReportDTO>> getAllReportsAdmin() {
        return ResponseEntity.ok(reportService.getAllReports());
    }
}