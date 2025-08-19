package com.example.erp.spend.web;

import com.example.erp.spend.domain.SavingsTracking;
import com.example.erp.spend.dto.SavingsTrackingDTO;
import com.example.erp.spend.service.SavingsTrackingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/spend/savings")
public class SavingsController {
    private final SavingsTrackingService savingsTrackingService;

    public SavingsController(SavingsTrackingService savingsTrackingService) {
        this.savingsTrackingService = savingsTrackingService;
    }

    @GetMapping
    public String page(Model model) {
        model.addAttribute("title", "Savings Tracking");
        model.addAttribute("items", savingsTrackingService.list());
        model.addAttribute("form", new SavingsTrackingDTO());
        return "spend/savings";
    }

    @PostMapping
    public String create(@ModelAttribute("form") SavingsTrackingDTO dto) {
        SavingsTracking s = savingsTrackingService.create(dto);
        return "redirect:/spend/savings";
    }
}

