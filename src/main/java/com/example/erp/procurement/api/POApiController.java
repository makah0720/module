package com.example.erp.procurement.api;

import com.example.erp.procurement.domain.ChangeOrder;
import com.example.erp.procurement.domain.DeliverySchedule;
import com.example.erp.procurement.domain.GoodsReceipt;
import com.example.erp.procurement.domain.PurchaseOrder;
import com.example.erp.procurement.dto.*;
import com.example.erp.procurement.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/po")
public class POApiController {
    private final PurchaseOrderService purchaseOrderService;
    private final DeliverySchedulingService deliverySchedulingService;
    private final ChangeOrderService changeOrderService;
    private final ExpeditingService expeditingService;
    private final SupplierCollaborationService supplierCollaborationService;
    private final ThreeWayMatchingService threeWayMatchingService;

    public POApiController(PurchaseOrderService purchaseOrderService,
                           DeliverySchedulingService deliverySchedulingService,
                           ChangeOrderService changeOrderService,
                           ExpeditingService expeditingService,
                           SupplierCollaborationService supplierCollaborationService,
                           ThreeWayMatchingService threeWayMatchingService) {
        this.purchaseOrderService = purchaseOrderService;
        this.deliverySchedulingService = deliverySchedulingService;
        this.changeOrderService = changeOrderService;
        this.expeditingService = expeditingService;
        this.supplierCollaborationService = supplierCollaborationService;
        this.threeWayMatchingService = threeWayMatchingService;
    }

    @PostMapping
    public PurchaseOrder create(@RequestBody PurchaseOrderDTO dto) {
        return purchaseOrderService.create(dto);
    }

    @GetMapping
    public List<PurchaseOrder> list() { return purchaseOrderService.listAll(); }

    @PostMapping("/{id}/submit")
    public PurchaseOrder submit(@PathVariable Long id) { return purchaseOrderService.submitForApproval(id); }

    @PostMapping("/{id}/approve")
    public PurchaseOrder approve(@PathVariable Long id, @RequestParam String approver) { return purchaseOrderService.approve(id, approver); }

    @PostMapping("/{id}/reject")
    public PurchaseOrder reject(@PathVariable Long id, @RequestParam String approver, @RequestParam String reason) { return purchaseOrderService.reject(id, approver, reason); }

    @PostMapping("/{id}/transmit")
    public PurchaseOrder transmit(@PathVariable Long id) { return purchaseOrderService.transmitToSupplier(id); }

    @PostMapping("/{id}/acknowledge")
    public ResponseEntity<?> ack(@PathVariable Long id, @RequestParam boolean accepted, @RequestParam String contact, @RequestParam(required = false) String comments) {
        return ResponseEntity.ok(purchaseOrderService.acknowledge(id, accepted, contact, comments));
    }

    @PostMapping("/{id}/goods-receipt")
    public GoodsReceipt goodsReceipt(@PathVariable Long id, @RequestBody GoodsReceiptDTO dto) {
        return purchaseOrderService.recordGoodsReceipt(id, dto);
    }

    @PostMapping("/{id}/change-order")
    public ChangeOrder changeOrder(@PathVariable Long id, @RequestBody ChangeOrderDTO dto) { return changeOrderService.create(id, dto); }

    @PostMapping("/change-order/{coId}/approve")
    public ChangeOrder approveChange(@PathVariable Long coId, @RequestParam String approver) { return purchaseOrderService.approveChange(coId, approver); }

    @PostMapping("/{id}/schedule")
    public DeliverySchedule schedule(@PathVariable Long id, @RequestBody DeliveryScheduleDTO dto) { return deliverySchedulingService.createSchedule(id, dto); }

    @PostMapping("/{id}/expedite")
    public ResponseEntity<?> expedite(@PathVariable Long id, @RequestParam String user) { return ResponseEntity.ok(expeditingService.flagLate(id, user)); }

    @PostMapping("/{id}/collaboration/enable")
    public ResponseEntity<?> enableCollab(@PathVariable Long id) { return ResponseEntity.ok(supplierCollaborationService.enableCollaboration(id)); }

    @GetMapping("/{id}/match")
    public ResponseEntity<?> match(@PathVariable Long id, @RequestParam BigDecimal invoiceQty) {
        boolean ok = threeWayMatchingService.matchByQuantity(id, invoiceQty);
        return ResponseEntity.ok(ok);
    }
}

