package com.example.erp.procurement.service;

import com.example.erp.procurement.domain.*;
import com.example.erp.procurement.dto.*;
import com.example.erp.procurement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final DeliveryScheduleRepository deliveryScheduleRepository;
    private final ChangeOrderRepository changeOrderRepository;
    private final GoodsReceiptRepository goodsReceiptRepository;
    private final SupplierAcknowledgmentRepository supplierAcknowledgmentRepository;
    private final ExpeditingWorkflowRepository expeditingWorkflowRepository;
    private final POCollaborationRepository poCollaborationRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,
                                DeliveryScheduleRepository deliveryScheduleRepository,
                                ChangeOrderRepository changeOrderRepository,
                                GoodsReceiptRepository goodsReceiptRepository,
                                SupplierAcknowledgmentRepository supplierAcknowledgmentRepository,
                                ExpeditingWorkflowRepository expeditingWorkflowRepository,
                                POCollaborationRepository poCollaborationRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.deliveryScheduleRepository = deliveryScheduleRepository;
        this.changeOrderRepository = changeOrderRepository;
        this.goodsReceiptRepository = goodsReceiptRepository;
        this.supplierAcknowledgmentRepository = supplierAcknowledgmentRepository;
        this.expeditingWorkflowRepository = expeditingWorkflowRepository;
        this.poCollaborationRepository = poCollaborationRepository;
    }

    public PurchaseOrder create(PurchaseOrderDTO dto) {
        PurchaseOrder po = new PurchaseOrder();
        po.setPoNumber(dto.poNumber);
        po.setSupplierId(dto.supplierId);
        po.setOrderDate(dto.orderDate);
        po.setExpectedDeliveryDate(dto.expectedDeliveryDate);
        po.setCurrency(dto.currency);
        po.setCreatedBy(dto.createdBy);
        if (dto.lines != null) {
            po.setLines(dto.lines.stream().map(lineDto -> {
                PurchaseOrderLine line = new PurchaseOrderLine();
                line.setItemCode(lineDto.itemCode);
                line.setDescription(lineDto.description);
                line.setQuantity(lineDto.quantity);
                line.setUnitPrice(lineDto.unitPrice);
                line.setUom(lineDto.uom);
                line.setPurchaseOrder(po);
                return line;
            }).collect(Collectors.toList()));
        }
        recalcTotal(po);
        return purchaseOrderRepository.save(po);
    }

    public PurchaseOrder submitForApproval(Long poId) {
        PurchaseOrder po = requirePo(poId);
        ensureStatus(po, POStatus.DRAFT);
        po.setStatus(POStatus.PENDING_APPROVAL);
        return purchaseOrderRepository.save(po);
    }

    public PurchaseOrder approve(Long poId, String approver) {
        PurchaseOrder po = requirePo(poId);
        ensureStatus(po, POStatus.PENDING_APPROVAL);
        po.setStatus(POStatus.APPROVED);
        return purchaseOrderRepository.save(po);
    }

    public PurchaseOrder reject(Long poId, String approver, String reason) {
        PurchaseOrder po = requirePo(poId);
        ensureStatus(po, POStatus.PENDING_APPROVAL);
        po.setStatus(POStatus.REJECTED);
        return purchaseOrderRepository.save(po);
    }

    public PurchaseOrder transmitToSupplier(Long poId) {
        PurchaseOrder po = requirePo(poId);
        ensureStatus(po, POStatus.APPROVED);
        po.setStatus(POStatus.SENT_TO_SUPPLIER);
        // initialize collaboration and expediting
        POCollaboration collab = new POCollaboration();
        collab.setPurchaseOrder(po);
        collab.setSharedPortalLink("/portal/po/" + po.getPoNumber());
        po.setCollaboration(collab);
        poCollaborationRepository.save(collab);

        ExpeditingWorkflow exp = new ExpeditingWorkflow();
        exp.setPurchaseOrder(po);
        exp.setFlaggedLate(false);
        po.setExpeditingWorkflow(exp);
        expeditingWorkflowRepository.save(exp);

        return purchaseOrderRepository.save(po);
    }

    public SupplierAcknowledgment acknowledge(Long poId, boolean accepted, String contact, String comments) {
        PurchaseOrder po = requirePo(poId);
        ensureStatusOneOf(po, POStatus.SENT_TO_SUPPLIER, POStatus.APPROVED);
        SupplierAcknowledgment ack = new SupplierAcknowledgment();
        ack.setPurchaseOrder(po);
        ack.setAccepted(accepted);
        ack.setSupplierContact(contact);
        ack.setComments(comments);
        if (accepted) {
            po.setStatus(POStatus.ACKNOWLEDGED_BY_SUPPLIER);
            purchaseOrderRepository.save(po);
        }
        return supplierAcknowledgmentRepository.save(ack);
    }

    public GoodsReceipt recordGoodsReceipt(Long poId, GoodsReceiptDTO dto) {
        PurchaseOrder po = requirePo(poId);
        GoodsReceipt gr = new GoodsReceipt();
        gr.setPurchaseOrder(po);
        gr.setReceiptNumber(dto.receiptNumber);
        gr.setReceivedAt(dto.receivedAt);
        gr.setReceivedBy(dto.receivedBy);
        gr.setSite(dto.site);
        gr.setRemarks(dto.remarks);
        gr.setTotalReceivedQuantity(dto.totalReceivedQuantity);
        goodsReceiptRepository.save(gr);

        // Update status heuristically
        po.setStatus(POStatus.PARTIALLY_RECEIVED);
        return gr;
    }

    public ChangeOrder requestChange(Long poId, ChangeOrderDTO dto) {
        PurchaseOrder po = requirePo(poId);
        ensureNotStatus(po, POStatus.CLOSED);
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

    public ChangeOrder approveChange(Long changeOrderId, String approver) {
        ChangeOrder co = changeOrderRepository.findById(changeOrderId).orElseThrow();
        co.setApproved(true);
        co.setApprovedBy(approver);
        if (co.getRevisedTotalAmount() != null) {
            PurchaseOrder po = co.getPurchaseOrder();
            po.setTotalAmount(co.getRevisedTotalAmount());
            po.setStatus(POStatus.APPROVED);
            purchaseOrderRepository.save(po);
        }
        return changeOrderRepository.save(co);
    }

    public DeliverySchedule scheduleDelivery(Long poId, DeliveryScheduleDTO dto) {
        PurchaseOrder po = requirePo(poId);
        DeliverySchedule ds = new DeliverySchedule();
        ds.setPurchaseOrder(po);
        ds.setScheduledDate(dto.scheduledDate);
        ds.setSite(dto.site);
        ds.setDeliveryWindow(dto.deliveryWindow);
        ds.setConfirmed(dto.confirmed);
        return deliveryScheduleRepository.save(ds);
    }

    public List<PurchaseOrder> listAll() {
        return purchaseOrderRepository.findAll();
    }

    public Optional<PurchaseOrder> findById(Long id) {
        return purchaseOrderRepository.findById(id);
    }

    public PurchaseOrder close(Long poId) {
        PurchaseOrder po = requirePo(poId);
        po.setStatus(POStatus.CLOSED);
        return purchaseOrderRepository.save(po);
    }

    private PurchaseOrder requirePo(Long poId) {
        return purchaseOrderRepository.findById(poId)
                .orElseThrow(() -> new IllegalArgumentException("PurchaseOrder not found: " + poId));
    }

    private void ensureStatus(PurchaseOrder po, POStatus expected) {
        if (po.getStatus() != expected) {
            throw new IllegalStateException("Invalid status: expected " + expected + " but was " + po.getStatus());
        }
    }

    private void ensureStatusOneOf(PurchaseOrder po, POStatus... statuses) {
        for (POStatus s : statuses) {
            if (po.getStatus() == s) return;
        }
        throw new IllegalStateException("Invalid status for operation: " + po.getStatus());
    }

    private void ensureNotStatus(PurchaseOrder po, POStatus forbidden) {
        if (po.getStatus() == forbidden) throw new IllegalStateException("Operation not allowed in status " + forbidden);
    }

    private void recalcTotal(PurchaseOrder po) {
        BigDecimal total = po.getLines().stream()
                .map(l -> l.getUnitPrice().multiply(l.getQuantity()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        po.setTotalAmount(total);
    }
}

