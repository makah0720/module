package com.example.erp.spend.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class SavingsTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String initiative;
    private String owner;
    private LocalDate targetDate;
    private BigDecimal targetAmount;
    private BigDecimal realizedAmount;

    public Long getId() { return id; }
    public String getInitiative() { return initiative; }
    public void setInitiative(String initiative) { this.initiative = initiative; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public LocalDate getTargetDate() { return targetDate; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }
    public BigDecimal getRealizedAmount() { return realizedAmount; }
    public void setRealizedAmount(BigDecimal realizedAmount) { this.realizedAmount = realizedAmount; }
}

