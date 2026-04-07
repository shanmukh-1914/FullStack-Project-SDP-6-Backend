package com.mutualfund.backend.controller;

import com.mutualfund.backend.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
// Exposes endpoints for client (frontend) to interact with backend services
// Follows Controller → Service → Repository architecture
@RestController
@RequestMapping("/api")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/investor/upload/profile-pic")
    public ResponseEntity<Map<String, String>> uploadProfilePic(
            @RequestParam("file") MultipartFile file) {
        String filePath = fileStorageService.storeFile(file);
        return ResponseEntity.ok(Map.of("filePath", filePath));
    }

    @PostMapping("/analyst/upload/report")
    public ResponseEntity<Map<String, String>> uploadReport(
            @RequestParam("file") MultipartFile file) {
        String filePath = fileStorageService.storeFile(file);
        return ResponseEntity.ok(Map.of("filePath", filePath));
    }
}