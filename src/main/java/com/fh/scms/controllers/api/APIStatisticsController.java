package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.enums.ProductStatus;
import com.fh.scms.services.StatisticsService;
import com.fh.scms.util.Constants;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/statistics", produces = "application/json; charset=UTF-8")
public class APIStatisticsController {

    private final StatisticsService statisticsService;

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

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(@NotNull HttpServletRequest req, EntityNotFoundException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<?> handleIllegalArgumentException(@NotNull HttpServletRequest req, IllegalArgumentException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(@NotNull HttpServletRequest req, AccessDeniedException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(@NotNull HttpServletRequest req, Exception e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.badRequest().body(List.of(new MessageResponse(e.getMessage())));
    }
}
