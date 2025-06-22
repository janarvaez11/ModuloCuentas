package com.banquito.core.cuentas.enums;

public enum MetodoCalculoTasaEnum {
    SALDO_DIARIO("SALDO DIARIO"),
    SALDO_PROMEDIO("SALDO PROMEDIO");

    private final String valor;

    MetodoCalculoTasaEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}