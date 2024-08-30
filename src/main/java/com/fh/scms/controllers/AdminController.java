package com.fh.scms.controllers;

import com.fh.scms.services.OrderService;
import com.fh.scms.services.SupplierService;
import com.fh.scms.services._StatisticsService;
import com.fh.scms.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final _StatisticsService statisticsService;
    private final SupplierService supplierService;
    private final OrderService orderService;

    @GetMapping("/login")
    public String login(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        return "redirect:/";
    }

    @GetMapping(path = "/")
    public String dashBoard(Model model) {
        model.addAttribute("revenueCurrentWeek", this.statisticsService.getStatisticsRevenueByWeeks(Constants.CURRENT_WEEK));
        model.addAttribute("revenueLastWeek", this.statisticsService.getStatisticsRevenueByWeeks(Constants.LAST_WEEK));
        model.addAttribute("recentlyOrders", this.orderService.findRecentlyOrders());

        return "dashboard";
    }

    @GetMapping(path = "/admin/statistics")
    public String statistics() {
        return "statistics";
    }

    @GetMapping(path = "/admin/statistics/supplier/performance")
    public String supplierPerformanceReport(Model model) {
        model.addAttribute("suppliers", this.supplierService.findAllWithFilter(null));

        return "supplier_performance";
    }

    @GetMapping(path = "/admin/statistics/revenue")
    public String statisticsRevenue() {
        return "statistics_revenue";
    }

    @GetMapping(path = "/admin/report/inventory")
    public String inventoryStatusReport(Model model) {
        model.addAttribute("warehouseCapacityReport", this.statisticsService.getWarehouseStatusReport());

        return "inventory_status";
    }
}
