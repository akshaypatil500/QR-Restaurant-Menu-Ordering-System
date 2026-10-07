package com.akshay.qrrestaurantmenusystem.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.akshay.qrrestaurantmenusystem.service.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${app.file.upload-dir}")
    private String uploadDirectory;

    @Override
    public String saveImage(MultipartFile file) {

        try {

            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Please select an image.");
            }

            File directory = new File(uploadDirectory);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFileName = file.getOriginalFilename();

            if (originalFileName == null
                    || originalFileName.trim().isEmpty()) {

                throw new IOException("Invalid image file name.");
            }

            String extension = "";

            int dotIndex = originalFileName.lastIndexOf(".");

            if (dotIndex >= 0) {
                extension = originalFileName.substring(dotIndex);
            }

            String fileName =
                    UUID.randomUUID().toString() + extension;

            File destinationFile =
                    new File(directory, fileName);
            
            //directory = src/main/resources/static/uploads/menu
            // fileName  = 550e8400-e29b-41d4-a716-446655440000.jpg

            file.transferTo(destinationFile);

            return fileName;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Image upload failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteImage(String fileName) {

        if (fileName == null || fileName.trim().isEmpty()) {
            return;
        }

        File file =
                new File(uploadDirectory, fileName);

        if (file.exists()) {
            file.delete();
        }
    }
}

