package com.akshay.qrrestaurantmenusystem.service;

import java.util.List;

import com.akshay.qrrestaurantmenusystem.entity.Cart;

public interface CartService {

    // Add Item To Cart
    void addToCart(Long tableId, Long menuId);

    // Get Cart Items
    List<Cart> getCartItems(Long tableId);

    // Increase Quantity
    void increaseQuantity(Long cartId);

    // Decrease Quantity
    void decreaseQuantity(Long cartId);

    // Remove Item
    void removeItem(Long cartId);

    // Grand Total
    Double getGrandTotal(Long tableId);

    // Clear Cart
    void clearCart(Long tableId);

}