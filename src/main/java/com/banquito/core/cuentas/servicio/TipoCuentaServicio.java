package com.banquito.core.cuentas.servicio;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;
import com.banquito.core.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.cuentas.modelo.TasasIntereses;
import com.banquito.core.cuentas.modelo.TiposCuentas;
import com.banquito.core.cuentas.repositorio.TasasInteresesRepositorio; // Necesario para validar la tasa por defecto
import com.banquito.core.cuentas.repositorio.TiposCuentasRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TipoCuentaServicio {

    private final TiposCuentasRepositorio tiposCuentasRepositorio;
    private final TasasInteresesRepositorio tasasInteresesRepositorio; // Para validar la existencia de la tasa por defecto

    public TipoCuentaServicio(TiposCuentasRepositorio tiposCuentasRepositorio, TasasInteresesRepositorio tasasInteresesRepositorio) {
        this.tiposCuentasRepositorio = tiposCuentasRepositorio;
        this.tasasInteresesRepositorio = tasasInteresesRepositorio;
    }

    public TiposCuentas buscarPorId(Integer id) {
        log.debug("Iniciando búsqueda de TipoCuenta con ID: {}", id);
        TiposCuentas tipoCuenta = tiposCuentasRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("TiposCuentas", "Tipo de cuenta con ID " + id + " no encontrada."));
        log.debug("TipoCuenta encontrada con ID: {}", id);
        return tipoCuenta;
    }


    public List<TiposCuentas> buscarPorNombre(String nombre) {
        log.debug("Iniciando búsqueda de TipoCuenta por nombre: {}", nombre);
        List<TiposCuentas> tiposCuentas = tiposCuentasRepositorio.findByNombreContainingIgnoreCase(nombre);
        log.debug("Encontrados {} TiposCuenta con nombre que contiene '{}'.", tiposCuentas.size(), nombre);
        return tiposCuentas;
    }

    public List<TiposCuentas> buscarTodosActivos() {
        log.debug("Buscando todos los Tipos de Cuenta activos.");
        List<TiposCuentas> tiposCuentas = tiposCuentasRepositorio.findByEstado(EstadoGeneralCuentasEnum.ACTIVO);
        log.debug("Encontrados {} Tipos de Cuenta activos.", tiposCuentas.size());
        return tiposCuentas;
    }


    @Transactional
    public TiposCuentas crearTipoCuenta(TiposCuentas tipoCuenta) {
        log.info("Intentando crear nuevo TipoCuenta: {}", tipoCuenta.getNombre());

        // Validar que la tasa de interés por defecto exista
        if (tipoCuenta.getIdTasaInteresPorDefecto() == null || tipoCuenta.getIdTasaInteresPorDefecto().getId() == null) {
            throw new CrearEntidadExcepcion("TiposCuentas", "El ID de la tasa de interés por defecto es obligatorio.");
        }
        Optional<TasasIntereses> tasaExistente = tasasInteresesRepositorio.findById(tipoCuenta.getIdTasaInteresPorDefecto().getId());
        if (tasaExistente.isEmpty()) {
            throw new CrearEntidadExcepcion("TiposCuentas", "La tasa de interés por defecto con ID " + tipoCuenta.getIdTasaInteresPorDefecto().getId() + " no existe.");
        }
        tipoCuenta.setIdTasaInteresPorDefecto(tasaExistente.get()); // Asegurarse de que la referencia sea la entidad gestionada

        // Validar que no exista un tipo de cuenta con el mismo nombre
        if (tiposCuentasRepositorio.findByNombre(tipoCuenta.getNombre()).isPresent()) {
            throw new CrearEntidadExcepcion("TiposCuentas", "Ya existe un tipo de cuenta con el nombre '" + tipoCuenta.getNombre() + "'.");
        }

        // Establecer campos por defecto/automáticos
        tipoCuenta.setFechaCreacion(Instant.now());
        tipoCuenta.setFechaModificacion(Instant.now());
        tipoCuenta.setEstado(EstadoGeneralCuentasEnum.ACTIVO);
        tipoCuenta.setVersion(0L); // Asumiendo versión inicial 0

        try {
            TiposCuentas nuevoTipoCuenta = tiposCuentasRepositorio.save(tipoCuenta);
            log.info("TipoCuenta creada exitosamente con ID: {}", nuevoTipoCuenta.getId());
            return nuevoTipoCuenta;
        } catch (Exception e) {
            log.error("Error al crear TipoCuenta: {}", e.getMessage(), e);
            throw new CrearEntidadExcepcion("TiposCuentas", "No se pudo crear el tipo de cuenta. Detalle: " + e.getMessage());
        }
    }


    @Transactional
    public TiposCuentas actualizarTipoCuenta(Integer id, TiposCuentas tipoCuenta) {
        log.info("Intentando actualizar TipoCuenta con ID: {}", id);
        TiposCuentas tipoCuentaExistente = tiposCuentasRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("TiposCuentas", "Tipo de cuenta con ID " + id + " no encontrada para actualizar."));

        // Validar que la nueva tasa de interés por defecto exista, si se proporciona
        if (tipoCuenta.getIdTasaInteresPorDefecto() != null && tipoCuenta.getIdTasaInteresPorDefecto().getId() != null) {
            Optional<TasasIntereses> tasaExistente = tasasInteresesRepositorio.findById(tipoCuenta.getIdTasaInteresPorDefecto().getId());
            if (tasaExistente.isEmpty()) {
                throw new ActualizarEntidadExcepcion("TiposCuentas", "La nueva tasa de interés por defecto con ID " + tipoCuenta.getIdTasaInteresPorDefecto().getId() + " no existe.");
            }
            tipoCuentaExistente.setIdTasaInteresPorDefecto(tasaExistente.get());
        }

        // Validar que el nuevo nombre no se duplique con otro tipo de cuenta (excluyendo el actual)
        if (tipoCuenta.getNombre() != null && !tipoCuenta.getNombre().equals(tipoCuentaExistente.getNombre())) {
            if (tiposCuentasRepositorio.findByNombre(tipoCuenta.getNombre()).isPresent()) {
                throw new ActualizarEntidadExcepcion("TiposCuentas", "Ya existe un tipo de cuenta con el nombre '" + tipoCuenta.getNombre() + "'.");
            }
        }

        // Actualizar campos permitidos (evitar nulls sobrescribiendo valores existentes si no se desean)
        tipoCuentaExistente.setIdMoneda(tipoCuenta.getIdMoneda() != null ? tipoCuenta.getIdMoneda() : tipoCuentaExistente.getIdMoneda());
        tipoCuentaExistente.setNombre(tipoCuenta.getNombre() != null ? tipoCuenta.getNombre() : tipoCuentaExistente.getNombre());
        tipoCuentaExistente.setDescripcion(tipoCuenta.getDescripcion() != null ? tipoCuenta.getDescripcion() : tipoCuentaExistente.getDescripcion());
        tipoCuentaExistente.setRequisitosApertura(tipoCuenta.getRequisitosApertura() != null ? tipoCuenta.getRequisitosApertura() : tipoCuentaExistente.getRequisitosApertura());
        tipoCuentaExistente.setTipoCliente(tipoCuenta.getTipoCliente() != null ? tipoCuenta.getTipoCliente() : tipoCuentaExistente.getTipoCliente());
        tipoCuentaExistente.setFechaModificacion(Instant.now()); // Actualizar la fecha de modificación

        try {
            TiposCuentas tipoCuentaActualizada = tiposCuentasRepositorio.save(tipoCuentaExistente);
            log.info("TipoCuenta con ID {} actualizada exitosamente.", tipoCuentaActualizada.getId());
            return tipoCuentaActualizada;
        } catch (Exception e) {
            log.error("Error al actualizar TipoCuenta con ID {}: {}", id, e.getMessage(), e);
            throw new ActualizarEntidadExcepcion("TiposCuentas", "No se pudo actualizar el tipo de cuenta con ID " + id + ". Detalle: " + e.getMessage());
        }
    }

    @Transactional
    public TiposCuentas desactivarTipoCuenta(Integer id) {
        log.info("Intentando desactivar TipoCuenta con ID: {}", id);
        TiposCuentas tipoCuentaExistente = tiposCuentasRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("TiposCuentas", "Tipo de cuenta con ID " + id + " no encontrada para desactivar."));

        if (tipoCuentaExistente.getEstado() == EstadoGeneralCuentasEnum.INACTIVO) {
            log.warn("TipoCuenta con ID {} ya se encuentra INACTIVA.", id);
            return tipoCuentaExistente;
        }

        tipoCuentaExistente.setEstado(EstadoGeneralCuentasEnum.INACTIVO);
        tipoCuentaExistente.setFechaModificacion(Instant.now());
        try {
            TiposCuentas tipoCuentaDesactivada = tiposCuentasRepositorio.save(tipoCuentaExistente);
            log.info("TipoCuenta con ID {} desactivada exitosamente.", tipoCuentaDesactivada.getId());
            return tipoCuentaDesactivada;
        } catch (Exception e) {
            log.error("Error al desactivar TipoCuenta con ID {}: {}", id, e.getMessage(), e);
            throw new ActualizarEntidadExcepcion("TiposCuentas", "No se pudo desactivar el tipo de cuenta con ID " + id + ". Detalle: " + e.getMessage());
        }
    }

    @Transactional
    public TiposCuentas activarTipoCuenta(Integer id) {
        log.info("Intentando activar TipoCuenta con ID: {}", id);
        TiposCuentas tipoCuentaExistente = tiposCuentasRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("TiposCuentas", "Tipo de cuenta con ID " + id + " no encontrada para activar."));

        if (tipoCuentaExistente.getEstado() == EstadoGeneralCuentasEnum.ACTIVO) {
            log.warn("TipoCuenta con ID {} ya se encuentra ACTIVA.", id);
            return tipoCuentaExistente;
        }

        tipoCuentaExistente.setEstado(EstadoGeneralCuentasEnum.ACTIVO);
        tipoCuentaExistente.setFechaModificacion(Instant.now());
        try {
            TiposCuentas tipoCuentaActivada = tiposCuentasRepositorio.save(tipoCuentaExistente);
            log.info("TipoCuenta con ID {} activada exitosamente.", tipoCuentaActivada.getId());
            return tipoCuentaActivada;
        } catch (Exception e) {
            log.error("Error al activar TipoCuenta con ID {}: {}", id, e.getMessage(), e);
            throw new ActualizarEntidadExcepcion("TiposCuentas", "No se pudo activar el tipo de cuenta con ID " + id + ". Detalle: " + e.getMessage());
        }
    }
}