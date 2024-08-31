package com.fh.scms.services;

import com.fh.scms.dto.statistics.*;

import java.util.List;

public interface StatisticsService {

    RevenueStatisticsEntry getStatisticsRevenueByWeeks(int days);

    SupplierPerformanceReport getSupplierPerformanceReport(Long supplierId, Integer year);

    List<WarehouseStatusReportEntry> getWarehouseStatusReport();

    List<InventoryStatusReportEntry> getInventoryStatusReportOfWarehouse(Long warehouseId);

    List<ProductStatisticEntry> getStatisticsProductsStatusOfInventory(Long inventoryId);

    List<ProductStatusReportEntry> findProductsOfInventoryByStatus(Long inventoryId, String status);
}
