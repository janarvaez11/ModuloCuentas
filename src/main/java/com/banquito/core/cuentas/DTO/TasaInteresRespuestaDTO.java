package com.banquito.core.cuentas.DTO;

import com.banquito.core.cuentas.enums.BaseCalculoTasaEnum;
import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;
import com.banquito.core.cuentas.enums.FrecuenciaEnum;
import com.banquito.core.cuentas.enums.MetodoCalculoTasaEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TasaInteresRespuestaDTO {
    private Integer id;
    private BaseCalculoTasaEnum baseCalculo;
    private MetodoCalculoTasaEnum metodoCalculo;
    private FrecuenciaEnum frecuenciaCapitalizacion;
    private LocalDate fechaInicioVigencia;
    private LocalDate fechaFinVigencia;
    private EstadoGeneralCuentasEnum estado;
    private Long version;
}