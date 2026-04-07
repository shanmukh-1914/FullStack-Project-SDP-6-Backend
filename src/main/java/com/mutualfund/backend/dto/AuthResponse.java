package com.mutualfund.backend.dto;

public class AuthResponse {
    private Long id;
    private String token;
    private String email;
    private String fullName;
    private String role;

    public AuthResponse(Long id, String token, String email, String fullName, String role) {
        this.id = id;
        this.token = token;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}