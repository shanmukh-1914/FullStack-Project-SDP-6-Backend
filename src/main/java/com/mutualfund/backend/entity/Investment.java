package com.mutualfund.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "investments")
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_id", nullable = false)
    private MutualFund fund;

    @Column(nullable = false)
    private Double amountInvested;

    @Column(nullable = false)
    private Double units;

    @Column(nullable = false)
    private Double navAtPurchase;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    private LocalDate investmentDate = LocalDate.now();

    public enum Status {
        ACTIVE, REDEEMED
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public MutualFund getFund() { return fund; }
    public void setFund(MutualFund fund) { this.fund = fund; }

    public Double getAmountInvested() { return amountInvested; }
    public void setAmountInvested(Double amountInvested) { this.amountInvested = amountInvested; }

    public Double getUnits() { return units; }
    public void setUnits(Double units) { this.units = units; }

    public Double getNavAtPurchase() { return navAtPurchase; }
    public void setNavAtPurchase(Double navAtPurchase) { this.navAtPurchase = navAtPurchase; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDate getInvestmentDate() { return investmentDate; }
    public void setInvestmentDate(LocalDate investmentDate) { this.investmentDate = investmentDate; }
}