package com.banquito.core.cuentas.controlador.dto;

import java.math.BigDecimal;

public class TransferenciaDTO {

    private Integer idTransaccion;
    private String cuentaDestino;
    private String institucionFinanciera;
    private BigDecimal monto;

    public TransferenciaDTO() {
    }

    // Getters y Setters

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public String getInstitucionFinanciera() {
        return institucionFinanciera;
    }

    public void setInstitucionFinanciera(String institucionFinanciera) {
        this.institucionFinanciera = institucionFinanciera;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
