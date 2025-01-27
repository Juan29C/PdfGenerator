package com.generatorPdf.PDF.Generator.infrastructure.adapters;

import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfRequest;
import com.generatorPdf.PDF.Generator.domain.ports.out.PDFServOut;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            float opacity = 0.08f; // Transparencia del 5% (valor entre 0 y 1)

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
        Font titleFontMax = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
        Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        Font labelFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10.5f, Font.BOLD);
        Font valueFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10.5f, Font.NORMAL);
        //Font valueFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10.5f, Font.NORMAL);

        // Títulos principales
        Paragraph title = new Paragraph("GERENCIA DE DESARROLLO ECONÓMICO Y TURISMO", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph subtitle = new Paragraph("Sub Gerencia de Comercio, Licencias y Promoción Empresarial", subTitleFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(5);
        document.add(subtitle);

        // Bloques de datos
        PdfPTable firstBlock = createDataTable(request, true, labelFont, valueFont);
        document.add(firstBlock);


        // Texto de cumplimiento
        Paragraph realized = new Paragraph(request.getTextoCumplimiento(), valueFont);
        realized.setLeading(0, 1.15f);
        realized.setSpacingBefore(5);
        realized.setSpacingAfter(5);
        realized.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(realized);


        Paragraph licenseTitle = new Paragraph("LICENCIA DE FUNCIONAMIENTO \n INDETERMINADA", titleFontMax);
        licenseTitle.setLeading(0, 1.15f);
        licenseTitle.setAlignment(Element.ALIGN_CENTER);
        licenseTitle.setSpacingBefore(0);
        licenseTitle.setSpacingAfter(5);
        document.add(licenseTitle);

        PdfPTable secondBlock = createDataTable(request, false, labelFont, valueFont);
        document.add(secondBlock);

        // Texto de límites
        Paragraph limits = new Paragraph(request.getTextoLimites(), valueFont);
        limits.setLeading(0, 1.15f);
        limits.setSpacingBefore(8);
        limits.setSpacingAfter(20);
        limits.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(limits);

        // Fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("d 'de' MMMM 'de' yyyy");
        String formattedDate = dateFormat.format(new Date()); // Genera la fecha actual en el formato deseado
        Paragraph date = new Paragraph("Nuevo Chimbote, " + formattedDate, valueFont);
        date.setAlignment(Element.ALIGN_RIGHT);
        date.setSpacingAfter(20);
        document.add(date);

        // Firmas
        PdfPTable signatureTable = new PdfPTable(2);
        signatureTable.setWidthPercentage(100);
        signatureTable.setSpacingBefore(30);

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
            Image footerImage = Image.getInstance("imagen/pie_pagina.png    ");

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
        PdfPTable table = new PdfPTable(3); // Tabla con 3 columnas como base
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{1.7f, 0.3f, 6}); // Proporciones de las columnas principales

        // Interlineado entre filas
        float rowSpacing = 10f;

        if (isFirstBlock) {
            // Filas del bloque 1
            addRowToTableWithSpacing(table, "Expediente Nº", ":", request.getExpediente(), labelFont, valueFont, rowSpacing);
            addRowToTableWithSpacing(table, "Resolución Nº", ":", request.getResolucion(), labelFont, valueFont, rowSpacing);

            // Combinar "Licencia Nº" y "Nivel de Riesgo" en una fila (cambiar a 4 columnas)
            PdfPTable tempTable = new PdfPTable(4); // Tabla temporal con 4 columnas
            tempTable.setWidthPercentage(100);
            tempTable.setWidths(new float[]{2, 0.5f, 3, 4}); // Proporciones específicas

            // Columna 1: "Licencia Nº"
            PdfPCell labelCell = new PdfPCell(new Phrase("Licencia Nº", labelFont));
            labelCell.setBorder(Rectangle.NO_BORDER);
            labelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tempTable.addCell(labelCell);

            // Columna 2: ":"
            PdfPCell separatorCell = new PdfPCell(new Phrase(":", labelFont));
            separatorCell.setBorder(Rectangle.NO_BORDER);
            separatorCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tempTable.addCell(separatorCell);

            // Columna 3: Valor de Licencia
            PdfPCell licenciaValueCell = new PdfPCell(new Phrase(request.getLicencia(), valueFont));
            licenciaValueCell.setBorder(Rectangle.NO_BORDER);
            licenciaValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tempTable.addCell(licenciaValueCell);

            // Columna 4: "Nivel de Riesgo: Alto"
            Phrase riesgoPhrase = new Phrase();
            riesgoPhrase.add(new Chunk("Nivel de Riesgo: ", labelFont));
            riesgoPhrase.add(new Chunk(request.getNivelRiesgo(), valueFont));

            PdfPCell riesgoCell = new PdfPCell(riesgoPhrase);
            riesgoCell.setBorder(Rectangle.NO_BORDER);
            riesgoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tempTable.addCell(riesgoCell);

            // Añadir la fila combinada a la tabla principal
            PdfPCell mergedCell = new PdfPCell(tempTable);
            mergedCell.setBorder(Rectangle.NO_BORDER);
            mergedCell.setColspan(3); // Ocupa todo el ancho de la tabla principal
            table.addCell(mergedCell);

        } else {
            // Filas del bloque 2
            addRowToTableWithSpacing(table, "Titular", ":", request.getTitular(), labelFont, valueFont, rowSpacing);
            addRowToTableWithSpacing(table, "RUC Nº", ":", request.getRuc(), labelFont, valueFont, rowSpacing);
            addRowToTableWithSpacing(table, "Zonificación", ":", request.getZonificacion(), labelFont, valueFont, rowSpacing);
            addRowToTableWithSpacing(table, "Nombre Comercial", ":", request.getNombreComercial(), labelFont, valueFont, rowSpacing);
            addRowToTableWithSpacing(table, "Giro", ":", request.getGiro(), labelFont, valueFont, rowSpacing);
            addRowToTableWithSpacing(table, "Actividad Comercial", ":", request.getActividadComercial(), labelFont, valueFont, rowSpacing);
            addRowToTableWithSpacing(table, "Ubicado en", ":", request.getUbicacion(), labelFont, valueFont, rowSpacing);

            // Combinar "Área Comercial" y "Horario de Atención" en una fila (cambiar a 4 columnas)
            PdfPTable tempTable = new PdfPTable(4); // Tabla temporal con 4 columnas
            tempTable.setWidthPercentage(100);
            tempTable.setWidths(new float[]{2, 0.5f, 3, 4}); // Proporciones específicas

            // Columna 1: "Área Comercial"
            PdfPCell labelCell = new PdfPCell(new Phrase("Área Comercial", labelFont));
            labelCell.setBorder(Rectangle.NO_BORDER);
            labelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tempTable.addCell(labelCell);

            // Columna 2: ":"
            PdfPCell separatorCell = new PdfPCell(new Phrase(":", labelFont));
            separatorCell.setBorder(Rectangle.NO_BORDER);
            separatorCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tempTable.addCell(separatorCell);

            // Columna 3: Valor de Área Comercial
            PdfPCell areaValueCell = new PdfPCell(new Phrase(request.getAreaComercial(), valueFont));
            areaValueCell.setBorder(Rectangle.NO_BORDER);
            areaValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tempTable.addCell(areaValueCell);

            // Columna 4: "Horario de Atención"
            Phrase horarioPhrase = new Phrase();
            horarioPhrase.add(new Chunk("Horario de Atención: ", labelFont));
            horarioPhrase.add(new Chunk("7:00" + " a " + "23:00", valueFont));

            PdfPCell horarioCell = new PdfPCell(horarioPhrase);
            horarioCell.setBorder(Rectangle.NO_BORDER);
            horarioCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tempTable.addCell(horarioCell);

            // Añadir la fila combinada a la tabla principal
            PdfPCell mergedCell = new PdfPCell(tempTable);
            mergedCell.setBorder(Rectangle.NO_BORDER);
            mergedCell.setColspan(3); // Ocupa todo el ancho de la tabla principal
            table.addCell(mergedCell);
        }

        return table;
    }


    // Nueva función auxiliar para agregar filas con interlineado
    private void addRowToTableWithSpacing(PdfPTable table, String label, String separator, String value, Font labelFont, Font valueFont, float spacingAfter) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(labelCell);

        PdfPCell separatorCell = new PdfPCell(new Phrase(separator, labelFont));
        separatorCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(separatorCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(valueCell);

        // Agregar espacio entre filas
        PdfPCell spacingCell = new PdfPCell();
        spacingCell.setBorder(Rectangle.NO_BORDER);
        spacingCell.setColspan(3);
        spacingCell.setFixedHeight(spacingAfter);
        table.addCell(spacingCell);
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
