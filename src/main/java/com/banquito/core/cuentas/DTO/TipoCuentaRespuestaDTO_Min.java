package com.banquito.core.cuentas.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipoCuentaRespuestaDTO_Min {
    private Integer id;
    private String nombre;
}