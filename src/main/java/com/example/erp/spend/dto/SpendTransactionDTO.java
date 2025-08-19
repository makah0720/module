package com.example.erp.spend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SpendTransactionDTO {
    public Long id;
    public String supplierId;
    public String supplierName;
    public String categoryCode;
    public String categoryName;
    public LocalDate transactionDate;
    public String currency;
    public BigDecimal amount;
    public String costCenter;
    public boolean approved;
    public boolean catalog;
}

