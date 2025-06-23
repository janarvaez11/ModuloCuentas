package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.DTO.TipoCuentaSolicitudDTO;
import com.banquito.core.cuentas.DTO.TipoCuentaRespuestaDTO;
import com.banquito.core.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.cuentas.mapper.TipoCuentaMapper;
import com.banquito.core.cuentas.modelo.TiposCuentas;
import com.banquito.core.cuentas.servicio.TipoCuentaServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tipos-cuentas")
@Slf4j
public class TipoCuentaControlador {

    private final TipoCuentaServicio tipoCuentaService;

    public TipoCuentaControlador(TipoCuentaServicio tipoCuentaService) {
        this.tipoCuentaService = tipoCuentaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCuentaRespuestaDTO> obtenerTipoCuentaPorId(@PathVariable Integer id) {
        log.info("Recibida solicitud para obtener TipoCuenta con ID: {}", id);
        try {
            TiposCuentas tipoCuenta = tipoCuentaService.buscarPorId(id);
            return ResponseEntity.ok(TipoCuentaMapper.toTipoCuentaRespuestaDTO(tipoCuenta));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("TipoCuenta no encontrada con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error inesperado al obtener TipoCuenta con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<TipoCuentaRespuestaDTO>> obtenerTiposCuentasPorNombre(@PathVariable String nombre) {
        log.info("Recibida solicitud para obtener TiposCuenta por nombre: {}", nombre);
        try {
            List<TiposCuentas> tiposCuentas = tipoCuentaService.buscarPorNombre(nombre);
            List<TipoCuentaRespuestaDTO> response = tiposCuentas.stream()
                    .map(TipoCuentaMapper::toTipoCuentaRespuestaDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error inesperado al obtener TiposCuenta por nombre: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/activas")
    public ResponseEntity<List<TipoCuentaRespuestaDTO>> obtenerTiposCuentasActivas() {
        log.info("Recibida solicitud para obtener todos los TiposCuenta activos.");
        try {
            List<TiposCuentas> tiposCuentas = tipoCuentaService.buscarTodosActivos();
            List<TipoCuentaRespuestaDTO> response = tiposCuentas.stream()
                    .map(TipoCuentaMapper::toTipoCuentaRespuestaDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error inesperado al obtener TiposCuenta activos: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<TipoCuentaRespuestaDTO> crearTipoCuenta(@RequestBody TipoCuentaSolicitudDTO tipoCuentaSolicitudDTO) {
        log.info("Recibida solicitud para crear un nuevo TipoCuenta: {}", tipoCuentaSolicitudDTO);
        try {
            TiposCuentas nuevoTipoCuenta = TipoCuentaMapper.toTiposCuentas(tipoCuentaSolicitudDTO);
            TiposCuentas tipoCuentaCreada = tipoCuentaService.crearTipoCuenta(nuevoTipoCuenta);
            return ResponseEntity.status(HttpStatus.CREATED).body(TipoCuentaMapper.toTipoCuentaRespuestaDTO(tipoCuentaCreada));
        } catch (CrearEntidadExcepcion e) {
            log.error("Error al crear TipoCuenta: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al crear TipoCuenta: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCuentaRespuestaDTO> actualizarTipoCuenta(@PathVariable Integer id, @RequestBody TipoCuentaSolicitudDTO tipoCuentaSolicitudDTO) {
        log.info("Recibida solicitud para actualizar TipoCuenta con ID: {}", id);
        try {
            TiposCuentas tipoCuentaAActualizar = TipoCuentaMapper.toTiposCuentas(tipoCuentaSolicitudDTO);
            TiposCuentas tipoCuentaActualizada = tipoCuentaService.actualizarTipoCuenta(id, tipoCuentaAActualizar);
            return ResponseEntity.ok(TipoCuentaMapper.toTipoCuentaRespuestaDTO(tipoCuentaActualizada));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("TipoCuenta no encontrada para actualizar con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ActualizarEntidadExcepcion e) {
            log.error("Error al actualizar TipoCuenta con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al actualizar TipoCuenta con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<TipoCuentaRespuestaDTO> desactivarTipoCuenta(@PathVariable Integer id) {
        log.info("Recibida solicitud para desactivar TipoCuenta con ID: {}", id);
        try {
            TiposCuentas tipoCuentaDesactivada = tipoCuentaService.desactivarTipoCuenta(id);
            return ResponseEntity.ok(TipoCuentaMapper.toTipoCuentaRespuestaDTO(tipoCuentaDesactivada));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("TipoCuenta no encontrada para desactivar con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ActualizarEntidadExcepcion e) {
            log.error("Error al desactivar TipoCuenta con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al desactivar TipoCuenta con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<TipoCuentaRespuestaDTO> activarTipoCuenta(@PathVariable Integer id) {
        log.info("Recibida solicitud para activar TipoCuenta con ID: {}", id);
        try {
            TiposCuentas tipoCuentaActivada = tipoCuentaService.activarTipoCuenta(id);
            return ResponseEntity.ok(TipoCuentaMapper.toTipoCuentaRespuestaDTO(tipoCuentaActivada));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("TipoCuenta no encontrada para activar con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ActualizarEntidadExcepcion e) {
            log.error("Error al activar TipoCuenta con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al activar TipoCuenta con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}