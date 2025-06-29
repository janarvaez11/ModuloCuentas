package com.banquito.core.cuentas.dto;

import com.banquito.core.cuentas.enums.EstadoCuentaClienteEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class CuentasClientesRespuestaDTO {
    private Integer id;
    private CuentaRespuestaDTO_Min2 idCuenta; // ID, código y nombre de la cuenta
    private Integer idCliente; // Solo el ID del cliente
    private String numeroCuenta;
    private BigDecimal saldoDisponible;
    private BigDecimal saldoContable;
    private Instant fechaApertura;
    private EstadoCuentaClienteEnum estado;
    private Long version;
}