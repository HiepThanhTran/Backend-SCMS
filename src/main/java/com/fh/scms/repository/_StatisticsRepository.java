package com.fh.scms.repository;

import com.fh.scms.dto.statistics.InventoryStatusReportEntry;
import com.fh.scms.dto.statistics.ProductStatusReportEntry;
import com.fh.scms.dto.statistics.RevenueStatisticsEntry;
import com.fh.scms.dto.statistics.WarehouseStatusReportEntry;

import java.util.List;

public interface _StatisticsRepository {

    RevenueStatisticsEntry generateStatisticsRevenueByWeeks(int days);

    List<Object[]> generateSupplierPerformanceReport(Long supplierId, Integer year);

    List<WarehouseStatusReportEntry> generateWarehouseStatusReport();

    List<InventoryStatusReportEntry> generateInventoryStatusReportOfWarehouse(Long warehouseId);

    List<Object[]> generateStatisticsProductsStatusOfInventory(Long inventoryId);

    List<ProductStatusReportEntry> findProductsOfInventoryByStatus(Long inventoryId, String status);
}
