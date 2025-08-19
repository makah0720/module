package com.example.erp.procurement.repository;

import com.example.erp.procurement.domain.ChangeOrder;
import com.example.erp.procurement.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChangeOrderRepository extends JpaRepository<ChangeOrder, Long> {
    List<ChangeOrder> findByPurchaseOrder(PurchaseOrder po);
}

