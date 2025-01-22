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
            Document document = new Document(PageSize.A4, 60, 60, 20, 80); // Ajustar margen inferior
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // ===== IMAGEN DE FONDO =====
            addBackgroundImage(writer);

            // ===== ENCABEZADO =====
            addHeader(document, writer, request);

            // ===== CONTENIDO =====
            addContent(document, request);

            // ===== PIE DE PÁGINA =====
            addFooter(writer, request);

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }

    private void addBackgroundImage(PdfWriter writer) {
        try {
            // Cargar la imagen de fondo
            Image background = Image.getInstance("imagen/logo.jpg"); // Ruta de la imagen de fondo

            // Ajustar el tamaño de la imagen, por ejemplo, con un 80% del tamaño de la página
            float scaleWidth = PageSize.A4.getWidth() * 0.7f; // 80% del ancho de la página
            float scaleHeight = PageSize.A4.getHeight() * 0.7f; // 80% de la altura de la página
            background.scaleToFit(scaleWidth, scaleHeight); // Escalar la imagen

            // Posicionar la imagen de fondo en el centro de la página
            float xPosition = (PageSize.A4.getWidth() - scaleWidth) / 2; // Centrar horizontalmente
            float yPosition = (PageSize.A4.getHeight() - scaleHeight) / 2; // Centrar verticalmente
            background.setAbsolutePosition(xPosition, yPosition); // Posicionar la imagen

            // Configurar la opacidad de la imagen (por ejemplo, 5% de opacidad)
            float opacity = 0.05f; // Transparencia del 5% (valor entre 0 y 1)

            // Aplicar la transparencia
            PdfContentByte canvas = writer.getDirectContentUnder();
            PdfGState gState = new PdfGState();
            gState.setFillOpacity(opacity); // Establecer la opacidad
            canvas.setGState(gState);
            canvas.addImage(background);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
        }
    }



    private void addHeader(Document document, PdfWriter writer, PdfRequest request) throws Exception {
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


    private void addContent(Document document, PdfRequest request) throws DocumentException {
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD);
        Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        Font labelFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10.5f, Font.BOLD);
        Font valueFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10.5f, Font.NORMAL);

        // Títulos principales
        Paragraph title = new Paragraph("GERENCIA DE DESARROLLO ECONÓMICO Y TURISMO", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph subtitle = new Paragraph("Sub Gerencia de Comercio, Licencias y Promoción Empresarial", subTitleFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(20);
        document.add(subtitle);

        // Bloques de datos
        PdfPTable firstBlock = createDataTable(request, true, labelFont, valueFont);
        document.add(firstBlock);

        Paragraph licenseTitle = new Paragraph("LICENCIA DE FUNCIONAMIENTO INDETERMINADA", titleFont);
        licenseTitle.setAlignment(Element.ALIGN_CENTER);
        licenseTitle.setSpacingBefore(20);
        licenseTitle.setSpacingAfter(20);
        document.add(licenseTitle);

        PdfPTable secondBlock = createDataTable(request, false, labelFont, valueFont);
        document.add(secondBlock);

        // Texto de límites
        Paragraph limits = new Paragraph(request.getTextoLimites(), valueFont);
        limits.setSpacingBefore(10);
        limits.setSpacingAfter(20);
        limits.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(limits);

        // Fecha
        Paragraph date = new Paragraph("Nuevo Chimbote, " + request.getFechaGeneracion(), valueFont);
        date.setAlignment(Element.ALIGN_RIGHT);
        date.setSpacingAfter(30);
        document.add(date);

        // Firmas
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
    }

    private void addFooter(PdfWriter writer, PdfRequest request) {
        try {
            // Cargar la imagen del pie de página desde la ruta proporcionada
            Image footerImage = Image.getInstance(request.getFooterImagePath());

            // Escalar la imagen para que ocupe todo el ancho de la hoja
            footerImage.scaleAbsolute(PageSize.A4.getWidth(), 120); // Ajusta el alto a 50 puntos (puedes modificarlo si es necesario)

            // Posicionar la imagen al borde inferior de la página
            footerImage.setAbsolutePosition(0, 0); // 0 puntos desde el borde izquierdo y abajo

            // Agregar la imagen al lienzo del PDF
            PdfContentByte canvas = writer.getDirectContent();
            canvas.addImage(footerImage);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen del pie de página: " + e.getMessage());
        }
    }



    private PdfPTable createDataTable(PdfRequest request, boolean isFirstBlock, Font labelFont, Font valueFont) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{3, 0.3f, 6});

        if (isFirstBlock) {
            addRowToTable(table, "Expediente Nº", ":", request.getExpediente(), labelFont, valueFont);
            addRowToTable(table, "Resolución Nº", ":", request.getResolucion(), labelFont, valueFont);
            addRowToTable(table, "Licencia Nº", ":", request.getLicencia(), labelFont, valueFont);
            addRowToTable(table, "Nivel de Riesgo", ":", request.getNivelRiesgo(), labelFont, valueFont);
        } else {
            addRowToTable(table, "Titular", ":", request.getTitular(), labelFont, valueFont);
            addRowToTable(table, "RUC Nº", ":", request.getRuc(), labelFont, valueFont);
            addRowToTable(table, "Zonificación", ":", request.getZonificacion(), labelFont, valueFont);
            addRowToTable(table, "Nombre Comercial", ":", request.getNombreComercial(), labelFont, valueFont);
            addRowToTable(table, "Giro", ":", request.getGiro(), labelFont, valueFont);
            addRowToTable(table, "Actividad Comercial", ":", request.getActividadComercial(), labelFont, valueFont);
            addRowToTable(table, "Ubicado en", ":", request.getUbicacion(), labelFont, valueFont);
            addRowToTable(table, "Area comercial", ":", request.getAreaComercial(), labelFont, valueFont);
            addRowToTable(table, "Horario de Atención", ":", request.getHorarioAtencionInicio() + " a " + request.getHorarioAtencionFin(), labelFont, valueFont);
        }

        return table;
    }

    private void addRowToTable(PdfPTable table, String label, String separator, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(labelCell);

        PdfPCell separatorCell = new PdfPCell(new Phrase(separator, labelFont));
        separatorCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(separatorCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(valueCell);
    }
}
