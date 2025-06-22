package com.banquito.core.cuentas.enums;

public enum FrecuenciaEnum {
    DIARIA("DIARIA"),
    MENSUAL("MENSUAL"),
    TRIMESTRAL("TRIMESTRAL"),
    ANUAL("ANUAL");

    private final String valor;

    FrecuenciaEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}