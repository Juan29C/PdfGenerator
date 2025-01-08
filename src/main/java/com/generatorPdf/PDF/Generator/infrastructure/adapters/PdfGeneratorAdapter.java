package com.generatorPdf.PDF.Generator.infrastructure.adapters;

import com.generatorPdf.PDF.Generator.domain.ports.out.PDFServOut;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;

@Component
public class PdfGeneratorAdapter implements PDFServOut {

    @Override
    public void createPdf(String content, String filePath) {
        try {
            // Crear el directorio si no existe
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            // Crear el documento PDF
            Document document = new Document();
            FileOutputStream fos = new FileOutputStream(filePath);

            // Inicializar el escritor de PDF
            PdfWriter.getInstance(document, fos);

            // Abrir el documento
            document.open();

            // Agregar contenido
            document.add(new Paragraph(content));

            // Cerrar el documento
            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }
}
