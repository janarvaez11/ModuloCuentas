package com.banquito.core.cuentas.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CuentasClientesRespuestaDTO_Min2 {
    private Integer id;
    private Integer idCliente;
    private String numeroCuenta;
}