package com.akshay.qrrestaurantmenusystem.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.akshay.qrrestaurantmenusystem.service.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final String uploadDirectory =
            "src/main/resources/static/uploads/menu/";

    @Override
    public String saveImage(MultipartFile file) {

        try {

            // Check whether file is actually selected
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Please select an image.");
            }

            // Create upload directory if it does not exist
            File directory = new File(uploadDirectory);

            if (!directory.exists()) {
                boolean created = directory.mkdirs();

                if (!created) {
                    throw new IOException(
                            "Unable to create upload directory: "
                                    + directory.getAbsolutePath());
                }
            }

            // Get original file name
            String originalFileName = file.getOriginalFilename();

            if (originalFileName == null
                    || originalFileName.trim().isEmpty()) {
                throw new IOException("Invalid image file name.");
            }

            // Find file extension
            String extension = "";

            int dotIndex = originalFileName.lastIndexOf(".");

            if (dotIndex >= 0) {
                extension = originalFileName.substring(dotIndex);
            }

            // Generate unique file name
            String fileName =
                    UUID.randomUUID().toString() + extension;

            // Create destination file
            File destinationFile =
                    new File(directory, fileName);

            // Save uploaded file
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
                new File(uploadDirectory + fileName);

        if (file.exists()) {
            file.delete();
        }
    }
}

