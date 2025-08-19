package com.example.erp.spend.service;

import com.example.erp.spend.domain.SavingsTracking;
import com.example.erp.spend.dto.SavingsTrackingDTO;
import com.example.erp.spend.repository.SavingsTrackingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingsTrackingService {
    private final SavingsTrackingRepository savingsTrackingRepository;

    public SavingsTrackingService(SavingsTrackingRepository savingsTrackingRepository) {
        this.savingsTrackingRepository = savingsTrackingRepository;
    }

    public SavingsTracking create(SavingsTrackingDTO dto) {
        SavingsTracking s = new SavingsTracking();
        s.setInitiative(dto.initiative);
        s.setOwner(dto.owner);
        s.setTargetDate(dto.targetDate);
        s.setTargetAmount(dto.targetAmount);
        s.setRealizedAmount(dto.realizedAmount);
        return savingsTrackingRepository.save(s);
    }

    public List<SavingsTracking> list() {
        return savingsTrackingRepository.findAll();
    }
}

