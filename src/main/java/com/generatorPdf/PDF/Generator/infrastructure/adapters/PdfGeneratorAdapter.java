package com.generatorPdf.PDF.Generator.infrastructure.adapters;

import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfRequest;
import com.generatorPdf.PDF.Generator.domain.ports.out.PDFServOut;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

@Component
public class PdfGeneratorAdapter implements PDFServOut {

    @Override
    public void createPdf(PdfRequest request, String filePath) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            // Crear el documento PDF con márgenes personalizados
            Document document = new Document(PageSize.A4, 80, 80, 40, 40);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Agregar imagen de fondo con el logo
            try {
                // Cargar la imagen de fondo
                Image background = Image.getInstance("imagen/logo.jpg"); // Ruta local fija del logo

                // Escalar la imagen para hacerla más pequeña
                background.scaleAbsolute(400, 400);

                // Posicionar la imagen en el centro
                float xPos = (PageSize.A4.getWidth() - 400) / 2;
                float yPos = (PageSize.A4.getHeight() - 200) / 2;
                background.setAbsolutePosition(xPos, yPos);

                // Crear un estado gráfico para aplicar transparencia
                PdfContentByte canvas = writer.getDirectContentUnder();
                PdfGState gState = new PdfGState();
                gState.setFillOpacity(0.2f); // Transparencia (0.0 completamente transparente, 1.0 completamente opaco)
                canvas.setGState(gState);

                // Agregar la imagen al canvas
                canvas.addImage(background);
            } catch (Exception e) {
                System.err.println("Error al cargar el fondo del logo: " + e.getMessage());
            }

            // Agregar imágenes (logo)
            Image logo = Image.getInstance("imagen/muni.png");

            // Logo de la municipalidad
            logo.setAbsolutePosition(200, 750); // Ajustar posición
            logo.scaleToFit(150, 150);
            document.add(logo);

            if (request.getQrCodeUrl() != null && !request.getQrCodeUrl().isEmpty()) {
                try {
                    URL url = new URL(request.getQrCodeUrl());
                    Image qr = Image.getInstance(url);
                    qr.setAbsolutePosition(450, 750); // Posición del QR
                    qr.scaleToFit(100, 100);
                    document.add(qr);
                } catch (Exception e) {
                    System.err.println("Error al cargar el QR: " + e.getMessage());
                }
            }

            // Espacio adicional
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));


            // Títulos principales
            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font subtitleFont = new Font(Font.HELVETICA, 12, Font.NORMAL);

            Paragraph title = new Paragraph("GERENCIA DE DESARROLLO ECONÓMICO Y TURISMO", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subtitle = new Paragraph("Sub Gerencia de Comercio, Licencias y Promoción Empresarial", subtitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20);
            document.add(subtitle);

            // Contenido principal
            Font labelFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font valueFont = new Font(Font.HELVETICA, 12, Font.NORMAL);

            addDataSection(document, "Expediente Nº:", request.getExpediente(), labelFont, valueFont);
            addDataSection(document, "Resolución Nº:", request.getResolucion(), labelFont, valueFont);
            addDataSection(document, "Licencia Nº:", request.getLicencia(), labelFont, valueFont);
            addDataSection(document, "Nivel de Riesgo:", request.getNivelRiesgo(), labelFont, valueFont);


            // Subtítulo de la licencia
            Paragraph licenseTitle = new Paragraph("LICENCIA DE FUNCIONAMIENTO INDETERMINADA", titleFont);
            licenseTitle.setAlignment(Element.ALIGN_CENTER);
            licenseTitle.setSpacingBefore(20);
            licenseTitle.setSpacingAfter(20);
            document.add(licenseTitle);



            addDataSection(document, "Titular:", request.getTitular(), labelFont, valueFont);
            addDataSection(document, "RUC Nº:", request.getRuc(), labelFont, valueFont);
            addDataSection(document, "Zonificación:", request.getZonificacion(), labelFont, valueFont);
            addDataSection(document, "Nombre Comercial:", request.getNombreComercial(), labelFont, valueFont);
            addDataSection(document, "Giro:", request.getGiro(), labelFont, valueFont);
            addDataSection(document, "Ubicación:", request.getUbicacion(), labelFont, valueFont);
            addDataSection(document, "Horario de Atención:", request.getHorarioAtencionInicio() + " a " + request.getHorarioAtencionFin(), labelFont, valueFont);

            // Texto de límites
            Paragraph limits = new Paragraph(request.getTextoLimites(), valueFont);
            limits.setSpacingBefore(10);
            limits.setSpacingAfter(20);
            limits.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(limits);

            // Fecha de generación
            Paragraph date = new Paragraph("Nuevo Chimbote, " + request.getFechaGeneracion(), valueFont);
            date.setAlignment(Element.ALIGN_RIGHT);
            date.setSpacingAfter(30);
            document.add(date);

            // Espacios para firmas
            PdfPTable signatureTable = new PdfPTable(2);
            signatureTable.setWidthPercentage(100);
            signatureTable.setSpacingBefore(40);

            PdfPCell gerenteCell = new PdfPCell(new Phrase("_____________________________\nFirma Gerente", valueFont));
            gerenteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            gerenteCell.setBorder(Rectangle.NO_BORDER);
            signatureTable.addCell(gerenteCell);

            PdfPCell subGerenteCell = new PdfPCell(new Phrase("_____________________________\nFirma Sub Gerente", valueFont));
            subGerenteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            subGerenteCell.setBorder(Rectangle.NO_BORDER);
            signatureTable.addCell(subGerenteCell);

            document.add(signatureTable);

            // Cerrar documento
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }

    private void addDataSection(Document document, String label, String value, Font labelFont, Font valueFont) throws DocumentException {
        if (value != null && !value.isEmpty()) {
            Paragraph paragraph = new Paragraph();
            paragraph.add(new Chunk(label, labelFont));
            paragraph.add(new Chunk(" " + value, valueFont));
            paragraph.setSpacingBefore(5);
            paragraph.setSpacingAfter(5);
            document.add(paragraph);
        }
    }

}


