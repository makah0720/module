package com.example.erp.procurement.service;

import com.example.erp.procurement.domain.GoodsReceipt;
import com.example.erp.procurement.domain.PurchaseOrder;
import com.example.erp.procurement.repository.GoodsReceiptRepository;
import com.example.erp.procurement.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ThreeWayMatchingService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final GoodsReceiptRepository goodsReceiptRepository;

    public ThreeWayMatchingService(PurchaseOrderRepository purchaseOrderRepository,
                                   GoodsReceiptRepository goodsReceiptRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.goodsReceiptRepository = goodsReceiptRepository;
    }

    public boolean matchByQuantity(Long poId, BigDecimal invoiceQuantity) {
        PurchaseOrder po = purchaseOrderRepository.findById(poId).orElseThrow();
        List<GoodsReceipt> receipts = goodsReceiptRepository.findByPurchaseOrder(po);
        BigDecimal received = receipts.stream()
                .map(GoodsReceipt::getTotalReceivedQuantity)
                .filter(q -> q != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ordered = po.getLines().stream()
                .map(l -> l.getQuantity())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalToMatch = received.min(ordered);
        return invoiceQuantity.compareTo(totalToMatch) <= 0;
    }
}

