package com.akshay.qrrestaurantmenusystem.service.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.akshay.qrrestaurantmenusystem.service.QRCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    // QR Image Save Folder
    private final String uploadDirectory =
            "src/main/resources/static/uploads/qr/";

    @Override
    public String generateQRCode(Long tableId) {

        try {

            // Create Folder if not exists
            File directory = new File(uploadDirectory);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            // QR Content (Customer Menu URL)
            String qrContent =
                    "http://localhost:8080/customer/menu/" + tableId;

            // QR Image Name
            String fileName =
                    "table-" + tableId + ".png";

            // Full Path
            Path path = Paths.get(uploadDirectory + fileName);

            // Generate QR
            BitMatrix bitMatrix =
                    new MultiFormatWriter().encode(
                            qrContent,
                            BarcodeFormat.QR_CODE,
                            300,
                            300);

            // Save PNG Image
            MatrixToImageWriter.writeToPath(
                    bitMatrix,
                    "PNG",
                    path);

            // Return File Name
            return fileName;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to Generate QR Code",
                    e);

        }

    }

}