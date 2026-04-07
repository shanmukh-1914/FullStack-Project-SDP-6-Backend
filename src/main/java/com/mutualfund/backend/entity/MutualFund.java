package com.mutualfund.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mutual_funds")
public class MutualFund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RiskLevel risk;

    @Column(nullable = false)
    private Double nav;

    private String aum;
    private String expenseRatio;
    private String minInvest;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double returns1yr;
    private Double returns3yr;
    private Double returns5yr;
    private String fundManager;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum RiskLevel {
        Low, Medium, High
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public RiskLevel getRisk() { return risk; }
    public void setRisk(RiskLevel risk) { this.risk = risk; }

    public Double getNav() { return nav; }
    public void setNav(Double nav) { this.nav = nav; }

    public String getAum() { return aum; }
    public void setAum(String aum) { this.aum = aum; }

    public String getExpenseRatio() { return expenseRatio; }
    public void setExpenseRatio(String expenseRatio) { this.expenseRatio = expenseRatio; }

    public String getMinInvest() { return minInvest; }
    public void setMinInvest(String minInvest) { this.minInvest = minInvest; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getReturns1yr() { return returns1yr; }
    public void setReturns1yr(Double returns1yr) { this.returns1yr = returns1yr; }

    public Double getReturns3yr() { return returns3yr; }
    public void setReturns3yr(Double returns3yr) { this.returns3yr = returns3yr; }

    public Double getReturns5yr() { return returns5yr; }
    public void setReturns5yr(Double returns5yr) { this.returns5yr = returns5yr; }

    public String getFundManager() { return fundManager; }
    public void setFundManager(String fundManager) { this.fundManager = fundManager; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}