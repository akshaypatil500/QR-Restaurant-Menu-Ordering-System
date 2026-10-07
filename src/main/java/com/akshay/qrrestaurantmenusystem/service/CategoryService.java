package com.akshay.qrrestaurantmenusystem.service;

import java.util.List;

import com.akshay.qrrestaurantmenusystem.entity.Category;

public interface CategoryService {

    Category saveCategory(Category category);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category updateCategory(Category category);

    void deleteCategory(Long id);
}