package com.example.erp.spend.web;

import com.example.erp.spend.service.SpendCubeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/spend/analytics")
public class SpendCubeController {
    private final SpendCubeService spendCubeService;

    public SpendCubeController(SpendCubeService spendCubeService) {
        this.spendCubeService = spendCubeService;
    }

    @GetMapping
    public String analytics(@RequestParam(defaultValue = "YEAR|CATEGORY|SUPPLIER") String key, Model model) {
        model.addAttribute("title", "Spend Analytics");
        model.addAttribute("key", key);
        model.addAttribute("rows", spendCubeService.queryByKey(key));
        return "spend/analytics";
    }
}

