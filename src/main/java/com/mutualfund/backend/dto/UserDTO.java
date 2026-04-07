package com.mutualfund.backend.dto;

import java.time.LocalDate;

public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String role;
    private String status;
    private String phone;
    private String pan;
    private String address;
    private String profilePic;
    private String riskAppetite;
    private String investmentHorizon;
    private Double sipBudget;
    private LocalDate joinDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPan() { return pan; }
    public void setPan(String pan) { this.pan = pan; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getProfilePic() { return profilePic; }
    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }

    public String getRiskAppetite() { return riskAppetite; }
    public void setRiskAppetite(String riskAppetite) { this.riskAppetite = riskAppetite; }

    public String getInvestmentHorizon() { return investmentHorizon; }
    public void setInvestmentHorizon(String investmentHorizon) { this.investmentHorizon = investmentHorizon; }

    public Double getSipBudget() { return sipBudget; }
    public void setSipBudget(Double sipBudget) { this.sipBudget = sipBudget; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
}