package com.fh.scms.repository;

import com.fh.scms.dto.statistics.*;

import java.util.List;

public interface StatisticsRepository {

    List<Object[]> generateStatisticsRevenueByPeroid(int year, String period);

    List<ProductStatisticsForRevenueEntry> findProductsOfRevenueByPeroid(int year, String period);

    List<CategoryStatisticsForeRevenueEntry> findCategoriesOfRevenueByPeroid(int year, String period);

    RevenueStatisticsForDashBoardEntry generateStatisticsRevenueByWeeks(int days);

    List<Object[]> generateSupplierPerformanceReport(Long supplierId, Integer year);

    List<WarehouseStatusReportEntry> generateWarehouseStatusReport();

    List<InventoryStatusReportEntry> generateInventoryStatusReportOfWarehouse(Long warehouseId);

    List<Object[]> generateStatisticsProductsStatusOfInventory(Long inventoryId);

    List<ProductStatusReportEntry> findProductsOfInventoryByStatus(Long inventoryId, String status);
}
