package com.example.erp.procurement.dto;

import java.time.LocalDate;

public class DeliveryScheduleDTO {
    public Long id;
    public LocalDate scheduledDate;
    public String site;
    public String deliveryWindow;
    public boolean confirmed;
}

