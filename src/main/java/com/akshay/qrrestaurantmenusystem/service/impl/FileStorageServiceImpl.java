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

            File directory = new File(uploadDirectory);

            if (!directory.exists()) {
                directory.mkdirs();
            }


            String originalFileName = file.getOriginalFilename();

            String extension = originalFileName
                    .substring(originalFileName.lastIndexOf("."));


            String fileName = UUID.randomUUID().toString() + extension;


            file.transferTo(new File(uploadDirectory + fileName));


            return fileName;


        } catch (IOException e) {

            throw new RuntimeException("Image upload failed", e);
        }
    }


    @Override
    public void deleteImage(String fileName) {

        File file = new File(uploadDirectory + fileName);

        if(file.exists()) {
            file.delete();
        }

    }

}