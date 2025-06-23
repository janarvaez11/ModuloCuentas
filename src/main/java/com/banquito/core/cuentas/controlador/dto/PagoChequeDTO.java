package com.banquito.core.cuentas.controlador.dto;

import java.math.BigDecimal;

public class PagoChequeDTO {

    private Integer idTransaccion;
    private String numeroCheque;
    private String bancoEmisor;
    private BigDecimal monto;

    public PagoChequeDTO() {
    }

    // Getters y Setters

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public String getBancoEmisor() {
        return bancoEmisor;
    }

    public void setBancoEmisor(String bancoEmisor) {
        this.bancoEmisor = bancoEmisor;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
