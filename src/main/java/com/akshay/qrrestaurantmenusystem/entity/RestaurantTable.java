package com.akshay.qrrestaurantmenusystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "restaurant_tables")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Table Number
    @NotNull(message = "Table number is required")
    @Column(name = "table_number", nullable = false, unique = true)
    private Integer tableNumber;

    // Seating Capacity
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Column(nullable = false)
    private Integer capacity;

    // AVAILABLE / OCCUPIED / RESERVED
    @Column(nullable = false, length = 20)
    private String status = "AVAILABLE";

    // Active / Inactive
    @Column(nullable = false)
    private Boolean active = true;

    // QR Code Image Name
    @Column(name = "qr_code_image")
    private String qrCodeImage;

    public RestaurantTable() {
    }

    public RestaurantTable(Long id, Integer tableNumber, Integer capacity,
                           String status, Boolean active, String qrCodeImage) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
        this.active = active;
        this.qrCodeImage = qrCodeImage;
    }

    public Long getId() {
        return id;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getActive() {
        return active;
    }

    public String getQrCodeImage() {
        return qrCodeImage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setQrCodeImage(String qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }

    @Override
    public String toString() {
        return "RestaurantTable [id=" + id +
                ", tableNumber=" + tableNumber +
                ", capacity=" + capacity +
                ", status=" + status +
                ", active=" + active +
                ", qrCodeImage=" + qrCodeImage + "]";
    }
}