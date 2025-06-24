package com.banquito.core.cuentas.servicio;

import com.banquito.core.cuentas.enums.EstadoCuentaClienteEnum; // Importación correcta para el estado de la cuenta
import com.banquito.core.cuentas.enums.EstadoEspecificoTransaccionEnum;
import com.banquito.core.cuentas.enums.EstadoTransaccionesEnum;
import com.banquito.core.cuentas.enums.TipoTransaccionEnum;
import com.banquito.core.cuentas.excepcion.CrearEntidadExcepcion;
import com.banquito.core.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.cuentas.modelo.CuentasClientes;
import com.banquito.core.cuentas.modelo.Transacciones;
import com.banquito.core.cuentas.modelo.Transferencias;
import com.banquito.core.cuentas.repositorio.CuentasClientesRepositorio;
import com.banquito.core.cuentas.repositorio.TransaccionesRepositorio;
import com.banquito.core.cuentas.repositorio.TransferenciasRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class TransaccionesServicio {

    private final TransaccionesRepositorio transaccionesRepositorio;
    private final CuentasClientesRepositorio cuentasClientesRepositorio;
    private final TransferenciasRepositorio transferenciasRepositorio; 

    public TransaccionesServicio(TransaccionesRepositorio transaccionesRepositorio,
                                 CuentasClientesRepositorio cuentasClientesRepositorio,
                                 TransferenciasRepositorio transferenciasRepositorio) {
        this.transaccionesRepositorio = transaccionesRepositorio;
        this.cuentasClientesRepositorio = cuentasClientesRepositorio;
        this.transferenciasRepositorio = transferenciasRepositorio;
    }


    public Transacciones buscarPorId(Integer id) {
        log.debug("Iniciando búsqueda de Transacciones con ID: {}", id);
        return transaccionesRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("Transacciones", "Transacción con ID " + id + " no encontrada."));
    }


    public List<Transacciones> buscarPorIdCuentaCliente(Integer idCuentaCliente) {
        log.debug("Iniciando búsqueda de Transacciones para idCuentaCliente: {}", idCuentaCliente);
        List<Transacciones> transacciones = transaccionesRepositorio.findByIdCuentaCliente_IdOrderByFechaTransaccionDesc(idCuentaCliente);
        if (transacciones.isEmpty()) {
            log.warn("No se encontraron transacciones para la cuenta cliente con ID: {}", idCuentaCliente);
        }
        return transacciones;
    }


    public List<Transacciones> buscarPorIdCuentaClienteYFechas(Integer idCuentaCliente, Instant fechaInicio, Instant fechaFin) {
        log.debug("Iniciando búsqueda de Transacciones para idCuentaCliente: {} entre {} y {}", idCuentaCliente, fechaInicio, fechaFin);
        List<Transacciones> transacciones = transaccionesRepositorio.findByIdCuentaCliente_IdAndFechaTransaccionBetweenOrderByFechaTransaccionDesc(idCuentaCliente, fechaInicio, fechaFin);
        if (transacciones.isEmpty()) {
            log.warn("No se encontraron transacciones para la cuenta cliente con ID: {} en el rango de fechas.", idCuentaCliente);
        }
        return transacciones;
    }


    @Transactional
    public Transacciones realizarDeposito(Transacciones transaccion) {
        log.info("Iniciando depósito de {} en cuenta cliente ID {}", transaccion.getMonto(), transaccion.getIdCuentaCliente().getId());

        if (transaccion.getTipoTransaccion() != TipoTransaccionEnum.DEPOSITO) {
            throw new CrearEntidadExcepcion("Transacciones", "El tipo de transacción debe ser DEPOSITO para esta operación.");
        }

        CuentasClientes cuentaCliente = cuentasClientesRepositorio.findById(transaccion.getIdCuentaCliente().getId())
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("CuentasClientes", "Cuenta Cliente con ID " + transaccion.getIdCuentaCliente().getId() + " no encontrada."));

        if (cuentaCliente.getEstado() != EstadoCuentaClienteEnum.ACTIVO) {
             throw new CrearEntidadExcepcion("Transacciones", "La cuenta cliente no está activa para realizar depósitos.");
        }

        BigDecimal nuevoSaldoDisponible = cuentaCliente.getSaldoDisponible().add(transaccion.getMonto());
        BigDecimal nuevoSaldoContable = cuentaCliente.getSaldoContable().add(transaccion.getMonto());
        cuentaCliente.setSaldoDisponible(nuevoSaldoDisponible);
        cuentaCliente.setSaldoContable(nuevoSaldoContable);

        try {
            cuentasClientesRepositorio.save(cuentaCliente); 
            transaccion.setIdCuentaCliente(cuentaCliente);
            transaccion.setFechaTransaccion(Instant.now());
            transaccion.setEstado(EstadoTransaccionesEnum.PROCESADO); 
            transaccion.setVersion(0L); 

            Transacciones transaccionCreada = transaccionesRepositorio.save(transaccion);
            log.info("Depósito de {} en cuenta cliente ID {} completado exitosamente. Nuevo saldo: {}", transaccion.getMonto(), cuentaCliente.getId(), nuevoSaldoDisponible);
            return transaccionCreada;
        } catch (Exception e) {
            log.error("Error al realizar depósito en cuenta cliente ID {}: {}", cuentaCliente.getId(), e.getMessage(), e);
            throw new CrearEntidadExcepcion("Transacciones", "No se pudo realizar el depósito. Detalle: " + e.getMessage());
        }
    }


    @Transactional
    public Transacciones realizarRetiro(Transacciones transaccion) {
        log.info("Iniciando retiro de {} de cuenta cliente ID {}", transaccion.getMonto(), transaccion.getIdCuentaCliente().getId());

        if (transaccion.getTipoTransaccion() != TipoTransaccionEnum.RETIRO) {
            throw new CrearEntidadExcepcion("Transacciones", "El tipo de transacción debe ser RETIRO para esta operación.");
        }

        CuentasClientes cuentaCliente = cuentasClientesRepositorio.findById(transaccion.getIdCuentaCliente().getId())
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("CuentasClientes", "Cuenta Cliente con ID " + transaccion.getIdCuentaCliente().getId() + " no encontrada."));

        if (cuentaCliente.getEstado() != EstadoCuentaClienteEnum.ACTIVO) {
            throw new CrearEntidadExcepcion("Transacciones", "La cuenta cliente no está activa para realizar retiros.");
        }

        if (cuentaCliente.getSaldoDisponible().compareTo(transaccion.getMonto()) < 0) {
            throw new CrearEntidadExcepcion("Transacciones", "Saldo insuficiente en la cuenta cliente " + cuentaCliente.getNumeroCuenta() + " para realizar el retiro.");
        }

        BigDecimal nuevoSaldoDisponible = cuentaCliente.getSaldoDisponible().subtract(transaccion.getMonto());
        BigDecimal nuevoSaldoContable = cuentaCliente.getSaldoContable().subtract(transaccion.getMonto());
        cuentaCliente.setSaldoDisponible(nuevoSaldoDisponible);
        cuentaCliente.setSaldoContable(nuevoSaldoContable);

        try {
            cuentasClientesRepositorio.save(cuentaCliente); 

            transaccion.setIdCuentaCliente(cuentaCliente); 
            transaccion.setFechaTransaccion(Instant.now());
            transaccion.setEstado(EstadoTransaccionesEnum.PROCESADO); 
            transaccion.setVersion(0L); 

            Transacciones transaccionCreada = transaccionesRepositorio.save(transaccion);
            log.info("Retiro de {} de cuenta cliente ID {} completado exitosamente. Nuevo saldo: {}", transaccion.getMonto(), cuentaCliente.getId(), nuevoSaldoDisponible);
            return transaccionCreada;
        } catch (Exception e) {
            log.error("Error al realizar retiro de cuenta cliente ID {}: {}", cuentaCliente.getId(), e.getMessage(), e);
            throw new CrearEntidadExcepcion("Transacciones", "No se pudo realizar el retiro. Detalle: " + e.getMessage());
        }
    }


    @Transactional
    public Transacciones realizarTransferencia(Integer idCuentaClienteOrigen, Integer idCuentaClienteDestino, BigDecimal monto, String descripcion) {
        log.info("Iniciando transferencia de {} desde cuenta ID {} a cuenta ID {}", monto, idCuentaClienteOrigen, idCuentaClienteDestino);

        if (idCuentaClienteOrigen.equals(idCuentaClienteDestino)) {
            throw new CrearEntidadExcepcion("Transferencias", "La cuenta origen y la cuenta destino no pueden ser la misma.");
        }

        CuentasClientes cuentaOrigen = cuentasClientesRepositorio.findById(idCuentaClienteOrigen)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("CuentasClientes", "Cuenta Cliente Origen con ID " + idCuentaClienteOrigen + " no encontrada."));

        CuentasClientes cuentaDestino = cuentasClientesRepositorio.findById(idCuentaClienteDestino)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("CuentasClientes", "Cuenta Cliente Destino con ID " + idCuentaClienteDestino + " no encontrada."));

        if (cuentaOrigen.getEstado() != EstadoCuentaClienteEnum.ACTIVO) {
            throw new CrearEntidadExcepcion("Transferencias", "La cuenta cliente origen no está activa para realizar transferencias.");
        }
        if (cuentaDestino.getEstado() != EstadoCuentaClienteEnum.ACTIVO) {
            throw new CrearEntidadExcepcion("Transferencias", "La cuenta cliente destino no está activa para recibir transferencias.");
        }

        if (cuentaOrigen.getSaldoDisponible().compareTo(monto) < 0) {
            throw new CrearEntidadExcepcion("Transferencias", "Saldo insuficiente en la cuenta origen " + cuentaOrigen.getNumeroCuenta() + " para realizar la transferencia.");
        }

        try {
            Transacciones transaccionOrigen = new Transacciones();
            transaccionOrigen.setIdCuentaCliente(cuentaOrigen);
            transaccionOrigen.setTipoTransaccion(TipoTransaccionEnum.TRANSFERENCIA); 
            transaccionOrigen.setMonto(monto.negate()); 
            transaccionOrigen.setDescripcion("Transferencia saliente a " + cuentaDestino.getNumeroCuenta() + ": " + descripcion);
            transaccionOrigen.setFechaTransaccion(Instant.now());
            transaccionOrigen.setEstado(EstadoTransaccionesEnum.PROCESADO);
            transaccionOrigen.setVersion(0L);
            Transacciones transaccionOrigenGuardada = transaccionesRepositorio.save(transaccionOrigen);

            cuentaOrigen.setSaldoDisponible(cuentaOrigen.getSaldoDisponible().subtract(monto));
            cuentaOrigen.setSaldoContable(cuentaOrigen.getSaldoContable().subtract(monto));
            cuentasClientesRepositorio.save(cuentaOrigen);

            Transacciones transaccionDestino = new Transacciones();
            transaccionDestino.setIdCuentaCliente(cuentaDestino);
            transaccionDestino.setTipoTransaccion(TipoTransaccionEnum.TRANSFERENCIA); 
            transaccionDestino.setMonto(monto); 
            transaccionDestino.setDescripcion("Transferencia entrante de " + cuentaOrigen.getNumeroCuenta() + ": " + descripcion);
            transaccionDestino.setFechaTransaccion(Instant.now());
            transaccionDestino.setEstado(EstadoTransaccionesEnum.PROCESADO); 
            transaccionDestino.setVersion(0L);
          
            transaccionesRepositorio.save(transaccionDestino);


            Transferencias detalleTransferencia = new Transferencias();
            detalleTransferencia.setIdTransaccion(transaccionOrigenGuardada); 
            detalleTransferencia.setIdCuentaClienteDestino(cuentaDestino);
            detalleTransferencia.setEstado(EstadoEspecificoTransaccionEnum.ENVIADO);
            detalleTransferencia.setVersion(0L);
            transferenciasRepositorio.save(detalleTransferencia);

            cuentaDestino.setSaldoDisponible(cuentaDestino.getSaldoDisponible().add(monto));
            cuentaDestino.setSaldoContable(cuentaDestino.getSaldoContable().add(monto));
            cuentasClientesRepositorio.save(cuentaDestino);

            log.info("Transferencia de {} de cuenta ID {} a cuenta ID {} completada exitosamente.", monto, idCuentaClienteOrigen, idCuentaClienteDestino);
            return transaccionOrigenGuardada; 
        } catch (Exception e) {
            log.error("Error al realizar transferencia de {} de cuenta ID {} a cuenta ID {}: {}", monto, idCuentaClienteOrigen, idCuentaClienteDestino, e.getMessage(), e);
            throw new CrearEntidadExcepcion("Transferencias", "No se pudo realizar la transferencia. Detalle: " + e.getMessage());
        }
    }
}