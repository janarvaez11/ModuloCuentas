package com.banquito.core.cuentas.dto;

import com.banquito.core.cuentas.enums.EstadoTransaccionesEnum;
import com.banquito.core.cuentas.enums.TipoTransaccionEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class TransaccionesRespuestaDTO {
    private Integer id;
    private CuentasClientesRespuestaDTO_Min2 idCuentaCliente; // ID, idCliente, numeroCuenta de la cuenta cliente
    private TipoTransaccionEnum tipoTransaccion;
    private BigDecimal monto;
    private String descripcion;
    private Instant fechaTransaccion;
    private EstadoTransaccionesEnum estado;
    private Long version;
}