package com.banquito.core.cuentas.enums;

public enum BaseCalculoComisionEnum {
    FIJO("FIJO"),
    PORCENTAJE("PORCENTAJE");

    private final String valor;

    BaseCalculoComisionEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}