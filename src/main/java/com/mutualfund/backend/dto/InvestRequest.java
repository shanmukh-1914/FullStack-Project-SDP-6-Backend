package com.mutualfund.backend.dto;

public class InvestRequest {
    private Long fundId;
    private Double amount;

    public Long getFundId() { return fundId; }
    public void setFundId(Long fundId) { this.fundId = fundId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}