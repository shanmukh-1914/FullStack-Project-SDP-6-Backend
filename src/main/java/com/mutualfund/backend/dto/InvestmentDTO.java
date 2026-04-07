package com.mutualfund.backend.dto;

import java.time.LocalDate;

public class InvestmentDTO {
    private Long id;
    private Long userId;
    private Long fundId;
    private String fundName;
    private String category;
    private String risk;
    private Double amountInvested;
    private Double units;
    private Double navAtPurchase;
    private Double currentValue;
    private Double gains;
    private Double gainPercent;
    private String status;
    private LocalDate investmentDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getFundId() { return fundId; }
    public void setFundId(Long fundId) { this.fundId = fundId; }

    public String getFundName() { return fundName; }
    public void setFundName(String fundName) { this.fundName = fundName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getRisk() { return risk; }
    public void setRisk(String risk) { this.risk = risk; }

    public Double getAmountInvested() { return amountInvested; }
    public void setAmountInvested(Double amountInvested) { this.amountInvested = amountInvested; }

    public Double getUnits() { return units; }
    public void setUnits(Double units) { this.units = units; }

    public Double getNavAtPurchase() { return navAtPurchase; }
    public void setNavAtPurchase(Double navAtPurchase) { this.navAtPurchase = navAtPurchase; }

    public Double getCurrentValue() { return currentValue; }
    public void setCurrentValue(Double currentValue) { this.currentValue = currentValue; }

    public Double getGains() { return gains; }
    public void setGains(Double gains) { this.gains = gains; }

    public Double getGainPercent() { return gainPercent; }
    public void setGainPercent(Double gainPercent) { this.gainPercent = gainPercent; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getInvestmentDate() { return investmentDate; }
    public void setInvestmentDate(LocalDate investmentDate) { this.investmentDate = investmentDate; }
}