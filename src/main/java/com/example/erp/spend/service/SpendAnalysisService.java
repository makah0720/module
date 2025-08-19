package com.example.erp.spend.service;

import com.example.erp.spend.domain.*;
import com.example.erp.spend.dto.SpendAnalyticsDTO;
import com.example.erp.spend.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class SpendAnalysisService {
    private final SpendTransactionRepository spendTransactionRepository;
    private final SpendAnalyticsRepository spendAnalyticsRepository;
    private final MaverickSpendRepository maverickSpendRepository;

    public SpendAnalysisService(SpendTransactionRepository spendTransactionRepository,
                                SpendAnalyticsRepository spendAnalyticsRepository,
                                MaverickSpendRepository maverickSpendRepository) {
        this.spendTransactionRepository = spendTransactionRepository;
        this.spendAnalyticsRepository = spendAnalyticsRepository;
        this.maverickSpendRepository = maverickSpendRepository;
    }

    public SpendAnalyticsDTO compute(LocalDate start, LocalDate end) {
        List<SpendTransaction> tx = spendTransactionRepository.findByTransactionDateBetween(start, end);
        BigDecimal total = tx.stream().map(SpendTransaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal maverick = tx.stream()
                .filter(t -> !t.isApproved() || !t.isCatalog())
                .map(SpendTransaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        SpendAnalytics sa = new SpendAnalytics();
        sa.setTotalSpend(total);
        sa.setMaverickSpend(maverick);
        sa.setSavings(BigDecimal.ZERO);
        sa.setSupplierCount(Math.toIntExact(tx.stream().map(SpendTransaction::getSupplierId).distinct().count()));
        sa.setCategoryCount(Math.toIntExact(tx.stream().map(SpendTransaction::getCategoryCode).distinct().count()));
        spendAnalyticsRepository.save(sa);

        SpendAnalyticsDTO dto = new SpendAnalyticsDTO();
        dto.totalSpend = total;
        dto.maverickSpend = maverick;
        dto.savings = sa.getSavings();
        dto.supplierCount = sa.getSupplierCount();
        dto.categoryCount = sa.getCategoryCount();
        return dto;
    }
}

