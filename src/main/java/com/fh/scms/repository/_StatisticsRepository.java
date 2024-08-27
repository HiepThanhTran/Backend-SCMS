package com.fh.scms.repository;

import com.fh.scms.dto.statistics.InventoryStatusReportEntry;
import com.fh.scms.dto.statistics.ProductStatusReportEntry;
import com.fh.scms.dto.statistics.WarehouseStatusReportEntry;

import java.math.BigDecimal;
import java.util.List;

public interface _StatisticsRepository {

    List<Object[]> generateRevenueByLast24Hours();

    List<Object[]> generateRevenueByLastWeek();

    List<Object[]> generateSupplierPerformanceReport(Long supplierId, Integer year);

    List<WarehouseStatusReportEntry> generateWarehouseStatusReport();

    List<InventoryStatusReportEntry> generateInventoryStatusReport(Long warehouseId);

    List<Object[]> generateStatisticsProductsByExpiryDate(Long inventoryId);

    List<ProductStatusReportEntry> findProductsExpiringSoon(Long inventoryId);

    List<ProductStatusReportEntry> findExpiredProducts(Long inventoryId);
}
