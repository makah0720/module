package com.example.erp.procurement.repository;

import com.example.erp.procurement.domain.DeliverySchedule;
import com.example.erp.procurement.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryScheduleRepository extends JpaRepository<DeliverySchedule, Long> {
    List<DeliverySchedule> findByPurchaseOrder(PurchaseOrder po);
}

