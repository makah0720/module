package com.example.erp.procurement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PurchaseOrderLineDTO {
    public Long id;
    @NotBlank
    public String itemCode;
    public String description;
    @NotNull
    public BigDecimal quantity;
    @NotNull
    public BigDecimal unitPrice;
    public String uom;
}

