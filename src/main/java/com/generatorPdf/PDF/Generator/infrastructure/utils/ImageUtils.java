package com.generatorPdf.PDF.Generator.infrastructure.utils;

import com.lowagie.text.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ImageUtils {

    private static final String GOOGLE_MAPS_API_KEY = "AIzaSyB0qLHniH28RRMquPY-LUz7SsAR2e7z93A"; //

    private ImageUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Image obtenerImagenMapa(String coordenadas) throws Exception {
        String[] parts = coordenadas.split(",");
        String lat = parts[0].trim();
        String lng = parts[1].trim();

        String urlString = "https://maps.googleapis.com/maps/api/staticmap?"
                + "center=" + URLEncoder.encode(lat + "," + lng, StandardCharsets.UTF_8)
                + "&zoom=18"
                + "&size=600x400"
                + "&maptype=roadmap"
                + "&markers=color:red%7Clabel:X%7C" + URLEncoder.encode(lat + "," + lng, StandardCharsets.UTF_8)
                + "&key="+GOOGLE_MAPS_API_KEY;

        URL url = new URL(urlString);
        BufferedImage bufferedImage = ImageIO.read(url);

        if (bufferedImage == null) {
            throw new Exception("No se pudo descargar la imagen del mapa.");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        com.lowagie.text.Image image = Image.getInstance(baos.toByteArray());

        image.scaleToFit(228, 120);

        return image;
    }
}