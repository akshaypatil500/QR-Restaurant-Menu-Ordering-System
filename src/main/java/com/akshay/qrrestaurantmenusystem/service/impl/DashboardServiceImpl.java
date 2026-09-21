package com.akshay.qrrestaurantmenusystem.service.impl;

import org.springframework.stereotype.Service;

import com.akshay.qrrestaurantmenusystem.entity.OrderStatus;
import com.akshay.qrrestaurantmenusystem.repository.CategoryRepository;
import com.akshay.qrrestaurantmenusystem.repository.MenuRepository;
import com.akshay.qrrestaurantmenusystem.repository.RestaurantOrderRepository;
import com.akshay.qrrestaurantmenusystem.repository.RestaurantTableRepository;
import com.akshay.qrrestaurantmenusystem.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;
    private final RestaurantTableRepository tableRepository;
    private final RestaurantOrderRepository orderRepository;
    // Constructor Injection
    public DashboardServiceImpl(CategoryRepository categoryRepository,
                                MenuRepository menuRepository,
                                RestaurantTableRepository tableRepository,
                                RestaurantOrderRepository orderRepository) {

        this.categoryRepository = categoryRepository;
        this.menuRepository = menuRepository;
        this.tableRepository = tableRepository;
        this.orderRepository = orderRepository;
    }

    // =========================================
    // Dashboard Counts
    // =========================================

    @Override
    public Long getTotalCategories() {

        return categoryRepository.count();

    }

    @Override
    public Long getTotalMenus() {

        return menuRepository.count();

    }

    @Override
    public Long getTotalTables() {

        return tableRepository.count();

    }

    @Override
    public Long getTotalOrders() {

        return orderRepository.count();

    }

    @Override
    public Long getPendingOrders() {

        return   orderRepository.countByStatus(OrderStatus.PENDING);

    }

    @Override
    public Long getPreparingOrders() {

        return orderRepository.countByStatus(OrderStatus.PREPARING);

    }

    @Override
    public Long getReadyOrders() {

        return  orderRepository.countByStatus(OrderStatus.READY);

    }

    @Override
    public Long getServedOrders() {

        return  orderRepository.countByStatus(OrderStatus.SERVED);

    }
    
}