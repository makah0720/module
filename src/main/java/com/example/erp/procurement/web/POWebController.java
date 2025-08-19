package com.example.erp.procurement.web;

import com.example.erp.procurement.domain.PurchaseOrder;
import com.example.erp.procurement.dto.PurchaseOrderDTO;
import com.example.erp.procurement.service.PurchaseOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/po")
public class POWebController {
    private final PurchaseOrderService purchaseOrderService;

    public POWebController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pos", purchaseOrderService.listAll());
        return "po/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("po", new PurchaseOrderDTO());
        return "po/create";
    }

    @PostMapping
    public String create(@ModelAttribute("po") @Valid PurchaseOrderDTO dto, BindingResult result) {
        if (result.hasErrors()) return "po/create";
        PurchaseOrder po = purchaseOrderService.create(dto);
        return "redirect:/po/" + po.getId();
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        PurchaseOrder po = purchaseOrderService.findById(id).orElseThrow();
        model.addAttribute("po", po);
        return "po/details";
    }
}

