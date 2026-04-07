package com.mutualfund.backend.service;

import com.mutualfund.backend.dto.ReportDTO;
import com.mutualfund.backend.entity.Report;
import com.mutualfund.backend.entity.User;
import com.mutualfund.backend.exception.ResourceNotFoundException;
import com.mutualfund.backend.repo.ReportRepo;
import com.mutualfund.backend.repo.UserRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

//Service layer handling business logic for User operations
// Connects controller with repository layer
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepo reportRepo;
    private final UserRepo userRepo;

    public ReportServiceImpl(ReportRepo reportRepo, UserRepo userRepo) {
        this.reportRepo = reportRepo;
        this.userRepo = userRepo;
    }

    @Override
    public ReportDTO createReport(ReportDTO dto, Long analystId) {
        User analyst = userRepo.findById(analystId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + analystId));

        Report report = new Report();
        report.setTitle(dto.getTitle());
        report.setReportType(dto.getReportType());
        report.setDescription(dto.getDescription());
        report.setFilePath(dto.getFilePath());
        report.setGeneratedBy(analyst);

        return mapToDTO(reportRepo.save(report));
    }

    @Override
    public List<ReportDTO> getAllReports() {
        return reportRepo.findAllByOrderByGeneratedAtDesc()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsByAnalyst(Long analystId) {
        return reportRepo.findByGeneratedById(analystId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDTO getReportById(Long id) {
        Report report = reportRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found: " + id));
        return mapToDTO(report);
    }

    @Override
    public void deleteReport(Long id) {
        if (!reportRepo.existsById(id)) {
            throw new ResourceNotFoundException("Report not found: " + id);
        }
        reportRepo.deleteById(id);
    }

    private ReportDTO mapToDTO(Report report) {
        ReportDTO dto = new ReportDTO();
        dto.setId(report.getId());
        dto.setTitle(report.getTitle());
        dto.setReportType(report.getReportType());
        dto.setDescription(report.getDescription());
        dto.setFilePath(report.getFilePath());
        dto.setGeneratedAt(report.getGeneratedAt());
        if (report.getGeneratedBy() != null) {
            dto.setGeneratedById(report.getGeneratedBy().getId());
            dto.setGeneratedByName(report.getGeneratedBy().getFullName());
        }
        return dto;
    }
}