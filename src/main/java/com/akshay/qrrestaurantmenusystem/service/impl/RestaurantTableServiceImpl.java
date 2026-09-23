package com.akshay.qrrestaurantmenusystem.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import com.akshay.qrrestaurantmenusystem.entity.RestaurantTable;
import com.akshay.qrrestaurantmenusystem.repository.RestaurantOrderRepository;
import com.akshay.qrrestaurantmenusystem.repository.RestaurantTableRepository;
import com.akshay.qrrestaurantmenusystem.service.QRCodeService;
import com.akshay.qrrestaurantmenusystem.service.RestaurantTableService;

@Service
public class RestaurantTableServiceImpl implements RestaurantTableService {

    private final RestaurantTableRepository tableRepository;
    private final QRCodeService qrCodeService;
    private final RestaurantOrderRepository orderRepository;

    // QR Image Folder
    private final String qrDirectory =
            "src/main/resources/static/uploads/qr/";

    // Constructor Injection
    public RestaurantTableServiceImpl(
            RestaurantTableRepository tableRepository,
            QRCodeService qrCodeService,
            RestaurantOrderRepository orderRepository) {

        this.tableRepository = tableRepository;
        this.qrCodeService = qrCodeService;
        this.orderRepository = orderRepository;
    }

    // ==========================================
    // SAVE TABLE + AUTO GENERATE QR
    // ==========================================

    @Override
    public RestaurantTable saveTable(RestaurantTable table) {

        // Duplicate Table Number Check
        if (tableRepository.existsByTableNumber(table.getTableNumber())) {

            throw new RuntimeException("Table Number Already Exists.");

        }

        // Save Table
        RestaurantTable savedTable = tableRepository.save(table);

        // Generate QR Automatically
        String qrImage =
                qrCodeService.generateQRCode(savedTable.getId());

        // Save QR Image Name
        savedTable.setQrCodeImage(qrImage);

        return tableRepository.save(savedTable);
    }

    // ==========================================
    // UPDATE TABLE
    // ==========================================

    @Override
    public RestaurantTable updateTable(RestaurantTable table) {

        RestaurantTable oldTable =
                tableRepository.findById(table.getId())
                        .orElseThrow(() ->
                                new RuntimeException("Table Not Found"));

        // Duplicate Table Number Check
        if (!oldTable.getTableNumber().equals(table.getTableNumber())
                && tableRepository.existsByTableNumber(table.getTableNumber())) {

            throw new RuntimeException("Table Number Already Exists.");

        }

        // Keep Existing QR Image
        table.setQrCodeImage(oldTable.getQrCodeImage());

        return tableRepository.save(table);
    }

    // ==========================================
    // GET ALL TABLES
    // ==========================================

    @Override
    public List<RestaurantTable> getAllTables() {

        return tableRepository.findAll();

    }

    // ==========================================
    // GET TABLE BY ID
    // ==========================================

    @Override
    public RestaurantTable getTableById(Long id) {

        return tableRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Table Not Found"));

    }

    // ==========================================
    // DELETE TABLE + DELETE QR IMAGE
    // ==========================================

    @Override
    public void deleteTable(Long id) {

        RestaurantTable table = tableRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Table Not Found"));

        // Order history check
        if (orderRepository.existsByTable_Id(id)) {

            throw new RuntimeException(
                    "Cannot delete table because order history exists for this table.");
        }

        // Delete QR Image
        if (table.getQrCodeImage() != null) {

            File qrFile = new File(
                    qrDirectory + table.getQrCodeImage());

            if (qrFile.exists()) {
                qrFile.delete();
            }
        }

        // Delete Table
        tableRepository.delete(table);
    }
    
    @Override
    public void regenerateQRCode(Long tableId) {

        RestaurantTable table = tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table Not Found"));

        String qrImage = qrCodeService.generateQRCode(table.getId());

        table.setQrCodeImage(qrImage);

        tableRepository.save(table);
    }

}