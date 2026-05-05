package com.example.BackOffice_Visa.service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class QrCodeService {

    @Value("${app.front-url:http://localhost:5173}")
    private String frontUrl;

    private static final int QR_WIDTH = 250;
    private static final int QR_HEIGHT = 250;

    /**
     * Génère l'URL du FrontReact pour voir le détail d'une demande.
     */
    public String buildDemandeDetailUrl(Integer demandeId) {
        return frontUrl + "/demandes/" + demandeId;
    }

    /**
     * Génère un QR code en Base64 (data URI) contenant le lien vers le détail
     * de la demande sur le FrontReact.
     */
    public String generateQrCodeBase64(Integer demandeId) {
        String url = buildDemandeDetailUrl(demandeId);
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.MARGIN, 2);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            return "data:image/png;base64," + base64;
        } catch (WriterException | java.io.IOException e) {
            throw new RuntimeException("Erreur lors de la génération du QR code pour la demande #" + demandeId, e);
        }
    }

    /**
     * Génère le QR code en bytes PNG bruts (pour un endpoint image).
     */
    public byte[] generateQrCodeBytes(Integer demandeId) {
        String url = buildDemandeDetailUrl(demandeId);
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.MARGIN, 2);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            return outputStream.toByteArray();
        } catch (WriterException | java.io.IOException e) {
            throw new RuntimeException("Erreur lors de la génération du QR code pour la demande #" + demandeId, e);
        }
    }
}
