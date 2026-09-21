package com.akshay.qrrestaurantmenusystem.service;

public interface DashboardService {

    // ==========================
    // Dashboard Counts
    // ==========================

    Long getTotalCategories();

    Long getTotalMenus();

    Long getTotalTables();

    Long getTotalOrders();
    
    Long getPendingOrders();

    Long getPreparingOrders();

    Long getReadyOrders();

    Long getServedOrders();

}