package com.banquito.core.cuentas.controlador.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.banquito.core.cuentas.enums.TipoTransaccionEnum;

public class TransaccionDTO {

    private Integer idCuentaCliente;
    private TipoTransaccionEnum tipoTransaccion;
    private BigDecimal monto;
    private String descripcion;
    private Instant fechaTransaccion;

    public TransaccionDTO() {
    }

    // Getters y Setters

    public Integer getIdCuentaCliente() {
        return idCuentaCliente;
    }

    public void setIdCuentaCliente(Integer idCuentaCliente) {
        this.idCuentaCliente = idCuentaCliente;
    }

    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instant getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Instant fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }
}
