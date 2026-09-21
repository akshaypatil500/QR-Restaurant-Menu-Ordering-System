package com.akshay.qrrestaurantmenusystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.akshay.qrrestaurantmenusystem.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    // Constructor Injection
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ==========================================
    // ADD TO CART
    // URL : /cart/add/{tableId}/{menuId}
    // ==========================================

    @GetMapping("/add/{tableId}/{menuId}")
    public String addToCart(@PathVariable Long tableId,
                            @PathVariable Long menuId) {

        cartService.addToCart(tableId, menuId);

        return "redirect:/customer/menu/" + tableId;
    }

    // ==========================================
    // VIEW CART
    // URL : /cart/{tableId}
    // ==========================================

    @GetMapping("/{tableId}")
    public String viewCart(@PathVariable Long tableId,
                           Model model) {

        model.addAttribute("cartItems",
                cartService.getCartItems(tableId));

        model.addAttribute("grandTotal",
                cartService.getGrandTotal(tableId));

        model.addAttribute("tableId", tableId);

        return "customer/cart";
    }

    // ==========================================
    // INCREASE QUANTITY
    // ==========================================

    @GetMapping("/increase/{cartId}/{tableId}")
    public String increaseQuantity(@PathVariable Long cartId,
                                   @PathVariable Long tableId) {

        cartService.increaseQuantity(cartId);

        return "redirect:/cart/" + tableId;
    }

    // ==========================================
    // DECREASE QUANTITY
    // ==========================================

    @GetMapping("/decrease/{cartId}/{tableId}")
    public String decreaseQuantity(@PathVariable Long cartId,
                                   @PathVariable Long tableId) {

        cartService.decreaseQuantity(cartId);

        return "redirect:/cart/" + tableId;
    }

    // ==========================================
    // REMOVE ITEM
    // ==========================================

    @GetMapping("/remove/{cartId}/{tableId}")
    public String removeItem(@PathVariable Long cartId,
                             @PathVariable Long tableId) {

        cartService.removeItem(cartId);

        return "redirect:/cart/" + tableId;
    }

}