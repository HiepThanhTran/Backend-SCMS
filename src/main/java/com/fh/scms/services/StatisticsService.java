package com.fh.scms.services;

import com.fh.scms.dto.statistics.*;

import java.util.List;
import java.util.Map;

public interface StatisticsService {

    Map<String, Object> getStatisticsRevenueByPeroid(int year, String period);

    List<ProductStatisticsForRevenueEntry> findProductsOfRevenueByPeroid(int year, String period);

    List<CategoryStatisticsForeRevenueEntry> findCategoriesOfRevenueByPeroid(int year, String period);

    RevenueStatisticsForDashBoardEntry getStatisticsRevenueByWeeks(int days);

    SupplierPerformanceReport getSupplierPerformanceReport(Long supplierId, Integer year);

    List<WarehouseStatusReportEntry> getWarehouseStatusReport();

    List<InventoryStatusReportEntry> getInventoryStatusReportOfWarehouse(Long warehouseId);

    List<ProductStatusStatisticEntry> getStatisticsProductsStatusOfInventory(Long inventoryId);

    List<ProductStatusReportEntry> findProductsOfInventoryByStatus(Long inventoryId, String status);
}
