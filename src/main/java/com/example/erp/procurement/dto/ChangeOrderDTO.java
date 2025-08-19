package com.example.erp.procurement.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ChangeOrderDTO {
    public Long id;
    public String reason;
    public String requestedBy;
    public LocalDateTime requestedAt;
    public boolean approved;
    public String approvedBy;
    public LocalDateTime approvedAt;
    public BigDecimal revisedTotalAmount;
    public String notes;
}

