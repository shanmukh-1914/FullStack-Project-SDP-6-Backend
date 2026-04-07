package com.mutualfund.backend.service;

import com.mutualfund.backend.dto.EducationalContentDTO;
import com.mutualfund.backend.entity.EducationalContent;
import com.mutualfund.backend.entity.User;
import com.mutualfund.backend.exception.ResourceNotFoundException;
import com.mutualfund.backend.repo.EducationalContentRepo;
import com.mutualfund.backend.repo.UserRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducationalContentServiceImpl implements EducationalContentService {

    private final EducationalContentRepo contentRepo;
    private final UserRepo userRepo;

    public EducationalContentServiceImpl(EducationalContentRepo contentRepo,
                                          UserRepo userRepo) {
        this.contentRepo = contentRepo;
        this.userRepo = userRepo;
    }

    @Override
    public EducationalContentDTO createContent(EducationalContentDTO dto, Long authorId) {
        User author = userRepo.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + authorId));

        EducationalContent content = new EducationalContent();
        content.setTitle(dto.getTitle());
        content.setCategory(dto.getCategory());
        content.setContent(dto.getContent());
        content.setKeyTakeaways(dto.getKeyTakeaways());
        content.setStatus(EducationalContent.Status.valueOf(dto.getStatus().toUpperCase()));
        content.setAuthor(author);

        return mapToDTO(contentRepo.save(content));
    }

    @Override
    public EducationalContentDTO updateContent(Long id, EducationalContentDTO dto) {
        EducationalContent content = contentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found: " + id));

        content.setTitle(dto.getTitle());
        content.setCategory(dto.getCategory());
        content.setContent(dto.getContent());
        content.setKeyTakeaways(dto.getKeyTakeaways());
        content.setStatus(EducationalContent.Status.valueOf(dto.getStatus().toUpperCase()));

        return mapToDTO(contentRepo.save(content));
    }

    @Override
    public List<EducationalContentDTO> getAllContent() {
        return contentRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EducationalContentDTO> getPublishedContent() {
        return contentRepo.findByStatusOrderByCreatedDateDesc(EducationalContent.Status.PUBLISHED)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EducationalContentDTO> getContentByAuthor(Long authorId) {
        return contentRepo.findByAuthorId(authorId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EducationalContentDTO getContentById(Long id) {
        EducationalContent content = contentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found: " + id));
        return mapToDTO(content);
    }

    @Override
    public void deleteContent(Long id) {
        if (!contentRepo.existsById(id)) {
            throw new ResourceNotFoundException("Content not found: " + id);
        }
        contentRepo.deleteById(id);
    }

    private EducationalContentDTO mapToDTO(EducationalContent content) {
        EducationalContentDTO dto = new EducationalContentDTO();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setCategory(content.getCategory());
        dto.setContent(content.getContent());
        dto.setKeyTakeaways(content.getKeyTakeaways());
        dto.setStatus(content.getStatus().name());
        dto.setViews(content.getViews());
        dto.setCreatedDate(content.getCreatedDate());
        if (content.getAuthor() != null) {
            dto.setAuthorId(content.getAuthor().getId());
            dto.setAuthorName(content.getAuthor().getFullName());
        }
        return dto;
    }
}