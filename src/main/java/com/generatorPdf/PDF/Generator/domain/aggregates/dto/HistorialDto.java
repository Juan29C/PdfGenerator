package com.generatorPdf.PDF.Generator.domain.aggregates.dto;


import java.time.LocalDateTime;

public class HistorialDto {
    private Integer historialId;

    private String numeroResolucion;
    private LocalDateTime fechaCreacion;

    //@NotBlank(message = "El usuario responsable no debe estar vacío")
    //@Size(min = 3, max = 50, message = "El usuario responsable debe tener entre 3 y 30 caracteres")
    private String usuarioResponsable;

    private LicenciaDto licenciaDto;

    private OrdenTupaDto ordenTupaDto;

    public Integer getHistorialId() {
        return historialId;
    }

    public void setHistorialId(Integer historialId) {
        this.historialId = historialId;
    }

    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public LicenciaDto getLicenciaDto() {
        return licenciaDto;
    }

    public void setLicenciaDto(LicenciaDto licenciaDto) {
        this.licenciaDto = licenciaDto;
    }

    public OrdenTupaDto getOrdenTupaDto() {
        return ordenTupaDto;
    }

    public void setOrdenTupaDto(OrdenTupaDto ordenTupaDto) {
        this.ordenTupaDto = ordenTupaDto;
    }
}

