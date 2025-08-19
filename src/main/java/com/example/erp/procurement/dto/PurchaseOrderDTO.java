package com.example.erp.procurement.dto;

import com.example.erp.procurement.domain.POStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PurchaseOrderDTO {
    public Long id;
    @NotBlank
    public String poNumber;
    @NotBlank
    public String supplierId;
    @NotNull
    public LocalDate orderDate;
    public LocalDate expectedDeliveryDate;
    public POStatus status;
    public String currency;
    public BigDecimal totalAmount;
    public String createdBy;
    public List<PurchaseOrderLineDTO> lines;
}

