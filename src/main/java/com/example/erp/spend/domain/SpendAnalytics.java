package com.example.erp.spend.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class SpendAnalytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalSpend;
    private BigDecimal maverickSpend;
    private BigDecimal savings;
    private Integer supplierCount;
    private Integer categoryCount;

    public Long getId() { return id; }
    public BigDecimal getTotalSpend() { return totalSpend; }
    public void setTotalSpend(BigDecimal totalSpend) { this.totalSpend = totalSpend; }
    public BigDecimal getMaverickSpend() { return maverickSpend; }
    public void setMaverickSpend(BigDecimal maverickSpend) { this.maverickSpend = maverickSpend; }
    public BigDecimal getSavings() { return savings; }
    public void setSavings(BigDecimal savings) { this.savings = savings; }
    public Integer getSupplierCount() { return supplierCount; }
    public void setSupplierCount(Integer supplierCount) { this.supplierCount = supplierCount; }
    public Integer getCategoryCount() { return categoryCount; }
    public void setCategoryCount(Integer categoryCount) { this.categoryCount = categoryCount; }
}

