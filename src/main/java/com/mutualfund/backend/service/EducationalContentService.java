package com.mutualfund.backend.service;

import com.mutualfund.backend.dto.EducationalContentDTO;
import java.util.List;
//Service layer handling business logic for User operations
// Connects controller with repository layer
public interface EducationalContentService {
    EducationalContentDTO createContent(EducationalContentDTO dto, Long authorId);
    EducationalContentDTO updateContent(Long id, EducationalContentDTO dto);
    List<EducationalContentDTO> getAllContent();
    List<EducationalContentDTO> getPublishedContent();
    List<EducationalContentDTO> getContentByAuthor(Long authorId);
    EducationalContentDTO getContentById(Long id);
    void deleteContent(Long id);
}