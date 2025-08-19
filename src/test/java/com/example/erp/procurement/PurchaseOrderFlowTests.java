package com.example.erp.procurement;

import com.example.erp.procurement.domain.POStatus;
import com.example.erp.procurement.dto.PurchaseOrderDTO;
import com.example.erp.procurement.dto.PurchaseOrderLineDTO;
import com.example.erp.procurement.service.PurchaseOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PurchaseOrderFlowTests {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Test
    void createSubmitApproveTransmitFlow() {
        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.poNumber = "PO-1001";
        dto.supplierId = "SUP-1";
        dto.orderDate = LocalDate.now();
        dto.expectedDeliveryDate = LocalDate.now().plusDays(7);
        dto.currency = "USD";
        dto.createdBy = "buyer";
        PurchaseOrderLineDTO line = new PurchaseOrderLineDTO();
        line.itemCode = "ITEM-1";
        line.description = "Sample";
        line.quantity = new BigDecimal("10");
        line.unitPrice = new BigDecimal("5");
        line.uom = "EA";
        dto.lines = List.of(line);

        var po = purchaseOrderService.create(dto);
        assertEquals(POStatus.DRAFT, po.getStatus());
        assertEquals(new BigDecimal("50"), po.getTotalAmount());

        po = purchaseOrderService.submitForApproval(po.getId());
        assertEquals(POStatus.PENDING_APPROVAL, po.getStatus());

        po = purchaseOrderService.approve(po.getId(), "approver");
        assertEquals(POStatus.APPROVED, po.getStatus());

        po = purchaseOrderService.transmitToSupplier(po.getId());
        assertEquals(POStatus.SENT_TO_SUPPLIER, po.getStatus());
    }
}

