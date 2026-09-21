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
@RequestMapping("/kitchen")
public class KitchenController {

    private final OrderService orderService;

    public KitchenController(OrderService orderService) {

        this.orderService = orderService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute(
                "pendingOrders",
                orderService.getPendingOrders());

        model.addAttribute(
                "preparingOrders",
                orderService.getPreparingOrders());

        model.addAttribute(
                "readyOrders",
                orderService.getReadyOrders());

        return "kitchen/dashboard";
    }
    
    
    @PostMapping("/status/{id}")
    public String updateKitchenStatus(@PathVariable Long id,
                                      @RequestParam OrderStatus status) {

        orderService.updateOrderStatus(id, status);

        return "redirect:/kitchen/dashboard";
    }

}