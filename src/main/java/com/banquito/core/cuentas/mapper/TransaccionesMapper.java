package com.banquito.core.cuentas.mapper;

import com.banquito.core.cuentas.dto.CuentasClientesRespuestaDTO_Min2;
import com.banquito.core.cuentas.dto.TransaccionesRespuestaDTO;
import com.banquito.core.cuentas.dto.TransaccionesSolicitudDTO;
import com.banquito.core.cuentas.modelo.CuentasClientes;
import com.banquito.core.cuentas.modelo.Transacciones;

public class TransaccionesMapper {

    public static Transacciones toTransacciones(TransaccionesSolicitudDTO dto) {
        if (dto == null) {
            return null;
        }
        Transacciones entity = new Transacciones();
        if (dto.getIdCuentaCliente() != null) {
            entity.setIdCuentaCliente(new CuentasClientes(dto.getIdCuentaCliente()));
        }
        entity.setTipoTransaccion(dto.getTipoTransaccion());
        entity.setMonto(dto.getMonto());
        entity.setDescripcion(dto.getDescripcion());
        // fechaTransaccion, estado y version son gestionados por el servicio/BD
        return entity;
    }

    public static TransaccionesRespuestaDTO toTransaccionesRespuestaDTO(Transacciones entity) {
        if (entity == null) {
            return null;
        }
        return TransaccionesRespuestaDTO.builder()
                .id(entity.getId())
                .idCuentaCliente(entity.getIdCuentaCliente() != null ?
                        CuentasClientesRespuestaDTO_Min2.builder()
                                .id(entity.getIdCuentaCliente().getId())
                                .idCliente(entity.getIdCuentaCliente().getIdCliente())
                                .numeroCuenta(entity.getIdCuentaCliente().getNumeroCuenta())
                                .build() : null)
                .tipoTransaccion(entity.getTipoTransaccion())
                .monto(entity.getMonto())
                .descripcion(entity.getDescripcion())
                .fechaTransaccion(entity.getFechaTransaccion())
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .build();
    }
}