package com.example.erp.procurement.web;

import com.example.erp.procurement.domain.PurchaseOrder;
import com.example.erp.procurement.dto.DeliveryScheduleDTO;
import com.example.erp.procurement.service.DeliverySchedulingService;
import com.example.erp.procurement.service.PurchaseOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/po/{id}/delivery")
public class DeliveryWebController {
    private final DeliverySchedulingService deliverySchedulingService;
    private final PurchaseOrderService purchaseOrderService;

    public DeliveryWebController(DeliverySchedulingService deliverySchedulingService, PurchaseOrderService purchaseOrderService) {
        this.deliverySchedulingService = deliverySchedulingService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public String page(@PathVariable Long id, Model model) {
        PurchaseOrder po = purchaseOrderService.findById(id).orElseThrow();
        model.addAttribute("po", po);
        model.addAttribute("schedules", deliverySchedulingService.listSchedules(id));
        model.addAttribute("form", new DeliveryScheduleDTO());
        return "po/delivery";
    }

    @PostMapping
    public String create(@PathVariable Long id, @ModelAttribute("form") DeliveryScheduleDTO form) {
        deliverySchedulingService.createSchedule(id, form);
        return "redirect:/po/" + id + "/delivery";
    }
}

