package com.generatorPdf.PDF.Generator.domain.ports.out;

import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfRequest;

public interface PDFServOut {
    void createPdf(PdfRequest request, String filePath);
}

