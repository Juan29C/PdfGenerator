package com.generatorPdf.PDF.Generator.domain.ports.out;

public interface PDFServOut {
    void createPdf(String content, String filePath, String title, String footer, String imageUrl);
}

