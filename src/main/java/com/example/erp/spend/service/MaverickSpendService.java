package com.example.erp.spend.service;

import com.example.erp.spend.domain.MaverickSpend;
import com.example.erp.spend.domain.SpendTransaction;
import com.example.erp.spend.repository.MaverickSpendRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MaverickSpendService {
    private final MaverickSpendRepository maverickSpendRepository;

    public MaverickSpendService(MaverickSpendRepository maverickSpendRepository) {
        this.maverickSpendRepository = maverickSpendRepository;
    }

    public List<MaverickSpend> detect(List<SpendTransaction> tx) {
        BigDecimal threshold = new BigDecimal("0");
        return tx.stream()
                .filter(t -> !t.isApproved() || !t.isCatalog())
                .map(t -> {
                    MaverickSpend m = new MaverickSpend();
                    m.setSupplierId(t.getSupplierId());
                    m.setReason(!t.isApproved() ? "Not Approved" : "Non Catalog");
                    m.setAmount(t.getAmount());
                    return maverickSpendRepository.save(m);
                }).toList();
    }
}

