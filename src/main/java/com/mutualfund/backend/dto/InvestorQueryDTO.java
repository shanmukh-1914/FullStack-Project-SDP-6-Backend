package com.mutualfund.backend.dto;

import java.time.LocalDateTime;

public class InvestorQueryDTO {
    private Long id;
    private Long investorId;
    private String investorName;
    private String investorEmail;
    private String queryText;
    private String replyText;
    private String repliedByName;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime repliedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getInvestorId() { return investorId; }
    public void setInvestorId(Long investorId) { this.investorId = investorId; }

    public String getInvestorName() { return investorName; }
    public void setInvestorName(String investorName) { this.investorName = investorName; }

    public String getInvestorEmail() { return investorEmail; }
    public void setInvestorEmail(String investorEmail) { this.investorEmail = investorEmail; }

    public String getQueryText() { return queryText; }
    public void setQueryText(String queryText) { this.queryText = queryText; }

    public String getReplyText() { return replyText; }
    public void setReplyText(String replyText) { this.replyText = replyText; }

    public String getRepliedByName() { return repliedByName; }
    public void setRepliedByName(String repliedByName) { this.repliedByName = repliedByName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getRepliedAt() { return repliedAt; }
    public void setRepliedAt(LocalDateTime repliedAt) { this.repliedAt = repliedAt; }
}