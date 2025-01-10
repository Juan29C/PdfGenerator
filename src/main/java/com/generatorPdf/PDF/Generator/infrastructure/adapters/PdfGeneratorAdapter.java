package com.generatorPdf.PDF.Generator.infrastructure.adapters;

import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfRequest;
import com.generatorPdf.PDF.Generator.domain.ports.out.PDFServOut;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

@Component
public class PdfGeneratorAdapter implements PDFServOut {

    @Override
    public void createPdf(PdfRequest request, String filePath) {
        try {
            // Crear el directorio si no existe
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            // Crear el documento PDF
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Título principal
            Font titleFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph(request.getTitle(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Subtítulo
            if (request.getSubtitle() != null && !request.getSubtitle().isEmpty()) {
                Font subtitleFont = new Font(Font.HELVETICA, 12, Font.ITALIC);
                Paragraph subtitle = new Paragraph(request.getSubtitle(), subtitleFont);
                subtitle.setAlignment(Element.ALIGN_CENTER);
                document.add(subtitle);
            }

            // Espacio
            document.add(new Paragraph("\n"));

            // Imagen en la parte superior derecha
            if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
                try {
                    URL url = new URL(request.getImageUrl());
                    Image image = Image.getInstance(url);
                    image.setAbsolutePosition(450, 750); // Ajustar posición de la imagen
                    image.scaleToFit(100, 100); // Escalar imagen
                    document.add(image);
                } catch (Exception e) {
                    System.err.println("Error al cargar la imagen: " + e.getMessage());
                }
            }

            // Contenido principal
            Font contentFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
            Paragraph content = new Paragraph(request.getContent(), contentFont);
            content.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(content);

            // Espacio adicional
            document.add(new Paragraph("\n"));

            // Agregar datos adicionales como secciones separadas
            Font sectionFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font textFont = new Font(Font.HELVETICA, 12, Font.NORMAL);

            addDataSection(document, "Expediente Nº:", request.getExpediente(), sectionFont, textFont);
            addDataSection(document, "Resolución Nº:", request.getResolucion(), sectionFont, textFont);
            addDataSection(document, "Licencia Nº:", request.getLicencia(), sectionFont, textFont);
            addDataSection(document, "Nivel de Riesgo:", request.getNivelRiesgo(), sectionFont, textFont);
            addDataSection(document, "Titular:", request.getTitular(), sectionFont, textFont);
            addDataSection(document, "RUC Nº:", request.getRuc(), sectionFont, textFont);
            addDataSection(document, "Zonificación:", request.getZonificacion(), sectionFont, textFont);
            addDataSection(document, "Nombre Comercial:", request.getComercial(), sectionFont, textFont);
            addDataSection(document, "Giro:", request.getGiro(), sectionFont, textFont);
            addDataSection(document, "Ubicación:", request.getUbicacion(), sectionFont, textFont);
            addDataSection(document, "Horario de Atención:", request.getHorario(), sectionFont, textFont);

            // Pie de página
            Font footerFont = new Font(Font.HELVETICA, 10, Font.ITALIC);
            Paragraph footer = new Paragraph(request.getFooter(), footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            // Cerrar el documento
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }

    /**
     * Método auxiliar para agregar secciones de datos al PDF.
     */
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


