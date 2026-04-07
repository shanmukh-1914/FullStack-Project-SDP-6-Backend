package com.mutualfund.backend.controller;

import com.mutualfund.backend.dto.EducationalContentDTO;
import com.mutualfund.backend.service.EducationalContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
// Exposes endpoints for client (frontend) to interact with backend services
// Follows Controller → Service → Repository architecture
@RestController
@RequestMapping("/api")
public class ContentController {

    private final EducationalContentService contentService;

    public ContentController(EducationalContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/investor/content")
    public ResponseEntity<List<EducationalContentDTO>> getPublishedContent() {
        return ResponseEntity.ok(contentService.getPublishedContent());
    }

    @GetMapping("/advisor/content")
    public ResponseEntity<List<EducationalContentDTO>> getAllContent() {
        return ResponseEntity.ok(contentService.getAllContent());
    }

    @GetMapping("/advisor/content/{id}")
    public ResponseEntity<EducationalContentDTO> getContentById(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getContentById(id));
    }

    @PostMapping("/advisor/content/{authorId}")
    public ResponseEntity<EducationalContentDTO> createContent(
            @PathVariable Long authorId,
            @RequestBody EducationalContentDTO dto) {
        return ResponseEntity.ok(contentService.createContent(dto, authorId));
    }

    @PutMapping("/advisor/content/{id}")
    public ResponseEntity<EducationalContentDTO> updateContent(
            @PathVariable Long id,
            @RequestBody EducationalContentDTO dto) {
        return ResponseEntity.ok(contentService.updateContent(id, dto));
    }

    @DeleteMapping("/advisor/content/{id}")
    public ResponseEntity<String> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.ok("Content deleted successfully");
    }

    @GetMapping("/admin/content")
    public ResponseEntity<List<EducationalContentDTO>> getAllContentAdmin() {
        return ResponseEntity.ok(contentService.getAllContent());
    }
}