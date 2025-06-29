package com.banquito.core.cuentas.dto;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
public class CuentasClientesSolicitudDTO {

    @NotNull(message = "El ID de la cuenta es obligatorio")
    private Integer idCuenta;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer idCliente;

    @NotBlank(message = "El número de cuenta es obligatorio")
    @Size(max = 10, message = "El número de cuenta no puede exceder los 10 caracteres")
    private String numeroCuenta;

    @NotNull(message = "El saldo disponible es obligatorio")
    private BigDecimal saldoDisponible;

    @NotNull(message = "El saldo contable es obligatorio")
    private BigDecimal saldoContable;
}