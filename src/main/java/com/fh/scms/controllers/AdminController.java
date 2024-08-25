package com.fh.scms.controllers;

import com.fh.scms.services._StatisticsService;
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

    @GetMapping("/login")
    public String login(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        return "redirect:/";
    }

    @GetMapping(path = "/")
    public String dashBoard() {
        return "dashboard";
    }

    @GetMapping(path = "/admin/statistics")
    public String statisticsReport() {
        return "statistics";
    }

    @GetMapping(path = "/admin/analytics")
    public String analyticsReport(Model model) {
        model.addAttribute("warehouseCapacityReport", this.statisticsService.getWarehouseReport());

        return "analytics";
    }
}
