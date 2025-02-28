package com.generatorPdf.PDF.Generator.domain.aggregates.dto;

import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

@Component
public class BackgroundLicenciaPdf {

    public static void addBackgroundImage(PdfWriter writer){
        try {
            // Cargar la imagen de fondo
            Image background = Image.getInstance("imagen/logo.jpg"); // Ruta de la imagen de fondo

            // Ajustar el tamaño de la imagen, por ejemplo, con un 80% del tamaño de la página
            float scaleWidth = PageSize.A4.getWidth() * 0.7f; // 80% del ancho de la página
            float scaleHeight = PageSize.A4.getHeight() * 0.7f; // 80% de la altura de la página
            background.scaleToFit(scaleWidth, scaleHeight); // Escalar la imagen

            // Posicionar la imagen de fondo en el centro de la página
            float xPosition = (PageSize.A4.getWidth() - scaleWidth) / 2; // Centrar horizontalmente
            float yPosition = (PageSize.A4.getHeight() - scaleHeight) / 2; // Centrar verticalmente
            background.setAbsolutePosition(xPosition, yPosition); // Posicionar la imagen

            // Configurar la opacidad de la imagen (por ejemplo, 5% de opacidad)
            float opacity = 0.08f; // Transparencia del 5% (valor entre 0 y 1)

            // Aplicar la transparencia
            PdfContentByte canvas = writer.getDirectContentUnder();
            PdfGState gState = new PdfGState();
            gState.setFillOpacity(opacity); // Establecer la opacidad
            canvas.setGState(gState);
            canvas.addImage(background);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
        }
    }
}
