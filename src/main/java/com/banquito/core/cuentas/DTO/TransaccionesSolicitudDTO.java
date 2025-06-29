package com.banquito.core.cuentas.dto;

import com.banquito.core.cuentas.enums.TipoTransaccionEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransaccionesSolicitudDTO {

    @NotNull(message = "El ID de la cuenta cliente es obligatorio")
    private Integer idCuentaCliente;

    @NotNull(message = "El tipo de transacción es obligatorio")
    private TipoTransaccionEnum tipoTransaccion;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que cero")
    private BigDecimal monto;

    @Size(max = 150, message = "La descripción no puede exceder los 150 caracteres")
    private String descripcion;
}