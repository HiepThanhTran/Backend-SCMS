package com.fh.scms.services;

import com.fh.scms.dto.statistics.InventoryStatusReportEntry;
import com.fh.scms.dto.statistics.ProductStatusReportEntry;
import com.fh.scms.dto.statistics.ProductStatisticEntry;
import com.fh.scms.dto.statistics.SupplierPerformanceReport;
import com.fh.scms.dto.statistics.WarehouseStatusReportEntry;

import java.util.List;

public interface _StatisticsService {

    SupplierPerformanceReport getSupplierPerformanceReport(Long supplierId, Integer year);

    List<WarehouseStatusReportEntry> getWarehouseStatusReport();

    List<InventoryStatusReportEntry> getInventoryStatusReport(Long warehouseId);

    List<ProductStatisticEntry> getStatisticsProductsByExpiryDate(Long inventoryId);

    List<ProductStatusReportEntry> findProductsExpiringSoon(Long inventoryId);

    List<ProductStatusReportEntry> findExpiredProducts(Long inventoryId);
}
