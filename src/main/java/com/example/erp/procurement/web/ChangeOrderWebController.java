package com.example.erp.procurement.web;

import com.example.erp.procurement.domain.PurchaseOrder;
import com.example.erp.procurement.dto.ChangeOrderDTO;
import com.example.erp.procurement.service.ChangeOrderService;
import com.example.erp.procurement.service.PurchaseOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/po/{id}/change")
public class ChangeOrderWebController {
    private final ChangeOrderService changeOrderService;
    private final PurchaseOrderService purchaseOrderService;

    public ChangeOrderWebController(ChangeOrderService changeOrderService, PurchaseOrderService purchaseOrderService) {
        this.changeOrderService = changeOrderService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public String page(@PathVariable Long id, Model model) {
        PurchaseOrder po = purchaseOrderService.findById(id).orElseThrow();
        model.addAttribute("po", po);
        model.addAttribute("changes", changeOrderService.list(id));
        model.addAttribute("form", new ChangeOrderDTO());
        return "po/change";
    }

    @PostMapping
    public String create(@PathVariable Long id, @ModelAttribute("form") ChangeOrderDTO form) {
        changeOrderService.create(id, form);
        return "redirect:/po/" + id + "/change";
    }
}

