package com.example.erp.procurement.web;

import com.example.erp.procurement.domain.PurchaseOrder;
import com.example.erp.procurement.service.PurchaseOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/po/{id}/approval")
public class WorkflowWebController {
    private final PurchaseOrderService purchaseOrderService;

    public WorkflowWebController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public String approval(@PathVariable Long id, Model model) {
        PurchaseOrder po = purchaseOrderService.findById(id).orElseThrow();
        model.addAttribute("po", po);
        return "po/approval";
    }

    @PostMapping("/submit")
    public String submit(@PathVariable Long id) {
        purchaseOrderService.submitForApproval(id);
        return "redirect:/po/" + id;
    }

    @PostMapping("/approve")
    public String approve(@PathVariable Long id) {
        purchaseOrderService.approve(id, "approver");
        return "redirect:/po/" + id;
    }

    @PostMapping("/reject")
    public String reject(@PathVariable Long id, @RequestParam String reason) {
        purchaseOrderService.reject(id, "approver", reason);
        return "redirect:/po/" + id;
    }
}

