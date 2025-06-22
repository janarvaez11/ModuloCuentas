package com.banquito.core.cuentas.enums;

public enum FrecuenciaComisionEnum {
    MENSUAL("MENSUAL"),
    TRIMESTRAL("TRIMESTRAL"),
    ANUAL("ANUAL");

    private final String valor;

    FrecuenciaComisionEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}