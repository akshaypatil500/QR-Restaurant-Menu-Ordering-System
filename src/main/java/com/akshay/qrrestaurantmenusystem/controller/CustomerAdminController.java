package com.akshay.qrrestaurantmenusystem.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akshay.qrrestaurantmenusystem.entity.RestaurantOrder;
import com.akshay.qrrestaurantmenusystem.service.OrderService;

@Controller
@RequestMapping("/admin/customer")
public class CustomerAdminController {

    private final OrderService orderService;

    public CustomerAdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String customerOrders(Model model) {

        model.addAttribute(
                "activeOrders",
                orderService.getActiveOrders());

        model.addAttribute(
                "completedOrders",
                orderService.getCompletedOrders());

        return "customer/customer-orders";
    }
}