package com.example.erp.procurement.web;

import com.example.erp.procurement.repository.ExpeditingWorkflowRepository;
import com.example.erp.procurement.service.ExpeditingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/po/expediting")
public class ExpeditingWebController {
    private final ExpeditingWorkflowRepository expeditingWorkflowRepository;
    private final ExpeditingService expeditingService;

    public ExpeditingWebController(ExpeditingWorkflowRepository expeditingWorkflowRepository, ExpeditingService expeditingService) {
        this.expeditingWorkflowRepository = expeditingWorkflowRepository;
        this.expeditingService = expeditingService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("late", expeditingWorkflowRepository.findByFlaggedLateTrue());
        return "po/expediting";
    }
}

