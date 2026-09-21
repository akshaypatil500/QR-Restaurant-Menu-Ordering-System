package com.akshay.qrrestaurantmenusystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    // ==========================================
    // Primary Key
    // ==========================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================================
    // Restaurant Order
    // Many OrderItems -> One Order
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private RestaurantOrder restaurantOrder;

    // ==========================================
    // Ordered Menu Item
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    // ==========================================
    // Quantity
    // ==========================================

    @Column(nullable = false)
    private Integer quantity;

    // ==========================================
    // Price Of One Item
    // ==========================================

    @Column(nullable = false)
    private Double price;

    // ==========================================
    // Total Price
    // quantity × price
    // ==========================================

    @Column(nullable = false)
    private Double totalPrice;

    // ==========================================
    // Default Constructor
    // ==========================================

    public OrderItem() {
    }

    // ==========================================
    // Parameterized Constructor
    // ==========================================

    public OrderItem(Long id,
                     RestaurantOrder restaurantOrder,
                     Menu menu,
                     Integer quantity,
                     Double price,
                     Double totalPrice) {

        this.id = id;
        this.restaurantOrder = restaurantOrder;
        this.menu = menu;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    // ==========================================
    // Getters
    // ==========================================

    public Long getId() {
        return id;
    }

    public RestaurantOrder getRestaurantOrder() {
        return restaurantOrder;
    }

    public Menu getMenu() {
        return menu;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    // ==========================================
    // Setters
    // ==========================================

    public void setId(Long id) {
        this.id = id;
    }

    public void setRestaurantOrder(RestaurantOrder restaurantOrder) {
        this.restaurantOrder = restaurantOrder;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // ==========================================
    // toString()
    // ==========================================

    @Override
    public String toString() {
        return "OrderItem [id=" + id
                + ", quantity=" + quantity
                + ", price=" + price
                + ", totalPrice=" + totalPrice + "]";
    }

}