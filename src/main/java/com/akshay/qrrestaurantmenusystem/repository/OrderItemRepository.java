package com.akshay.qrrestaurantmenusystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshay.qrrestaurantmenusystem.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // All Items Of One Order
    List<OrderItem> findByRestaurantOrderId(Long orderId);

}