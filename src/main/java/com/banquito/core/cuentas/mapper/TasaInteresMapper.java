package com.banquito.core.cuentas.mapper; // Nuevo paquete para mappers

import com.banquito.core.cuentas.DTO.TasaInteresSolicitudDTO;
import com.banquito.core.cuentas.DTO.TasaInteresRespuestaDTO;
import com.banquito.core.cuentas.modelo.TasasIntereses;

public class TasaInteresMapper {

    public static TasasIntereses toTasasIntereses(TasaInteresSolicitudDTO dto) {
        if (dto == null) {
            return null;
        }
        TasasIntereses entity = new TasasIntereses();
        entity.setBaseCalculo(dto.getBaseCalculo());
        entity.setMetodoCalculo(dto.getMetodoCalculo());
        entity.setFrecuenciaCapitalizacion(dto.getFrecuenciaCapitalizacion());
        entity.setFechaInicioVigencia(dto.getFechaInicioVigencia());
        entity.setFechaFinVigencia(dto.getFechaFinVigencia());
        return entity;
    }

    public static TasaInteresRespuestaDTO toTasaInteresRespuestaDTO(TasasIntereses entity) {
        if (entity == null) {
            return null;
        }
        return TasaInteresRespuestaDTO.builder()
                .id(entity.getId())
                .baseCalculo(entity.getBaseCalculo())
                .metodoCalculo(entity.getMetodoCalculo())
                .frecuenciaCapitalizacion(entity.getFrecuenciaCapitalizacion())
                .fechaInicioVigencia(entity.getFechaInicioVigencia())
                .fechaFinVigencia(entity.getFechaFinVigencia())
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .build();
    }
}