package com.generatorPdf.PDF.Generator.infrastructure.adapters.pdf;

import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfTramiteLicenciaDoc;
import com.generatorPdf.PDF.Generator.infrastructure.utils.ImageUtils;
import com.lowagie.text.*;
import com.lowagie.text.pdf.GrayColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class PdfTramiteLicenciaBuilder {
    static Font boldFont = new Font(Font.HELVETICA, 9, Font.BOLD);
    static Font italicFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 6);
    static Font normalFont2 = new Font(Font.HELVETICA, 6, Font.NORMAL);
    static Font footerFont = new Font(Font.HELVETICA, 5, Font.BOLDITALIC);
    static Font subTitleFont = new Font(Font.HELVETICA, 7, Font.BOLD);
    static Font titleFont = new Font(Font.HELVETICA, 6, Font.BOLD);
    static Font inputFont = new Font(Font.HELVETICA, 8, Font.NORMAL, new GrayColor(0.5f));
    static Font normalFont = new Font(Font.HELVETICA, 5, Font.NORMAL);
    static Font inputFontEspecial = new Font(Font.HELVETICA,5,Font.NORMAL, new GrayColor(0.5f));

    private PdfTramiteLicenciaBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static void addHeader_TramiteLicenciaDoc(Document document, PdfWriter writer, PdfTramiteLicenciaDoc pdfTramiteLicenciaDoc) throws Exception {
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{22f, 46f, 32f});
        headerTable.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
        headerTable.setLockedWidth(true);
        headerTable.writeSelectedRows(0, -1, document.leftMargin(), document.getPageSize().getHeight() - 50, writer.getDirectContent());

        try {
            // Columna 1: Logo
            PdfPCell logoCell;
            try {
                Image logo = Image.getInstance("imagen/LOGO-MDNCH.png");
                logo.scaleToFit(85, 65);
                logoCell = new PdfPCell(logo);
            } catch (Exception e) {
                logoCell = new PdfPCell(new Phrase("LOGO NO DISPONIBLE", normalFont2));
            }
            logoCell.setBorder(Rectangle.BOX);
            logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            logoCell.setPadding(5f);
            headerTable.addCell(logoCell);

            // Columna 2: Título y Subtítulo
            PdfPTable titleTable = new PdfPTable(1);
            titleTable.setWidthPercentage(100);

            PdfPCell titleCell = new PdfPCell(new Phrase("FORMATO DE DECLARACIÓN JURADA PARA LICENCIA DE FUNCIONAMIENTO", boldFont));
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.setPaddingTop(18f);
            titleTable.addCell(titleCell);

            PdfPCell subtitleCell = new PdfPCell(new Phrase("LEY N° 28976 - Ley Marco de Licencia de Funcionamiento y modificatorias \n Versión 03", italicFont));
            subtitleCell.setBorder(Rectangle.NO_BORDER);
            subtitleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            subtitleCell.setPaddingTop(5f);
            titleTable.addCell(subtitleCell);

            PdfPCell titleContainer = new PdfPCell(titleTable);
            titleContainer.setBorder(Rectangle.BOX);
            headerTable.addCell(titleContainer);

            // Columna 3: Datos del Expediente
            PdfPTable expedienteTable = new PdfPTable(1);
            expedienteTable.setWidthPercentage(100);

            PdfPCell cell1 = new PdfPCell(new Phrase("N° de expediente:", normalFont2));
            cell1.setBorder(Rectangle.BOX);
            cell1.setPadding(6);
            cell1.setPaddingLeft(3);
            expedienteTable.addCell(cell1);

            PdfPTable subTable = new PdfPTable(2);
            subTable.setWidthPercentage(100);
            subTable.setWidths(new float[]{30f, 70f});

            PdfPCell cell2 = new PdfPCell(new Phrase("Página: 1 de 2", normalFont2));
            cell2.setBorder(Rectangle.BOX);
            cell2.setPadding(6);
            cell2.setPaddingLeft(3);
            subTable.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Phrase("Fecha de recepción:", normalFont2));
            cell3.setBorder(Rectangle.BOX);
            cell3.setPadding(6);
            cell3.setPaddingLeft(3);
            subTable.addCell(cell3);

            PdfPCell subTableContainer = new PdfPCell(subTable);
            subTableContainer.setBorder(Rectangle.BOX);
            expedienteTable.addCell(subTableContainer);

            PdfPCell cell4 = new PdfPCell(new Phrase("N° de recibo de pago:", normalFont2));
            cell4.setBorder(Rectangle.BOX);
            cell4.setPadding(6);
            cell4.setPaddingLeft(3);
            expedienteTable.addCell(cell4);

            PdfPCell cell5 = new PdfPCell(new Phrase("Fecha de pago:", normalFont2));
            cell5.setBorder(Rectangle.BOX);
            cell5.setPadding(6);
            cell5.setPaddingLeft(3);
            expedienteTable.addCell(cell5);

            PdfPCell expedienteContainer = new PdfPCell(expedienteTable);
            expedienteContainer.setBorder(Rectangle.BOX);
            headerTable.addCell(expedienteContainer);

            // Pie de página del encabezado
            PdfPTable footerTable = new PdfPTable(1);
            footerTable.setWidthPercentage(100);
            PdfPCell footerCell = new PdfPCell(new Phrase("VER INSTRUCCIONES PARA EL LLENADO (Página 2)", footerFont));
            footerCell.setBorder(Rectangle.NO_BORDER);
            footerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            footerCell.setPadding(7f);
            footerTable.addCell(footerCell);

            document.add(headerTable);
            document.add(footerTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addContentI_TramiteLicenciaDoc(Document document, PdfWriter writer, PdfTramiteLicenciaDoc pdfTramiteLicenciaDoc) throws Exception {

        try {
            PdfPTable titleContentI = new PdfPTable(1);
            titleContentI.setWidthPercentage(100);
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
            // <<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA 1 >>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPTable columnAlternativa1 = new PdfPTable(1);
            columnAlternativa1.setWidthPercentage(100);
            PdfPCell tituloContentA1 = new PdfPCell(new Phrase("Licencia de funcionamiento", subTitleFont));
            tituloContentA1.setHorizontalAlignment(Element.ALIGN_CENTER);
            tituloContentA1.setPadding(5);
            tituloContentA1.setBorder(Rectangle.NO_BORDER);
            columnAlternativa1.addCell(tituloContentA1);

            // <<<<<<<<<<<<<<<<<<<<< COLUMNA 1: fila 1 >>>>>>>>>>>>>>>>>>>>>
            PdfPTable fila2Tabla = new PdfPTable(2);
            fila2Tabla.setWidthPercentage(100);
            fila2Tabla.setWidths(new float[]{50f, 50f});
            fila2Tabla.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPTable columnaIzquierda = new PdfPTable(1);
            columnaIzquierda.setWidthPercentage(100);
            columnaIzquierda.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPTable filaIzq1 = new PdfPTable(2);
            filaIzq1.setWidths(new float[]{10f, 90f});
            filaIzq1.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            String imagenPath = "imagen/cuadro-vacío.png"; // Imagen por defecto
            if ("INDETERMINADA".equals(pdfTramiteLicenciaDoc.getFechaEstadoLicencia())) {
                imagenPath = "imagen/cuadro-marcado.png";
            }

            Image cuadro = Image.getInstance(imagenPath);
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

            String imagenPath2 = "imagen/cuadro-vacío.png";
            if("TEMPORAL".equals(pdfTramiteLicenciaDoc.getFechaEstadoLicencia())){
                imagenPath2="imagen/cuadro-marcado.png";
            }

            Image cuadro2 = Image.getInstance(imagenPath2);
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

            String txtPlazo= "Indicar plazo: " + "......................";

            if ("TEMPORAL".equals(pdfTramiteLicenciaDoc.getFechaEstadoLicencia())){
                txtPlazo= "Indicar plazo: "+pdfTramiteLicenciaDoc.getVigenciaLicencia().toString();
            }

            PdfPCell textoPlazo = new PdfPCell(new Phrase(txtPlazo, normalFont));
            textoPlazo.setBorder(Rectangle.NO_BORDER);
            textoPlazo.setPadding(2);

            filaDer2.addCell(textoPlazo);


            // <<<<<<<<<<<<<<<<<<<<<<<< COLUMNA 1: fila 2 >>>>>>>>>>>>>>>>>>>>>>>>
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
            fila3C2.setPaddingTop(10);
            fila3C2.setPaddingBottom(5);


            fila3Tabla.addCell(fila3C2);

            // <<<<<<<<<<<<<<<<<<<<<< COLUMNA 1: fila 3 >>>>>>>>>>>>>>>>>>>>>>

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
            fila4C2.setPaddingTop(10);
            fila4C2.setPaddingBottom(5);

            fila4Tabla.addCell(fila4C2);

            // <<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA 1: fila 4 >>>>>>>>>>>>>>>>>>>>>>>>>
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
            fila5C1.setPaddingLeft(2);
            fila5C1.setPaddingRight(2);
            fila5C1.setPaddingTop(10);

            fila5Tabla.addCell(fila5C1);

            PdfPCell fila5C2 = new PdfPCell(new Phrase("Licencia para mercados de abastos, galerías comerciales y centros comerciales", normalFont));
            fila5C2.setBorder(Rectangle.NO_BORDER);
            fila5C2.setPaddingTop(10);
            fila5C2.setPaddingRight(2);
            fila5C2.setPaddingLeft(2);


            fila5Tabla.addCell(fila5C2);

            PdfPCell fila5 = new PdfPCell(fila5Tabla);
            fila5.setBorder(Rectangle.NO_BORDER);
            fila5.setPaddingBottom(23);
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

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA 2 - FILA 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPTable fila1Tabla = new PdfPTable(2);
            fila1Tabla.setWidthPercentage(100);
            fila1Tabla.setWidths(new float[]{10f, 90f});


            Image cuadroF1C2 = Image.getInstance("imagen/cuadro-vacío.png");
            cuadroF1C2.scaleAbsolute(10f, 10f);


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

            //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA 2 - FILA 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
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
            fila2.setPaddingTop(20);
            columnAlternativa2.addCell(fila2);

            alternativaContentI.addCell(columnAlternativa2);
            document.add(alternativaContentI);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA 3 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPTable columnAlternativa3 = new PdfPTable(1);
            columnAlternativa3.setWidthPercentage(100);

            PdfPCell tituloContentA3 = new PdfPCell(new Phrase("Otros", subTitleFont));
            tituloContentA3.setHorizontalAlignment(Element.ALIGN_CENTER);
            tituloContentA3.setPadding(5);
            tituloContentA3.setBorder(Rectangle.NO_BORDER);
            columnAlternativa3.addCell(tituloContentA3);

            // <<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA 3 - FILA 1 >>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPTable fila1Tabla = new PdfPTable(2);
            fila1Tabla.setWidthPercentage(100);
            fila1Tabla.setWidths(new float[]{10f, 90f});

            Image cuadroF1C3 = Image.getInstance("imagen/cuadro-vacío.png");
            cuadroF1C3.scaleAbsolute(10f, 10f);


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

            // <<<<<<<<<<<<<<<<<<<< COLUMNA 3 - FILA 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>

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
            //<<<<<<<<<<<<<<<<<<<<<<<<ESPACIO VACÍO>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPTable vacioEspacio = new PdfPTable(1);
            vacioEspacio.setWidthPercentage(100);
            PdfPCell vacioContent = new PdfPCell(new Phrase("", titleFont));
            vacioContent.setBorder(Rectangle.NO_BORDER);
            vacioContent.setHorizontalAlignment(Element.ALIGN_CENTER);
            vacioContent.setPaddingTop(4f);
            vacioContent.setPaddingBottom(4f);
            vacioEspacio.addCell(vacioContent);
            document.add(vacioEspacio);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void addContetII_TramiteLicenciaDoc(Document document, PdfWriter writer, PdfTramiteLicenciaDoc pdfTramiteLicenciaDoc) throws  Exception{

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
            titleApe_Nom_RS.setPaddingTop(3f);
            titleApe_Nom_RS.setPaddingBottom(3f);
            titleApe_Nom_RS.setBackgroundColor(new GrayColor(0.92f));
            titleContentII.addCell(titleApe_Nom_RS);


            PdfPCell varNombre_RazonSocial = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getNombreCiudadano()+" "+pdfTramiteLicenciaDoc.getApellidoCiudadano(),inputFont));
            varNombre_RazonSocial.setBorder(Rectangle.BOX);
            varNombre_RazonSocial.setHorizontalAlignment(Element.ALIGN_CENTER);
            varNombre_RazonSocial.setPaddingTop(8f);
            varNombre_RazonSocial.setPaddingBottom(8f);
            titleContentII.addCell(varNombre_RazonSocial);

            document.add(titleContentII);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< CREACIÓN DE 4 COLUMNAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPTable datosSolicitanteContent = new PdfPTable(4);
            datosSolicitanteContent.setWidthPercentage(100);
            datosSolicitanteContent.setWidths(new float[]{21f, 23f, 13f, 43f});

            GrayColor headerBgColor = new GrayColor(0.92f);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO DNI >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerDNI = new PdfPCell(new Phrase("N° DNI/N° C.E", normalFont));
            headerDNI.setBorder(Rectangle.BOX);
            headerDNI.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerDNI.setPadding(2f);
            headerDNI.setBackgroundColor(headerBgColor);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO RUC >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerRUC = new PdfPCell(new Phrase("N° RUC", normalFont));
            headerRUC.setBorder(Rectangle.BOX);
            headerRUC.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerRUC.setPadding(2f);
            headerRUC.setBackgroundColor(headerBgColor);

            // <<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO TELÉFONO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerTelefono = new PdfPCell(new Phrase("N° Teléfono", normalFont));
            headerTelefono.setBorder(Rectangle.BOX);
            headerTelefono.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTelefono.setPadding(2f);
            headerTelefono.setBackgroundColor(headerBgColor);

            // <<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO CORREO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerCorreo = new PdfPCell(new Phrase("Correo electrónico", normalFont));
            headerCorreo.setBorder(Rectangle.BOX);
            headerCorreo.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCorreo.setPadding(2f);
            headerCorreo.setBackgroundColor(headerBgColor);

            // ---- SE AGREGA EL CONTENIDO A LAS 4 COLUMNAS
            datosSolicitanteContent.addCell(headerDNI);
            datosSolicitanteContent.addCell(headerRUC);
            datosSolicitanteContent.addCell(headerTelefono);
            datosSolicitanteContent.addCell(headerCorreo);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT DNI >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataDNI = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getDniCiudadano(), inputFont));
            dataDNI.setBorder(Rectangle.BOX);
            dataDNI.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataDNI.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataDNI.setPadding(8f);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT RUC >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataRUC = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getNumeroDocumentoRUC(), inputFont));
            dataRUC.setBorder(Rectangle.BOX);
            dataRUC.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataRUC.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataRUC.setPadding(8f);

            // <<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT TELÉFONO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataTelefono = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getTelefonoCiudadano(), inputFont));
            dataTelefono.setBorder(Rectangle.BOX);
            dataTelefono.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataTelefono.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataTelefono.setPadding(8f);

            // <<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT CORREO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataCorreo = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getCorreoElectronicoCiudadano(), inputFont));
            dataCorreo.setBorder(Rectangle.BOX);
            dataCorreo.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataCorreo.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataCorreo.setPadding(8f);

            // --------------- SE AGREGA  EL CONTENIDO A LAS 4 COLUMNAS
            datosSolicitanteContent.addCell(dataDNI);
            datosSolicitanteContent.addCell(dataRUC);
            datosSolicitanteContent.addCell(dataTelefono);
            datosSolicitanteContent.addCell(dataCorreo);

            // se agrega al documento
            document.add(datosSolicitanteContent);


            // Título Dirección
            PdfPTable titleDireccionSolicitanteContent = new PdfPTable(1);
            titleDireccionSolicitanteContent.setWidthPercentage(100);

            PdfPCell headerTitleDireccion = new PdfPCell(new Phrase("Dirección", normalFont));
            headerTitleDireccion.setBorder(Rectangle.BOX);
            headerTitleDireccion.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTitleDireccion.setPadding(2f);
            headerTitleDireccion.setBackgroundColor(headerBgColor);

            titleDireccionSolicitanteContent.addCell(headerTitleDireccion);

            document.add(titleDireccionSolicitanteContent);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void addContentII_II_TramiteLicenciaDoc(Document document, PdfWriter writer, PdfTramiteLicenciaDoc pdfTramiteLicenciaDoc) throws  Exception {

        try {
            // CONTENIDO DE DIRECCIÓN
            PdfPTable datosDireccionSolicitanteContent = new PdfPTable(4);
            datosDireccionSolicitanteContent.setWidthPercentage(100);
            datosDireccionSolicitanteContent.setWidths(new float[]{21f, 23f, 30f, 26f});


            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO Av./Jr./Ca./Pje./Otros >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerAvC = new PdfPCell(new Phrase("Av./Jr./Ca./Pje./Otros", normalFont));
            headerAvC.setBorder(Rectangle.BOX);
            headerAvC.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerAvC.setPadding(2f);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO N°/Int./Mz./LL/Otros >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerMzC = new PdfPCell(new Phrase("N°/Int./Mz./LL/Otros", normalFont));
            headerMzC.setBorder(Rectangle.BOX);
            headerMzC.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerMzC.setPadding(2f);

            // <<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO Urb./AA.HH/Otros >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerUrbC = new PdfPCell(new Phrase("Urb./AA.HH/Otros", normalFont));
            headerUrbC.setBorder(Rectangle.BOX);
            headerUrbC.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerUrbC.setPadding(2f);

            // <<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO PROVINCIA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerProvinciaC = new PdfPCell(new Phrase("Provincia", normalFont));
            headerProvinciaC.setBorder(Rectangle.BOX);
            headerProvinciaC.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerProvinciaC.setPadding(2f);

            // --------------- SE AGREGA EL CONTENIDO A LAS 4 COLUMNAS
            datosDireccionSolicitanteContent.addCell(headerAvC);
            datosDireccionSolicitanteContent.addCell(headerMzC);
            datosDireccionSolicitanteContent.addCell(headerUrbC);
            datosDireccionSolicitanteContent.addCell(headerProvinciaC);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT Av./Jr./Ca./Pje./Otros >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataAvC = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getAvenidaCiudadano(), inputFont));
            dataAvC.setBorder(Rectangle.BOX);
            dataAvC.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataAvC.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataAvC.setPadding(8f);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT N°/Int./Mz./LL/Otros >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataMzC = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getManzanaCiudadano(), inputFont));
            dataMzC.setBorder(Rectangle.BOX);
            dataMzC.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataMzC.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataMzC.setPadding(8f);

            // <<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT Urb./AA.HH/Otros >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataUrbC = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getUrbanizacionCiudadano(), inputFont));
            dataUrbC.setBorder(Rectangle.BOX);
            dataUrbC.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataUrbC.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataUrbC.setPadding(8f);

            // <<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT PROVINCIA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataProvinciaC = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getProvinciaCiudadano(), inputFont));
            dataProvinciaC.setBorder(Rectangle.BOX);
            dataProvinciaC.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataProvinciaC.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataProvinciaC.setPadding(8f);

            // --------------- SE AGREGA EL CONTENIDO A LAS 4 COLUMNAS
            datosDireccionSolicitanteContent.addCell(dataAvC);
            datosDireccionSolicitanteContent.addCell(dataMzC);
            datosDireccionSolicitanteContent.addCell(dataUrbC);
            datosDireccionSolicitanteContent.addCell(dataProvinciaC);

            // se agrega al documento
            document.add(datosDireccionSolicitanteContent);

            // ESPACIO VACÍO
            PdfPTable vacioEspacio = new PdfPTable(1);
            vacioEspacio.setWidthPercentage(100);
            PdfPCell vacioContent = new PdfPCell(new Phrase("", normalFont));
            vacioContent.setBorder(Rectangle.NO_BORDER);
            vacioContent.setHorizontalAlignment(Element.ALIGN_CENTER);
            vacioContent.setPaddingTop(3f);
            vacioContent.setPaddingBottom(3f);
            vacioEspacio.addCell(vacioContent);
            document.add(vacioEspacio);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addContetIII_TramiteLicenciaDoc(Document document, PdfWriter writer, PdfTramiteLicenciaDoc pdfTramiteLicenciaDoc) throws  Exception {


        try {
            PdfPTable titleContentIII = new PdfPTable(1);
            titleContentIII.setWidthPercentage(100);

            PdfPCell titloContent = new PdfPCell(new Phrase("III DATOS DEL REPRESENTANTE LEGAL O APODERADO", titleFont));
            titloContent.setBorder(Rectangle.BOX);
            titloContent.setHorizontalAlignment(Element.ALIGN_CENTER);
            titloContent.setPaddingTop(5f);
            titloContent.setPaddingBottom(5f);
            titloContent.setBackgroundColor(new GrayColor(0.85f)); // Gris claro
            titleContentIII.addCell(titloContent);
            document.add(titleContentIII);
            try {
                // <<<<<<<<<<<<<<<<<<<<<<<<<<<< CREACIÓN DE 3 COLUMNAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                PdfPTable datosSUNARPContent = new PdfPTable(3);
                datosSUNARPContent.setWidthPercentage(100);
                datosSUNARPContent.setWidths(new float[]{53f, 21f, 26f});

                GrayColor headerBgColor = new GrayColor(0.92f);

                // <<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO APELLIDOS Y NOMBRES >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                PdfPCell headerApellidoNombre = new PdfPCell(new Phrase("Apellidos y Nombres", normalFont));
                headerApellidoNombre.setBorder(Rectangle.BOX);
                headerApellidoNombre.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerApellidoNombre.setPadding(2f);
                headerApellidoNombre.setPaddingTop(4f);
                headerApellidoNombre.setBackgroundColor(headerBgColor);

                // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO DNI >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                PdfPCell headerDNI = new PdfPCell(new Phrase("N° DNI/N° C.E", normalFont));
                headerDNI.setBorder(Rectangle.BOX);
                headerDNI.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerDNI.setPadding(2f);
                headerDNI.setPaddingTop(4f);
                headerDNI.setBackgroundColor(headerBgColor);

                // <<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO PARTIDA SUNARP >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                PdfPCell headerPartidaSUNARP = new PdfPCell(new Phrase("N° de partida electrónica y asiento de inscripción SUNARP (de corresponder)", normalFont));
                headerPartidaSUNARP.setBorder(Rectangle.BOX);
                headerPartidaSUNARP.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerPartidaSUNARP.setPadding(2f);
                headerPartidaSUNARP.setBackgroundColor(headerBgColor);

                // --------------- SE AGREGA EL CONTENIDO A LAS 3 COLUMNAS
                datosSUNARPContent.addCell(headerApellidoNombre);
                datosSUNARPContent.addCell(headerDNI);
                datosSUNARPContent.addCell(headerPartidaSUNARP);

                // <<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT APELLIDOS Y NOMBRES >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                float spaciado = 12f;
                String nombreApellidoR="";
                if(pdfTramiteLicenciaDoc.getNombreRepresentante()!=null && pdfTramiteLicenciaDoc.getApellidoRepresentante()!=null){
                    nombreApellidoR= pdfTramiteLicenciaDoc.getNombreRepresentante()+" "+pdfTramiteLicenciaDoc.getApellidoRepresentante();
                    spaciado=8f;
                }
                PdfPCell dataApellidoNombre = new PdfPCell(new Phrase(nombreApellidoR, inputFont));
                dataApellidoNombre.setBorder(Rectangle.BOX);
                dataApellidoNombre.setHorizontalAlignment(Element.ALIGN_CENTER);
                dataApellidoNombre.setVerticalAlignment(Element.ALIGN_MIDDLE);
                dataApellidoNombre.setPadding(spaciado);

                // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT DNI >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                String dniR = "";
                if(pdfTramiteLicenciaDoc.getDniRepresentante()!=null){
                    dniR = pdfTramiteLicenciaDoc.getDniRepresentante();
                    spaciado=8f;
                }
                PdfPCell dataDNI = new PdfPCell(new Phrase(dniR, inputFont));
                dataDNI.setBorder(Rectangle.BOX);
                dataDNI.setHorizontalAlignment(Element.ALIGN_CENTER);
                dataDNI.setVerticalAlignment(Element.ALIGN_MIDDLE);
                dataDNI.setPadding(spaciado);

                // <<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT PARTIDA SUNARP >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                String nSunarpR = "";
                if(pdfTramiteLicenciaDoc.getnSunarp()!=null){
                    nSunarpR = pdfTramiteLicenciaDoc.getnSunarp();
                    spaciado=8f;
                }
                PdfPCell dataPartidaSUNARP = new PdfPCell(new Phrase(nSunarpR, inputFont));
                dataPartidaSUNARP.setBorder(Rectangle.BOX);
                dataPartidaSUNARP.setHorizontalAlignment(Element.ALIGN_CENTER);
                dataPartidaSUNARP.setVerticalAlignment(Element.ALIGN_MIDDLE);

                dataPartidaSUNARP.setPadding(spaciado);

                // --------------- SE AGREGA EL CONTENIDO A LAS 3 COLUMNAS
                datosSUNARPContent.addCell(dataApellidoNombre);
                datosSUNARPContent.addCell(dataDNI);
                datosSUNARPContent.addCell(dataPartidaSUNARP);

                // se agrega al documento
                document.add(datosSUNARPContent);


                // ESPACIO VACÍO
                PdfPTable vacioEspacio = new PdfPTable(1);
                vacioEspacio.setWidthPercentage(100);
                PdfPCell vacioContent = new PdfPCell(new Phrase("", normalFont));
                vacioContent.setBorder(Rectangle.NO_BORDER);
                vacioContent.setHorizontalAlignment(Element.ALIGN_CENTER);
                vacioContent.setPaddingTop(3f);
                vacioContent.setPaddingBottom(3f);
                vacioEspacio.addCell(vacioContent);
                document.add(vacioEspacio);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addContetIV_TramiteLicenciaDoc(Document document, PdfWriter writer, PdfTramiteLicenciaDoc pdfTramiteLicenciaDoc) throws  Exception{

        try {
            PdfPTable titleContentIV = new PdfPTable(1);
            titleContentIV.setWidthPercentage(100);


            PdfPCell titloContent = new PdfPCell(new Phrase("IV DATOS DEL ESTABLECIMIENTO", titleFont));
            titloContent.setBorder(Rectangle.BOX);
            titloContent.setHorizontalAlignment(Element.ALIGN_CENTER);
            titloContent.setPaddingTop(5f);
            titloContent.setPaddingBottom(5f);
            titloContent.setBackgroundColor(new GrayColor(0.85f)); // Gris claro
            titleContentIV.addCell(titloContent);

            PdfPCell titleApe_Nom_RS = new PdfPCell(new Phrase("Nombre Comercial", normalFont));
            titleApe_Nom_RS.setBorder(Rectangle.BOX);
            titleApe_Nom_RS.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleApe_Nom_RS.setPaddingTop(2f);
            titleApe_Nom_RS.setPaddingBottom(2f);
            titleApe_Nom_RS.setBackgroundColor(new GrayColor(0.92f));
            titleContentIV.addCell(titleApe_Nom_RS);


            PdfPCell varNombre_RazonSocial = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getNombreNegocio(),inputFont));
            varNombre_RazonSocial.setBorder(Rectangle.BOX);
            varNombre_RazonSocial.setHorizontalAlignment(Element.ALIGN_CENTER);
            varNombre_RazonSocial.setPaddingTop(8f);
            varNombre_RazonSocial.setPaddingBottom(8f);
            titleContentIV.addCell(varNombre_RazonSocial);

            document.add(titleContentIV);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< CREACIÓN DE 4 COLUMNAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPTable datosNegocioI = new PdfPTable(4);
            datosNegocioI.setWidthPercentage(100);
            datosNegocioI.setWidths(new float[]{21f, 37f, 20f, 22f});
            GrayColor headerBgColor = new GrayColor(0.92f);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO CIIU >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerCIIU = new PdfPCell(new Phrase("Código CIIU *", normalFont));
            headerCIIU.setBorder(Rectangle.BOX);
            headerCIIU.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCIIU.setPadding(2f);
            headerCIIU.setBackgroundColor(headerBgColor);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO GIROS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerGiro = new PdfPCell(new Phrase("Giro/s *", normalFont));
            headerGiro.setBorder(Rectangle.BOX);
            headerGiro.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerGiro.setPadding(2f);
            headerGiro.setBackgroundColor(headerBgColor);

            // <<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO ACTIVIDAD >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerActividad = new PdfPCell(new Phrase("Actividad", normalFont));
            headerActividad.setBorder(Rectangle.BOX);
            headerActividad.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerActividad.setPadding(2f);
            headerActividad.setBackgroundColor(headerBgColor);

            // <<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO ZONIFICACIÓN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerZonificacion = new PdfPCell(new Phrase("Zonificación", normalFont));
            headerZonificacion.setBorder(Rectangle.BOX);
            headerZonificacion.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerZonificacion.setPadding(2f);
            headerZonificacion.setBackgroundColor(headerBgColor);

            // --------------- SE AGREGA EL CONTENIDO A LAS 4 COLUMNAS
            datosNegocioI.addCell(headerCIIU);
            datosNegocioI.addCell(headerGiro);
            datosNegocioI.addCell(headerActividad);
            datosNegocioI.addCell(headerZonificacion);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT CIIU >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataCIIU = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getCodigoCiiu(), inputFont));
            dataCIIU.setBorder(Rectangle.BOX);
            dataCIIU.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataCIIU.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataCIIU.setPadding(8f);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT GIROS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataGiro = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getNombreGiro(), inputFont));
            dataGiro.setBorder(Rectangle.BOX);
            dataGiro.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataGiro.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataGiro.setPadding(8f);

            // <<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT ACTIVIDAD >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataActividad = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getActividadNegocio(), inputFontEspecial));
            dataActividad.setBorder(Rectangle.BOX);
            dataActividad.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataActividad.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataActividad.setPadding(8f);

            // <<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT ZONIFICACIÓN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataZonificacion = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getZonificacionNegocio(), inputFont));
            dataZonificacion.setBorder(Rectangle.BOX);
            dataZonificacion.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataZonificacion.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataZonificacion.setPadding(8f);


            // --------------- SE AGREGA EL CONTENIDO A LAS 4 COLUMNAS
            datosNegocioI.addCell(dataCIIU);
            datosNegocioI.addCell(dataGiro);
            datosNegocioI.addCell(dataActividad);
            datosNegocioI.addCell(dataZonificacion);

            // se agrega al documento
            document.add(datosNegocioI);

        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            PdfPTable titleContentIV_2 = new PdfPTable(1);
            titleContentIV_2.setWidthPercentage(100);

            PdfPCell titleDireccion = new PdfPCell(new Phrase("Dirección", normalFont));
            titleDireccion.setBorder(Rectangle.BOX);
            titleDireccion.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleDireccion.setPaddingTop(2f);
            titleDireccion.setPaddingBottom(2f);
            titleDireccion.setBackgroundColor(new GrayColor(0.92f));
            titleContentIV_2.addCell(titleDireccion);

            document.add(titleContentIV_2);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< CREACIÓN DE 4 COLUMNAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPTable datosNegocioII = new PdfPTable(4);
            datosNegocioII.setWidthPercentage(100);
            datosNegocioII.setWidths(new float[]{21f, 24f, 30f, 25f});

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO Av./Jr./Ca./Pje./Otros >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerAv = new PdfPCell(new Phrase("Av./Jr./Ca./Pje./Otros", normalFont));
            headerAv.setBorder(Rectangle.BOX);
            headerAv.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerAv.setPadding(2f);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO N°/Int./Mz./LL/Otros >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerMz = new PdfPCell(new Phrase("N°/Int./Mz./LL/Otros", normalFont));
            headerMz.setBorder(Rectangle.BOX);
            headerMz.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerMz.setPadding(2f);

            // <<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO Urb./AA.HH/Otros >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerUrb = new PdfPCell(new Phrase("Urb./AA.HH/Otros", normalFont));
            headerUrb.setBorder(Rectangle.BOX);
            headerUrb.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerUrb.setPadding(2f);

            // <<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO PROVINCIA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerProvincia = new PdfPCell(new Phrase("Provincia", normalFont));
            headerProvincia.setBorder(Rectangle.BOX);
            headerProvincia.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerProvincia.setPadding(2f);

            // --------------- SE AGREGA EL CONTENIDO A LAS 4 COLUMNAS
            datosNegocioII.addCell(headerAv);
            datosNegocioII.addCell(headerMz);
            datosNegocioII.addCell(headerUrb);
            datosNegocioII.addCell(headerProvincia);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT Av./Jr./Ca./Pje./Otros >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataAv = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getAvenidaNegocio(), inputFont));
            dataAv.setBorder(Rectangle.BOX);
            dataAv.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataAv.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataAv.setPadding(8f);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT N°/Int./Mz./LL/Otros >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataMz = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getManzanaNegocio(), inputFont));
            dataMz.setBorder(Rectangle.BOX);
            dataMz.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataMz.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataMz.setPadding(8f);

            // <<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT Urb./AA.HH/Otros >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataUrb = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getUrbanizacionNegocio(), inputFont));
            dataUrb.setBorder(Rectangle.BOX);
            dataUrb.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataUrb.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataUrb.setPadding(8f);

            // <<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT PROVINCIA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataProvincia = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getProvinciaNegocio(), inputFont));
            dataProvincia.setBorder(Rectangle.BOX);
            dataProvincia.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataProvincia.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataProvincia.setPadding(8f);

            // --------------- SE AGREGA EL CONTENIDO A LAS 4 COLUMNAS
            datosNegocioII.addCell(dataAv);
            datosNegocioII.addCell(dataMz);
            datosNegocioII.addCell(dataUrb);
            datosNegocioII.addCell(dataProvincia);

            // se agrega al documento
            document.add(datosNegocioII);
        }catch (Exception e){
            e.printStackTrace();
        }


        try {
            PdfPTable titleContentIV_3 = new PdfPTable(1);
            titleContentIV_3.setWidthPercentage(100);

            PdfPCell titleAutorizacionSectorial = new PdfPCell(new Phrase("Autorización Sectorial (de corresponder)", normalFont));
            titleAutorizacionSectorial.setBorder(Rectangle.BOX);
            titleAutorizacionSectorial.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleAutorizacionSectorial.setPaddingTop(2f);
            titleAutorizacionSectorial.setPaddingBottom(2f);
            titleAutorizacionSectorial.setBackgroundColor(new GrayColor(0.92f));
            titleContentIV_3.addCell(titleAutorizacionSectorial);

            document.add(titleContentIV_3);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< CREACIÓN DE 4 COLUMNAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPTable datosNegocioIII = new PdfPTable(4);
            datosNegocioIII.setWidthPercentage(100);
            datosNegocioIII.setWidths(new float[]{31f, 36f, 14f, 19f});
            GrayColor headerBgColor = new GrayColor(0.92f);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO ENTIDAD >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerEntidad = new PdfPCell(new Phrase("Entidad que otorga autorización", normalFont));
            headerEntidad.setBorder(Rectangle.BOX);
            headerEntidad.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerEntidad.setPadding(2f);
            headerEntidad.setBackgroundColor(headerBgColor);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO DENOMINACIÓN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerDenominacion = new PdfPCell(new Phrase("Denominación de la autoridad sectorial", normalFont));
            headerDenominacion.setBorder(Rectangle.BOX);
            headerDenominacion.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerDenominacion.setPadding(2f);
            headerDenominacion.setBackgroundColor(headerBgColor);

            // <<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO FECHA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerFecha = new PdfPCell(new Phrase("Fecha de autorización", normalFont));
            headerFecha.setBorder(Rectangle.BOX);
            headerFecha.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerFecha.setPadding(2f);
            headerFecha.setBackgroundColor(headerBgColor);

            // <<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO NÚMERO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerNumero = new PdfPCell(new Phrase("Número de autorización", normalFont));
            headerNumero.setBorder(Rectangle.BOX);
            headerNumero.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerNumero.setPadding(2f);
            headerNumero.setBackgroundColor(headerBgColor);

            // --------------- SE AGREGA EL CONTENIDO A LAS 4 COLUMNAS
            datosNegocioIII.addCell(headerEntidad);
            datosNegocioIII.addCell(headerDenominacion);
            datosNegocioIII.addCell(headerFecha);
            datosNegocioIII.addCell(headerNumero);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT ENTIDAD >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataEntidad = new PdfPCell(new Phrase("", inputFont));
            dataEntidad.setBorder(Rectangle.BOX);
            dataEntidad.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataEntidad.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataEntidad.setPadding(12f);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT DENOMINACIÓN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataDenominacion = new PdfPCell(new Phrase("", inputFont));
            dataDenominacion.setBorder(Rectangle.BOX);
            dataDenominacion.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataDenominacion.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataDenominacion.setPadding(12f);

            // <<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT FECHA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataFecha = new PdfPCell(new Phrase("", inputFont));
            dataFecha.setBorder(Rectangle.BOX);
            dataFecha.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataFecha.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataFecha.setPadding(12f);

            // <<<<<<<<<<<<<<<<<<<<<<< COLUMNA INPUT NÚMERO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataNumero = new PdfPCell(new Phrase("", inputFont));
            dataNumero.setBorder(Rectangle.BOX);
            dataNumero.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataNumero.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataNumero.setPadding(12f);

            // --------------- SE AGREGA EL CONTENIDO A LAS 4 COLUMNAS
            datosNegocioIII.addCell(dataEntidad);
            datosNegocioIII.addCell(dataDenominacion);
            datosNegocioIII.addCell(dataFecha);
            datosNegocioIII.addCell(dataNumero);

            // se agrega al documento
            document.add(datosNegocioIII);

            // ESPACIO VACÍO
            PdfPTable vacioEspacio = new PdfPTable(1);
            vacioEspacio.setWidthPercentage(100);
            PdfPCell vacioContent = new PdfPCell(new Phrase("", normalFont));
            vacioContent.setBorder(Rectangle.NO_BORDER);
            vacioContent.setHorizontalAlignment(Element.ALIGN_CENTER);
            vacioContent.setPaddingTop(3f);
            vacioContent.setPaddingBottom(3f);
            vacioEspacio.addCell(vacioContent);
            document.add(vacioEspacio);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addContetV_TramiteLicenciaDoc(Document document, PdfWriter writer, PdfTramiteLicenciaDoc pdfTramiteLicenciaDoc) throws  Exception {

        try {
            // <<<<<<<<<<<<<<<<<<<<<<<<<<<< CREACIÓN DE 3 COLUMNAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPTable datosArea_Mapa = new PdfPTable(3);
            datosArea_Mapa.setWidthPercentage(100);
            datosArea_Mapa.setWidths(new float[]{35f, 28f, 37f}); //{45f, 8f, 47f})
            GrayColor headerBgColor = new GrayColor(0.92f);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO ÁREA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerArea = new PdfPCell(new Phrase("Área total solicitada (m\u00B2)", normalFont));
            headerArea.setBorder(Rectangle.BOX);
            headerArea.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerArea.setPadding(2f);
            headerArea.setPaddingTop(4f);
            headerArea.setBackgroundColor(headerBgColor);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< COLUMNA VACÍA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell vacioContent = new PdfPCell(new Phrase("", normalFont));
            vacioContent.setBorder(Rectangle.NO_BORDER);
            vacioContent.setHorizontalAlignment(Element.ALIGN_CENTER);
            vacioContent.setPaddingTop(3f);
            vacioContent.setPaddingBottom(3f);

            // <<<<<<<<<<<<<<<<<<<<<< COLUMNA TÍTULO CROQUIS DE UBICACIÓN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell headerCroquis = new PdfPCell(new Phrase("Croquis de ubicación", normalFont));
            headerCroquis.setBorder(Rectangle.BOX);
            headerCroquis.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCroquis.setPadding(2f);
            headerCroquis.setBackgroundColor(headerBgColor);

            // --------------- SE AGREGA EL CONTENIDO A LAS 3 COLUMNAS
            datosArea_Mapa.addCell(headerArea);
            datosArea_Mapa.addCell(vacioContent);
            datosArea_Mapa.addCell(headerCroquis);

            // se agrega al documento
            document.add(datosArea_Mapa);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Creación de la tabla con 3 columnas
            PdfPTable datosArea_Mapa = new PdfPTable(3);
            datosArea_Mapa.setWidthPercentage(100);
            datosArea_Mapa.setWidths(new float[]{35f, 28f, 37f});

            GrayColor headerBgColor = new GrayColor(0.92f);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< PRIMERA FILA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataArea1 = new PdfPCell(new Phrase(pdfTramiteLicenciaDoc.getAreaNegocio().toString(), inputFont));
            dataArea1.setBorder(Rectangle.BOX);
            dataArea1.setFixedHeight(20f);
            dataArea1.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataArea1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dataArea1.setPadding(5f);

            PdfPCell vacioContent1 = new PdfPCell(new Phrase("", normalFont));
            vacioContent1.setBorder(Rectangle.NO_BORDER);
            vacioContent1.setHorizontalAlignment(Element.ALIGN_CENTER);
            vacioContent1.setPaddingTop(3f);
            vacioContent1.setPaddingBottom(3f);


            String coordenadas = pdfTramiteLicenciaDoc.getCoordenadasNegocio();
            Image mapaImage = ImageUtils.obtenerImagenMapa(coordenadas);
            PdfPCell dataCroquisContent = new PdfPCell(mapaImage, true);
            dataCroquisContent.setBorder(Rectangle.BOX);
            dataCroquisContent.setHorizontalAlignment(Element.ALIGN_CENTER);
            dataCroquisContent.setPadding(5f);
            dataCroquisContent.setFixedHeight(120f);
            dataCroquisContent.setRowspan(6);

            datosArea_Mapa.addCell(dataArea1);
            datosArea_Mapa.addCell(vacioContent1);
            datosArea_Mapa.addCell(dataCroquisContent);

            //  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< SEGUNDA FILA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataArea2 = new PdfPCell();
            dataArea2.setBorder(Rectangle.NO_BORDER);
            dataArea2.setFixedHeight(20f);
            dataArea2.setPadding(8f);

            PdfPCell vacioContent2 = new PdfPCell(new Phrase("", normalFont));
            vacioContent2.setBorder(Rectangle.NO_BORDER);
            vacioContent2.setHorizontalAlignment(Element.ALIGN_CENTER);
            vacioContent2.setPaddingTop(3f);
            vacioContent2.setPaddingBottom(3f);

            datosArea_Mapa.addCell(dataArea2);
            datosArea_Mapa.addCell(vacioContent2);

            //  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< TERCERA FILA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataArea3 = new PdfPCell();
            dataArea3.setBorder(Rectangle.NO_BORDER);
            dataArea3.setFixedHeight(20f);
            dataArea3.setPadding(5f);

            PdfPCell vacioContent3 = new PdfPCell(new Phrase("", normalFont));
            vacioContent3.setBorder(Rectangle.NO_BORDER);
            vacioContent3.setHorizontalAlignment(Element.ALIGN_CENTER);
            vacioContent3.setPaddingTop(3f);
            vacioContent3.setPaddingBottom(3f);

            datosArea_Mapa.addCell(dataArea3);
            datosArea_Mapa.addCell(vacioContent3);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< CUARTA FILA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataArea4 = new PdfPCell();
            dataArea4.setBorder(Rectangle.NO_BORDER);
            dataArea4.setFixedHeight(20f);
            dataArea4.setPadding(5f);

            PdfPCell vacioContent4 = new PdfPCell(new Phrase("", normalFont));
            vacioContent4.setBorder(Rectangle.NO_BORDER);
            vacioContent4.setHorizontalAlignment(Element.ALIGN_CENTER);
            vacioContent4.setPaddingTop(3f);
            vacioContent4.setPaddingBottom(3f);

            datosArea_Mapa.addCell(dataArea4);
            datosArea_Mapa.addCell(vacioContent4);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< QUINTA FILA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataArea5 = new PdfPCell();
            dataArea5.setBorder(Rectangle.NO_BORDER);
            dataArea5.setFixedHeight(20f);
            dataArea5.setPadding(5f);

            PdfPCell vacioContent5 = new PdfPCell(new Phrase("", normalFont));
            vacioContent5.setBorder(Rectangle.NO_BORDER);
            vacioContent5.setHorizontalAlignment(Element.ALIGN_CENTER);
            vacioContent5.setPaddingTop(3f);
            vacioContent5.setPaddingBottom(3f);

            datosArea_Mapa.addCell(dataArea5);
            datosArea_Mapa.addCell(vacioContent5);

            // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< SEXTA FILA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            PdfPCell dataArea6 = new PdfPCell();
            dataArea6.setBorder(Rectangle.NO_BORDER);
            dataArea6.setFixedHeight(20f);
            dataArea6.setPadding(5f);

            PdfPCell vacioContent6 = new PdfPCell(new Phrase("", normalFont));
            vacioContent6.setBorder(Rectangle.NO_BORDER);
            vacioContent6.setHorizontalAlignment(Element.ALIGN_CENTER);
            vacioContent6.setPaddingTop(3f);
            vacioContent6.setPaddingBottom(3f);

            datosArea_Mapa.addCell(dataArea6);
            datosArea_Mapa.addCell(vacioContent6);

            document.add(datosArea_Mapa);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
