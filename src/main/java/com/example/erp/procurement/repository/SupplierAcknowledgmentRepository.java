package com.example.erp.procurement.repository;

import com.example.erp.procurement.domain.PurchaseOrder;
import com.example.erp.procurement.domain.SupplierAcknowledgment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierAcknowledgmentRepository extends JpaRepository<SupplierAcknowledgment, Long> {
    List<SupplierAcknowledgment> findByPurchaseOrder(PurchaseOrder po);
}

