package com.akshay.qrrestaurantmenusystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.akshay.qrrestaurantmenusystem.entity.Category;
import com.akshay.qrrestaurantmenusystem.repository.CategoryRepository;
import com.akshay.qrrestaurantmenusystem.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}