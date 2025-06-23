package com.banquito.core.cuentas.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal; // Aunque monto está en Transacciones, lo ponemos para claridad si el controlador lo necesita

@Data
@Builder
public class TransferenciasSolicitudDTO {

    @NotNull(message = "El ID de la cuenta cliente origen es obligatorio")
    private Integer idCuentaClienteOrigen; // Corresponde a idCuentaCliente en Transacciones

    @NotNull(message = "El ID de la cuenta cliente destino es obligatorio")
    private Integer idCuentaClienteDestino;

    @NotNull(message = "El monto de la transferencia es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto de la transferencia debe ser mayor que cero")
    private BigDecimal monto; // Repetimos monto aquí para la solicitud completa de transferencia

    private String descripcion; // Descripción de la transferencia
}