package com.mutualfund.backend.repo;

import com.mutualfund.backend.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

//Repository interface for User entity
// Provides CRUD operations using Spring Data JPA
@Repository
public interface ReportRepo extends JpaRepository<Report, Long> {
    List<Report> findByGeneratedById(Long userId);
    List<Report> findByReportType(String reportType);
    List<Report> findAllByOrderByGeneratedAtDesc();
}