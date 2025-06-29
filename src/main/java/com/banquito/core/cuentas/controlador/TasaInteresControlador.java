package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.dto.TasaInteresRespuestaDTO;
import com.banquito.core.cuentas.dto.TasaInteresSolicitudDTO;
import com.banquito.core.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.cuentas.mapper.TasaInteresMapper;
import com.banquito.core.cuentas.modelo.TasasIntereses;
import com.banquito.core.cuentas.servicio.TasaInteresServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tasas-interes")
@Slf4j
public class TasaInteresControlador {

    private final TasaInteresServicio tasaInteresService;

    public TasaInteresControlador(TasaInteresServicio tasaInteresService) {
        this.tasaInteresService = tasaInteresService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TasaInteresRespuestaDTO> obtenerTasaInteresPorId(@PathVariable Integer id) {
        log.info("Recibida solicitud para obtener TasaInteres con ID: {}", id);
        try {
            TasasIntereses tasa = tasaInteresService.buscarPorId(id);
            return ResponseEntity.ok(TasaInteresMapper.toTasaInteresRespuestaDTO(tasa));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("TasaInteres no encontrada con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error inesperado al obtener TasaInteres con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/activas")
    public ResponseEntity<List<TasaInteresRespuestaDTO>> obtenerTasasInteresActivas() {
        log.info("Recibida solicitud para obtener todas las TasasIntereses activas.");
        try {
            List<TasasIntereses> tasas = tasaInteresService.buscarTodasActivas();
            List<TasaInteresRespuestaDTO> response = tasas.stream()
                    .map(TasaInteresMapper::toTasaInteresRespuestaDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error inesperado al obtener TasasIntereses activas: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Eliminado el método obtenerTodasLasTasasInteres()

    @PostMapping
    public ResponseEntity<TasaInteresRespuestaDTO> crearTasaInteres(
            @RequestBody TasaInteresSolicitudDTO TasaInteresSolicitudDTO) {
        log.info("Recibida solicitud para crear una nueva TasaInteres: {}", TasaInteresSolicitudDTO);
        try {
            TasasIntereses nuevaTasa = TasaInteresMapper.toTasasIntereses(TasaInteresSolicitudDTO);
            TasasIntereses tasaCreada = tasaInteresService.crearTasaInteres(nuevaTasa);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(TasaInteresMapper.toTasaInteresRespuestaDTO(tasaCreada));
        } catch (CrearEntidadExcepcion e) {
            log.error("Error al crear TasaInteres: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al crear TasaInteres: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TasaInteresRespuestaDTO> actualizarTasaInteres(@PathVariable Integer id,
            @RequestBody TasaInteresSolicitudDTO TasaInteresSolicitudDTO) {
        log.info("Recibida solicitud para actualizar TasaInteres con ID: {}", id);
        try {
            TasasIntereses tasaAActualizar = TasaInteresMapper.toTasasIntereses(TasaInteresSolicitudDTO);
            TasasIntereses tasaActualizada = tasaInteresService.actualizarTasaInteres(id, tasaAActualizar);
            return ResponseEntity.ok(TasaInteresMapper.toTasaInteresRespuestaDTO(tasaActualizada));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("TasaInteres no encontrada para actualizar con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ActualizarEntidadExcepcion e) {
            log.error("Error al actualizar TasaInteres con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al actualizar TasaInteres con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<TasaInteresRespuestaDTO> desactivarTasaInteres(@PathVariable Integer id) {
        log.info("Recibida solicitud para desactivar TasaInteres con ID: {}", id);
        try {
            TasasIntereses tasaDesactivada = tasaInteresService.desactivarTasaInteres(id);
            return ResponseEntity.ok(TasaInteresMapper.toTasaInteresRespuestaDTO(tasaDesactivada));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("TasaInteres no encontrada para desactivar con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ActualizarEntidadExcepcion e) {
            log.error("Error al desactivar TasaInteres con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al desactivar TasaInteres con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<TasaInteresRespuestaDTO> activarTasaInteres(@PathVariable Integer id) {
        log.info("Recibida solicitud para activar TasaInteres con ID: {}", id);
        try {
            TasasIntereses tasaActivada = tasaInteresService.activarTasaInteres(id);
            return ResponseEntity.ok(TasaInteresMapper.toTasaInteresRespuestaDTO(tasaActivada));
        } catch (EntidadNoEncontradaExcepcion e) {
            log.warn("TasaInteres no encontrada para activar con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ActualizarEntidadExcepcion e) {
            log.error("Error al activar TasaInteres con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error inesperado al activar TasaInteres con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}