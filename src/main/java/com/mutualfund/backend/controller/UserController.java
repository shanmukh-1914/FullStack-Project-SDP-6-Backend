package com.mutualfund.backend.controller;

import com.mutualfund.backend.dto.UserDTO;
import com.mutualfund.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
// Exposes endpoints for client (frontend) to interact with backend services
// Follows Controller → Service → Repository architecture
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/admin/users/{id}/toggle-status")
    public ResponseEntity<String> toggleStatus(@PathVariable Long id) {
        userService.toggleUserStatus(id);
        return ResponseEntity.ok("User status updated successfully");
    }

    @PutMapping("/investor/profile/{id}")
    public ResponseEntity<UserDTO> updateProfile(@PathVariable Long id,
                                                  @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @GetMapping("/investor/profile/{id}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}