package com.banquito.core.cuentas.excepcion;

public class EntidadNoEncontradaExcepcion extends RuntimeException {

    private final Integer errorCode;
    private final String entidad;

    public EntidadNoEncontradaExcepcion(String entidad, String mensaje) {
        super(mensaje);
        this.errorCode = 1;
        this.entidad = entidad;
    }

    @Override
    public String getMessage() {
        return "Error code: " + this.errorCode + ", Entidad: " + this.entidad + ", Mensaje: " + super.getMessage();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getEntidad() {
        return entidad;
    }
}
