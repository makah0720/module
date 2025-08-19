package com.example.erp.procurement.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ExpeditingWorkflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private PurchaseOrder purchaseOrder;

    private boolean flaggedLate;
    private LocalDateTime lastFollowUpAt;
    private String lastFollowUpBy;
    private String supplierResponse;

    public Long getId() { return id; }
    public PurchaseOrder getPurchaseOrder() { return purchaseOrder; }
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) { this.purchaseOrder = purchaseOrder; }
    public boolean isFlaggedLate() { return flaggedLate; }
    public void setFlaggedLate(boolean flaggedLate) { this.flaggedLate = flaggedLate; }
    public LocalDateTime getLastFollowUpAt() { return lastFollowUpAt; }
    public void setLastFollowUpAt(LocalDateTime lastFollowUpAt) { this.lastFollowUpAt = lastFollowUpAt; }
    public String getLastFollowUpBy() { return lastFollowUpBy; }
    public void setLastFollowUpBy(String lastFollowUpBy) { this.lastFollowUpBy = lastFollowUpBy; }
    public String getSupplierResponse() { return supplierResponse; }
    public void setSupplierResponse(String supplierResponse) { this.supplierResponse = supplierResponse; }
}

