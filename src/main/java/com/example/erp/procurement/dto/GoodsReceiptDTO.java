package com.example.erp.procurement.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GoodsReceiptDTO {
    public Long id;
    public String receiptNumber;
    public LocalDateTime receivedAt;
    public String receivedBy;
    public String site;
    public String remarks;
    public BigDecimal totalReceivedQuantity;
}

