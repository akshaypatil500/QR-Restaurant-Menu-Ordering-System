package com.akshay.qrrestaurantmenusystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshay.qrrestaurantmenusystem.entity.OrderStatus;
import com.akshay.qrrestaurantmenusystem.entity.RestaurantOrder;

public interface RestaurantOrderRepository
        extends JpaRepository<RestaurantOrder, Long> {

    // Orders Of One Table
    List<RestaurantOrder> findByTable_Id(Long tableId);

    // Orders By Status
    List<RestaurantOrder> findByStatus(OrderStatus status);

    // Table Has Orders?
    boolean existsByTable_Id(Long tableId);

    // Dashboard Counts
    long countByStatus(OrderStatus status);
}