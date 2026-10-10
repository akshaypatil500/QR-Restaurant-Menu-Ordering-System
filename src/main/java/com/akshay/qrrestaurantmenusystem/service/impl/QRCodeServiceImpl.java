
package com.akshay.qrrestaurantmenusystem.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.akshay.qrrestaurantmenusystem.service.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    @Value("${app.qr.upload-dir:uploads/qr/}")
    private String uploadDirectory;

    @Override
    public String generateQRCode(Long tableId) {

        try {
            // QR image save folder
            Path directory = Paths.get(uploadDirectory)
                    .toAbsolutePath()
                    .normalize();

            // Create folder if it does not exist
            Files.createDirectories(directory);

            // Customer menu URL
            String qrContent =
                    "http://localhost:8080/customer/menu/" + tableId;

            // QR image name
            String fileName = "table-" + tableId + ".png";

            // Full image path
            Path path = directory.resolve(fileName);

            // Generate QR code
            BitMatrix bitMatrix =
                    new MultiFormatWriter().encode(
                            qrContent,
                            BarcodeFormat.QR_CODE,
                            300,
                            300);

            // Save QR image
            MatrixToImageWriter.writeToPath(
                    bitMatrix,
                    "PNG",
                    path);

            // Return image filename
            return fileName;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to Generate QR Code",
                    e);
        }
    }
}
