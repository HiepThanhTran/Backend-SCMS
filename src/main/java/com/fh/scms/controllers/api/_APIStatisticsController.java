package com.fh.scms.controllers.api;

import com.fh.scms.enums.ProductStatus;
import com.fh.scms.services._StatisticsService;
import com.fh.scms.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/statistics", produces = "application/json; charset=UTF-8")
public class _APIStatisticsController {

    private final _StatisticsService statisticsService;

    @GetMapping("/revenue/current-week")
    public ResponseEntity<?> revenueCurrentWeek() {
        return ResponseEntity.ok(this.statisticsService.getStatisticsRevenueByWeeks(Constants.CURRENT_WEEK));
    }

    @GetMapping("/revenue/last-week")
    public ResponseEntity<?> revenueLastWeek() {
        return ResponseEntity.ok(this.statisticsService.getStatisticsRevenueByWeeks(Constants.LAST_WEEK));
    }

    @GetMapping("/supplier/{supplierId}/performance")
    public ResponseEntity<?> supplierReport(@PathVariable(value = "supplierId") Long supplierId, @RequestParam Integer year) {
        return ResponseEntity.ok(this.statisticsService.getSupplierPerformanceReport(supplierId, year));
    }

    @GetMapping("/warehouse/report")
    public ResponseEntity<?> warehouseReport() {
        return ResponseEntity.ok(this.statisticsService.getWarehouseStatusReport());
    }

    @GetMapping("/inventory/report")
    public ResponseEntity<?> inventoryReport(@RequestParam Long warehouseId) {
        return ResponseEntity.ok(this.statisticsService.getInventoryStatusReportOfWarehouse(warehouseId));
    }

    @GetMapping(path = "/inventory/{inventoryId}/report/product/expiry-date")
    public ResponseEntity<?> productExpiryDate(@PathVariable(value = "inventoryId") Long inventoryId) {
        return ResponseEntity.ok(this.statisticsService.getStatisticsProductsStatusOfInventory(inventoryId));
    }

    @GetMapping(path = "/inventory/{inventoryId}/report/product/expiring-soon")
    public ResponseEntity<?> productExpiringSoon(@PathVariable(value = "inventoryId") Long inventoryId) {
        return ResponseEntity.ok(this.statisticsService.findProductsOfInventoryByStatus(inventoryId, ProductStatus.EXPIRING_SOON.name()));
    }

    @GetMapping(path = "/inventory/{inventoryId}/report/product/expired")
    public ResponseEntity<?> expiredProduct(@PathVariable(value = "inventoryId") Long inventoryId) {
        return ResponseEntity.ok(this.statisticsService.findProductsOfInventoryByStatus(inventoryId, ProductStatus.EXPIRED.name()));
    }
}
