package com.example.erp.spend.repository;

import com.example.erp.spend.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpendTransactionRepository extends JpaRepository<SpendTransaction, Long> {
    List<SpendTransaction> findByTransactionDateBetween(LocalDate start, LocalDate end);
}

public interface SpendCategoryRepository extends JpaRepository<SpendCategory, Long> {
    SpendCategory findByCode(String code);
}

public interface SpendCubeRepository extends JpaRepository<SpendCube, Long> {
    List<SpendCube> findByDimensionKey(String dimensionKey);
}

public interface SpendAnalyticsRepository extends JpaRepository<SpendAnalytics, Long> { }

public interface MaverickSpendRepository extends JpaRepository<MaverickSpend, Long> { }

public interface SavingsTrackingRepository extends JpaRepository<SavingsTracking, Long> { }

public interface SpendDashboardRepository extends JpaRepository<SpendDashboard, Long> { }

public interface CategoryAnalysisRepository extends JpaRepository<CategoryAnalysis, Long> { }

public interface SupplierSpendAnalysisRepository extends JpaRepository<SupplierSpendAnalysis, Long> { }

public interface TrendAnalysisRepository extends JpaRepository<TrendAnalysis, Long> { }

