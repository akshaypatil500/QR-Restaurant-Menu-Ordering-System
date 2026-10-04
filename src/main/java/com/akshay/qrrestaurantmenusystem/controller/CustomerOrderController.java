package com.akshay.qrrestaurantmenusystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akshay.qrrestaurantmenusystem.entity.RestaurantOrder;
import com.akshay.qrrestaurantmenusystem.service.OrderService;

@Controller
@RequestMapping("/customer/order")
public class CustomerOrderController {

    private final OrderService orderService;

    public CustomerOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place/{tableId}")
    public String placeOrder(@PathVariable Long tableId) {

        orderService.placeOrder(tableId);

        return "redirect:/customer/order/" + tableId;
    }

    @GetMapping("/{tableId}")
    public String currentOrder(@PathVariable Long tableId,
                               Model model) {

        RestaurantOrder order =
                orderService.getCurrentOrder(tableId);

        model.addAttribute("order", order);
        model.addAttribute("tableId", tableId);

        if (order != null) {

            model.addAttribute(
                    "items",
                    orderService.getOrderItems(order.getId())
            );
        }

        return "customer/order-status";
    }
}