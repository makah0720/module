package com.example.erp.procurement.web;

import com.example.erp.procurement.service.AnalyticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/po/analytics")
public class AnalyticsWebController {
    private final AnalyticsService analyticsService;

    public AnalyticsWebController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    public String page(Model model) {
        model.addAttribute("statusCounts", analyticsService.statusCounts());
        model.addAttribute("totalSpend", analyticsService.totalSpend());
        return "po/analytics";
    }
}

