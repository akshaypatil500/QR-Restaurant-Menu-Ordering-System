package com.akshay.qrrestaurantmenusystem.service;

import java.util.List;

import com.akshay.qrrestaurantmenusystem.entity.RestaurantTable;

public interface RestaurantTableService {

    RestaurantTable saveTable(RestaurantTable table);

    RestaurantTable updateTable(RestaurantTable table);

    List<RestaurantTable> getAllTables();

    RestaurantTable getTableById(Long id);

    void deleteTable(Long id);
    
    void regenerateQRCode(Long tableId);

}