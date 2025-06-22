package com.banquito.core.cuentas.enums;

public enum TipoComisionEnum {
    TRANSACCION("TRANSACCION"),
    SERVICIO("SERVICIO"),
    PERIODICO("PERIODICO");

    private final String valor;

    TipoComisionEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}