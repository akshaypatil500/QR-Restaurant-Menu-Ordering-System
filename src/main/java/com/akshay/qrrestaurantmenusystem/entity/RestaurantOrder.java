package com.akshay.qrrestaurantmenusystem.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class RestaurantOrder {

    // ==========================================
    // Primary Key
    // ==========================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================================
    // Restaurant Table
    // ==========================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = false)
    private RestaurantTable table;

    // ==========================================
    // Order Date & Time
    // ==========================================

    @Column(nullable = false)
    private LocalDateTime orderDate;

    // ==========================================
    // Order Status
    // Pending
    // Preparing
    // Ready
    // Served
    // Paid
    // ==========================================

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // ==========================================
    // Grand Total
    // ==========================================

    @Column(nullable = false)
    private Double grandTotal;

    // ==========================================
    // Order Items
    // ==========================================

    @OneToMany(
            mappedBy = "restaurantOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // ==========================================
    // Default Constructor
    // ==========================================

    public RestaurantOrder() {
    }

    // ==========================================
    // Parameterized Constructor
    // ==========================================

    public RestaurantOrder(Long id,
                           RestaurantTable table,
                           LocalDateTime orderDate,
                           OrderStatus status,
                           Double grandTotal,
                           List<OrderItem> orderItems) {

        this.id = id;
        this.table = table;
        this.orderDate = orderDate;
        this.status = status;
        this.grandTotal = grandTotal;
        this.orderItems = orderItems;
    }

    // ==========================================
    // Getters
    // ==========================================

    public Long getId() {
        return id;
    }

    public RestaurantTable getTable() {
        return table;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    // ==========================================
    // Setters
    // ==========================================

    public void setId(Long id) {
        this.id = id;
    }

    public void setTable(RestaurantTable table) {
        this.table = table;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // ==========================================
    // toString()
    // ==========================================

    @Override
    public String toString() {

        return "RestaurantOrder [id=" + id
                + ", orderDate=" + orderDate
                + ", status=" + status
                + ", grandTotal=" + grandTotal + "]";
    }

}