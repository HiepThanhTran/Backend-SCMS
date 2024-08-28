package com.fh.scms.services.implement;

import com.fh.scms.dto.statistics.*;
import com.fh.scms.enums.CriteriaType;
import com.fh.scms.repository._StatisticsRepository;
import com.fh.scms.services._StatisticsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class _StatisticsServiceImplement implements _StatisticsService {

    @Autowired
    private _StatisticsRepository statisticsRepository;

    private static @NotNull List<SupplierPerformanceReport.RatingMonth> getRatingMonths(@NotNull SupplierPerformanceReport supplierReport, CriteriaType criteria) {
        Map<CriteriaType, List<SupplierPerformanceReport.RatingMonth>> criteriaRatings = supplierReport.getCriteriaRatings();
        // Nếu chưa có danh sách điểm mỗi tháng cho tiêu chí này thì tạo mới
        // Có rồi thì trả về danh sách điểm đã có
        return criteriaRatings.computeIfAbsent(criteria, k -> {
            List<SupplierPerformanceReport.RatingMonth> list = new ArrayList<>();
            for (int monthLoop = 1; monthLoop <= 12; monthLoop++) {
                list.add(new SupplierPerformanceReport.RatingMonth(monthLoop, 0.0));
            }
            return list;
        });
    }

    @Override
    public RevenueStatisticsEntry getRevenueByLastDays(int days) {
        return this.statisticsRepository.generateRevenueByLastDays(days);
    }

    @Override
    public SupplierPerformanceReport getSupplierPerformanceReport(Long supplierId, Integer year) {
        List<Object[]> results = this.statisticsRepository.generateSupplierPerformanceReport(supplierId, year);
        Map<Long, SupplierPerformanceReport> supplierMap = new HashMap<>();

        for (Object[] result : results) {
            Long id = Long.parseLong(String.valueOf(result[0]));
            String name = String.valueOf(result[1]);
            CriteriaType criteria = CriteriaType.valueOf(String.valueOf(result[2]));
            int month = Integer.parseInt(String.valueOf(result[3]));
            Double averageRating = Double.parseDouble(String.valueOf(result[4]));

            // Tạo hoặc cập nhật đối tượng SupplierPerformanceReport
            SupplierPerformanceReport supplierReport = supplierMap.computeIfAbsent(id, k -> {
                SupplierPerformanceReport report = new SupplierPerformanceReport();
                report.setSupplierId(id);
                report.setSupplierName(name);
                report.setCriteriaRatings(new HashMap<>());
                return report;
            });

            // Lấy hoặc tạo danh sách điểm đánh giá cho tiêu chí
            List<SupplierPerformanceReport.RatingMonth> ratingList = getRatingMonths(supplierReport, criteria);

            // Cập nhật điểm đánh giá cho tháng
            ratingList.set(month - 1, new SupplierPerformanceReport.RatingMonth(month, averageRating));
        }

        return supplierMap.getOrDefault(supplierId, null);
    }

    @Override
    public List<WarehouseStatusReportEntry> getWarehouseStatusReport() {
        return this.statisticsRepository.generateWarehouseStatusReport();
    }

    @Override
    public List<InventoryStatusReportEntry> getInventoryStatusReport(Long warehouseId) {
        return this.statisticsRepository.generateInventoryStatusReport(warehouseId);
    }

    @Override
    public List<ProductStatisticEntry> getStatisticsProductsByExpiryDate(Long inventoryId) {
        List<Object[]> results = this.statisticsRepository.generateStatisticsProductsByExpiryDate(inventoryId);
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
    public List<ProductStatusReportEntry> findProductsByStatus(Long inventoryId, String status) {
        return this.statisticsRepository.findProductsByStatus(inventoryId, status);
    }
}
