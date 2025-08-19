package com.example.erp.spend.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/spend/reporting")
public class SpendReportingPageController {
    @GetMapping
    public String page(Model model) {
        model.addAttribute("title", "Spend Reporting");
        return "spend/reporting";
    }
}

