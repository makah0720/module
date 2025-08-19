package com.example.erp.procurement.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class POCollaboration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private PurchaseOrder purchaseOrder;

    private String sharedPortalLink;
    private LocalDateTime invitedAt = LocalDateTime.now();
    private boolean supplierViewed;
    private boolean messagesEnabled;

    public Long getId() { return id; }
    public PurchaseOrder getPurchaseOrder() { return purchaseOrder; }
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) { this.purchaseOrder = purchaseOrder; }
    public String getSharedPortalLink() { return sharedPortalLink; }
    public void setSharedPortalLink(String sharedPortalLink) { this.sharedPortalLink = sharedPortalLink; }
    public LocalDateTime getInvitedAt() { return invitedAt; }
    public void setInvitedAt(LocalDateTime invitedAt) { this.invitedAt = invitedAt; }
    public boolean isSupplierViewed() { return supplierViewed; }
    public void setSupplierViewed(boolean supplierViewed) { this.supplierViewed = supplierViewed; }
    public boolean isMessagesEnabled() { return messagesEnabled; }
    public void setMessagesEnabled(boolean messagesEnabled) { this.messagesEnabled = messagesEnabled; }
}

