package com.akshay.qrrestaurantmenusystem.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.file.upload-dir}")
    private String uploadDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String uploadPath =
                new File(uploadDirectory)
                        .getAbsolutePath();

        registry.addResourceHandler("/uploads/menu/**")
                .addResourceLocations(
                        "file:" + uploadPath + File.separator
                );
    }
}

