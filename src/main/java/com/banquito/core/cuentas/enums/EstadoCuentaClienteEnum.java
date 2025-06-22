package com.banquito.core.cuentas.enums;

public enum EstadoCuentaClienteEnum {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO"),
    SUSPENDIDO("SUSPENDIDO"),
    BLOQUEADO("BLOQUEADO");

    private final String valor;

    EstadoCuentaClienteEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}