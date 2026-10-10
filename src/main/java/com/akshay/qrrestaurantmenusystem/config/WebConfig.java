package com.akshay.qrrestaurantmenusystem.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Get the menu image folder path from application.properties
    @Value("${app.file.upload-dir}")
    private String uploadDirectory;

    // Get the QR image folder path; use the default path if not configured
    @Value("${app.qr.upload-dir:uploads/qr/}")
    private String qrUploadDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Get the absolute path of the menu image folder
        String uploadPath =
                new File(uploadDirectory).getAbsolutePath();

        // Map menu image URLs to the actual menu image folder
        registry.addResourceHandler("/uploads/menu/**")
                .addResourceLocations(
                        "file:" + uploadPath + File.separator
                );

        // Get the absolute path of the QR image folder
        String qrUploadPath =
                new File(qrUploadDirectory).getAbsolutePath();

        // Map QR image URLs to the actual QR image folder
        registry.addResourceHandler("/uploads/qr/**")
                .addResourceLocations(
                        "file:" + qrUploadPath + File.separator
                );
    }
}
