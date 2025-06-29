package com.banquito.core.cuentas.mapper;

import com.banquito.core.cuentas.dto.TipoCuentaRespuestaDTO;
import com.banquito.core.cuentas.dto.TipoCuentaSolicitudDTO;
import com.banquito.core.cuentas.modelo.TasasIntereses;
import com.banquito.core.cuentas.modelo.TiposCuentas;

public class TipoCuentaMapper {

    public static TiposCuentas toTiposCuentas(TipoCuentaSolicitudDTO dto) {
        if (dto == null) {
            return null;
        }
        TiposCuentas entity = new TiposCuentas();
        entity.setIdMoneda(dto.getIdMoneda());
        // Solo creamos una referencia a la TasaInteres por su ID
        if (dto.getIdTasaInteresPorDefecto() != null) {
            entity.setIdTasaInteresPorDefecto(new TasasIntereses(dto.getIdTasaInteresPorDefecto()));
        }
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setRequisitosApertura(dto.getRequisitosApertura());
        entity.setTipoCliente(dto.getTipoCliente());
        // FechaCreacion, FechaModificacion, Estado y Version son gestionados por el servicio/BD
        return entity;
    }

    public static TipoCuentaRespuestaDTO toTipoCuentaRespuestaDTO(TiposCuentas entity) {
        if (entity == null) {
            return null;
        }
        return TipoCuentaRespuestaDTO.builder()
                .id(entity.getId())
                .idMoneda(entity.getIdMoneda())
                .idTasaInteresPorDefecto(TasaInteresMapper.toTasaInteresRespuestaDTO(entity.getIdTasaInteresPorDefecto())) // Convertir la entidad a su DTO de respuesta
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .requisitosApertura(entity.getRequisitosApertura())
                .tipoCliente(entity.getTipoCliente())
                .fechaCreacion(entity.getFechaCreacion())
                .fechaModificacion(entity.getFechaModificacion())
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .build();
    }
}