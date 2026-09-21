package com.akshay.qrrestaurantmenusystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshay.qrrestaurantmenusystem.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}