package com.example.erp.procurement.repository;

import com.example.erp.procurement.domain.GoodsReceipt;
import com.example.erp.procurement.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsReceiptRepository extends JpaRepository<GoodsReceipt, Long> {
    List<GoodsReceipt> findByPurchaseOrder(PurchaseOrder po);
}

