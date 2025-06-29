package com.banquito.core.cuentas.dto;

import com.banquito.core.cuentas.enums.EstadoEspecificoTransaccionEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferenciasRespuestaDTO {
    private Integer id;
    private TransaccionesRespuestaDTO idTransaccion; // DTO completo de la Transaccion asociada
    private CuentasClientesRespuestaDTO_Min2 idCuentaClienteDestino; // ID, idCliente, numeroCuenta de la cuenta cliente destino
    private EstadoEspecificoTransaccionEnum estado;
    private Long version;
}