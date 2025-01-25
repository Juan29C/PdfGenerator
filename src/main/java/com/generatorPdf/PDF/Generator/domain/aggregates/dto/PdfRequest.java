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
    private String horarioAtencionInicio;
    private String horarioAtencionFin;
    private String textoLimites;
    private String fechaGeneracion;
    private String logoLocalPath;
    private String qrCodeUrl;
    private String footerImagePath;
    private String BackgroundImagePath;


    public String getBackgroundImagePath() {
        return BackgroundImagePath;
    }

    public void setBackgroundImagePath(String backgroundImagePath) {
        BackgroundImagePath = backgroundImagePath;
    }

    public String getTextoCumplimiento() {
        return textoCumplimiento;
    }

    public void setTextoCumplimiento(String textoCumplimiento) {
        this.textoCumplimiento = textoCumplimiento;
    }

    // Getters y setters
    public String getFooterImagePath() {
        return footerImagePath;
    }

    public void setFooterImagePath(String footerImagePath) {
        this.footerImagePath = footerImagePath;
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

    public String getHorarioAtencionInicio() {
        return horarioAtencionInicio;
    }

    public void setHorarioAtencionInicio(String horarioAtencionInicio) {
        this.horarioAtencionInicio = horarioAtencionInicio;
    }

    public String getHorarioAtencionFin() {
        return horarioAtencionFin;
    }

    public void setHorarioAtencionFin(String horarioAtencionFin) {
        this.horarioAtencionFin = horarioAtencionFin;
    }

    public String getTextoLimites() {
        return textoLimites;
    }

    public void setTextoLimites(String textoLimites) {
        this.textoLimites = textoLimites;
    }

    public String getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(String fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
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

