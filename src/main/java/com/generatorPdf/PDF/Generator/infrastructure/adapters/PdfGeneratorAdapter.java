package com.generatorPdf.PDF.Generator.infrastructure.adapters;

import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfRequest;
import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfTramiteLicenciaDoc;
import com.generatorPdf.PDF.Generator.domain.ports.out.PDFServOut;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Component;

import javax.swing.*;
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





////// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// ////// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// ///
////// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// ////// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// ///
////// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// ////// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// ///
////// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// ////// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// /// ///

    @Override
    public void createDocTramiteLicenciaPDF(PdfTramiteLicenciaDoc pdfTramiteLicenciaDoc, String filePath) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            // Crear el documento PDF con márgenes personalizados
            Document document = new Document(PageSize.A4, 10, 10, 12, 12);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // ===== ENCABEZADO DEL TRÁMITE ===== \\
            addHeader_TramiteLicenciaDoc(document, writer, pdfTramiteLicenciaDoc);

            // === PRIMER CONTENIDO DEL TRÁMITE === \\
            addContentI_TramiteLicenciaDoc(document, writer, pdfTramiteLicenciaDoc);

            addContetII_TramiteLicenciaDoc(document, writer, pdfTramiteLicenciaDoc);

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }

    // ======================================================================= ENCABEZADO ======================================================================= \\
    private void addHeader_TramiteLicenciaDoc(Document document, PdfWriter writer, PdfTramiteLicenciaDoc pdfTramiteLicenciaDoc) throws Exception {
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100);

        float[] columnWidths = {22f, 46f, 32f};
        headerTable.setWidths(columnWidths);

        // Establecer altura total de la tabla
        headerTable.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
        headerTable.setLockedWidth(true);

        // Ajustar altura de la tabla a 50 unidades
        headerTable.writeSelectedRows(0, -1, document.leftMargin(), document.getPageSize().getHeight() - 50, writer.getDirectContent());

        try {
            Image logo_MDNCH = Image.getInstance("imagen/LOGO-MDNCH.png");
            logo_MDNCH.scaleToFit(70, 50);
            PdfPCell header_Colum1 = new PdfPCell(logo_MDNCH);
            header_Colum1.setBorder(Rectangle.BOX);
            header_Colum1.setHorizontalAlignment(Element.ALIGN_CENTER);
            header_Colum1.setPaddingTop(5f);
            header_Colum1.setPaddingBottom(5f);

            headerTable.addCell(header_Colum1);

        } catch (Exception e) {
            PdfPCell fallbackText = new PdfPCell(new Phrase("LOGO NO DISPONIBLE"));
            fallbackText.setBorder(Rectangle.BOX);
            fallbackText.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTable.addCell(fallbackText);
        }

        try {
            PdfPTable tablaAnidada = new PdfPTable(1);
            tablaAnidada.setWidthPercentage(100);
            tablaAnidada.setHorizontalAlignment(Element.ALIGN_CENTER);

            Font boldFont = new Font(Font.UNDEFINED, 9, Font.BOLD);
            PdfPCell fila1 = new PdfPCell(new Phrase("FORMATO DE DECLARACIÓN JURADA PARA LICENCIA DE FUNCIONAMIENTO", boldFont));
            fila1.setBorder(Rectangle.NO_BORDER);
            fila1.setHorizontalAlignment(Element.ALIGN_CENTER);
            fila1.setPaddingTop(10f);
            fila1.setPaddingLeft(5);
            fila1.setPaddingRight(5);
            tablaAnidada.addCell(fila1);


            Font cursivaFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 6);
            PdfPCell fila2 = new PdfPCell(new Phrase("LEY N° 28976 - Ley Marco de Licencia de Funcionamiento y modificatorias \n Versión 03", cursivaFont));
            fila2.setBorder(Rectangle.NO_BORDER);
            fila2.setHorizontalAlignment(Element.ALIGN_CENTER);
            fila2.setPaddingTop(5f);
            fila2.setPaddingLeft(5);
            fila2.setPaddingRight(5);

            tablaAnidada.addCell(fila2);

            PdfPCell header_Colum2 = new PdfPCell(tablaAnidada);
            header_Colum2.setBorder(Rectangle.BOX);

            headerTable.addCell(header_Colum2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PdfPTable tablaAnidada = new PdfPTable(1);
            tablaAnidada.setWidthPercentage(100);

            Font normalFont = new Font(Font.HELVETICA, 6, Font.NORMAL);

            PdfPCell fila1 = new PdfPCell(new Phrase("N° de expediente:", normalFont));
            fila1.setBorder(Rectangle.BOX);
            fila1.setPaddingTop(5);
            fila1.setPaddingBottom(5);
            fila1.setPaddingLeft(3);
            tablaAnidada.addCell(fila1);

            PdfPTable fila2Tabla = new PdfPTable(2);
            fila2Tabla.setWidthPercentage(100);
            fila2Tabla.setWidths(new float[]{30f, 70f});

            PdfPCell fila2C1 = new PdfPCell(new Phrase("Página:    1 de 2", normalFont));
            fila2C1.setPaddingTop(5);
            fila2C1.setPaddingBottom(5);
            fila2C1.setPaddingLeft(3);
            fila2Tabla.addCell(fila2C1);

            PdfPCell fila2C2 = new PdfPCell(new Phrase("Fecha de recepción:", normalFont));
            fila2C2.setPaddingTop(5);
            fila2C2.setPaddingBottom(5);
            fila2C2.setPaddingLeft(3);
            fila2Tabla.addCell(fila2C2);

            PdfPCell fila2 = new PdfPCell(fila2Tabla);
            fila2.setBorder(Rectangle.BOX);
            tablaAnidada.addCell(fila2);

            // Tercera fila
            PdfPCell fila3 = new PdfPCell(new Phrase("N° de recibo de pago:", normalFont));
            fila3.setBorder(Rectangle.BOX);
            fila3.setPaddingTop(5);
            fila3.setPaddingBottom(5);
            fila3.setPaddingLeft(3);
            tablaAnidada.addCell(fila3);

            // Cuarta fila
            PdfPCell fila4 = new PdfPCell(new Phrase("Fecha de pago:", normalFont));
            fila4.setBorder(Rectangle.BOX);
            fila4.setPaddingTop(5);
            fila4.setPaddingBottom(5);
            fila4.setPaddingLeft(3);
            tablaAnidada.addCell(fila4);

            // Encapsular la tabla anidada en una celda con borde
            PdfPCell header_Colum3 = new PdfPCell(tablaAnidada);
            header_Colum3.setBorder(Rectangle.BOX);
            headerTable.addCell(header_Colum3);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PdfPTable finHeaderTable = new PdfPTable(1);
            finHeaderTable.setWidthPercentage(100);
            Font footerFont = new Font(Font.UNDEFINED, 5, Font.BOLDITALIC);
            PdfPCell finHeader = new PdfPCell(new Phrase("VER INSTRUCCIONES PARA EL LLENADO (Página 2)", footerFont));
            finHeader.setBorder(Rectangle.NO_BORDER);
            finHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            finHeader.setPaddingTop(6f);
            finHeader.setPaddingBottom(6f);
            finHeaderTable.addCell(finHeader);
            document.add(headerTable);
            document.add(finHeaderTable);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addContentI_TramiteLicenciaDoc(Document document, PdfWriter writer, PdfTramiteLicenciaDoc pdfTramiteLicenciaDoc) throws Exception {
        Font subTitleFont = new Font(Font.HELVETICA, 5, Font.BOLD);

        try {
            PdfPTable titleContentI = new PdfPTable(1);
            titleContentI.setWidthPercentage(100);
            Font titleFont = new Font(Font.HELVETICA, 5, Font.BOLD);
            PdfPCell titloContent = new PdfPCell(new Phrase("I MODALIDAD DEL TRÁMITE QUE SOLICITA (marcar más de una alternativa si corresponde)", titleFont));
            titloContent.setBorder(Rectangle.BOX);
            titloContent.setHorizontalAlignment(Element.ALIGN_CENTER);
            titloContent.setPaddingTop(5f);
            titloContent.setPaddingBottom(5f);
            titloContent.setBackgroundColor(new GrayColor(0.85f)); // Gris claro

            titleContentI.addCell(titloContent);
            document.add(titleContentI);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PdfPTable alternativaContentI = new PdfPTable(3);
        alternativaContentI.setWidthPercentage(100);
        alternativaContentI.setWidths(new float[]{33.3f, 33.3f, 33.3f});

        try {
            // todo: >>>>>>>>>>>>>>>> COLUMNA 1 <<<<<<<<<<<<<<<<<<<<<<
            PdfPTable columnAlternativa1 = new PdfPTable(1);
            columnAlternativa1.setWidthPercentage(100);
            PdfPCell tituloContentA1 = new PdfPCell(new Phrase("Licencia de funcionamiento", subTitleFont));
            tituloContentA1.setHorizontalAlignment(Element.ALIGN_CENTER);
            tituloContentA1.setPadding(5);
            tituloContentA1.setBorder(Rectangle.NO_BORDER);
            columnAlternativa1.addCell(tituloContentA1);

            // todo: >>>>>>>>>>>> COLUMNA 1: fila 1 <<<<<<<<<<<<<<<<<<
            PdfPTable fila2Tabla = new PdfPTable(2);
            fila2Tabla.setWidthPercentage(100);
            fila2Tabla.setWidths(new float[]{50f, 50f});
            fila2Tabla.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            Font normalFont = new Font(Font.HELVETICA, 6, Font.NORMAL);

            PdfPTable columnaIzquierda = new PdfPTable(1);
            columnaIzquierda.setWidthPercentage(100);
            columnaIzquierda.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPTable filaIzq1 = new PdfPTable(2);
            filaIzq1.setWidths(new float[]{10f, 90f});
            filaIzq1.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            Image cuadro = Image.getInstance("imagen/cuadro-vacío.png");
            cuadro.scaleAbsolute(10f, 10f);

            PdfPCell checkboxTemporal = new PdfPCell(cuadro);
            checkboxTemporal.setHorizontalAlignment(Element.ALIGN_CENTER);
            checkboxTemporal.setVerticalAlignment(Element.ALIGN_MIDDLE);
            checkboxTemporal.setBorder(Rectangle.NO_BORDER);
            checkboxTemporal.setPadding(2);

            filaIzq1.addCell(checkboxTemporal);

            PdfPCell textoTemporal = new PdfPCell(new Phrase("     Indeterminada", normalFont));
            textoTemporal.setBorder(Rectangle.NO_BORDER);
            textoTemporal.setPadding(5);
            filaIzq1.addCell(textoTemporal);

            PdfPCell filaIzq1Cell = new PdfPCell(filaIzq1);
            filaIzq1Cell.setBorder(Rectangle.NO_BORDER);
            columnaIzquierda.addCell(filaIzq1Cell);

            PdfPCell textoExtra = new PdfPCell(new Phrase("", normalFont));
            textoExtra.setBorder(Rectangle.NO_BORDER);
            textoExtra.setPadding(5);
            columnaIzquierda.addCell(textoExtra);

            PdfPTable columnaDerecha = new PdfPTable(1);
            columnaDerecha.setWidthPercentage(100);
            columnaDerecha.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPTable filaDer1 = new PdfPTable(2);
            filaDer1.setWidths(new float[]{10f, 90f});
            filaDer1.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            Image cuadro2 = Image.getInstance("imagen/cuadro-vacío.png");
            cuadro2.scaleAbsolute(10f, 10f);

            PdfPCell checkboxMundo = new PdfPCell(cuadro2);
            checkboxMundo.setHorizontalAlignment(Element.ALIGN_CENTER);
            checkboxMundo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            checkboxMundo.setBorder(Rectangle.NO_BORDER);
            checkboxMundo.setPadding(2);

            filaDer1.addCell(checkboxMundo);

            PdfPCell textoMundo = new PdfPCell(new Phrase("     Temporal", normalFont));
            textoMundo.setBorder(Rectangle.NO_BORDER);
            textoMundo.setPadding(5);
            filaDer1.addCell(textoMundo);

            PdfPCell filaDer1Cell = new PdfPCell(filaDer1);
            filaDer1Cell.setBorder(Rectangle.NO_BORDER);
            columnaDerecha.addCell(filaDer1Cell);

            PdfPTable filaDer2 = new PdfPTable(1);
            filaDer2.setWidthPercentage(100);

            PdfPCell textoPlazo = new PdfPCell(new Phrase("Indicar plazo: " + "......................", normalFont));
            textoPlazo.setBorder(Rectangle.NO_BORDER);
            textoPlazo.setPadding(2);
            filaDer2.addCell(textoPlazo);


            // todo: >>>>>>>>>>>> COLUMNA 1: fila 2 <<<<<<<<<<<<<<<<<<
            PdfPCell filaDer2Cell = new PdfPCell(filaDer2);
            filaDer2Cell.setBorder(Rectangle.NO_BORDER);
            columnaDerecha.addCell(filaDer2Cell);

            fila2Tabla.addCell(columnaIzquierda);
            fila2Tabla.addCell(columnaDerecha);

            PdfPCell fila2 = new PdfPCell(fila2Tabla);
            fila2.setBorder(Rectangle.NO_BORDER);
            columnAlternativa1.addCell(fila2);

            PdfPTable fila3Tabla = new PdfPTable(2);
            fila3Tabla.setWidthPercentage(100);
            fila3Tabla.setWidths(new float[]{10f, 90f});
            fila3Tabla.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            Image cuadro3 = Image.getInstance("imagen/cuadro-vacío.png");
            cuadro3.scaleAbsolute(10f, 10f);

            PdfPCell fila3C1 = new PdfPCell(cuadro3);
            fila3C1.setHorizontalAlignment(Element.ALIGN_CENTER);
            fila3C1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            fila3C1.setBorder(Rectangle.NO_BORDER);
            fila3C1.setPadding(2);

            fila3Tabla.addCell(fila3C1);

            PdfPTable fila3Textos = new PdfPTable(1);
            fila3Textos.setWidthPercentage(100);

            PdfPCell textoPrincipal = new PdfPCell(new Phrase("Licencia de funcionamiento más autorización publicitario", normalFont));
            textoPrincipal.setBorder(Rectangle.NO_BORDER);
            textoPrincipal.setPadding(2);
            fila3Textos.addCell(textoPrincipal);

            PdfPCell textoTipoAnuncio = new PdfPCell(new Phrase("Tipo de anuncio (especificar):", normalFont));
            textoTipoAnuncio.setBorder(Rectangle.NO_BORDER);
            textoTipoAnuncio.setPadding(2);
            fila3Textos.addCell(textoTipoAnuncio);

            PdfPCell textoLinea = new PdfPCell(new Phrase("...........................................................", normalFont));
            textoLinea.setBorder(Rectangle.NO_BORDER);
            textoLinea.setPadding(2);
            fila3Textos.addCell(textoLinea);

            PdfPCell fila3C2 = new PdfPCell(fila3Textos);
            fila3C2.setBorder(Rectangle.NO_BORDER);
            fila3C2.setPadding(5);

            fila3Tabla.addCell(fila3C2);

            // todo: >>>>>>>>>>>> COLUMNA 1: fila 3 <<<<<<<<<<<<<<<<<<

            PdfPCell fila3 = new PdfPCell(fila3Tabla);
            fila3.setBorder(Rectangle.NO_BORDER);
            columnAlternativa1.addCell(fila3);

            PdfPTable fila4Tabla = new PdfPTable(2);
            fila4Tabla.setWidthPercentage(100);
            fila4Tabla.setWidths(new float[]{10f, 90f});
            fila4Tabla.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            Image cuadro4 = Image.getInstance("imagen/cuadro-vacío.png");
            cuadro4.scaleAbsolute(10f, 10f);

            PdfPCell fila4C1 = new PdfPCell(cuadro4);
            fila4C1.setHorizontalAlignment(Element.ALIGN_CENTER);
            fila4C1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            fila4C1.setBorder(Rectangle.NO_BORDER);
            fila4C1.setPadding(2);

            fila4Tabla.addCell(fila4C1);

            PdfPTable fila4Textos = new PdfPTable(1);
            fila4Textos.setWidthPercentage(100);

            PdfPCell textoFila4 = new PdfPCell(new Phrase("Licencia para cesionario", normalFont));
            textoFila4.setBorder(Rectangle.NO_BORDER);
            textoFila4.setPadding(2);
            fila4Textos.addCell(textoFila4);

            PdfPCell textoNumLicencia = new PdfPCell(new Phrase("N° de licencia de funcionamiento principal:", normalFont));
            textoNumLicencia.setBorder(Rectangle.NO_BORDER);
            textoNumLicencia.setPadding(2);
            fila4Textos.addCell(textoNumLicencia);

            PdfPCell textoLinea2 = new PdfPCell(new Phrase("...........................................................", normalFont));
            textoLinea2.setBorder(Rectangle.NO_BORDER);
            textoLinea2.setPadding(2);
            fila4Textos.addCell(textoLinea2);

            PdfPCell fila4C2 = new PdfPCell(fila4Textos);
            fila4C2.setBorder(Rectangle.NO_BORDER);
            fila4C2.setPadding(5);

            fila4Tabla.addCell(fila4C2);

            // todo: >>>>>>>>>>>> COLUMNA 1: fila 4 <<<<<<<<<<<<<<<<<<
            PdfPCell fila4 = new PdfPCell(fila4Tabla);
            fila4.setBorder(Rectangle.NO_BORDER);
            columnAlternativa1.addCell(fila4);

            PdfPTable fila5Tabla = new PdfPTable(2);
            fila5Tabla.setWidthPercentage(100);
            fila5Tabla.setWidths(new float[]{10f, 90f});
            fila5Tabla.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            Image cuadro5 = Image.getInstance("imagen/cuadro-vacío.png");
            cuadro5.scaleAbsolute(10f, 10f);

            PdfPCell fila5C1 = new PdfPCell(cuadro5);
            fila5C1.setHorizontalAlignment(Element.ALIGN_CENTER);
            fila5C1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            fila5C1.setBorder(Rectangle.NO_BORDER);
            fila5C1.setPadding(2);

            fila5Tabla.addCell(fila5C1);

            PdfPCell fila5C2 = new PdfPCell(new Phrase("Licencia para mercados de abastos, galerías comerciales y centros comerciales", normalFont));
            fila5C2.setBorder(Rectangle.NO_BORDER);
            fila5C2.setPadding(5);
            fila5Tabla.addCell(fila5C2);

            PdfPCell fila5 = new PdfPCell(fila5Tabla);
            fila5.setBorder(Rectangle.NO_BORDER);
            fila5.setPaddingBottom(5);

            columnAlternativa1.addCell(fila5);

            alternativaContentI.addCell(columnAlternativa1);
            document.add(alternativaContentI);

        }catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PdfPTable columnAlternativa2 = new PdfPTable(1);
            columnAlternativa2.setWidthPercentage(100);

            PdfPCell tituloContentA2 = new PdfPCell(new Phrase("Cambios o modificaciones", subTitleFont));
            tituloContentA2.setHorizontalAlignment(Element.ALIGN_CENTER);
            tituloContentA2.setPadding(5);
            tituloContentA2.setBorder(Rectangle.NO_BORDER);
            columnAlternativa2.addCell(tituloContentA2);

            //TODO: <<<<<<<<<<<<<<<<<<<< COLUMNA 2 - FILA 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPTable fila1Tabla = new PdfPTable(2);
            fila1Tabla.setWidthPercentage(100);
            fila1Tabla.setWidths(new float[]{10f, 90f});


            Image cuadroF1C2 = Image.getInstance("imagen/cuadro-vacío.png");
            cuadroF1C2.scaleAbsolute(10f, 10f);

            Font normalFont = new Font(Font.HELVETICA, 6, Font.NORMAL);

            PdfPCell fila1C1 = new PdfPCell(cuadroF1C2);
            fila1C1.setHorizontalAlignment(Element.ALIGN_CENTER);
            fila1C1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            fila1C1.setBorder(Rectangle.NO_BORDER);
            fila1C1.setPadding(2);

            PdfPCell fila1C2 = new PdfPCell(new Phrase("Cambio de denominación o nombre comercial de la persona jurídica (solo completar secciones II, III y V)", normalFont));
            fila1C2.setBorder(Rectangle.NO_BORDER);
            fila1C2.setPadding(2);

            fila1Tabla.addCell(fila1C1);
            fila1Tabla.addCell(fila1C2);

            PdfPCell vacia1 = new PdfPCell();
            vacia1.setBorder(Rectangle.NO_BORDER);
            vacia1.setPadding(2);
            PdfPCell texto1 = new PdfPCell(new Phrase("N° de licencia de funcionamiento:", normalFont));
            texto1.setBorder(Rectangle.NO_BORDER);
            texto1.setPadding(2);
            fila1Tabla.addCell(vacia1);
            fila1Tabla.addCell(texto1);

            PdfPCell vacia2 = new PdfPCell();
            vacia2.setBorder(Rectangle.NO_BORDER);
            vacia2.setPadding(2);
            PdfPCell texto2 = new PdfPCell(new Phrase("...........................................................", normalFont));
            texto2.setBorder(Rectangle.NO_BORDER);
            texto2.setPadding(2);
            fila1Tabla.addCell(vacia2);
            fila1Tabla.addCell(texto2);

            PdfPCell vacia3 = new PdfPCell();
            vacia3.setBorder(Rectangle.NO_BORDER);
            vacia3.setPadding(2);
            PdfPCell texto3 = new PdfPCell(new Phrase("Indicar nueva denominación o nombre comercial", normalFont));
            texto3.setBorder(Rectangle.NO_BORDER);
            texto3.setPadding(2);
            fila1Tabla.addCell(vacia3);
            fila1Tabla.addCell(texto3);

            PdfPCell vacia4 = new PdfPCell();
            vacia4.setBorder(Rectangle.NO_BORDER);
            vacia4.setPadding(2);
            PdfPCell texto4 = new PdfPCell(new Phrase("...........................................................", normalFont));
            texto4.setBorder(Rectangle.NO_BORDER);
            texto4.setPadding(2);
            fila1Tabla.addCell(vacia4);
            fila1Tabla.addCell(texto4);

            PdfPCell fila1 = new PdfPCell(fila1Tabla);
            fila1.setBorder(Rectangle.NO_BORDER);
            fila1.setPadding(2);

            columnAlternativa2.addCell(fila1);

            //TODO: <<<<<<<<<<<<<<<<<<<< COLUMNA 2 - FILA 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPTable fila2Tabla = new PdfPTable(2);
            fila2Tabla.setWidthPercentage(100);
            fila2Tabla.setWidths(new float[]{10f, 90f});

            Image cuadroF2C2 = Image.getInstance("imagen/cuadro-vacío.png");
            cuadroF2C2.scaleAbsolute(10f, 10f);

            PdfPCell fila2C1 = new PdfPCell(cuadroF2C2);
            fila2C1.setHorizontalAlignment(Element.ALIGN_CENTER);
            fila2C1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            fila2C1.setBorder(Rectangle.NO_BORDER);
            fila2C1.setPadding(2);

            PdfPCell fila2C2 = new PdfPCell(new Phrase("Transferencia de licencia de funcionamiento (Solo completas secciones II, III y IV y adjuntar copia simple de contrato de transferencia)", normalFont));
            fila2C2.setBorder(Rectangle.NO_BORDER);
            fila2C2.setPadding(2);

            fila2Tabla.addCell(fila2C1);
            fila2Tabla.addCell(fila2C2);

            PdfPCell vacia5 = new PdfPCell();
            vacia5.setBorder(Rectangle.NO_BORDER);
            vacia5.setPadding(2);
            PdfPCell texto5 = new PdfPCell(new Phrase("N° de licencia de funcionamiento:", normalFont));
            texto5.setBorder(Rectangle.NO_BORDER);
            texto5.setPadding(2);
            fila2Tabla.addCell(vacia5);
            fila2Tabla.addCell(texto5);

            PdfPCell vacia6 = new PdfPCell();
            vacia6.setBorder(Rectangle.NO_BORDER);
            vacia6.setPadding(2);
            PdfPCell texto6 = new PdfPCell(new Phrase("...........................................................", normalFont));
            texto6.setBorder(Rectangle.NO_BORDER);
            texto6.setPadding(2);
            fila2Tabla.addCell(vacia6);
            fila2Tabla.addCell(texto6);

            PdfPCell fila2 = new PdfPCell(fila2Tabla);
            fila2.setBorder(Rectangle.NO_BORDER);
            fila2.setPaddingTop(15);
            columnAlternativa2.addCell(fila2);

            alternativaContentI.addCell(columnAlternativa2);
            document.add(alternativaContentI);

        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            // TODO: >>>>>>>>>>>>>>>> COLUMNA 3 <<<<<<<<<<<<<<<<<<<<<<
            PdfPTable columnAlternativa3 = new PdfPTable(1);
            columnAlternativa3.setWidthPercentage(100);

            PdfPCell tituloContentA3 = new PdfPCell(new Phrase("Otros", subTitleFont));
            tituloContentA3.setHorizontalAlignment(Element.ALIGN_CENTER);
            tituloContentA3.setPadding(5);
            tituloContentA3.setBorder(Rectangle.NO_BORDER);
            columnAlternativa3.addCell(tituloContentA3);

            //TODO: <<<<<<<<<<<<<<<<<<<< COLUMNA 3 - FILA 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPTable fila1Tabla = new PdfPTable(2);
            fila1Tabla.setWidthPercentage(100);
            fila1Tabla.setWidths(new float[]{10f, 90f});

            Image cuadroF1C3 = Image.getInstance("imagen/cuadro-vacío.png");
            cuadroF1C3.scaleAbsolute(10f, 10f);

            Font normalFont = new Font(Font.HELVETICA, 6, Font.NORMAL);

            PdfPCell fila1C1 = new PdfPCell(cuadroF1C3);
            fila1C1.setHorizontalAlignment(Element.ALIGN_CENTER);
            fila1C1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            fila1C1.setBorder(Rectangle.NO_BORDER);
            fila1C1.setPadding(2);

            PdfPCell fila1C2 = new PdfPCell(new Phrase("Cese de actividades (solo completar secciones II, III y V)", normalFont));
            fila1C2.setBorder(Rectangle.NO_BORDER);
            fila1C2.setPadding(2);

            fila1Tabla.addCell(fila1C1);
            fila1Tabla.addCell(fila1C2);

            PdfPCell vacia1 = new PdfPCell();
            vacia1.setBorder(Rectangle.NO_BORDER);
            vacia1.setPadding(2);
            PdfPCell texto1 = new PdfPCell(new Phrase("N° de licencia de funcionamiento:", normalFont));
            texto1.setBorder(Rectangle.NO_BORDER);
            texto1.setPadding(2);
            fila1Tabla.addCell(vacia1);
            fila1Tabla.addCell(texto1);

            PdfPCell vacia2 = new PdfPCell();
            vacia2.setBorder(Rectangle.NO_BORDER);
            vacia2.setPadding(2);
            PdfPCell texto2 = new PdfPCell(new Phrase("...........................................................", normalFont));
            texto2.setBorder(Rectangle.NO_BORDER);
            texto2.setPadding(2);
            fila1Tabla.addCell(vacia2);
            fila1Tabla.addCell(texto2);

            PdfPCell fila1 = new PdfPCell(fila1Tabla);
            fila1.setBorder(Rectangle.NO_BORDER);
            fila1.setPaddingTop(3);

            columnAlternativa3.addCell(fila1);


            //TODO: <<<<<<<<<<<<<<<<<<<< COLUMNA 3 - FILA 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>

            PdfPTable fila2Tabla = new PdfPTable(2);
            fila2Tabla.setWidthPercentage(100);
            fila2Tabla.setWidths(new float[]{10f, 90f});

            Image cuadroF2C3 = Image.getInstance("imagen/cuadro-vacío.png");
            cuadroF2C3.scaleAbsolute(10f, 10f);

            PdfPCell fila2C1 = new PdfPCell(cuadroF2C3);
            fila2C1.setHorizontalAlignment(Element.ALIGN_CENTER);
            fila2C1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            fila2C1.setBorder(Rectangle.NO_BORDER);
            fila2C1.setPadding(2);

            PdfPCell fila2C2 = new PdfPCell(new Phrase("Otros (especificar)", normalFont));
            fila2C2.setBorder(Rectangle.NO_BORDER);
            fila2C2.setPadding(2);

            fila2Tabla.addCell(fila2C1);
            fila2Tabla.addCell(fila2C2);

            PdfPCell vacia5 = new PdfPCell();
            vacia5.setBorder(Rectangle.NO_BORDER);
            vacia5.setPadding(2);
            PdfPCell texto5 = new PdfPCell(new Phrase("...........................................................", normalFont));
            texto5.setBorder(Rectangle.NO_BORDER);
            texto5.setPadding(2);
            fila2Tabla.addCell(vacia5);
            fila2Tabla.addCell(texto5);

            PdfPCell fila2 = new PdfPCell(fila2Tabla);
            fila2.setBorder(Rectangle.NO_BORDER);
            fila2.setPaddingTop(15);
            columnAlternativa3.addCell(fila2);

            alternativaContentI.addCell(columnAlternativa3);
            document.add(alternativaContentI);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //TODO: ESPACIO VACÍO
            PdfPTable vacioEspacio = new PdfPTable(1);
            vacioEspacio.setWidthPercentage(100);
            Font titleFont = new Font(Font.HELVETICA, 5, Font.BOLD);
            PdfPCell vacioContent = new PdfPCell(new Phrase("", titleFont));
            vacioContent.setBorder(Rectangle.NO_BORDER);
            vacioContent.setHorizontalAlignment(Element.ALIGN_CENTER);
            vacioContent.setPaddingTop(2f);
            vacioContent.setPaddingBottom(2f);
            vacioEspacio.addCell(vacioContent);
            document.add(vacioEspacio);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addContetII_TramiteLicenciaDoc(Document document, PdfWriter writer, PdfTramiteLicenciaDoc pdfTramiteLicenciaDoc) throws  Exception{
        Font subTitleFont = new Font(Font.HELVETICA, 5, Font.BOLD);
        Font titleFont = new Font(Font.HELVETICA, 5, Font.BOLD);
        Font normalFont = new Font(Font.HELVETICA, 5, Font.NORMAL);
        Font inputFont = new Font(Font.HELVETICA, 7, Font.NORMAL);
        try {
            PdfPTable titleContentII = new PdfPTable(1);
            titleContentII.setWidthPercentage(100);


            PdfPCell titloContent = new PdfPCell(new Phrase("II DATOS DEL SOLICITANTE", titleFont));
            titloContent.setBorder(Rectangle.BOX);
            titloContent.setHorizontalAlignment(Element.ALIGN_CENTER);
            titloContent.setPaddingTop(5f);
            titloContent.setPaddingBottom(5f);
            titloContent.setBackgroundColor(new GrayColor(0.85f)); // Gris claro
            titleContentII.addCell(titloContent);

            PdfPCell titleApe_Nom_RS = new PdfPCell(new Phrase("Apellidos y Nombres / Razón Social", normalFont));
            titleApe_Nom_RS.setBorder(Rectangle.BOX);
            titleApe_Nom_RS.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleApe_Nom_RS.setPaddingTop(2f);
            titleApe_Nom_RS.setPaddingBottom(2f);
            titleApe_Nom_RS.setBackgroundColor(new GrayColor(0.92f));
            titleContentII.addCell(titleApe_Nom_RS);


            PdfPCell varNombre_RazonSocial = new PdfPCell(new Phrase("CARLOS GILBERTO MOTUPE DE VILLEGAS",inputFont));
            varNombre_RazonSocial.setBorder(Rectangle.BOX);
            varNombre_RazonSocial.setHorizontalAlignment(Element.ALIGN_CENTER);
            varNombre_RazonSocial.setPaddingTop(5f);
            varNombre_RazonSocial.setPaddingBottom(5f);
            titleContentII.addCell(varNombre_RazonSocial);

            document.add(titleContentII);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            PdfPTable datosSolicitanteContent = new PdfPTable(4);
            datosSolicitanteContent.setWidthPercentage(100);
            datosSolicitanteContent.setWidths(new float[]{21f, 24f, 30f, 25f});

            PdfPCell titleDNI = new PdfPCell(new Phrase("N° DNI/N° C.E", normalFont));
            titleDNI.setBorder(Rectangle.BOX);
            titleDNI.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleDNI.setPadding(2f);
            titleDNI.setPaddingBottom(2f);
            titleDNI.setBackgroundColor(new GrayColor(0.92f));
            datosSolicitanteContent.addCell(titleDNI);

            PdfPCell titleRUC = new PdfPCell(new Phrase("N° RUC", normalFont ));
            titleRUC.setBorder(Rectangle.BOX);
            titleRUC.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleRUC.setPadding(2f);
            titleRUC.setPaddingBottom(2f);
            titleRUC.setBackgroundColor(new GrayColor(0.92f));
            datosSolicitanteContent.addCell(titleRUC);

            PdfPCell titleTelefono = new PdfPCell(new Phrase("N° Teléfono",normalFont));
            titleTelefono.setBorder(Rectangle.BOX);
            titleTelefono.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTelefono.setPadding(2f);
            titleTelefono.setPaddingBottom(2f);
            titleTelefono.setBackgroundColor(new GrayColor(0.92f));
            datosSolicitanteContent.addCell(titleTelefono);


            PdfPCell titleCorreo = new PdfPCell(new Phrase("Correo electrónico", normalFont));
            titleCorreo.setBorder(Rectangle.BOX);
            titleCorreo.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCorreo.setPadding(2f);
            titleCorreo.setPaddingBottom(2f);
            titleCorreo.setBackgroundColor(new GrayColor(0.92f));
            datosSolicitanteContent.addCell(titleCorreo);

            document.add(datosSolicitanteContent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
