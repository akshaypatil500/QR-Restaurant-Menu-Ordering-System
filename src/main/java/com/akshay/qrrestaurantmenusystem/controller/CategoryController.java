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

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    public String showAddPage(Model model) {

        model.addAttribute("category", new Category());

        return "category/add-category";
    }

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

    @GetMapping("/list")
    public String listCategory(Model model) {

        model.addAttribute(
                "categories",
                categoryService.getAllCategories());

        return "category/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(
            @PathVariable Long id,
            Model model) {

        Category category =
                categoryService.getCategoryById(id);

        if (category == null) {
            return "redirect:/category/list";
        }

        model.addAttribute("category", category);

        return "category/edit-category";
    }
    
    @PostMapping("/update")
    public String updateCategory(
            @ModelAttribute Category category,
            RedirectAttributes redirectAttributes) {

        try {

            categoryService.updateCategory(category);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Category updated successfully.");

        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage());
        }

        return "redirect:/category/list";
    }
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