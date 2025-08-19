package com.example.erp.spend.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class MaverickSpend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String supplierId;
    private String reason; // e.g., Non-PO, non-catalog
    private BigDecimal amount;

    public Long getId() { return id; }
    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}

