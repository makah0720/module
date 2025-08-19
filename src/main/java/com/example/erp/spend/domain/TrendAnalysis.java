package com.example.erp.spend.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class TrendAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String period; // YYYY-MM
    private BigDecimal totalSpend;

    public Long getId() { return id; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public BigDecimal getTotalSpend() { return totalSpend; }
    public void setTotalSpend(BigDecimal totalSpend) { this.totalSpend = totalSpend; }
}

