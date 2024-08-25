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

    @GetMapping("/warehouse-report")
    public ResponseEntity<?> warehouseReport() {
        return ResponseEntity.ok(this.statisticsService.getWarehouseReport());
    }

    @GetMapping("/inventory-report")
    public ResponseEntity<?> inventoryReport(@RequestParam Long warehouseId) {
        return ResponseEntity.ok(this.statisticsService.getInventoryReport(warehouseId));
    }

    @GetMapping(path = "/inventory/product/expiry-date")
    public ResponseEntity<?> productExpiryDate(@RequestParam Long inventoryId) {
        return ResponseEntity.ok(this.statisticsService.statisticsProductsByExpiryDate(inventoryId));
    }

    @GetMapping(path = "/inventory/product/expiring-soon")
    public ResponseEntity<?> productExpiringSoon(@RequestParam Long inventoryId) {
        return ResponseEntity.ok(this.statisticsService.findProductsExpiringSoon(inventoryId));
    }

    @GetMapping(path = "/inventory/product/expired")
    public ResponseEntity<?> expiredProduct(@RequestParam Long inventoryId) {
        return ResponseEntity.ok(this.statisticsService.findExpiredProducts(inventoryId));
    }
}
