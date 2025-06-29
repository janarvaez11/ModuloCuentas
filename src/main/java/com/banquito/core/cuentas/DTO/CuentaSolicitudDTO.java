package com.banquito.core.cuentas.dto;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Builder
public class CuentaSolicitudDTO {

    @NotNull(message = "El ID del tipo de cuenta es obligatorio")
    private Integer idTipoCuenta;

    @NotNull(message = "El ID de la tasa de interés es obligatorio")
    private Integer idTasaInteres;

    @NotBlank(message = "El código de cuenta es obligatorio")
    @Size(max = 20, message = "El código de cuenta no puede exceder los 20 caracteres")
    private String codigoCuenta;

    @NotBlank(message = "El nombre de la cuenta es obligatorio")
    @Size(max = 100, message = "El nombre de la cuenta no puede exceder los 100 caracteres")
    private String nombre;

    @Size(max = 150, message = "La descripción no puede exceder los 150 caracteres")
    private String descripcion;
}