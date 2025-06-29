package com.banquito.core.cuentas.mapper;

import com.banquito.core.cuentas.dto.CuentaRespuestaDTO;
import com.banquito.core.cuentas.dto.CuentaSolicitudDTO;
import com.banquito.core.cuentas.dto.TasaInteresRespuestaDTO_IdOnly;
import com.banquito.core.cuentas.dto.TipoCuentaRespuestaDTO_Min;
import com.banquito.core.cuentas.modelo.Cuentas;
import com.banquito.core.cuentas.modelo.TasasIntereses;
import com.banquito.core.cuentas.modelo.TiposCuentas;

public class CuentaMapper {

    public static Cuentas toCuentas(CuentaSolicitudDTO dto) {
        if (dto == null) {
            return null;
        }
        Cuentas entity = new Cuentas();
        if (dto.getIdTipoCuenta() != null) {
            entity.setIdTipoCuenta(new TiposCuentas(dto.getIdTipoCuenta()));
        }
        if (dto.getIdTasaInteres() != null) {
            entity.setIdTasaInteres(new TasasIntereses(dto.getIdTasaInteres()));
        }
        entity.setCodigoCuenta(dto.getCodigoCuenta());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        // FechaCreacion, FechaModificacion, Estado y Version son gestionados por el servicio/BD
        return entity;
    }

    public static CuentaRespuestaDTO toCuentaRespuestaDTO(Cuentas entity) {
        if (entity == null) {
            return null;
        }
        return CuentaRespuestaDTO.builder()
                .id(entity.getId())
                .idTipoCuenta(entity.getIdTipoCuenta() != null ?
                        TipoCuentaRespuestaDTO_Min.builder()
                                .id(entity.getIdTipoCuenta().getId())
                                .nombre(entity.getIdTipoCuenta().getNombre())
                                .build() : null)
                .idTasaInteres(entity.getIdTasaInteres() != null ?
                        TasaInteresRespuestaDTO_IdOnly.builder()
                                .id(entity.getIdTasaInteres().getId())
                                .build() : null)
                .codigoCuenta(entity.getCodigoCuenta())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .fechaCreacion(entity.getFechaCreacion())
                .fechaModificacion(entity.getFechaModificacion())
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .build();
    }
}