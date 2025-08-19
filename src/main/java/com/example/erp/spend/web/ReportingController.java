package com.example.erp.spend.web;

import com.example.erp.spend.dto.SpendReportDTO;
import com.example.erp.spend.service.SpendReportingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/spend/reporting")
public class ReportingController {
    private final SpendReportingService spendReportingService;

    public ReportingController(SpendReportingService spendReportingService) {
        this.spendReportingService = spendReportingService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> export(@RequestBody SpendReportDTO dto) {
        byte[] content = spendReportingService.export(dto);
        String filename = dto.title + ("PDF".equalsIgnoreCase(dto.format) ? ".pdf" : ".xlsx");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType("PDF".equalsIgnoreCase(dto.format) ? MediaType.APPLICATION_PDF : MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(content);
    }
}

