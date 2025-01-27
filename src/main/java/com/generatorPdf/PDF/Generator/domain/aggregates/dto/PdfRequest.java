package com.generatorPdf.PDF.Generator.domain.aggregates.dto;

public class PdfRequest {
    private String expediente;
    private String resolucion;
    private String licencia;
    private String nivelRiesgo;
    private String textoCumplimiento;
    private String titular;
    private String ruc;
    private String zonificacion;
    private String nombreComercial;
    private String giro;
    private String actividadComercial;
    private String ubicacion;
    private String areaComercial;
    private String textoLimites;
    private String logoLocalPath;
    private String qrCodeUrl;

    public String getTextoCumplimiento() {
        return textoCumplimiento;
    }

    public void setTextoCumplimiento(String textoCumplimiento) {
        this.textoCumplimiento = textoCumplimiento;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getZonificacion() {
        return zonificacion;
    }

    public void setZonificacion(String zonificacion) {
        this.zonificacion = zonificacion;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getActividadComercial() {
        return actividadComercial;
    }

    public void setActividadComercial(String actividadComercial) {
        this.actividadComercial = actividadComercial;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getAreaComercial() {
        return areaComercial;
    }

    public void setAreaComercial(String areaComercial) {
        this.areaComercial = areaComercial;
    }

    public String getTextoLimites() {
        return textoLimites;
    }

    public void setTextoLimites(String textoLimites) {
        this.textoLimites = textoLimites;
    }

    public String getLogoLocalPath() {
        return logoLocalPath;
    }

    public void setLogoLocalPath(String logoLocalPath) {
        this.logoLocalPath = logoLocalPath;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }
}

