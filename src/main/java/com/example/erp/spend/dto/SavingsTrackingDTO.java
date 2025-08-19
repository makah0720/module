package com.example.erp.spend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingsTrackingDTO {
    public Long id;
    public String initiative;
    public String owner;
    public LocalDate targetDate;
    public BigDecimal targetAmount;
    public BigDecimal realizedAmount;
}

