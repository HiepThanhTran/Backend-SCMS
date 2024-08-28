package com.fh.scms.services;

import com.fh.scms.dto.statistics.*;

import java.util.List;
import java.util.Map;

public interface _StatisticsService {

    RevenueStatisticsEntry getRevenueByLastDays(int days);

    SupplierPerformanceReport getSupplierPerformanceReport(Long supplierId, Integer year);

    List<WarehouseStatusReportEntry> getWarehouseStatusReport();

    List<InventoryStatusReportEntry> getInventoryStatusReport(Long warehouseId);

    List<ProductStatisticEntry> getStatisticsProductsByExpiryDate(Long inventoryId);

    List<ProductStatusReportEntry> findProductsByStatus(Long inventoryId, String status);
}
