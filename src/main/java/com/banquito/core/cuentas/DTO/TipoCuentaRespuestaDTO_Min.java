package com.banquito.core.cuentas.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipoCuentaRespuestaDTO_Min {
    private Integer id;
    private String nombre;
}