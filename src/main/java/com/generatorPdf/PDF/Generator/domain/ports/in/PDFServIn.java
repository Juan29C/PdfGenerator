    package com.generatorPdf.PDF.Generator.domain.ports.in;

    import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfRequest;

    public interface PDFServIn {
        void generatePdf(PdfRequest request);
    }
