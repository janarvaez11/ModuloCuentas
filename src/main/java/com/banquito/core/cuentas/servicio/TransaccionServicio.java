package com.banquito.core.cuentas.servicio;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.cuentas.enums.EstadoTransaccionesEnum;
import com.banquito.core.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.cuentas.modelo.PagosCheques;
import com.banquito.core.cuentas.modelo.PagosTarjetaDebito;
import com.banquito.core.cuentas.modelo.Transferencias;
import com.banquito.core.cuentas.modelo.Transacciones;
import com.banquito.core.cuentas.repositorio.PagosChequesRepositorio;
import com.banquito.core.cuentas.repositorio.PagosTarjetaDebitoRepositorio;
import com.banquito.core.cuentas.repositorio.TranferenciasRepositorio;
import com.banquito.core.cuentas.repositorio.TransaccionesRepositorio;

@Service
public class TransaccionServicio {

    private final TransaccionesRepositorio transaccionesRepositorio;
    private final PagosChequesRepositorio pagosChequesRepositorio;
    private final PagosTarjetaDebitoRepositorio pagosTarjetaDebitoRepositorio;
    private final TranferenciasRepositorio tranferenciasRepositorio;

    public TransaccionServicio(TransaccionesRepositorio transaccionesRepositorio,
                              PagosChequesRepositorio pagosChequesRepositorio,
                              PagosTarjetaDebitoRepositorio pagosTarjetaDebitoRepositorio,
                              TranferenciasRepositorio tranferenciasRepositorio) {
        this.transaccionesRepositorio = transaccionesRepositorio;
        this.pagosChequesRepositorio = pagosChequesRepositorio;
        this.pagosTarjetaDebitoRepositorio = pagosTarjetaDebitoRepositorio;
        this.tranferenciasRepositorio = tranferenciasRepositorio;
    }

    public Transacciones obtenerPorId(Integer id) {
        return transaccionesRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("Transacciones", "No se encontró la transacción con ID: " + id));
    }

    public List<Transacciones> listarUltimas100() {
        return transaccionesRepositorio.findAll().stream().limit(100).toList();
    }

    public Transacciones registrarTransaccion(Transacciones transaccion) {
        if (transaccion.getMonto() == null || transaccion.getMonto().signum() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0.");
        }
        if (transaccion.getTipoTransaccion() == null) {
            throw new IllegalArgumentException("El tipo de transacción es obligatorio.");
        }
        if (transaccion.getFechaTransaccion() == null) {
            transaccion.setFechaTransaccion(Instant.now());
        }
        transaccion.setEstado(EstadoTransaccionesEnum.PENDIENTE);
        return transaccionesRepositorio.save(transaccion);
    }

    public PagosCheques registrarPagoCheque(PagosCheques pagoCheque) {
        if (pagoCheque.getIdTransaccion() == null || pagoCheque.getNumeroCheque() == null) {
            throw new IllegalArgumentException("Información incompleta del pago por cheque.");
        }
        return pagosChequesRepositorio.save(pagoCheque);
    }

    public PagosTarjetaDebito registrarPagoTarjeta(PagosTarjetaDebito pagoTarjeta) {
        if (pagoTarjeta.getIdTransaccion() == null || pagoTarjeta.getNumeroTarjeta() == null) {
            throw new IllegalArgumentException("Información incompleta del pago con tarjeta.");
        }
        return pagosTarjetaDebitoRepositorio.save(pagoTarjeta);
    }

    public Transferencias registrarTransferencia(Transferencias transferencia) {
        if (transferencia.getIdTransaccion() == null || transferencia.getIdCuentaClienteDestino() == null) {
            throw new IllegalArgumentException("Información incompleta de la transferencia.");
        }
        return tranferenciasRepositorio.save(transferencia);
    }

    @Transactional
    public void eliminarLogicamente(Integer id) {
        Transacciones transaccion = obtenerPorId(id);
        transaccion.setEstado(EstadoTransaccionesEnum.RECHAZADO);
        transaccionesRepositorio.save(transaccion);
    }
} 
