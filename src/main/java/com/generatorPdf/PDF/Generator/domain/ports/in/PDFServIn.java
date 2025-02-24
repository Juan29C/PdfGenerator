    package com.generatorPdf.PDF.Generator.domain.ports.in;

    import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfRequest;
    import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfTramiteLicenciaDoc;

    public interface PDFServIn {
        void generatePdf(PdfRequest request);

        void generateDocTramiteLicenciaPDF(PdfTramiteLicenciaDoc pdfTramiteLicenciaDoc);
    }

