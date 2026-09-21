package com.akshay.qrrestaurantmenusystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.akshay.qrrestaurantmenusystem.entity.RestaurantTable;
import com.akshay.qrrestaurantmenusystem.service.RestaurantTableService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/table")
public class RestaurantTableController {

    private final RestaurantTableService tableService;

    // Constructor Injection
    public RestaurantTableController(RestaurantTableService tableService) {
        this.tableService = tableService;
    }

    // ==========================================
    // SHOW ADD TABLE PAGE
    // URL : /table/add
    // ==========================================

    @GetMapping("/add")
    public String showAddTablePage(Model model) {

        model.addAttribute("restaurantTable", new RestaurantTable());

        return "table/add-table";
    }

    // ==========================================
    // SAVE TABLE
    // ==========================================

    @PostMapping("/save")
    public String saveTable(

            @Valid
            @ModelAttribute("restaurantTable")
            RestaurantTable restaurantTable,

            BindingResult result) {

        if (result.hasErrors()) {

            return "table/add-table";
        }

        tableService.saveTable(restaurantTable);

        return "redirect:/table/list";
    }

    // ==========================================
    // TABLE LIST
    // ==========================================

    @GetMapping("/list")
    public String tableList(Model model) {

        model.addAttribute(
                "tables",
                tableService.getAllTables());

        return "table/table-list";
    }

    // ==========================================
    // EDIT PAGE
    // ==========================================

    @GetMapping("/edit/{id}")
    public String editTable(

            @PathVariable Long id,

            Model model) {

        model.addAttribute(
                "restaurantTable",
                tableService.getTableById(id));

        return "table/edit-table";
    }

    // ==========================================
    // UPDATE TABLE
    // ==========================================

    @PostMapping("/update")
    public String updateTable(

            @Valid
            @ModelAttribute("restaurantTable")
            RestaurantTable restaurantTable,

            BindingResult result) {

        if (result.hasErrors()) {

            return "table/edit-table";
        }

        tableService.updateTable(restaurantTable);

        return "redirect:/table/list";
    }

    // ==========================================
    // DELETE TABLE
    // ==========================================

    @GetMapping("/delete/{id}")
    public String deleteTable(
            @PathVariable Long id,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        try {

            tableService.deleteTable(id);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Table deleted successfully.");

        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage());
        }

        return "redirect:/table/list";
    }
    
    @GetMapping("/regenerate-qr/{id}")
    public String regenerateQRCode(@PathVariable Long id) {

        tableService.regenerateQRCode(id);

        return "redirect:/table/list";
    }
}