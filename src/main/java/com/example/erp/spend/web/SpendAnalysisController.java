package com.example.erp.spend.web;

import com.example.erp.spend.dto.SpendAnalyticsDTO;
import com.example.erp.spend.service.SpendAnalysisService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/spend")
public class SpendAnalysisController {
    private final SpendAnalysisService spendAnalysisService;

    public SpendAnalysisController(SpendAnalysisService spendAnalysisService) {
        this.spendAnalysisService = spendAnalysisService;
    }

    @GetMapping
    public String dashboard(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                            Model model) {
        if (start == null) start = LocalDate.now().minusMonths(1);
        if (end == null) end = LocalDate.now();
        SpendAnalyticsDTO analytics = spendAnalysisService.compute(start, end);
        model.addAttribute("title", "Spend Dashboard");
        model.addAttribute("analytics", analytics);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        return "spend/dashboard";
    }
}

