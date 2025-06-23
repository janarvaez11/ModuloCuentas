package com.banquito.core.cuentas.controlador.dto;

import java.math.BigDecimal;

public class PagoTarjetaDTO {

    private Integer idTransaccion;
    private String numeroTarjeta;
    private String entidadEmisora;
    private BigDecimal monto;

    public PagoTarjetaDTO() {
    }

    // Getters y Setters

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getEntidadEmisora() {
        return entidadEmisora;
    }

    public void setEntidadEmisora(String entidadEmisora) {
        this.entidadEmisora = entidadEmisora;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
