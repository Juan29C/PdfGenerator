package com.generatorPdf.PDF.Generator.domain.ports.out;

import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfRequest;
import com.generatorPdf.PDF.Generator.domain.aggregates.dto.PdfTramiteLicenciaDoc;

public interface PDFServOut {
    void createPdf(PdfRequest request, String filePath);

    void createDocTramiteLicenciaPDF(PdfTramiteLicenciaDoc pdfTramiteLicenciaDoc, String filePath);
}

