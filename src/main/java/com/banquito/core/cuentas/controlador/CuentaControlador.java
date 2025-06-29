package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.CuentaRespuestaDTO;
import com.banquito.core.cuentas.dto.CuentaSolicitudDTO;
import com.banquito.core.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.cuentas.mapper.CuentaMapper;
import com.banquito.core.cuentas.modelo.Cuentas;
import com.banquito.core.cuentas.servicio.CuentaServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cuentas")
@Slf4j
public class CuentaControlador {

    private final CuentaServicio cuentaService;

    public CuentaControlador(CuentaServicio cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaRespuestaDTO> obtenerCuentaPorId(@PathVariable Integer id) {
        log.info("Recibida solicitud para obtener Cuenta con ID: {}", id);
        try {
            Cuentas cuenta = cuentaService.buscarPorId(id);
            return ResponseEntity.ok(CuentaMapper.toCuentaRespuestaDTO(cuenta));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("Cuenta no encontrada con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error inesperado al obtener Cuenta con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/codigo/{codigoCuenta}")
    public ResponseEntity<CuentaRespuestaDTO> obtenerCuentaPorCodigo(@PathVariable String codigoCuenta) {
        log.info("Recibida solicitud para obtener Cuenta por código: {}", codigoCuenta);
        try {
            Cuentas cuenta = cuentaService.buscarPorCodigoCuenta(codigoCuenta);
            return ResponseEntity.ok(CuentaMapper.toCuentaRespuestaDTO(cuenta));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("Cuenta no encontrada con código {}: {}", codigoCuenta, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error inesperado al obtener Cuenta con código {}: {}", codigoCuenta, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<CuentaRespuestaDTO>> obtenerCuentasPorNombre(@PathVariable String nombre) {
        log.info("Recibida solicitud para obtener Cuentas por nombre: {}", nombre);
        try {
            List<Cuentas> cuentas = cuentaService.buscarPorNombre(nombre);
            List<CuentaRespuestaDTO> response = cuentas.stream()
                    .map(CuentaMapper::toCuentaRespuestaDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error inesperado al obtener Cuentas por nombre: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/activas")
    public ResponseEntity<List<CuentaRespuestaDTO>> obtenerCuentasActivas() {
        log.info("Recibida solicitud para obtener todas las Cuentas activas.");
        try {
            List<Cuentas> cuentas = cuentaService.buscarTodosActivos();
            List<CuentaRespuestaDTO> response = cuentas.stream()
                    .map(CuentaMapper::toCuentaRespuestaDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error inesperado al obtener Cuentas activas: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<CuentaRespuestaDTO> crearCuenta(@RequestBody CuentaSolicitudDTO cuentaSolicitudDTO) {
        log.info("Recibida solicitud para crear una nueva Cuenta con código: {}", cuentaSolicitudDTO.getCodigoCuenta());
        try {
            Cuentas nuevaCuenta = CuentaMapper.toCuentas(cuentaSolicitudDTO);
            Cuentas cuentaCreada = cuentaService.crearCuenta(nuevaCuenta);
            return ResponseEntity.status(HttpStatus.CREATED).body(CuentaMapper.toCuentaRespuestaDTO(cuentaCreada));
        } catch (CrearEntidadExcepcion e) {
            log.error("Error al crear Cuenta: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al crear Cuenta: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaRespuestaDTO> actualizarCuenta(@PathVariable Integer id, @RequestBody CuentaSolicitudDTO cuentaSolicitudDTO) {
        log.info("Recibida solicitud para actualizar Cuenta con ID: {}", id);
        try {
            Cuentas cuentaAActualizar = CuentaMapper.toCuentas(cuentaSolicitudDTO);
            Cuentas cuentaActualizada = cuentaService.actualizarCuenta(id, cuentaAActualizar);
            return ResponseEntity.ok(CuentaMapper.toCuentaRespuestaDTO(cuentaActualizada));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("Cuenta no encontrada para actualizar con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ActualizarEntidadExcepcion e) {
            log.error("Error al actualizar Cuenta con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al actualizar Cuenta con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<CuentaRespuestaDTO> desactivarCuenta(@PathVariable Integer id) {
        log.info("Recibida solicitud para desactivar Cuenta con ID: {}", id);
        try {
            Cuentas cuentaDesactivada = cuentaService.desactivarCuenta(id);
            return ResponseEntity.ok(CuentaMapper.toCuentaRespuestaDTO(cuentaDesactivada));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("Cuenta no encontrada para desactivar con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ActualizarEntidadExcepcion e) {
            log.error("Error al desactivar Cuenta con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al desactivar Cuenta con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<CuentaRespuestaDTO> activarCuenta(@PathVariable Integer id) {
        log.info("Recibida solicitud para activar Cuenta con ID: {}", id);
        try {
            Cuentas cuentaActivada = cuentaService.activarCuenta(id);
            return ResponseEntity.ok(CuentaMapper.toCuentaRespuestaDTO(cuentaActivada));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("Cuenta no encontrada para activar con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ActualizarEntidadExcepcion e) {
            log.error("Error al activar Cuenta con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al activar Cuenta con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}