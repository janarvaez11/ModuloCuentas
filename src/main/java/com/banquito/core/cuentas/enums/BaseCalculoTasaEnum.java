package com.banquito.core.cuentas.enums;

public enum BaseCalculoTasaEnum {
    BASE_30_360("30/360"),
    BASE_31_365("31/365");

    private final String valor;

    BaseCalculoTasaEnum(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor;
    }
    
    public String getValor() {
        return valor;
    }
}