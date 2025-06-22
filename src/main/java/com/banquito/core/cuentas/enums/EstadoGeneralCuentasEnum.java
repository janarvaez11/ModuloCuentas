package com.banquito.core.cuentas.enums;

public enum EstadoGeneralCuentasEnum {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO");

    private final String valor;

    EstadoGeneralCuentasEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}