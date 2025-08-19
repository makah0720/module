package com.example.erp.procurement.web;

import com.example.erp.procurement.domain.PurchaseOrder;
import com.example.erp.procurement.service.ThreeWayMatchingService;
import com.example.erp.procurement.service.PurchaseOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/po/{id}/matching")
public class MatchingWebController {
    private final ThreeWayMatchingService threeWayMatchingService;
    private final PurchaseOrderService purchaseOrderService;

    public MatchingWebController(ThreeWayMatchingService threeWayMatchingService, PurchaseOrderService purchaseOrderService) {
        this.threeWayMatchingService = threeWayMatchingService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public String matching(@PathVariable Long id, Model model) {
        PurchaseOrder po = purchaseOrderService.findById(id).orElseThrow();
        model.addAttribute("po", po);
        model.addAttribute("result", null);
        return "po/matching";
    }

    @PostMapping
    public String doMatch(@PathVariable Long id, @RequestParam BigDecimal invoiceQty, Model model) {
        PurchaseOrder po = purchaseOrderService.findById(id).orElseThrow();
        boolean ok = threeWayMatchingService.matchByQuantity(id, invoiceQty);
        model.addAttribute("po", po);
        model.addAttribute("result", ok);
        return "po/matching";
    }
}

