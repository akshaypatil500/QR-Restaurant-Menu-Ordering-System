package com.akshay.qrrestaurantmenusystem.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String saveImage(MultipartFile file);

    void deleteImage(String fileName);

}