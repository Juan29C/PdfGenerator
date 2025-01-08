package com.generatorPdf.PDF.Generator.application.controller;


import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfRequest;
import com.generatorPdf.PDF.Generator.domain.ports.in.PDFServIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pdf")
public class GeneratePdfController {

    private final PDFServIn pdfServIn;

    public GeneratePdfController(PDFServIn pdfServIn) {
        this.pdfServIn = pdfServIn;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generatePdf(@RequestBody PdfRequest request) {
        pdfServIn.generatePdf(request);
        return ResponseEntity.ok("PDF generado exitosamente");
    }

}
