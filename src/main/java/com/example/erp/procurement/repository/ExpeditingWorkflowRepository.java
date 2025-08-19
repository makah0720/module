package com.example.erp.procurement.repository;

import com.example.erp.procurement.domain.ExpeditingWorkflow;
import com.example.erp.procurement.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpeditingWorkflowRepository extends JpaRepository<ExpeditingWorkflow, Long> {
    Optional<ExpeditingWorkflow> findByPurchaseOrder(PurchaseOrder po);
    java.util.List<ExpeditingWorkflow> findByFlaggedLateTrue();
}

