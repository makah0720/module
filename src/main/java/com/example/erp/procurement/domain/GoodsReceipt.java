package com.example.erp.procurement.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class GoodsReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private PurchaseOrder purchaseOrder;

    private String receiptNumber;
    private LocalDateTime receivedAt = LocalDateTime.now();
    private String receivedBy;
    private String site;
    private String remarks;

    private BigDecimal totalReceivedQuantity;

    public Long getId() { return id; }
    public PurchaseOrder getPurchaseOrder() { return purchaseOrder; }
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) { this.purchaseOrder = purchaseOrder; }
    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }
    public LocalDateTime getReceivedAt() { return receivedAt; }
    public void setReceivedAt(LocalDateTime receivedAt) { this.receivedAt = receivedAt; }
    public String getReceivedBy() { return receivedBy; }
    public void setReceivedBy(String receivedBy) { this.receivedBy = receivedBy; }
    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public BigDecimal getTotalReceivedQuantity() { return totalReceivedQuantity; }
    public void setTotalReceivedQuantity(BigDecimal totalReceivedQuantity) { this.totalReceivedQuantity = totalReceivedQuantity; }
}

