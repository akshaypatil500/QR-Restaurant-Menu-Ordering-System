package com.akshay.qrrestaurantmenusystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.akshay.qrrestaurantmenusystem.entity.OrderStatus;
import com.akshay.qrrestaurantmenusystem.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    // Constructor Injection
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ==========================================
    // PLACE ORDER
    // URL : /order/checkout/{tableId}
    // ==========================================

    @GetMapping("/checkout/{tableId}")
    public String placeOrder(@PathVariable Long tableId) {

        orderService.placeOrder(tableId);

        return "redirect:/order/success";
    }

    // ==========================================
    // ORDER SUCCESS PAGE
    // URL : /order/success
    // ==========================================

    @GetMapping("/success")
    public String orderSuccess() {

        return "customer/order-success";
    }

    // ==========================================
    // ADMIN ORDER LIST
    // URL : /order/list
    // ==========================================

    @GetMapping("/list")
    public String orderList(Model model) {

        model.addAttribute(
                "orders",
                orderService.getAllOrders());

        return "order/order-list";
    }

    // ==========================================
    // ORDER DETAILS
    // URL : /order/details/{id}
    // ==========================================
    
    @GetMapping("/details/{id}")
    public String orderDetails(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "order",
                orderService.getOrderById(id));

        model.addAttribute(
                "items",
                orderService.getOrderItems(id));

        return "order/order-details";
    }

    // ==========================================
    // UPDATE ORDER STATUS
    // URL : /order/status/{id}
    // ==========================================

    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam OrderStatus status) {

        orderService.updateOrderStatus(id, status);

        return "redirect:/order/list";
    }

}