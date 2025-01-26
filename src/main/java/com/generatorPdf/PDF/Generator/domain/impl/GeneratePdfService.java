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
        // Validaciones básicas
        if (request == null) {
            throw new IllegalArgumentException("El objeto PdfRequest no puede ser nulo.");
        }

        if (request.getTitular() == null || request.getTitular().isEmpty()) {
            throw new IllegalArgumentException("El texto del PDF no puede estar vacío.");
        }

        // Definir la ruta del archivo basado en el título
        String filePath = "output/" + request.getTitular().replaceAll("\\s+", "_") + ".pdf";

        // Pasar todos los valores al adaptador
        pdfServOut.createPdf(request, filePath);
    }
}

