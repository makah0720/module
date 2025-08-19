package com.example.erp.procurement.service;

import com.example.erp.procurement.domain.ChangeOrder;
import com.example.erp.procurement.domain.POStatus;
import com.example.erp.procurement.domain.PurchaseOrder;
import com.example.erp.procurement.dto.ChangeOrderDTO;
import com.example.erp.procurement.repository.ChangeOrderRepository;
import com.example.erp.procurement.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChangeOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ChangeOrderRepository changeOrderRepository;

    public ChangeOrderService(PurchaseOrderRepository purchaseOrderRepository, ChangeOrderRepository changeOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.changeOrderRepository = changeOrderRepository;
    }

    public ChangeOrder create(Long poId, ChangeOrderDTO dto) {
        PurchaseOrder po = purchaseOrderRepository.findById(poId).orElseThrow();
        ChangeOrder co = new ChangeOrder();
        co.setPurchaseOrder(po);
        co.setReason(dto.reason);
        co.setRequestedBy(dto.requestedBy);
        co.setNotes(dto.notes);
        co.setRevisedTotalAmount(dto.revisedTotalAmount);
        po.setStatus(POStatus.UNDER_CHANGE);
        purchaseOrderRepository.save(po);
        return changeOrderRepository.save(co);
    }

    public List<ChangeOrder> list(Long poId) {
        PurchaseOrder po = purchaseOrderRepository.findById(poId).orElseThrow();
        return changeOrderRepository.findByPurchaseOrder(po);
    }
}

