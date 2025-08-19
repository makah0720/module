package com.example.erp.procurement.repository;

import com.example.erp.procurement.domain.POCollaboration;
import com.example.erp.procurement.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface POCollaborationRepository extends JpaRepository<POCollaboration, Long> {
    Optional<POCollaboration> findByPurchaseOrder(PurchaseOrder po);
}

