package com.fh.scms.services;

import com.fh.scms.dto.product.ProductStatisticEntry;

import java.util.List;
import java.util.Map;

public interface _StatisticsService {

    List<Map<String, Object>> getWarehouseReport();

    List<Map<String, Object>> getInventoryReport(Long warehouseId);

    List<ProductStatisticEntry> statisticsProductsByExpiryDate(Long inventoryId);

    List<Map<String, Object>> findProductsExpiringSoon(Long inventoryId);

    List<Map<String, Object>> findExpiredProducts(Long inventoryId);
}
