package com.banquito.core.cuentas.enums;

public enum EstadoTransaccionesEnum {
    ACTIVO("ACTIVO"),
    PROCESADO("PROCESADO"),
    PENDIENTE("PENDIENTE"),
    RECHAZADO("RECHAZADO"),;

    private final String valor;

    EstadoTransaccionesEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
