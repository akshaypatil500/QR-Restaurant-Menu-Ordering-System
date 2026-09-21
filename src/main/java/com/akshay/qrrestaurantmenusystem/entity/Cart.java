package com.akshay.qrrestaurantmenusystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Restaurant Table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private RestaurantTable table;

    // Menu Item
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    // Quantity
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(nullable = false)
    private Integer quantity = 1;

    // Price of one menu item
    @Column(nullable = false)
    private Double price;

    // Total Price
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    public Cart() {
    }

    public Cart(Long id,
                RestaurantTable table,
                Menu menu,
                Integer quantity,
                Double price,
                Double totalPrice) {

        this.id = id;
        this.table = table;
        this.menu = menu;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public RestaurantTable getTable() {
        return table;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setTable(RestaurantTable table) {
        this.table = table;
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

    @Override
    public String toString() {
        return "Cart [id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPrice=" + totalPrice + "]";
    }
}