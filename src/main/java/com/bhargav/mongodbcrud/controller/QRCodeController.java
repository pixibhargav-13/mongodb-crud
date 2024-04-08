package com.bhargav.mongodbcrud.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

@RestController
public class QRCodeController {

    @GetMapping("/generateQRCode")
    public ResponseEntity<byte[]> generateQRCode(
            @RequestParam String url,
            @RequestParam String imageType,
            @RequestParam String fileName) throws WriterException, IOException {

        int width = 200;
        int height = 200;

        Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height, hintMap);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        if ("svg".equalsIgnoreCase(imageType)) {
            // Generate SVG format
            String svgQRCode = generateSVG(bitMatrix);
            byteArrayOutputStream.write(svgQRCode.getBytes());
            imageType = "svg"; // Update imageType to ensure correct content type
        } else {
            // Generate other image formats (PNG, JPEG, etc.)
            BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            ImageIO.write(qrImage, imageType, byteArrayOutputStream);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("image/" + imageType));
        headers.setContentDispositionFormData("attachment", fileName + "." + imageType);

        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);
    }

    private String generateSVG(BitMatrix bitMatrix) {
        StringBuilder svgContent = new StringBuilder();
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();

        svgContent.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"")
                .append(width)
                .append("\" height=\"")
                .append(height)
                .append("\">\n");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (bitMatrix.get(x, y)) {
                    svgContent.append("<rect x=\"")
                            .append(x)
                            .append("\" y=\"")
                            .append(y)
                            .append("\" width=\"1\" height=\"1\" fill=\"black\"/>\n");
                }
            }
        }

        svgContent.append("</svg>");

        return svgContent.toString();
    }
}
