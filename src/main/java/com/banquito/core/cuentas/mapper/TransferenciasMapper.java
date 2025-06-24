package com.banquito.core.cuentas.mapper;

import com.banquito.core.cuentas.DTO.CuentasClientesRespuestaDTO_Min2;
import com.banquito.core.cuentas.DTO.TransferenciasSolicitudDTO;
import com.banquito.core.cuentas.DTO.TransferenciasRespuestaDTO;
import com.banquito.core.cuentas.modelo.CuentasClientes;
import com.banquito.core.cuentas.modelo.Transferencias;

public class TransferenciasMapper {

    // Este mapper para solicitud se usará principalmente internamente en el servicio de Transacciones
    // para construir la entidad Transferencias a partir de un DTO de solicitud de transferencia más general.
    public static Transferencias toTransferencias(TransferenciasSolicitudDTO dto) {
        if (dto == null) {
            return null;
        }
        Transferencias entity = new Transferencias();
        // idTransaccion se asignará después de crear la Transacciones principal
        if (dto.getIdCuentaClienteDestino() != null) {
            entity.setIdCuentaClienteDestino(new CuentasClientes(dto.getIdCuentaClienteDestino()));
        }
        // estado y version son gestionados por el servicio/BD
        return entity;
    }

    public static TransferenciasRespuestaDTO toTransferenciasRespuestaDTO(Transferencias entity) {
        if (entity == null) {
            return null;
        }
        return TransferenciasRespuestaDTO.builder()
                .id(entity.getId())
                .idTransaccion(TransaccionesMapper.toTransaccionesRespuestaDTO(entity.getIdTransaccion())) // Usamos el mapper de Transacciones
                .idCuentaClienteDestino(entity.getIdCuentaClienteDestino() != null ?
                        CuentasClientesRespuestaDTO_Min2.builder()
                                .id(entity.getIdCuentaClienteDestino().getId())
                                .idCliente(entity.getIdCuentaClienteDestino().getIdCliente())
                                .numeroCuenta(entity.getIdCuentaClienteDestino().getNumeroCuenta())
                                .build() : null)
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .build();
    }
}