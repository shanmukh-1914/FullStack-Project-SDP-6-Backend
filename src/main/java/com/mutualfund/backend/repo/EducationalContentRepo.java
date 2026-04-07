package com.mutualfund.backend.repo;

import com.mutualfund.backend.entity.EducationalContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EducationalContentRepo extends JpaRepository<EducationalContent, Long> {
    List<EducationalContent> findByStatus(EducationalContent.Status status);
    List<EducationalContent> findByAuthorId(Long authorId);
    List<EducationalContent> findByCategory(String category);
    List<EducationalContent> findByStatusOrderByCreatedDateDesc(EducationalContent.Status status);
}