package com.example.erp.procurement.dto;

import java.time.LocalDateTime;

public class SupplierAcknowledgmentDTO {
    public Long id;
    public boolean accepted;
    public String supplierContact;
    public LocalDateTime acknowledgedAt;
    public String comments;
}

