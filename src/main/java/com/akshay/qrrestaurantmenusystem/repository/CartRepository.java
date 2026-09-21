package com.akshay.qrrestaurantmenusystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshay.qrrestaurantmenusystem.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // Get all cart items of a table
    List<Cart> findByTableId(Long tableId);

    // Check if same menu already exists in cart
    Optional<Cart> findByTableIdAndMenuId(Long tableId, Long menuId);

    // Delete all items after order placed
    void deleteByTableId(Long tableId);

}