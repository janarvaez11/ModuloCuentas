package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.CuentasClientesRespuestaDTO;
import com.banquito.core.cuentas.dto.CuentasClientesSolicitudDTO;
import com.banquito.core.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.cuentas.mapper.CuentasClientesMapper;
import com.banquito.core.cuentas.modelo.CuentasClientes;
import com.banquito.core.cuentas.servicio.CuentasClientesServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cuentas-clientes")
@Slf4j
public class CuentasClientesControlador {

    private final CuentasClientesServicio cuentasClientesService;

    public CuentasClientesControlador(CuentasClientesServicio cuentasClientesService) {
        this.cuentasClientesService = cuentasClientesService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentasClientesRespuestaDTO> obtenerCuentasClientesPorId(@PathVariable Integer id) {
        log.info("Recibida solicitud para obtener CuentasClientes con ID: {}", id);
        try {
            CuentasClientes cuentasClientes = cuentasClientesService.buscarPorId(id);
            return ResponseEntity.ok(CuentasClientesMapper.toCuentasClientesRespuestaDTO(cuentasClientes));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("CuentasClientes no encontrada con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error inesperado al obtener CuentasClientes con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/numero-cuenta/{numeroCuenta}")
    public ResponseEntity<CuentasClientesRespuestaDTO> obtenerCuentasClientesPorNumeroCuenta(@PathVariable String numeroCuenta) {
        log.info("Recibida solicitud para obtener CuentasClientes por número de cuenta: {}", numeroCuenta);
        try {
            CuentasClientes cuentasClientes = cuentasClientesService.buscarPorNumeroCuenta(numeroCuenta);
            return ResponseEntity.ok(CuentasClientesMapper.toCuentasClientesRespuestaDTO(cuentasClientes));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("CuentasClientes no encontrada con número de cuenta {}: {}", numeroCuenta, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error inesperado al obtener CuentasClientes por número de cuenta {}: {}", numeroCuenta, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/cliente/{idCliente}/numero-cuenta/{numeroCuenta}")
    public ResponseEntity<CuentasClientesRespuestaDTO> obtenerCuentasClientesPorIdClienteYNumeroCuenta(
            @PathVariable Integer idCliente,
            @PathVariable String numeroCuenta) {
        log.info("Recibida solicitud para obtener CuentasClientes por ID Cliente: {} y Número de Cuenta: {}", idCliente, numeroCuenta);
        try {
            CuentasClientes cuentasClientes = cuentasClientesService.buscarPorIdClienteAndNumeroCuenta(idCliente, numeroCuenta);
            return ResponseEntity.ok(CuentasClientesMapper.toCuentasClientesRespuestaDTO(cuentasClientes));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("CuentasClientes no encontrada para Cliente ID {} y Número de Cuenta {}: {}", idCliente, numeroCuenta, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error inesperado al obtener CuentasClientes por ID Cliente {} y Número de Cuenta {}: {}", idCliente, numeroCuenta, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<CuentasClientesRespuestaDTO> crearCuentasClientes(@Valid @RequestBody CuentasClientesSolicitudDTO dto) {
        log.info("Recibida solicitud para crear una nueva CuentasClientes para cliente ID: {} y número de cuenta: {}", dto.getIdCliente(), dto.getNumeroCuenta());
        try {
            CuentasClientes nuevaCuentaCliente = CuentasClientesMapper.toCuentasClientes(dto);
            CuentasClientes cuentaClienteCreada = cuentasClientesService.crearCuentasClientes(nuevaCuentaCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(CuentasClientesMapper.toCuentasClientesRespuestaDTO(cuentaClienteCreada));
        } catch (CrearEntidadExcepcion e) {
            log.error("Error al crear CuentasClientes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al crear CuentasClientes: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentasClientesRespuestaDTO> actualizarCuentasClientes(@PathVariable Integer id, @Valid @RequestBody CuentasClientesSolicitudDTO dto) {
        log.info("Recibida solicitud para actualizar CuentasClientes con ID: {}", id);
        try {
            CuentasClientes cuentaClienteAActualizar = CuentasClientesMapper.toCuentasClientes(dto);
            CuentasClientes cuentaClienteActualizada = cuentasClientesService.actualizarCuentasClientes(id, cuentaClienteAActualizar);
            return ResponseEntity.ok(CuentasClientesMapper.toCuentasClientesRespuestaDTO(cuentaClienteActualizada));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("CuentasClientes no encontrada para actualizar con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ActualizarEntidadExcepcion e) {
            log.error("Error al actualizar CuentasClientes con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al actualizar CuentasClientes con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<CuentasClientesRespuestaDTO> desactivarCuentasClientes(@PathVariable Integer id) {
        log.info("Recibida solicitud para desactivar CuentasClientes con ID: {}", id);
        try {
            CuentasClientes cuentaClienteDesactivada = cuentasClientesService.desactivarCuentasClientes(id);
            return ResponseEntity.ok(CuentasClientesMapper.toCuentasClientesRespuestaDTO(cuentaClienteDesactivada));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("CuentasClientes no encontrada para desactivar con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ActualizarEntidadExcepcion e) {
            log.error("Error al desactivar CuentasClientes con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al desactivar CuentasClientes con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<CuentasClientesRespuestaDTO> activarCuentasClientes(@PathVariable Integer id) {
        log.info("Recibida solicitud para activar CuentasClientes con ID: {}", id);
        try {
            CuentasClientes cuentaClienteActivada = cuentasClientesService.activarCuentasClientes(id);
            return ResponseEntity.ok(CuentasClientesMapper.toCuentasClientesRespuestaDTO(cuentaClienteActivada));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("CuentasClientes no encontrada para activar con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ActualizarEntidadExcepcion e) {
            log.error("Error al activar CuentasClientes con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al activar CuentasClientes con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}