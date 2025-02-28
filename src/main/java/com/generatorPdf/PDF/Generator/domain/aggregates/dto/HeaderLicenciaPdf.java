package com.generatorPdf.PDF.Generator.domain.aggregates.dto;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component

public class HeaderLicenciaPdf {
    public static void addHeader(Document document, PdfWriter writer, PdfRequest request) throws Exception {
        // Crear una tabla con tres columnas (centrada y alineada a la derecha)
        PdfPTable headerTable = new PdfPTable(3); // Tres columnas: izquierda, centro, derecha
        headerTable.setWidthPercentage(100); // Ocupa el 100% del ancho

        // ===== CELDA VACÍA A LA IZQUIERDA =====
        PdfPCell leftCell = new PdfPCell();
        leftCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(leftCell); // La columna de la izquierda permanece vacía

        // ===== LOGO CENTRADO =====
        try {
            Image logo = Image.getInstance("imagen/muni.png");
            logo.scaleToFit(150, 120); // Escalar a un tamaño uniforme
            PdfPCell logoCell = new PdfPCell(logo);
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setHorizontalAlignment(Element.ALIGN_CENTER); // Alineación centrada
            logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE); // Alineación vertical media
            headerTable.addCell(logoCell); // Agregar el logo al centro
        } catch (Exception e) {
            System.err.println("Error al cargar el logo: " + e.getMessage());
        }

        // ===== QR A LA DERECHA =====
        try {
            if (request.getQrCodeUrl() != null && !request.getQrCodeUrl().isEmpty()) {
                URL qrUrl = new URL(request.getQrCodeUrl());
                Image qr = Image.getInstance(qrUrl);
                qr.scaleToFit(70, 70); // QR un poco más grande que el logo
                PdfPCell qrCell = new PdfPCell(qr);
                qrCell.setBorder(Rectangle.NO_BORDER);
                qrCell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Alineación a la derecha
                qrCell.setVerticalAlignment(Element.ALIGN_MIDDLE); // Alineación vertical media
                headerTable.addCell(qrCell); // Agregar el QR a la derecha
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el QR: " + e.getMessage());
        }

        // Agregar la tabla al documento
        document.add(headerTable);
    }
}
