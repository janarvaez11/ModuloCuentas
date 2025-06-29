package com.banquito.core.cuentas.dto;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;
import com.banquito.core.cuentas.enums.TipoClienteEnum;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class TipoCuentaRespuestaDTO {
    private Integer id;
    private String idMoneda;
    private TasaInteresRespuestaDTO idTasaInteresPorDefecto; // Se devuelve el DTO completo de la tasa
    private String nombre;
    private String descripcion;
    private String requisitosApertura;
    private TipoClienteEnum tipoCliente;
    private Instant fechaCreacion;
    private Instant fechaModificacion;
    private EstadoGeneralCuentasEnum estado;
    private Long version;
}
/*
 * Nota sobre idTasaInteresPorDefecto en TipoCuentaRespuestaDTO: Al devolver TasaInteresRespuestaDTO, 
 * el cliente obtiene una vista más rica de la tasa de interés asociada, no solo su ID. Esto es una elección de diseño; 
 * si prefieres solo el ID, se puede cambiar a private Integer idTasaInteresPorDefectoId;. 
 * Para este caso, creo que mostrar la información de la tasa es más útil.
 */