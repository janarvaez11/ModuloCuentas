package com.banquito.core.cuentas.dto;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CuentaRespuestaDTO {
    private Integer id;
    private TipoCuentaRespuestaDTO_Min idTipoCuenta; // Solo ID y nombre del tipo de cuenta
    private TasaInteresRespuestaDTO_IdOnly idTasaInteres; // Solo ID de la tasa de interés
    private String codigoCuenta;
    private String nombre;
    private String descripcion;
    private Instant fechaCreacion;
    private Instant fechaModificacion;
    private EstadoGeneralCuentasEnum estado;
    private Long version;
}