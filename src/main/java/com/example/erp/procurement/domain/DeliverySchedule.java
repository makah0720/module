package com.example.erp.procurement.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DeliverySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private PurchaseOrder purchaseOrder;

    private LocalDate scheduledDate;
    private String site;
    private String deliveryWindow; // e.g., AM/PM or time range
    private boolean confirmed;

    public Long getId() { return id; }
    public PurchaseOrder getPurchaseOrder() { return purchaseOrder; }
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) { this.purchaseOrder = purchaseOrder; }
    public LocalDate getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDate scheduledDate) { this.scheduledDate = scheduledDate; }
    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }
    public String getDeliveryWindow() { return deliveryWindow; }
    public void setDeliveryWindow(String deliveryWindow) { this.deliveryWindow = deliveryWindow; }
    public boolean isConfirmed() { return confirmed; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }
}

