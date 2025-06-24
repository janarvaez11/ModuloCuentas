package com.banquito.core.cuentas.servicio;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;
import com.banquito.core.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.cuentas.modelo.Cuentas;
import com.banquito.core.cuentas.modelo.TasasIntereses;
import com.banquito.core.cuentas.modelo.TiposCuentas;
import com.banquito.core.cuentas.repositorio.CuentasRepositorio;
import com.banquito.core.cuentas.repositorio.TasasInteresesRepositorio;
import com.banquito.core.cuentas.repositorio.TiposCuentasRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CuentaServicio {

    private final CuentasRepositorio cuentasRepositorio;
    private final TiposCuentasRepositorio tiposCuentasRepositorio;
    private final TasasInteresesRepositorio tasasInteresesRepositorio;

    public CuentaServicio(CuentasRepositorio cuentasRepositorio,
                          TiposCuentasRepositorio tiposCuentasRepositorio,
                          TasasInteresesRepositorio tasasInteresesRepositorio) {
        this.cuentasRepositorio = cuentasRepositorio;
        this.tiposCuentasRepositorio = tiposCuentasRepositorio;
        this.tasasInteresesRepositorio = tasasInteresesRepositorio;
    }

    public Cuentas buscarPorId(Integer id) {
        log.debug("Iniciando búsqueda de Cuenta con ID: {}", id);
        Cuentas cuenta = cuentasRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("Cuentas", "Cuenta con ID " + id + " no encontrada."));
        log.debug("Cuenta encontrada con ID: {}", id);
        return cuenta;
    }


    public Cuentas buscarPorCodigoCuenta(String codigoCuenta) {
        log.debug("Iniciando búsqueda de Cuenta por código de cuenta: {}", codigoCuenta);
        Cuentas cuenta = cuentasRepositorio.findByCodigoCuenta(codigoCuenta)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("Cuentas", "Cuenta con código " + codigoCuenta + " no encontrada."));
        log.debug("Cuenta encontrada con código: {}", codigoCuenta);
        return cuenta;
    }


    public List<Cuentas> buscarPorNombre(String nombre) {
        log.debug("Iniciando búsqueda de Cuenta por nombre: {}", nombre);
        List<Cuentas> cuentas = cuentasRepositorio.findByNombreContainingIgnoreCase(nombre);
        log.debug("Encontradas {} Cuentas con nombre que contiene '{}'.", cuentas.size(), nombre);
        return cuentas;
    }

    public List<Cuentas> buscarTodosActivos() {
        log.debug("Buscando todas las Cuentas activas.");
        List<Cuentas> cuentas = cuentasRepositorio.findByEstado(EstadoGeneralCuentasEnum.ACTIVO);
        log.debug("Encontradas {} Cuentas activas.", cuentas.size());
        return cuentas;
    }

    @Transactional
    public Cuentas crearCuenta(Cuentas cuenta) {
        log.info("Intentando crear nueva Cuenta con código: {}", cuenta.getCodigoCuenta());

        // 1. Validar que el tipo de cuenta exista
        if (cuenta.getIdTipoCuenta() == null || cuenta.getIdTipoCuenta().getId() == null) {
            throw new CrearEntidadExcepcion("Cuentas", "El ID del tipo de cuenta es obligatorio.");
        }
        Optional<TiposCuentas> tipoCuentaExistente = tiposCuentasRepositorio.findById(cuenta.getIdTipoCuenta().getId());
        if (tipoCuentaExistente.isEmpty()) {
            throw new CrearEntidadExcepcion("Cuentas", "El tipo de cuenta con ID " + cuenta.getIdTipoCuenta().getId() + " no existe.");
        }
        cuenta.setIdTipoCuenta(tipoCuentaExistente.get()); // Asegurarse de que la referencia sea la entidad gestionada

        // 2. Validar que la tasa de interés exista
        if (cuenta.getIdTasaInteres() == null || cuenta.getIdTasaInteres().getId() == null) {
            throw new CrearEntidadExcepcion("Cuentas", "El ID de la tasa de interés es obligatorio.");
        }
        Optional<TasasIntereses> tasaInteresExistente = tasasInteresesRepositorio.findById(cuenta.getIdTasaInteres().getId());
        if (tasaInteresExistente.isEmpty()) {
            throw new CrearEntidadExcepcion("Cuentas", "La tasa de interés con ID " + cuenta.getIdTasaInteres().getId() + " no existe.");
        }
        cuenta.setIdTasaInteres(tasaInteresExistente.get()); // Asegurarse de que la referencia sea la entidad gestionada

        // 3. Validar que no exista una cuenta con el mismo código de cuenta
        if (cuentasRepositorio.findByCodigoCuenta(cuenta.getCodigoCuenta()).isPresent()) {
            throw new CrearEntidadExcepcion("Cuentas", "Ya existe una cuenta con el código '" + cuenta.getCodigoCuenta() + "'.");
        }

        // 4. Establecer campos por defecto/automáticos
        cuenta.setFechaCreacion(Instant.now());
        cuenta.setFechaModificacion(Instant.now());
        cuenta.setEstado(EstadoGeneralCuentasEnum.ACTIVO);
        cuenta.setVersion(0L); // Asumiendo versión inicial 0

        try {
            Cuentas nuevaCuenta = cuentasRepositorio.save(cuenta);
            log.info("Cuenta creada exitosamente con ID: {} y código: {}", nuevaCuenta.getId(), nuevaCuenta.getCodigoCuenta());
            return nuevaCuenta;
        } catch (Exception e) {
            log.error("Error al crear Cuenta con código {}: {}", cuenta.getCodigoCuenta(), e.getMessage(), e);
            throw new CrearEntidadExcepcion("Cuentas", "No se pudo crear la cuenta. Detalle: " + e.getMessage());
        }
    }

    @Transactional
    public Cuentas actualizarCuenta(Integer id, Cuentas cuenta) {
        log.info("Intentando actualizar Cuenta con ID: {}", id);
        Cuentas cuentaExistente = cuentasRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("Cuentas", "Cuenta con ID " + id + " no encontrada para actualizar."));

        // 1. Validar el nuevo tipo de cuenta si se proporciona
        if (cuenta.getIdTipoCuenta() != null && cuenta.getIdTipoCuenta().getId() != null) {
            Optional<TiposCuentas> tipoCuentaExistente = tiposCuentasRepositorio.findById(cuenta.getIdTipoCuenta().getId());
            if (tipoCuentaExistente.isEmpty()) {
                throw new ActualizarEntidadExcepcion("Cuentas", "El nuevo tipo de cuenta con ID " + cuenta.getIdTipoCuenta().getId() + " no existe.");
            }
            cuentaExistente.setIdTipoCuenta(tipoCuentaExistente.get());
        }

        // 2. Validar la nueva tasa de interés si se proporciona
        if (cuenta.getIdTasaInteres() != null && cuenta.getIdTasaInteres().getId() != null) {
            Optional<TasasIntereses> tasaInteresExistente = tasasInteresesRepositorio.findById(cuenta.getIdTasaInteres().getId());
            if (tasaInteresExistente.isEmpty()) {
                throw new ActualizarEntidadExcepcion("Cuentas", "La nueva tasa de interés con ID " + cuenta.getIdTasaInteres().getId() + " no existe.");
            }
            cuentaExistente.setIdTasaInteres(tasaInteresExistente.get());
        }

        // 3. Validar que el nuevo código de cuenta no se duplique con otra cuenta (excluyendo la actual)
        if (cuenta.getCodigoCuenta() != null && !cuenta.getCodigoCuenta().equals(cuentaExistente.getCodigoCuenta())) {
            if (cuentasRepositorio.findByCodigoCuenta(cuenta.getCodigoCuenta()).isPresent()) {
                throw new ActualizarEntidadExcepcion("Cuentas", "Ya existe una cuenta con el código '" + cuenta.getCodigoCuenta() + "'.");
            }
        }

        // 4. Actualizar campos permitidos (evitar nulls sobrescribiendo valores existentes si no se desean)
        cuentaExistente.setNombre(cuenta.getNombre() != null ? cuenta.getNombre() : cuentaExistente.getNombre());
        cuentaExistente.setDescripcion(cuenta.getDescripcion() != null ? cuenta.getDescripcion() : cuentaExistente.getDescripcion());
        cuentaExistente.setFechaModificacion(Instant.now()); // Actualizar la fecha de modificación

        try {
            Cuentas cuentaActualizada = cuentasRepositorio.save(cuentaExistente);
            log.info("Cuenta con ID {} actualizada exitosamente.", cuentaActualizada.getId());
            return cuentaActualizada;
        } catch (Exception e) {
            log.error("Error al actualizar Cuenta con ID {}: {}", id, e.getMessage(), e);
            throw new ActualizarEntidadExcepcion("Cuentas", "No se pudo actualizar la cuenta con ID " + id + ". Detalle: " + e.getMessage());
        }
    }

    @Transactional
    public Cuentas desactivarCuenta(Integer id) {
        log.info("Intentando desactivar Cuenta con ID: {}", id);
        Cuentas cuentaExistente = cuentasRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("Cuentas", "Cuenta con ID " + id + " no encontrada para desactivar."));

        if (cuentaExistente.getEstado() == EstadoGeneralCuentasEnum.INACTIVO) {
            log.warn("Cuenta con ID {} ya se encuentra INACTIVA.", id);
            return cuentaExistente;
        }

        cuentaExistente.setEstado(EstadoGeneralCuentasEnum.INACTIVO);
        cuentaExistente.setFechaModificacion(Instant.now());
        try {
            Cuentas cuentaDesactivada = cuentasRepositorio.save(cuentaExistente);
            log.info("Cuenta con ID {} desactivada exitosamente.", cuentaDesactivada.getId());
            return cuentaDesactivada;
        } catch (Exception e) {
            log.error("Error al desactivar Cuenta con ID {}: {}", id, e.getMessage(), e);
            throw new ActualizarEntidadExcepcion("Cuentas", "No se pudo desactivar la cuenta con ID " + id + ". Detalle: " + e.getMessage());
        }
    }


    @Transactional
    public Cuentas activarCuenta(Integer id) {
        log.info("Intentando activar Cuenta con ID: {}", id);
        Cuentas cuentaExistente = cuentasRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("Cuentas", "Cuenta con ID " + id + " no encontrada para activar."));

        if (cuentaExistente.getEstado() == EstadoGeneralCuentasEnum.ACTIVO) {
            log.warn("Cuenta con ID {} ya se encuentra ACTIVA.", id);
            return cuentaExistente;
        }

        cuentaExistente.setEstado(EstadoGeneralCuentasEnum.ACTIVO);
        cuentaExistente.setFechaModificacion(Instant.now());
        try {
            Cuentas cuentaActivada = cuentasRepositorio.save(cuentaExistente);
            log.info("Cuenta con ID {} activada exitosamente.", cuentaActivada.getId());
            return cuentaActivada;
        } catch (Exception e) {
            log.error("Error al activar Cuenta con ID {}: {}", id, e.getMessage(), e);
            throw new ActualizarEntidadExcepcion("Cuentas", "No se pudo activar la cuenta con ID " + id + ". Detalle: " + e.getMessage());
        }
    }
}