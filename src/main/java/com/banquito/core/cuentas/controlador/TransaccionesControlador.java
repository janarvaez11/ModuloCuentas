package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.DTO.TransaccionesSolicitudDTO;
import com.banquito.core.cuentas.DTO.TransaccionesRespuestaDTO;
import com.banquito.core.cuentas.DTO.TransferenciasSolicitudDTO;
import com.banquito.core.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.cuentas.mapper.TransaccionesMapper;
import com.banquito.core.cuentas.modelo.Transacciones;
import com.banquito.core.cuentas.servicio.TransaccionesServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transacciones")
@Slf4j
public class TransaccionesControlador {

    private final TransaccionesServicio transaccionesService;

    public TransaccionesControlador(TransaccionesServicio transaccionesService) {
        this.transaccionesService = transaccionesService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionesRespuestaDTO> obtenerTransaccionPorId(@PathVariable Integer id) {
        log.info("Recibida solicitud para obtener Transacción con ID: {}", id);
        try {
            Transacciones transaccion = transaccionesService.buscarPorId(id);
            return ResponseEntity.ok(TransaccionesMapper.toTransaccionesRespuestaDTO(transaccion));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("Transacción no encontrada con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error inesperado al obtener Transacción con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/por-cuenta/{idCuentaCliente}")
    public ResponseEntity<List<TransaccionesRespuestaDTO>> obtenerTransaccionesPorCuentaCliente(@PathVariable Integer idCuentaCliente) {
        log.info("Recibida solicitud para obtener transacciones para idCuentaCliente: {}", idCuentaCliente);
        try {
            List<Transacciones> transacciones = transaccionesService.buscarPorIdCuentaCliente(idCuentaCliente);
            List<TransaccionesRespuestaDTO> dtos = transacciones.stream()
                    .map(TransaccionesMapper::toTransaccionesRespuestaDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error("Error inesperado al obtener transacciones para idCuentaCliente {}: {}", idCuentaCliente, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/por-cuenta/{idCuentaCliente}/rango-fechas")
    public ResponseEntity<List<TransaccionesRespuestaDTO>> obtenerTransaccionesPorCuentaClienteYFechas(
            @PathVariable Integer idCuentaCliente,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        log.info("Recibida solicitud para obtener transacciones para idCuentaCliente: {} en rango {} a {}", idCuentaCliente, fechaInicio, fechaFin);
        try {
            Instant start = Instant.parse(fechaInicio);
            Instant end = Instant.parse(fechaFin);
            List<Transacciones> transacciones = transaccionesService.buscarPorIdCuentaClienteYFechas(idCuentaCliente, start, end);
            List<TransaccionesRespuestaDTO> dtos = transacciones.stream()
                    .map(TransaccionesMapper::toTransaccionesRespuestaDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (DateTimeParseException e) {
            log.error("Formato de fecha inválido: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al obtener transacciones por rango de fechas: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/deposito")
    public ResponseEntity<TransaccionesRespuestaDTO> realizarDeposito(@Valid @RequestBody TransaccionesSolicitudDTO dto) {
        log.info("Recibida solicitud de depósito para cuenta cliente ID: {}", dto.getIdCuentaCliente());
        try {
            // Aseguramos que el tipo de transacción en el DTO sea DEPOSITO
            dto.setTipoTransaccion(com.banquito.core.cuentas.enums.TipoTransaccionEnum.DEPOSITO);
            Transacciones transaccion = TransaccionesMapper.toTransacciones(dto);
            Transacciones transaccionRealizada = transaccionesService.realizarDeposito(transaccion);
            return ResponseEntity.status(HttpStatus.CREATED).body(TransaccionesMapper.toTransaccionesRespuestaDTO(transaccionRealizada));
        } catch (CrearEntidadExcepcion | EntidadNoEncontradaExcepcion e) {
            log.error("Error al realizar depósito: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // O un DTO de error
        } catch (Exception e) {
            log.error("Error inesperado al realizar depósito: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/retiro")
    public ResponseEntity<TransaccionesRespuestaDTO> realizarRetiro(@Valid @RequestBody TransaccionesSolicitudDTO dto) {
        log.info("Recibida solicitud de retiro para cuenta cliente ID: {}", dto.getIdCuentaCliente());
        try {
            // Aseguramos que el tipo de transacción en el DTO sea RETIRO
            dto.setTipoTransaccion(com.banquito.core.cuentas.enums.TipoTransaccionEnum.RETIRO);
            Transacciones transaccion = TransaccionesMapper.toTransacciones(dto);
            Transacciones transaccionRealizada = transaccionesService.realizarRetiro(transaccion);
            return ResponseEntity.status(HttpStatus.CREATED).body(TransaccionesMapper.toTransaccionesRespuestaDTO(transaccionRealizada));
        } catch (CrearEntidadExcepcion | EntidadNoEncontradaExcepcion e) {
            log.error("Error al realizar retiro: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("Error inesperado al realizar retiro: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/transferencia")
    public ResponseEntity<TransaccionesRespuestaDTO> realizarTransferencia(@Valid @RequestBody TransferenciasSolicitudDTO dto) {
        log.info("Recibida solicitud de transferencia de cuenta ID: {} a cuenta ID: {}", dto.getIdCuentaClienteOrigen(), dto.getIdCuentaClienteDestino());
        try {
            Transacciones transaccionOrigen = transaccionesService.realizarTransferencia(
                    dto.getIdCuentaClienteOrigen(),
                    dto.getIdCuentaClienteDestino(),
                    dto.getMonto(),
                    dto.getDescripcion()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(TransaccionesMapper.toTransaccionesRespuestaDTO(transaccionOrigen));
        } catch (CrearEntidadExcepcion | EntidadNoEncontradaExcepcion e) {
            log.error("Error al realizar transferencia: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("Error inesperado al realizar transferencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}