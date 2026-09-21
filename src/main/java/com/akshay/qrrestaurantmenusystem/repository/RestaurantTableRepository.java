package com.akshay.qrrestaurantmenusystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshay.qrrestaurantmenusystem.entity.RestaurantTable;

public interface RestaurantTableRepository
        extends JpaRepository<RestaurantTable, Long> {

    // Check duplicate table number
    boolean existsByTableNumber(Integer tableNumber);

    // Find table by table number
    Optional<RestaurantTable> findByTableNumber(Integer tableNumber);

}