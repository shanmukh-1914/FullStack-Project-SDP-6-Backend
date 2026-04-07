package com.mutualfund.backend.controller;

import com.mutualfund.backend.dto.AuthResponse;
import com.mutualfund.backend.dto.LoginRequest;
import com.mutualfund.backend.dto.RegisterRequest;
import com.mutualfund.backend.dto.UserDTO;
import com.mutualfund.backend.entity.User;
import com.mutualfund.backend.repo.UserRepo;
import com.mutualfund.backend.security.JwtUtil;
import com.mutualfund.backend.service.EmailService;
import com.mutualfund.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRepo userRepo;
    private final EmailService emailService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserService userService,
                          UserRepo userRepo,
                          EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userRepo = userRepo;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        UserDTO user = userService.register(request);
        try {
            emailService.sendWelcomeEmail(user.getEmail(), user.getFullName());
        } catch (Exception e) {
            // continue even if email fails
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        User user = userRepo.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(new AuthResponse(
            user.getId(),
                token,
                user.getEmail(),
                user.getFullName(),
                user.getRole().name()
        ));
    }
}