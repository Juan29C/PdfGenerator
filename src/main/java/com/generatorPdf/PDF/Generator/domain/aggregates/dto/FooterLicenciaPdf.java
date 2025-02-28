package com.generatorPdf.PDF.Generator.domain.aggregates.dto;

import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

@Component

public class FooterLicenciaPdf {
    public static void addFooter(PdfWriter writer, PdfRequest request) {
        try {
            // Cargar la imagen del pie de página desde la ruta proporcionada
            Image footerImage = Image.getInstance("imagen/pie_pagina.png    ");

            // Escalar la imagen para que ocupe todo el ancho de la hoja
            footerImage.scaleAbsolute(PageSize.A4.getWidth(), 120); // Ajusta el alto a 50 puntos (puedes modificarlo si es necesario)

            // Posicionar la imagen al borde inferior de la página
            footerImage.setAbsolutePosition(0, 0); // 0 puntos desde el borde izquierdo y abajo

            // Agregar la imagen al lienzo del PDF
            PdfContentByte canvas = writer.getDirectContent();
            canvas.addImage(footerImage);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen del pie de página: " + e.getMessage());
        }
    }
}
