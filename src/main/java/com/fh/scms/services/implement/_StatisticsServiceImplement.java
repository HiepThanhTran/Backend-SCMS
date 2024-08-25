package com.fh.scms.services.implement;

import com.fh.scms.dto.product.ProductStatisticEntry;
import com.fh.scms.repository._StatisticsRepository;
import com.fh.scms.services._StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class _StatisticsServiceImplement implements _StatisticsService {

    @Autowired
    private _StatisticsRepository statisticsRepository;

    @Override
    public List<Map<String, Object>> getWarehouseReport() {
        return this.statisticsRepository.getWarehouseReport().stream().map(result -> {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("warehouseId", result[0]);
            data.put("warehouseName", result[1]);
            data.put("warehouseCapacity", result[2]);
            data.put("remainingCapacity", result[3]);
            return data;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getInventoryReport(Long warehouseId) {
        return this.statisticsRepository.getInventoryReport(warehouseId).stream().map(result -> {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("inventoryId", result[0]);
            data.put("inventoryName", result[1]);
            data.put("totalQuantity", result[2]);
            return data;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductStatisticEntry> statisticsProductsByExpiryDate(Long inventoryId) {
        List<Object[]> results = this.statisticsRepository.statisticsProductsByExpiryDate(inventoryId);

        if (results.isEmpty()) {
            return Collections.emptyList();
        }

        Object[] result = results.get(0);
        Long validCount = ((Number) result[0]).longValue();
        Long expiredCount = ((Number) result[1]).longValue();
        Long expiringSoonCount = ((Number) result[2]).longValue();

        return Arrays.asList(
                new ProductStatisticEntry("Sản phẩm còn hạn", validCount),
                new ProductStatisticEntry("Sản phẩm đã hết hạn", expiredCount),
                new ProductStatisticEntry("Sản phẩm sắp hết hạn", expiringSoonCount)
        );
    }

    @Override
    public List<Map<String, Object>> findProductsExpiringSoon(Long inventoryId) {
        return this.statisticsRepository.findProductsExpiringSoon(inventoryId).stream().map(result -> {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("productId", result[0]);
            data.put("productName", result[1]);
            data.put("productUnit", result[2]);
            data.put("productQuantity", result[3]);
            data.put("expiryDate", DateTimeFormatter.ofPattern("dd-MM-yyyy").format((TemporalAccessor) result[4]));
            return data;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> findExpiredProducts(Long inventoryId) {
        return this.statisticsRepository.findExpiredProducts(inventoryId).stream().map(result -> {
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("productId", result[0]);
            data.put("productName", result[1]);
            data.put("productUnit", result[2]);
            data.put("productQuantity", result[3]);
            data.put("expiryDate", DateTimeFormatter.ofPattern("dd-MM-yyyy").format((TemporalAccessor) result[4]));
            return data;
        }).collect(Collectors.toList());
    }
}
