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

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               MenuRepository menuRepository) {
        this.categoryRepository = categoryRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public Category saveCategory(Category category) {

        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Category already exists.");
        }

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {

        return categoryRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Category updateCategory(Category category) {

        Category oldCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() ->
                        new RuntimeException("Category Not Found"));

        // Check duplicate name except current category
        if (categoryRepository.existsByNameAndIdNot(
                category.getName(),
                category.getId())) {

            throw new RuntimeException("Category already exists.");
        }

        oldCategory.setName(category.getName());
        oldCategory.setDescription(category.getDescription());

        return categoryRepository.save(oldCategory);
    }

    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category Not Found"));

        if (menuRepository.existsByCategoryId(id)) {

            throw new RuntimeException(
                    "Cannot delete category because menus exist in this category.");
        }

        categoryRepository.delete(category);
    }
}