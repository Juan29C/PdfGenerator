package com.generatorPdf.PDF.Generator.application.controller;

import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfRequest;
import com.generatorPdf.PDF.Generator.domain.ports.in.PDFServIn;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/api/v1/pdf")
public class GeneratePdfController {

    private final PDFServIn pdfServIn;

    public GeneratePdfController(PDFServIn pdfServIn) {
        this.pdfServIn = pdfServIn;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generatePdf(@RequestBody PdfRequest request) {
        try {
            // Generar el PDF
            pdfServIn.generatePdf(request);
            return ResponseEntity.ok("PDF generado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar el PDF: " + e.getMessage());
        }
    }

    @GetMapping("/generate-and-download")
    public ResponseEntity<byte[]> generateAndDownloadPdf(@RequestParam String title) throws IOException {
        // Ruta donde se guardó el archivo
        String filePath = "output/" + title.replaceAll("\\s+", "_") + ".pdf";

        File pdfFile = new File(filePath);

        if (!pdfFile.exists()) {
            return ResponseEntity.notFound().build(); // Retorna 404 si el archivo no existe
        }

        // Leer el archivo en bytes
        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        byte[] pdfBytes = fileInputStream.readAllBytes();
        fileInputStream.close();

        // Configurar headers para la respuesta
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + pdfFile.getName() + "\"")
                .body(pdfBytes);
    }
}

