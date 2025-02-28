package com.generatorPdf.PDF.Generator.domain.aggregates.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class LicenciaDto {
    private Integer licenciaId;

    private String numeroLicencia;

    private LocalDate vigencia;

    private String fechaEstado;

    private String urlQr;

    private String numeroExpediente;

    private String numeroResolucion;

    private String estado;

    private String usuarioResponsable;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaModificacion;

    private Integer nivelDeRiesgoId;

    private Long ciudadanoId;

    private Integer declaracionJuradaId;

    private Long codigoZonificacionId;

    private Long trabajadorId;


    public Integer getLicenciaId() {
        return licenciaId;
    }

    public void setLicenciaId(Integer licenciaId) {
        this.licenciaId = licenciaId;
    }


    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public LocalDate getVigencia() {
        return vigencia;
    }

    public void setVigencia(LocalDate vigencia) {
        this.vigencia = vigencia;
    }





    public String getUrlQr() {
        return urlQr;
    }

    public void setUrlQr(String urlQr) {
        this.urlQr = urlQr;
    }


    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    public Integer getNivelDeRiesgoId() {
        return nivelDeRiesgoId;
    }

    public void setNivelDeRiesgoId(Integer nivelDeRiesgoId) {
        this.nivelDeRiesgoId = nivelDeRiesgoId;
    }

    public Long getCiudadanoId() {
        return ciudadanoId;
    }

    public void setCiudadanoId(Long ciudadanoId) {
        this.ciudadanoId = ciudadanoId;
    }

    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }



    public String getNumeroLicencia() {
        return Optional.ofNullable(numeroLicencia).filter(s -> !s.isBlank()).orElse("SIN REGISTRAR");
    }

    public String getNumeroExpediente() {
        return Optional.ofNullable(numeroExpediente).filter(s -> !s.isBlank()).orElse("SIN REGISTRAR");
    }

    public String getNumeroResolucion() {
        return Optional.ofNullable(numeroResolucion).filter(s -> !s.isBlank()).orElse("SIN REGISTRAR");
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getDeclaracionJuradaId() {
        return declaracionJuradaId;
    }

    public void setDeclaracionJuradaId(Integer declaracionJuradaId) {
        this.declaracionJuradaId = declaracionJuradaId;
    }

    public Long getCodigoZonificacionId() {
        return codigoZonificacionId;
    }

    public void setCodigoZonificacionId(Long codigoZonificacionId) {
        this.codigoZonificacionId = codigoZonificacionId;
    }

    public Long getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(Long trabajadorId) {
        this.trabajadorId = trabajadorId;
    }

    public String getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(String fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
