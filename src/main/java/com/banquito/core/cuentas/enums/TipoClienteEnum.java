package com.banquito.core.cuentas.enums;

public enum TipoClienteEnum {
    PERSONA("PERSONA"),
    EMPRESA("EMPRESA"),
    AMBOS("AMBOS");

    private final String valor;

    TipoClienteEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}