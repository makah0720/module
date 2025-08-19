package com.example.erp.procurement.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String poNumber;

    private String supplierId;

    private LocalDate orderDate;

    private LocalDate expectedDeliveryDate;

    @Enumerated(EnumType.STRING)
    private POStatus status = POStatus.DRAFT;

    private String currency;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    private String createdBy;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderLine> lines = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliverySchedule> deliverySchedules = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChangeOrder> changeOrders = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GoodsReceipt> goodsReceipts = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplierAcknowledgment> acknowledgments = new ArrayList<>();

    @OneToOne(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private ExpeditingWorkflow expeditingWorkflow;

    @OneToOne(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private POCollaboration collaboration;

    public Long getId() { return id; }
    public String getPoNumber() { return poNumber; }
    public void setPoNumber(String poNumber) { this.poNumber = poNumber; }
    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }
    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public LocalDate getExpectedDeliveryDate() { return expectedDeliveryDate; }
    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) { this.expectedDeliveryDate = expectedDeliveryDate; }
    public POStatus getStatus() { return status; }
    public void setStatus(POStatus status) { this.status = status; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public List<PurchaseOrderLine> getLines() { return lines; }
    public void setLines(List<PurchaseOrderLine> lines) { this.lines = lines; }
    public List<DeliverySchedule> getDeliverySchedules() { return deliverySchedules; }
    public void setDeliverySchedules(List<DeliverySchedule> deliverySchedules) { this.deliverySchedules = deliverySchedules; }
    public List<ChangeOrder> getChangeOrders() { return changeOrders; }
    public void setChangeOrders(List<ChangeOrder> changeOrders) { this.changeOrders = changeOrders; }
    public List<GoodsReceipt> getGoodsReceipts() { return goodsReceipts; }
    public void setGoodsReceipts(List<GoodsReceipt> goodsReceipts) { this.goodsReceipts = goodsReceipts; }
    public List<SupplierAcknowledgment> getAcknowledgments() { return acknowledgments; }
    public void setAcknowledgments(List<SupplierAcknowledgment> acknowledgments) { this.acknowledgments = acknowledgments; }
    public ExpeditingWorkflow getExpeditingWorkflow() { return expeditingWorkflow; }
    public void setExpeditingWorkflow(ExpeditingWorkflow expeditingWorkflow) { this.expeditingWorkflow = expeditingWorkflow; }
    public POCollaboration getCollaboration() { return collaboration; }
    public void setCollaboration(POCollaboration collaboration) { this.collaboration = collaboration; }
}

