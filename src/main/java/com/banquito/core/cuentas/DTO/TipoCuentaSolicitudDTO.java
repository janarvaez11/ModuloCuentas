package com.banquito.core.cuentas.DTO;

import com.banquito.core.cuentas.enums.TipoClienteEnum;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Builder
public class TipoCuentaSolicitudDTO {

    @NotBlank(message = "El ID de moneda es obligatorio")
    @Size(max = 3, message = "El ID de moneda no puede exceder los 3 caracteres") // Asumiendo un código de moneda como USD, EUR, etc.
    private String idMoneda;

    @NotNull(message = "El ID de la tasa de interés por defecto es obligatorio")
    private Integer idTasaInteresPorDefecto; // Solo el ID de la tasa de interés

    @NotBlank(message = "El nombre del tipo de cuenta es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @Size(max = 150, message = "La descripción no puede exceder los 150 caracteres")
    private String descripcion;

    @NotBlank(message = "Los requisitos de apertura son obligatorios")
    @Size(max = 300, message = "Los requisitos de apertura no pueden exceder los 300 caracteres")
    private String requisitosApertura;

    @NotNull(message = "El tipo de cliente es obligatorio")
    private TipoClienteEnum tipoCliente;
}