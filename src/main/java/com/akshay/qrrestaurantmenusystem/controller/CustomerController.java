package com.akshay.qrrestaurantmenusystem.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akshay.qrrestaurantmenusystem.entity.Category;
import com.akshay.qrrestaurantmenusystem.entity.Menu;
import com.akshay.qrrestaurantmenusystem.entity.RestaurantTable;
import com.akshay.qrrestaurantmenusystem.service.CategoryService;
import com.akshay.qrrestaurantmenusystem.service.MenuService;
import com.akshay.qrrestaurantmenusystem.service.RestaurantTableService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final RestaurantTableService tableService;
    private final CategoryService categoryService;
    private final MenuService menuService;

    public CustomerController(RestaurantTableService tableService,
                              CategoryService categoryService,
                              MenuService menuService) {

        this.tableService = tableService;
        this.categoryService = categoryService;
        this.menuService = menuService;
    }

    // ==========================================
    // CUSTOMER MENU PAGE
    // ==========================================

    @GetMapping("/menu/{tableId}")
    public String customerMenu(@PathVariable Long tableId,
                               Model model) {

        RestaurantTable table = tableService.getTableById(tableId);

        List<Category> categories = categoryService.getAllCategories();

        // Category -> Menu Mapping
        Map<Category, List<Menu>> categoryMenus = new LinkedHashMap<>();

        for (Category category : categories) {

            categoryMenus.put(
                    category,
                    menuService.getAvailableMenusByCategory(category.getId())
            );

        }

        model.addAttribute("table", table);
        model.addAttribute("categoryMenus", categoryMenus);

        return "customer/customer-menu";
    }

}