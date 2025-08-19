package com.example.erp.procurement.service;

import com.example.erp.procurement.domain.POCollaboration;
import com.example.erp.procurement.domain.PurchaseOrder;
import com.example.erp.procurement.repository.POCollaborationRepository;
import com.example.erp.procurement.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class SupplierCollaborationService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final POCollaborationRepository poCollaborationRepository;

    public SupplierCollaborationService(PurchaseOrderRepository purchaseOrderRepository, POCollaborationRepository poCollaborationRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.poCollaborationRepository = poCollaborationRepository;
    }

    public POCollaboration enableCollaboration(Long poId) {
        PurchaseOrder po = purchaseOrderRepository.findById(poId).orElseThrow();
        POCollaboration collab = poCollaborationRepository.findByPurchaseOrder(po)
                .orElseGet(() -> {
                    POCollaboration c = new POCollaboration();
                    c.setPurchaseOrder(po);
                    return c;
                });
        collab.setMessagesEnabled(true);
        collab.setSharedPortalLink("/portal/po/" + po.getPoNumber());
        return poCollaborationRepository.save(collab);
    }
}

