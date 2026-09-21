package com.akshay.qrrestaurantmenusystem.service;

import java.util.List;

import com.akshay.qrrestaurantmenusystem.entity.OrderItem;
import com.akshay.qrrestaurantmenusystem.entity.OrderStatus;
import com.akshay.qrrestaurantmenusystem.entity.RestaurantOrder;

public interface OrderService {

    // Place Customer Order
    void placeOrder(Long tableId);

    // All Orders
    List<RestaurantOrder> getAllOrders();

    // Order By Id
    RestaurantOrder getOrderById(Long id);

    // Orders By Status
    List<RestaurantOrder> getOrdersByStatus(OrderStatus status);
    
    // Update Status
    void updateOrderStatus(Long orderId, OrderStatus status);
    
    List<OrderItem> getOrderItems(Long orderId);
    
    List<RestaurantOrder> getPendingOrders();

    List<RestaurantOrder> getPreparingOrders();
    
    List<RestaurantOrder> getReadyOrders();
}