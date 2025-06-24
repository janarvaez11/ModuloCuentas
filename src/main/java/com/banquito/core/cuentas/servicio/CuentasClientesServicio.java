package com.banquito.core.cuentas.servicio;

import com.banquito.core.cuentas.enums.EstadoCuentaClienteEnum;
import com.banquito.core.cuentas.excepcion.ActualizarEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.cuentas.modelo.Cuentas;
import com.banquito.core.cuentas.modelo.CuentasClientes;
import com.banquito.core.cuentas.repositorio.CuentasClientesRepositorio;
import com.banquito.core.cuentas.repositorio.CuentasRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
public class CuentasClientesServicio {

    private final CuentasClientesRepositorio cuentasClientesRepositorio;
    private final CuentasRepositorio cuentasRepositorio;

    public CuentasClientesServicio(CuentasClientesRepositorio cuentasClientesRepositorio,
                                   CuentasRepositorio cuentasRepositorio) {
        this.cuentasClientesRepositorio = cuentasClientesRepositorio;
        this.cuentasRepositorio = cuentasRepositorio;
    }


    public CuentasClientes buscarPorId(Integer id) {
        log.debug("Iniciando búsqueda de CuentasClientes con ID: {}", id);
        CuentasClientes cuentaCliente = cuentasClientesRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("CuentasClientes", "Cuenta Cliente con ID " + id + " no encontrada."));
        log.debug("Cuenta Cliente encontrada con ID: {}", id);
        return cuentaCliente;
    }

    public CuentasClientes buscarPorNumeroCuenta(String numeroCuenta) {
        log.debug("Iniciando búsqueda de CuentasClientes por número de cuenta: {}", numeroCuenta);
        CuentasClientes cuentaCliente = cuentasClientesRepositorio.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("CuentasClientes", "Cuenta Cliente con número " + numeroCuenta + " no encontrada."));
        log.debug("Cuenta Cliente encontrada con número: {}", numeroCuenta);
        return cuentaCliente;
    }

    public CuentasClientes buscarPorIdClienteAndNumeroCuenta(Integer idCliente, String numeroCuenta) {
        log.debug("Iniciando búsqueda de CuentasClientes por ID Cliente: {} y Número de Cuenta: {}", idCliente, numeroCuenta);
        CuentasClientes cuentaCliente = cuentasClientesRepositorio.findByIdClienteAndNumeroCuenta(idCliente, numeroCuenta)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("CuentasClientes", "Cuenta Cliente no encontrada para Cliente ID " + idCliente + " y Número de Cuenta " + numeroCuenta + "."));
        log.debug("Cuenta Cliente encontrada para ID Cliente: {} y Número de Cuenta: {}", idCliente, numeroCuenta);
        return cuentaCliente;
    }


    @Transactional
    public CuentasClientes crearCuentasClientes(CuentasClientes cuentaCliente) {
        log.info("Intentando crear nueva CuentasClientes para cliente ID: {} y número de cuenta: {}", cuentaCliente.getIdCliente(), cuentaCliente.getNumeroCuenta());

        // 1. Validar que la cuenta maestra (Cuentas) exista
        if (cuentaCliente.getIdCuenta() == null || cuentaCliente.getIdCuenta().getId() == null) {
            throw new CrearEntidadExcepcion("CuentasClientes", "El ID de la cuenta maestra es obligatorio.");
        }
        Optional<Cuentas> cuentaMaestraExistente = cuentasRepositorio.findById(cuentaCliente.getIdCuenta().getId());
        if (cuentaMaestraExistente.isEmpty()) {
            throw new CrearEntidadExcepcion("CuentasClientes", "La cuenta maestra con ID " + cuentaCliente.getIdCuenta().getId() + " no existe.");
        }
        cuentaCliente.setIdCuenta(cuentaMaestraExistente.get()); // Asegurarse de que la referencia sea la entidad gestionada

        // 2. Validar que no exista una cuenta cliente con el mismo número de cuenta para el mismo cliente
        if (cuentasClientesRepositorio.findByIdClienteAndNumeroCuenta(cuentaCliente.getIdCliente(), cuentaCliente.getNumeroCuenta()).isPresent()) {
            throw new CrearEntidadExcepcion("CuentasClientes", "Ya existe una cuenta cliente con el número '" + cuentaCliente.getNumeroCuenta() + "' para el cliente con ID " + cuentaCliente.getIdCliente() + ".");
        }
        
        // 3. Establecer campos por defecto/automáticos
        cuentaCliente.setFechaApertura(Instant.now());
        cuentaCliente.setEstado(EstadoCuentaClienteEnum.ACTIVO);
        cuentaCliente.setVersion(0L); // Asumiendo versión inicial 0

        try {
            CuentasClientes nuevaCuentaCliente = cuentasClientesRepositorio.save(cuentaCliente);
            log.info("CuentasClientes creada exitosamente con ID: {} y número de cuenta: {}", nuevaCuentaCliente.getId(), nuevaCuentaCliente.getNumeroCuenta());
            return nuevaCuentaCliente;
        } catch (Exception e) {
            log.error("Error al crear CuentasClientes para cliente ID {} y número {}: {}", cuentaCliente.getIdCliente(), cuentaCliente.getNumeroCuenta(), e.getMessage(), e);
            throw new CrearEntidadExcepcion("CuentasClientes", "No se pudo crear la cuenta cliente. Detalle: " + e.getMessage());
        }
    }


    @Transactional
    public CuentasClientes actualizarCuentasClientes(Integer id, CuentasClientes cuentaCliente) {
        log.info("Intentando actualizar CuentasClientes con ID: {}", id);
        CuentasClientes cuentaClienteExistente = cuentasClientesRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("CuentasClientes", "Cuenta Cliente con ID " + id + " no encontrada para actualizar."));

        // 1. Validar la nueva cuenta maestra (Cuentas) si se proporciona
        if (cuentaCliente.getIdCuenta() != null && cuentaCliente.getIdCuenta().getId() != null) {
            Optional<Cuentas> cuentaMaestraExistente = cuentasRepositorio.findById(cuentaCliente.getIdCuenta().getId());
            if (cuentaMaestraExistente.isEmpty()) {
                throw new ActualizarEntidadExcepcion("CuentasClientes", "La nueva cuenta maestra con ID " + cuentaCliente.getIdCuenta().getId() + " no existe.");
            }
            cuentaClienteExistente.setIdCuenta(cuentaMaestraExistente.get());
        }

        // 2. Validar que la combinación idCliente y numeroCuenta no se duplique con otra CuentasClientes (excluyendo la actual)
        if (cuentaCliente.getIdCliente() != null && cuentaCliente.getNumeroCuenta() != null &&
            (!cuentaCliente.getIdCliente().equals(cuentaClienteExistente.getIdCliente()) || !cuentaCliente.getNumeroCuenta().equals(cuentaClienteExistente.getNumeroCuenta()))) {

            if (cuentasClientesRepositorio.findByIdClienteAndNumeroCuenta(cuentaCliente.getIdCliente(), cuentaCliente.getNumeroCuenta()).isPresent()) {
                throw new ActualizarEntidadExcepcion("CuentasClientes", "Ya existe una cuenta cliente con el número '" + cuentaCliente.getNumeroCuenta() + "' para el cliente con ID " + cuentaCliente.getIdCliente() + ".");
            }
        }
        
        // 3. Actualizar campos permitidos
        cuentaClienteExistente.setIdCliente(cuentaCliente.getIdCliente() != null ? cuentaCliente.getIdCliente() : cuentaClienteExistente.getIdCliente());
        cuentaClienteExistente.setNumeroCuenta(cuentaCliente.getNumeroCuenta() != null ? cuentaCliente.getNumeroCuenta() : cuentaClienteExistente.getNumeroCuenta());
        cuentaClienteExistente.setSaldoDisponible(cuentaCliente.getSaldoDisponible() != null ? cuentaCliente.getSaldoDisponible() : cuentaClienteExistente.getSaldoDisponible());
        cuentaClienteExistente.setSaldoContable(cuentaCliente.getSaldoContable() != null ? cuentaCliente.getSaldoContable() : cuentaClienteExistente.getSaldoContable());
        // fechaApertura no se actualiza, es la fecha inicial de apertura.

        try {
            CuentasClientes cuentaClienteActualizada = cuentasClientesRepositorio.save(cuentaClienteExistente);
            log.info("CuentasClientes con ID {} actualizada exitosamente.", cuentaClienteActualizada.getId());
            return cuentaClienteActualizada;
        } catch (Exception e) {
            log.error("Error al actualizar CuentasClientes con ID {}: {}", id, e.getMessage(), e);
            throw new ActualizarEntidadExcepcion("CuentasClientes", "No se pudo actualizar la cuenta cliente con ID " + id + ". Detalle: " + e.getMessage());
        }
    }

    @Transactional
    public CuentasClientes desactivarCuentasClientes(Integer id) {
        log.info("Intentando desactivar CuentasClientes con ID: {}", id);
        CuentasClientes cuentaClienteExistente = cuentasClientesRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("CuentasClientes", "Cuenta Cliente con ID " + id + " no encontrada para desactivar."));

        if (cuentaClienteExistente.getEstado() == EstadoCuentaClienteEnum.INACTIVO) {
            log.warn("CuentasClientes con ID {} ya se encuentra INACTIVA.", id);
            return cuentaClienteExistente;
        }

        cuentaClienteExistente.setEstado(EstadoCuentaClienteEnum.INACTIVO);
        try {
            CuentasClientes cuentaClienteDesactivada = cuentasClientesRepositorio.save(cuentaClienteExistente);
            log.info("CuentasClientes con ID {} desactivada exitosamente.", cuentaClienteDesactivada.getId());
            return cuentaClienteDesactivada;
        } catch (Exception e) {
            log.error("Error al desactivar CuentasClientes con ID {}: {}", id, e.getMessage(), e);
            throw new ActualizarEntidadExcepcion("CuentasClientes", "No se pudo desactivar la cuenta cliente con ID " + id + ". Detalle: " + e.getMessage());
        }
    }


    @Transactional
    public CuentasClientes activarCuentasClientes(Integer id) {
        log.info("Intentando activar CuentasClientes con ID: {}", id);
        CuentasClientes cuentaClienteExistente = cuentasClientesRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("CuentasClientes", "Cuenta Cliente con ID " + id + " no encontrada para activar."));

        if (cuentaClienteExistente.getEstado() == EstadoCuentaClienteEnum.ACTIVO) {
            log.warn("CuentasClientes con ID {} ya se encuentra ACTIVA.", id);
            return cuentaClienteExistente;
        }

        cuentaClienteExistente.setEstado(EstadoCuentaClienteEnum.ACTIVO);
        try {
            CuentasClientes cuentaClienteActivada = cuentasClientesRepositorio.save(cuentaClienteExistente);
            log.info("CuentasClientes con ID {} activada exitosamente.", cuentaClienteActivada.getId());
            return cuentaClienteActivada;
        } catch (Exception e) {
            log.error("Error al activar CuentasClientes con ID {}: {}", id, e.getMessage(), e);
            throw new ActualizarEntidadExcepcion("CuentasClientes", "No se pudo activar la cuenta cliente con ID " + id + ". Detalle: " + e.getMessage());
        }
    }
}