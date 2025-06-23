package com.banquito.core.cuentas.servicio;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;
import com.banquito.core.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.cuentas.modelo.TasasIntereses;
import com.banquito.core.cuentas.repositorio.TasasInteresesRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j // Para usar el logger de SLF4J a través de Lombok
public class TasaInteresServicio {

    private final TasasInteresesRepositorio tasasInteresesRepositorio;

    public TasaInteresServicio(TasasInteresesRepositorio tasasInteresesRepositorio) {
        this.tasasInteresesRepositorio = tasasInteresesRepositorio;
    }

    public TasasIntereses buscarPorId(Integer id) {
        log.debug("Iniciando búsqueda de TasaInteres con ID: {}", id); // Uso de debug para operaciones de búsqueda
        TasasIntereses tasaInteres = tasasInteresesRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("TasasIntereses", "Tasa de interés con ID " + id + " no encontrada."));
        log.debug("TasaInteres encontrada con ID: {}", id);
        return tasaInteres;
    }

    @Transactional // Marca el método como transaccional
    public TasasIntereses crearTasaInteres(TasasIntereses tasaInteres) {
        log.info("Intentando crear nueva TasaInteres: {}", tasaInteres.toString()); // Uso de info para operaciones de creación
        // Validaciones de negocio antes de guardar
        if (tasaInteres.getFechaFinVigencia().isBefore(tasaInteres.getFechaInicioVigencia())) {
            throw new CrearEntidadExcepcion("TasasIntereses", "La fecha de fin de vigencia no puede ser anterior a la fecha de inicio.");
        }
        // Se asegura que el estado sea ACTIVO por defecto al crear
        if (tasaInteres.getEstado() == null) {
            tasaInteres.setEstado(EstadoGeneralCuentasEnum.ACTIVO);
        }

        try {
            TasasIntereses nuevaTasa = tasasInteresesRepositorio.save(tasaInteres);
            log.info("TasaInteres creada exitosamente con ID: {}", nuevaTasa.getId());
            return nuevaTasa;
        } catch (Exception e) {
            log.error("Error al crear TasaInteres: {}", e.getMessage(), e); // Log de error con stack trace
            throw new CrearEntidadExcepcion("TasasIntereses", "No se pudo crear la tasa de interés. Detalle: " + e.getMessage());
        }
    }


    @Transactional
    public TasasIntereses actualizarTasaInteres(Integer id, TasasIntereses tasaInteres) {
        log.info("Intentando actualizar TasaInteres con ID: {}", id);
        TasasIntereses tasaExistente = tasasInteresesRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("TasasIntereses", "Tasa de interés con ID " + id + " no encontrada para actualizar."));

        // Validaciones y actualización de campos específicos
        if (tasaInteres.getFechaFinVigencia() != null && tasaInteres.getFechaInicioVigencia() != null &&
            tasaInteres.getFechaFinVigencia().isBefore(tasaInteres.getFechaInicioVigencia())) {
            throw new ActualizarEntidadExcepcion("TasasIntereses", "La fecha de fin de vigencia no puede ser anterior a la fecha de inicio.");
        }

        tasaExistente.setBaseCalculo(tasaInteres.getBaseCalculo() != null ? tasaInteres.getBaseCalculo() : tasaExistente.getBaseCalculo());
        tasaExistente.setMetodoCalculo(tasaInteres.getMetodoCalculo() != null ? tasaInteres.getMetodoCalculo() : tasaExistente.getMetodoCalculo());
        tasaExistente.setFrecuenciaCapitalizacion(tasaInteres.getFrecuenciaCapitalizacion() != null ? tasaInteres.getFrecuenciaCapitalizacion() : tasaExistente.getFrecuenciaCapitalizacion());
        tasaExistente.setFechaInicioVigencia(tasaInteres.getFechaInicioVigencia() != null ? tasaInteres.getFechaInicioVigencia() : tasaExistente.getFechaInicioVigencia());
        tasaExistente.setFechaFinVigencia(tasaInteres.getFechaFinVigencia() != null ? tasaInteres.getFechaFinVigencia() : tasaExistente.getFechaFinVigencia());
        // El estado se actualiza con un método separado para control de lógica de negocio
        // La versión se maneja automáticamente por JPA

        try {
            TasasIntereses tasaActualizada = tasasInteresesRepositorio.save(tasaExistente);
            log.info("TasaInteres con ID {} actualizada exitosamente.", tasaActualizada.getId());
            return tasaActualizada;
        } catch (Exception e) {
            log.error("Error al actualizar TasaInteres con ID {}: {}", id, e.getMessage(), e);
            throw new ActualizarEntidadExcepcion("TasasIntereses", "No se pudo actualizar la tasa de interés con ID " + id + ". Detalle: " + e.getMessage());
        }
    }

    @Transactional
    public TasasIntereses desactivarTasaInteres(Integer id) {
        log.info("Intentando desactivar TasaInteres con ID: {}", id);
        TasasIntereses tasaExistente = tasasInteresesRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("TasasIntereses", "Tasa de interés con ID " + id + " no encontrada para desactivar."));

        if (tasaExistente.getEstado() == EstadoGeneralCuentasEnum.INACTIVO) {
            log.warn("TasaInteres con ID {} ya se encuentra INACTIVA.", id);
            return tasaExistente;
        }

        tasaExistente.setEstado(EstadoGeneralCuentasEnum.INACTIVO);
        try {
            TasasIntereses tasaDesactivada = tasasInteresesRepositorio.save(tasaExistente);
            log.info("TasaInteres con ID {} desactivada exitosamente.", tasaDesactivada.getId());
            return tasaDesactivada;
        } catch (Exception e) {
            log.error("Error al desactivar TasaInteres con ID {}: {}", id, e.getMessage(), e);
            throw new ActualizarEntidadExcepcion("TasasIntereses", "No se pudo desactivar la tasa de interés con ID " + id + ". Detalle: " + e.getMessage());
        }
    }

    @Transactional
    public TasasIntereses activarTasaInteres(Integer id) {
        log.info("Intentando activar TasaInteres con ID: {}", id);
        TasasIntereses tasaExistente = tasasInteresesRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("TasasIntereses", "Tasa de interés con ID " + id + " no encontrada para activar."));

        if (tasaExistente.getEstado() == EstadoGeneralCuentasEnum.ACTIVO) {
            log.warn("TasaInteres con ID {} ya se encuentra ACTIVA.", id);
            return tasaExistente;
        }

        tasaExistente.setEstado(EstadoGeneralCuentasEnum.ACTIVO);
        try {
            TasasIntereses tasaActivada = tasasInteresesRepositorio.save(tasaExistente);
            log.info("TasaInteres con ID {} activada exitosamente.", tasaActivada.getId());
            return tasaActivada;
        } catch (Exception e) {
            log.error("Error al activar TasaInteres con ID {}: {}", id, e.getMessage(), e);
            throw new ActualizarEntidadExcepcion("TasasIntereses", "No se pudo activar la tasa de interés con ID " + id + ". Detalle: " + e.getMessage());
        }
    }

    public List<TasasIntereses> buscarTodasActivas() {
        log.debug("Buscando todas las TasasIntereses activas.");
        List<TasasIntereses> tasas = tasasInteresesRepositorio.findByEstado(EstadoGeneralCuentasEnum.ACTIVO);
        log.debug("Encontradas {} TasasIntereses activas.", tasas.size());
        return tasas;
    }
}