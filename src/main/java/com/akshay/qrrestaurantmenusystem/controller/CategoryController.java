package com.akshay.qrrestaurantmenusystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.akshay.qrrestaurantmenusystem.entity.Category;
import com.akshay.qrrestaurantmenusystem.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {

    // Constructor Injection
    private final CategoryService categoryService;

    // Constructor
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ============================
    // Show Add Category Page
    // URL : /category/add
    // ============================
    @GetMapping("/add")
    public String showAddPage(Model model) {

        model.addAttribute("category", new Category());

        return "category/add-category";
    }

    // ============================
    // Save Category
    // URL : /category/save
    // ============================
    @PostMapping("/save")
    public String saveCategory(
            @ModelAttribute Category category,
            RedirectAttributes redirectAttributes) {

        try {

            categoryService.saveCategory(category);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Category saved successfully.");

        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage());
        }

        return "redirect:/category/list";
    }

    // ============================
    // Display All Categories
    // URL : /category/list
    // ============================
    @GetMapping("/list")
    public String listCategory(Model model) {

        model.addAttribute(
                "categories",
                categoryService.getAllCategories());

        return "category/categories";
    }

    // ============================
    // Edit Category
    // URL : /category/edit/{id}
    // ============================
    @GetMapping("/edit/{id}")
    public String editCategory(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "category",
                categoryService.getCategoryById(id));

        return "category/add-category";
    }

    // ============================
    // Delete Category
    // URL : /category/delete/{id}
    // ============================
    @GetMapping("/delete/{id}")
    public String deleteCategory(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {

            categoryService.deleteCategory(id);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Category deleted successfully.");

        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage());
        }

        return "redirect:/category/list";
    }
}

