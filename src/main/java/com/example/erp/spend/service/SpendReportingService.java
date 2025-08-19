package com.example.erp.spend.service;

import com.example.erp.spend.dto.SpendReportDTO;
import com.example.erp.spend.util.ReportExporter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpendReportingService {
    public byte[] export(SpendReportDTO dto) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> rows = (List<Map<String, Object>>) dto.data.getOrDefault("rows", List.of());
        List<String> headers = (List<String>) dto.data.getOrDefault("headers", List.of());
        List<List<String>> lines = rows.stream()
                .map(m -> headers.stream().map(h -> String.valueOf(m.getOrDefault(h, ""))).collect(Collectors.toList()))
                .collect(Collectors.toList());
        if ("PDF".equalsIgnoreCase(dto.format)) {
            return ReportExporter.toPdf(dto.title, headers, lines);
        } else {
            return ReportExporter.toXlsx(dto.title, headers, lines);
        }
    }
}

