package com.example.erp.spend.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class SpendCube {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dimensionKey; // e.g., YEAR|CATEGORY|SUPPLIER
    private String dimensionValue; // e.g., 2025|IT|SUP-1
    private BigDecimal amount;

    public Long getId() { return id; }
    public String getDimensionKey() { return dimensionKey; }
    public void setDimensionKey(String dimensionKey) { this.dimensionKey = dimensionKey; }
    public String getDimensionValue() { return dimensionValue; }
    public void setDimensionValue(String dimensionValue) { this.dimensionValue = dimensionValue; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}

