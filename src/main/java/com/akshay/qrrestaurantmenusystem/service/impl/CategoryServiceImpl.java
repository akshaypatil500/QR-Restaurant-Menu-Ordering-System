package com.akshay.qrrestaurantmenusystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.akshay.qrrestaurantmenusystem.entity.Category;
import com.akshay.qrrestaurantmenusystem.repository.CategoryRepository;
import com.akshay.qrrestaurantmenusystem.repository.MenuRepository;
import com.akshay.qrrestaurantmenusystem.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final MenuRepository menuRepository;


    // Constructor Injection
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               MenuRepository menuRepository) {

        this.categoryRepository = categoryRepository;
        this.menuRepository = menuRepository;
    }


    // ============================
    // Save Category
    // ============================

    @Override
    public Category saveCategory(Category category) {

        if (categoryRepository.existsByName(category.getName())) {

            throw new RuntimeException(
                    "Category already exists.");
        }

        return categoryRepository.save(category);
    }


    // ============================
    // Get All Categories
    // ============================

    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }


    // ============================
    // Get Category By ID
    // ============================

    @Override
    public Category getCategoryById(Long id) {

        return categoryRepository.findById(id)
                .orElse(null);
    }


    // ============================
    // Delete Category
    // ============================

    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category Not Found"));


        // Check whether this category contains menus

        if (menuRepository.existsByCategoryId(id)) {

            throw new RuntimeException(
                    "Cannot delete category because menus exist in this category.");
        }


        // Delete category

        categoryRepository.delete(category);
    }
}
