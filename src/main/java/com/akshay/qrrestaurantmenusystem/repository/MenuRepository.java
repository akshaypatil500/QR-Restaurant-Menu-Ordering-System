package com.akshay.qrrestaurantmenusystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akshay.qrrestaurantmenusystem.entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    // Get only available menu items
    List<Menu> findByAvailableTrue();

    // Get Available Menus By Category
    List<Menu> findByCategoryIdAndAvailableTrue(Long categoryId);

    // Check whether a category contains any menu
    boolean existsByCategoryId(Long categoryId);
}

