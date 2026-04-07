package com.mutualfund.backend.service;

import com.mutualfund.backend.dto.ReportDTO;
import java.util.List;

public interface ReportService {
    ReportDTO createReport(ReportDTO dto, Long analystId);
    List<ReportDTO> getAllReports();
    List<ReportDTO> getReportsByAnalyst(Long analystId);
    ReportDTO getReportById(Long id);
    void deleteReport(Long id);
}