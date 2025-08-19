package com.example.erp.procurement.web;

import com.example.erp.procurement.domain.PurchaseOrder;
import com.example.erp.procurement.service.PurchaseOrderService;
import com.example.erp.procurement.service.SupplierCollaborationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/po/{id}/collaboration")
public class CollaborationWebController {
    private final SupplierCollaborationService supplierCollaborationService;
    private final PurchaseOrderService purchaseOrderService;

    public CollaborationWebController(SupplierCollaborationService supplierCollaborationService, PurchaseOrderService purchaseOrderService) {
        this.supplierCollaborationService = supplierCollaborationService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public String page(@PathVariable Long id, Model model) {
        PurchaseOrder po = purchaseOrderService.findById(id).orElseThrow();
        model.addAttribute("po", po);
        return "po/collaboration";
    }

    @PostMapping("/enable")
    public String enable(@PathVariable Long id) {
        supplierCollaborationService.enableCollaboration(id);
        return "redirect:/po/" + id + "/collaboration";
    }
}

