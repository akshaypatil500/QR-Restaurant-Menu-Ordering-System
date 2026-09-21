package com.akshay.qrrestaurantmenusystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.akshay.qrrestaurantmenusystem.entity.Cart;
import com.akshay.qrrestaurantmenusystem.entity.Menu;
import com.akshay.qrrestaurantmenusystem.entity.RestaurantTable;
import com.akshay.qrrestaurantmenusystem.repository.CartRepository;
import com.akshay.qrrestaurantmenusystem.repository.MenuRepository;
import com.akshay.qrrestaurantmenusystem.repository.RestaurantTableRepository;
import com.akshay.qrrestaurantmenusystem.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final RestaurantTableRepository tableRepository;
    private final MenuRepository menuRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           RestaurantTableRepository tableRepository,
                           MenuRepository menuRepository) {

        this.cartRepository = cartRepository;
        this.tableRepository = tableRepository;
        this.menuRepository = menuRepository;
    }

    // ==========================================
    // ADD TO CART
    // ==========================================

    @Override
    public void addToCart(Long tableId, Long menuId) {

        RestaurantTable table = tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table Not Found"));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu Not Found"));

        // Check if item already exists
        Cart existingCart = cartRepository
                .findByTableIdAndMenuId(tableId, menuId)
                .orElse(null);

        if (existingCart != null) {

            existingCart.setQuantity(existingCart.getQuantity() + 1);

            existingCart.setTotalPrice(
                    existingCart.getQuantity() * existingCart.getPrice());

            cartRepository.save(existingCart);

            return;
        }

        Cart cart = new Cart();

        cart.setTable(table);
        cart.setMenu(menu);

        cart.setQuantity(1);

        cart.setPrice(menu.getPrice());

        cart.setTotalPrice(menu.getPrice());

        cartRepository.save(cart);
    }

    // ==========================================
    // GET CART ITEMS
    // ==========================================

    @Override
    public List<Cart> getCartItems(Long tableId) {

        return cartRepository.findByTableId(tableId);

    }

    // ==========================================
    // INCREASE QUANTITY
    // ==========================================

    @Override
    public void increaseQuantity(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart Item Not Found"));

        cart.setQuantity(cart.getQuantity() + 1);

        cart.setTotalPrice(cart.getQuantity() * cart.getPrice());

        cartRepository.save(cart);

    }

    // ==========================================
    // DECREASE QUANTITY
    // ==========================================

    @Override
    public void decreaseQuantity(Long cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart Item Not Found"));

        if (cart.getQuantity() > 1) {

            cart.setQuantity(cart.getQuantity() - 1);

            cart.setTotalPrice(cart.getQuantity() * cart.getPrice());

            cartRepository.save(cart);

        }

    }

    // ==========================================
    // REMOVE ITEM
    // ==========================================

    @Override
    public void removeItem(Long cartId) {

        cartRepository.deleteById(cartId);

    }

    // ==========================================
    // GRAND TOTAL
    // ==========================================

    @Override
    public Double getGrandTotal(Long tableId) {

        return getCartItems(tableId)
                .stream()
                .mapToDouble(Cart::getTotalPrice)
                .sum();

    }

    // ==========================================
    // CLEAR CART
    // ==========================================

    @Override
    public void clearCart(Long tableId) {

        cartRepository.deleteByTableId(tableId);

    }

}