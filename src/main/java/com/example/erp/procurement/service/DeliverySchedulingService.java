package com.example.erp.procurement.service;

import com.example.erp.procurement.domain.DeliverySchedule;
import com.example.erp.procurement.domain.PurchaseOrder;
import com.example.erp.procurement.dto.DeliveryScheduleDTO;
import com.example.erp.procurement.repository.DeliveryScheduleRepository;
import com.example.erp.procurement.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliverySchedulingService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final DeliveryScheduleRepository deliveryScheduleRepository;

    public DeliverySchedulingService(PurchaseOrderRepository purchaseOrderRepository, DeliveryScheduleRepository deliveryScheduleRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.deliveryScheduleRepository = deliveryScheduleRepository;
    }

    public DeliverySchedule createSchedule(Long poId, DeliveryScheduleDTO dto) {
        PurchaseOrder po = purchaseOrderRepository.findById(poId).orElseThrow();
        DeliverySchedule ds = new DeliverySchedule();
        ds.setPurchaseOrder(po);
        ds.setScheduledDate(dto.scheduledDate);
        ds.setSite(dto.site);
        ds.setDeliveryWindow(dto.deliveryWindow);
        ds.setConfirmed(dto.confirmed);
        return deliveryScheduleRepository.save(ds);
    }

    public List<DeliverySchedule> listSchedules(Long poId) {
        PurchaseOrder po = purchaseOrderRepository.findById(poId).orElseThrow();
        return deliveryScheduleRepository.findByPurchaseOrder(po);
    }
}

