package com.akshay.qrrestaurantmenusystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akshay.qrrestaurantmenusystem.entity.Cart;
import com.akshay.qrrestaurantmenusystem.entity.OrderItem;
import com.akshay.qrrestaurantmenusystem.entity.OrderStatus;
import com.akshay.qrrestaurantmenusystem.entity.RestaurantOrder;
import com.akshay.qrrestaurantmenusystem.entity.RestaurantTable;
import com.akshay.qrrestaurantmenusystem.repository.CartRepository;
import com.akshay.qrrestaurantmenusystem.repository.OrderItemRepository;
import com.akshay.qrrestaurantmenusystem.repository.RestaurantOrderRepository;
import com.akshay.qrrestaurantmenusystem.repository.RestaurantTableRepository;
import com.akshay.qrrestaurantmenusystem.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final RestaurantOrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final RestaurantTableRepository tableRepository;

    public OrderServiceImpl(RestaurantOrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            CartRepository cartRepository,
                            RestaurantTableRepository tableRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.tableRepository = tableRepository;
    }

    // ==========================================
    // PLACE ORDER
    // ==========================================

    @Override
    public void placeOrder(Long tableId) {

        RestaurantTable table = tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table Not Found"));

        List<Cart> cartItems = cartRepository.findByTableId(tableId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart Is Empty");
        }

        RestaurantOrder order = new RestaurantOrder();

        order.setTable(table);
        order.setOrderDate(LocalDateTime.now());

        // Default status
        order.setStatus(OrderStatus.PENDING);

        double grandTotal = 0;

        for (Cart cart : cartItems) {
            grandTotal += cart.getTotalPrice();
        }

        order.setGrandTotal(grandTotal);

        orderRepository.save(order);

        // ==========================================
        // SAVE ORDER ITEMS
        // ==========================================

        for (Cart cart : cartItems) {

            OrderItem item = new OrderItem();

            item.setRestaurantOrder(order);
            item.setMenu(cart.getMenu());
            item.setQuantity(cart.getQuantity());
            item.setPrice(cart.getPrice());
            item.setTotalPrice(cart.getTotalPrice());

            orderItemRepository.save(item);
        }

        // ==========================================
        // CLEAR CART
        // ==========================================

        cartRepository.deleteByTableId(tableId);
    }

    // ==========================================
    // GET ALL ORDERS
    // ==========================================

    @Override
    public List<RestaurantOrder> getAllOrders() {

        return orderRepository.findAll();
    }

    // ==========================================
    // GET ORDER BY ID
    // ==========================================

    @Override
    public RestaurantOrder getOrderById(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
    }

    // ==========================================
    // GET ORDERS BY STATUS
    // ==========================================

    @Override
    public List<RestaurantOrder> getOrdersByStatus(OrderStatus status) {

        return orderRepository.findByStatus(status);
    }

    // ==========================================
    // UPDATE ORDER STATUS
    // ==========================================

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus status) {

        RestaurantOrder order = getOrderById(orderId);

        order.setStatus(status);

        orderRepository.save(order);
    }

    // ==========================================
    // GET ORDER ITEMS
    // ==========================================

    @Override
    public List<OrderItem> getOrderItems(Long orderId) {

        return orderItemRepository.findByRestaurantOrderId(orderId);
    }

    // ==========================================
    // GET PENDING ORDERS
    // ==========================================

    @Override
    public List<RestaurantOrder> getPendingOrders() {

        return orderRepository.findByStatus(OrderStatus.PENDING);
    }

    // ==========================================
    // GET PREPARING ORDERS
    // ==========================================

    @Override
    public List<RestaurantOrder> getPreparingOrders() {

        return orderRepository.findByStatus(OrderStatus.PREPARING);
    }

    // ==========================================
    // GET READY ORDERS
    // ==========================================

    @Override
    public List<RestaurantOrder> getReadyOrders() {

        return orderRepository.findByStatus(OrderStatus.READY);
    }
}