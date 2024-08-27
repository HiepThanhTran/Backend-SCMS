package com.fh.scms.controllers.api;

import com.fh.scms.services._StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/statistics", produces = "application/json; charset=UTF-8")
public class _APIStatisticsController {

    private final _StatisticsService statisticsService;

    @GetMapping("/revenue/last-24-hours")
    public ResponseEntity<?> revenueLast24Hours() {
        return ResponseEntity.ok(this.statisticsService.getRevenueByLast24Hours());
    }

    @GetMapping("/revenue/last-week")
    public ResponseEntity<?> revenueLastWeek() {
        return ResponseEntity.ok(this.statisticsService.getRevenueByLastWeek());
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
        return ResponseEntity.ok(this.statisticsService.getInventoryStatusReport(warehouseId));
    }

    @GetMapping(path = "/inventory/{inventoryId}/report/product/expiry-date")
    public ResponseEntity<?> productExpiryDate(@PathVariable(value = "inventoryId") Long inventoryId) {
        return ResponseEntity.ok(this.statisticsService.getStatisticsProductsByExpiryDate(inventoryId));
    }

    @GetMapping(path = "/inventory/{inventoryId}/report/product/expiring-soon")
    public ResponseEntity<?> productExpiringSoon(@PathVariable(value = "inventoryId") Long inventoryId) {
        return ResponseEntity.ok(this.statisticsService.findProductsExpiringSoon(inventoryId));
    }

    @GetMapping(path = "/inventory/{inventoryId}/report/product/expired")
    public ResponseEntity<?> expiredProduct(@PathVariable(value = "inventoryId") Long inventoryId) {
        return ResponseEntity.ok(this.statisticsService.findExpiredProducts(inventoryId));
    }
}
