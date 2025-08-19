package com.example.erp.procurement.service;

import com.example.erp.procurement.domain.POStatus;
import com.example.erp.procurement.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

@Service
public class AnalyticsService {
    private final PurchaseOrderRepository purchaseOrderRepository;

    public AnalyticsService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public Map<POStatus, Long> statusCounts() {
        Map<POStatus, Long> map = new EnumMap<>(POStatus.class);
        for (POStatus s : POStatus.values()) {
            map.put(s, (long) purchaseOrderRepository.findByStatus(s).size());
        }
        return map;
    }

    public BigDecimal totalSpend() {
        return purchaseOrderRepository.findAll().stream()
                .map(po -> po.getTotalAmount() == null ? BigDecimal.ZERO : po.getTotalAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

