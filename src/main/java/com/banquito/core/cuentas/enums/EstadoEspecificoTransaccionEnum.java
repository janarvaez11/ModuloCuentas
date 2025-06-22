package com.banquito.core.cuentas.enums;

public enum EstadoEspecificoTransaccionEnum {
    ENVIADO("ENVIADO"),
    PENDIENTE("PENDIENTE"),
    RECHAZADO("RECHAZADO"),
    COMPLETADO("COMPLETADO"),;

    private final String valor;

    EstadoEspecificoTransaccionEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
