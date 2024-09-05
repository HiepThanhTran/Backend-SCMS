package com.fh.scms.services.implement;

import com.fh.scms.dto.statistics.*;
import com.fh.scms.enums.CriteriaType;
import com.fh.scms.repository.StatisticsRepository;
import com.fh.scms.services.StatisticsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class StatisticsServiceImplement implements StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    public Map<String, Object> getStatisticsRevenueByPeroid(int year, String period) {
        List<Object[]> results = statisticsRepository.generateStatisticsRevenueByPeroid(year, period);

        List<String> labels = initialLabels(period);
        List<Double> data = initialData(period);

        for (Object[] result : results) {
            int key = Integer.parseInt(String.valueOf(result[0]));
            Double value = Double.parseDouble(String.valueOf(result[1]));

            data.set(key - 1, value);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("data", data);

        return response;
    }

    private List<String> initialLabels(@NotNull String period) {
        List<String> labels = new ArrayList<>();
        switch (period) {
            case "month":
                for (int i = 1; i <= 12; i++) {
                    labels.add("Tháng " + i);
                }
                break;
            case "quarter":
                labels = Arrays.asList("Quý 1", "Quý 2", "Quý 3", "Quý 4");
                break;
            default:
                throw new IllegalArgumentException("Unsupported period type: " + period);
        }
        return labels;
    }

    private List<Double> initialData(@NotNull String period) {
        List<Double> data = new ArrayList<>();
        switch (period) {
            case "month":
                for (int i = 1; i <= 12; i++) {
                    data.add(0.0);
                }
                break;
            case "quarter":
                data = Arrays.asList(0.0, 0.0, 0.0, 0.0);
                break;
            default:
                throw new IllegalArgumentException("Unsupported period type: " + period);
        }
        return data;
    }

    @Override
    public List<ProductStatisticsForRevenueEntry> findProductsOfRevenueByPeroid(int year, String period) {
        return this.statisticsRepository.findProductsOfRevenueByPeroid(year, period);
    }

    @Override
    public List<CategoryStatisticsForeRevenueEntry> findCategoriesOfRevenueByPeroid(int year, String period) {
        return this.statisticsRepository.findCategoriesOfRevenueByPeroid(year, period);
    }

    @Override
    public RevenueStatisticsForDashBoardEntry getStatisticsRevenueByWeeks(int days) {
        return this.statisticsRepository.generateStatisticsRevenueByWeeks(days);
    }

    @Override
    public SupplierPerformanceReport getSupplierPerformanceReport(Long supplierId, Integer year) {
        List<Object[]> results = this.statisticsRepository.generateSupplierPerformanceReport(supplierId, year);

        SupplierPerformanceReport supplierReport = new SupplierPerformanceReport();
        List<SupplierPerformanceReport.RatingMonth> costRatings = initializeRatingMonths();
        List<SupplierPerformanceReport.RatingMonth> qualityRatings = initializeRatingMonths();
        List<SupplierPerformanceReport.RatingMonth> timelyDeliveryRatings = initializeRatingMonths();

        for (Object[] result : results) {
            CriteriaType criteria = CriteriaType.valueOf(String.valueOf(result[0]));
            int month = Integer.parseInt(String.valueOf(result[1]));
            Double averageRating = Double.parseDouble(String.valueOf(result[2]));

            SupplierPerformanceReport.RatingMonth ratingMonth = new SupplierPerformanceReport.RatingMonth(month, averageRating);

            switch (criteria) {
                case COST:
                    costRatings.set(month - 1, ratingMonth);
                    break;
                case QUALITY:
                    qualityRatings.set(month - 1, ratingMonth);
                    break;
                case TIMELY_DELIVERY:
                    timelyDeliveryRatings.set(month - 1, ratingMonth);
                    break;
            }
        }

        supplierReport.setCost(costRatings);
        supplierReport.setQuality(qualityRatings);
        supplierReport.setTimelyDelivery(timelyDeliveryRatings);

        return supplierReport;
    }

    private @NotNull List<SupplierPerformanceReport.RatingMonth> initializeRatingMonths() {
        List<SupplierPerformanceReport.RatingMonth> list = new ArrayList<>();
        for (int monthLoop = 1; monthLoop <= 12; monthLoop++) {
            list.add(new SupplierPerformanceReport.RatingMonth(monthLoop, 0.0));
        }
        return list;
    }

    @Override
    public List<WarehouseStatusReportEntry> getWarehouseStatusReport() {
        return this.statisticsRepository.generateWarehouseStatusReport();
    }

    @Override
    public List<InventoryStatusReportEntry> getInventoryStatusReportOfWarehouse(Long warehouseId) {
        return this.statisticsRepository.generateInventoryStatusReportOfWarehouse(warehouseId);
    }

    @Override
    public List<ProductStatusStatisticEntry> getStatisticsProductsStatusOfInventory(Long inventoryId) {
        List<Object[]> results = this.statisticsRepository.generateStatisticsProductsStatusOfInventory(inventoryId);
        if (results.isEmpty()) {
            return Collections.emptyList();
        }

        Object[] result = results.get(0);
        Long validCount = ((Number) result[0]).longValue();
        Long expiredCount = ((Number) result[1]).longValue();
        Long expiringSoonCount = ((Number) result[2]).longValue();

        return Arrays.asList(
                new ProductStatusStatisticEntry("Sản phẩm còn hạn", validCount),
                new ProductStatusStatisticEntry("Sản phẩm đã hết hạn", expiredCount),
                new ProductStatusStatisticEntry("Sản phẩm sắp hết hạn", expiringSoonCount)
        );
    }

    @Override
    public List<ProductStatusReportEntry> findProductsOfInventoryByStatus(Long inventoryId, String status) {
        return this.statisticsRepository.findProductsOfInventoryByStatus(inventoryId, status);
    }
}
