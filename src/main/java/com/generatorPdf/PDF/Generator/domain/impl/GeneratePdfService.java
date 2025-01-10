package com.generatorPdf.PDF.Generator.domain.impl;

import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfRequest;
import com.generatorPdf.PDF.Generator.domain.ports.in.PDFServIn;
import com.generatorPdf.PDF.Generator.domain.ports.out.PDFServOut;
import org.springframework.stereotype.Service;

@Service
public class GeneratePdfService implements PDFServIn {
    private final PDFServOut pdfServOut;

    public GeneratePdfService(PDFServOut pdfServOut) {
        this.pdfServOut = pdfServOut;
    }

    @Override
    public void generatePdf(PdfRequest request) {
        if (request.getContent() == null || request.getContent().isEmpty()) {
            throw new IllegalArgumentException("El contenido no puede estar vacío");
        }
        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }

        // Definir la ruta del archivo basado en el título
        String filePath = "output/" + request.getTitle().replaceAll("\\s+", "_") + ".pdf";

        // Pasar todos los valores al adaptador
        pdfServOut.createPdf(
                request.getContent(),
                filePath,
                request.getTitle(),
                request.getFooter(),
                request.getImageUrl()
        );
    }
}
