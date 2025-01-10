package com.generatorPdf.PDF.Generator.infrastructure.adapters;

import com.generatorPdf.PDF.Generator.domain.ports.out.PDFServOut;
import com.lowagie.text.*;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

@Component
public class PdfGeneratorAdapter implements PDFServOut {

    @Override
    public void createPdf(String content, String filePath, String title, String footer, String imageUrl) {
        try {
            // Crear el directorio si no existe
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            // Crear el documento PDF
            Document document = new Document(PageSize.A4);
            FileOutputStream fos = new FileOutputStream(filePath);

            // Inicializar el escritor de PDF
            PdfWriter.getInstance(document, fos);

            // Abrir el documento
            document.open();

            // Título principal
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph titleParagraph = new Paragraph(title, titleFont);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(titleParagraph);

            // Espacio
            document.add(new Paragraph("\n"));

            // Contenido
            Font contentFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
            Paragraph body = new Paragraph(content, contentFont);
            document.add(body);

            // Agregar imagen desde URL
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    // Descargar la imagen directamente como InputStream
                    URL url = new URL(imageUrl);
                    try (InputStream in = url.openStream()) {
                        // Crear la imagen directamente desde el InputStream
                        Image qrImage = Image.getInstance(in.readAllBytes());
                        qrImage.setAlignment(Element.ALIGN_CENTER); // Centrar la imagen
                        qrImage.scaleToFit(150, 150); // Ajustar tamaño
                        document.add(qrImage);
                    }
                } catch (Exception e) {
                    System.err.println("Error al cargar la imagen desde la URL: " + e.getMessage());
                }
            }


            // Pie de página
            Font footerFont = new Font(Font.HELVETICA, 10, Font.ITALIC);
            Paragraph footerParagraph = new Paragraph(footer, footerFont);
            footerParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(footerParagraph);

            // Cerrar el documento
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }
}

