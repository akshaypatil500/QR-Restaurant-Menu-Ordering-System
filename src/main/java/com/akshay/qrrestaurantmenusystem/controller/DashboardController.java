package com.akshay.qrrestaurantmenusystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.akshay.qrrestaurantmenusystem.service.DashboardService;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    // Constructor Injection
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // ==============================
    // Dashboard
    // URL : /dashboard
    // ==============================

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalCategories",
                dashboardService.getTotalCategories());

        model.addAttribute("totalMenus",
                dashboardService.getTotalMenus());

        model.addAttribute("totalTables",
                dashboardService.getTotalTables());

        model.addAttribute("totalOrders",
                dashboardService.getTotalOrders());

        model.addAttribute("pendingOrders",
                dashboardService.getPendingOrders());

        model.addAttribute("preparingOrders",
                dashboardService.getPreparingOrders());

        model.addAttribute("readyOrders",
                dashboardService.getReadyOrders());

        model.addAttribute("servedOrders",
                dashboardService.getServedOrders());

        return "dashboard/dashboard";
    }

}