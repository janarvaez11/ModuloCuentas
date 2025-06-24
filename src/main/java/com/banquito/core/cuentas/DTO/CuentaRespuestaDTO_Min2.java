package com.banquito.core.cuentas.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CuentaRespuestaDTO_Min2 {
    private Integer id;
    private String codigoCuenta;
    private String nombre;
}