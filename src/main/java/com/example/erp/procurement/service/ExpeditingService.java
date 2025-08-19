package com.example.erp.procurement.service;

import com.example.erp.procurement.domain.ExpeditingWorkflow;
import com.example.erp.procurement.domain.POStatus;
import com.example.erp.procurement.domain.PurchaseOrder;
import com.example.erp.procurement.repository.ExpeditingWorkflowRepository;
import com.example.erp.procurement.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExpeditingService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ExpeditingWorkflowRepository expeditingWorkflowRepository;

    public ExpeditingService(PurchaseOrderRepository purchaseOrderRepository, ExpeditingWorkflowRepository expeditingWorkflowRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.expeditingWorkflowRepository = expeditingWorkflowRepository;
    }

    public ExpeditingWorkflow flagLate(Long poId, String user) {
        PurchaseOrder po = purchaseOrderRepository.findById(poId).orElseThrow();
        ExpeditingWorkflow wf = expeditingWorkflowRepository.findByPurchaseOrder(po)
                .orElseGet(() -> {
                    ExpeditingWorkflow w = new ExpeditingWorkflow();
                    w.setPurchaseOrder(po);
                    return w;
                });
        wf.setFlaggedLate(true);
        wf.setLastFollowUpBy(user);
        wf.setLastFollowUpAt(LocalDateTime.now());
        po.setStatus(POStatus.PENDING_APPROVAL); // could trigger escalation
        purchaseOrderRepository.save(po);
        return expeditingWorkflowRepository.save(wf);
    }

    public ExpeditingWorkflow recordSupplierResponse(Long poId, String response, String user) {
        PurchaseOrder po = purchaseOrderRepository.findById(poId).orElseThrow();
        ExpeditingWorkflow wf = expeditingWorkflowRepository.findByPurchaseOrder(po).orElseThrow();
        wf.setSupplierResponse(response);
        wf.setLastFollowUpBy(user);
        wf.setLastFollowUpAt(LocalDateTime.now());
        return expeditingWorkflowRepository.save(wf);
    }
}

