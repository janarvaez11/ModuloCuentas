package com.banquito.core.cuentas.excepcion;

public class CrearEntidadExcepcion extends RuntimeException {
    private final Integer errorCode;
    private final String entidad;

    public CrearEntidadExcepcion(String entidad, String mensaje) {
        super(mensaje);
        this.errorCode = 2;
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
