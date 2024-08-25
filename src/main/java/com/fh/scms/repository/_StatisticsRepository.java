package com.fh.scms.repository;

import java.util.List;
import java.util.Map;

public interface _StatisticsRepository {

    List<Object[]> getWarehouseReport();

    List<Object[]> getInventoryReport(Long warehouseId);

    List<Object[]> statisticsProductsByExpiryDate(Long inventoryId);

    List<Object[]> findProductsExpiringSoon(Long inventoryId);

    List<Object[]> findExpiredProducts(Long inventoryId);
}
