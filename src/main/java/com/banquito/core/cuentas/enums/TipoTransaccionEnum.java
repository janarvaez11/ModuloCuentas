package com.banquito.core.cuentas.enums;

public enum TipoTransaccionEnum {
    DEPOSITO("DEPOSITO"),
    RETIRO("RETIRO"),
    TRANSFERENCIA("TRANSFERENCIA"),
    PAGO_DEBITO("PAGO DEBITO"),
    PAGO_CHEQUE("PAGO CHEQUE");

    private final String valor;

    TipoTransaccionEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
