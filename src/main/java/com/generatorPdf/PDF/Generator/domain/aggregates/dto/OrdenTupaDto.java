package com.generatorPdf.PDF.Generator.domain.aggregates.dto;



import java.time.LocalDateTime;

public class OrdenTupaDto {
    private Integer ordenTupaId;

    //@Size(min = 3, max = 20, message = "El código de orden debe tener entre 3 y 20 caracteres")
    private String codigoOrden;

    private String proceAdministrativo;

    private LocalDateTime fechaCreacion;

    public boolean isValidCodigoOrden() {
        if ("Revocatoria".equalsIgnoreCase(proceAdministrativo) || "Nulidad".equalsIgnoreCase(proceAdministrativo)) {
            return "-".equals(codigoOrden); // Solo permite "-"
        }
        return codigoOrden != null && codigoOrden.length() >= 3 && codigoOrden.length() <= 20;
    }

    public Integer getOrdenTupaId() {
        return ordenTupaId;
    }

    public void setOrdenTupaId(Integer ordenTupaId) {
        this.ordenTupaId = ordenTupaId;
    }

    public String getCodigoOrden() {
        return codigoOrden;
    }

    public void setCodigoOrden(String codigoOrden) {
        this.codigoOrden = codigoOrden;
    }

    public String getProceAdministrativo() {
        return proceAdministrativo;
    }

    public void setProceAdministrativo(String proceAdministrativo) {
        this.proceAdministrativo = proceAdministrativo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
