package com.banquito.core.cuentas.servicio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;
import com.banquito.core.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.cuentas.modelo.Cuentas;
import com.banquito.core.cuentas.modelo.ComisionesCargosCuentas;
import com.banquito.core.cuentas.modelo.ServiciosAsociadosCuentas;
import com.banquito.core.cuentas.repositorio.CuentasRepositorio;
import com.banquito.core.cuentas.repositorio.ComisionesCargosCuentasRepositorio;
import com.banquito.core.cuentas.repositorio.ServiciosAsociadosCuentasRepositorio;

@Service
public class CuentaServicio {

    private final CuentasRepositorio cuentasRepositorio;
    private final ComisionesCargosCuentasRepositorio comisionesCargosCuentasRepositorio;
    private final ServiciosAsociadosCuentasRepositorio serviciosAsociadosCuentasRepositorio;

    public CuentaServicio(CuentasRepositorio cuentasRepositorio,
                         ComisionesCargosCuentasRepositorio comisionesCargosCuentasRepositorio,
                         ServiciosAsociadosCuentasRepositorio serviciosAsociadosCuentasRepositorio) {
        this.cuentasRepositorio = cuentasRepositorio;
        this.comisionesCargosCuentasRepositorio = comisionesCargosCuentasRepositorio;
        this.serviciosAsociadosCuentasRepositorio = serviciosAsociadosCuentasRepositorio;
    }

    public Cuentas obtenerPorId(Integer id) {
        return cuentasRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("Cuentas", "No se encontró la cuenta con ID: " + id));
    }

    public List<Cuentas> listarPrimeras100() {
        return cuentasRepositorio.findAll().stream().limit(100).toList();
    }

    public Cuentas crearCuenta(Cuentas cuenta) {
        return cuentasRepositorio.save(cuenta);
    }

    public Cuentas actualizarCuenta(Cuentas cuenta) {
        if (!cuentasRepositorio.existsById(cuenta.getId())) {
            throw new EntidadNoEncontradaExcepcion("Cuentas", "No se encontró la cuenta con ID: " + cuenta.getId());
        }
        return cuentasRepositorio.save(cuenta);
    }

    @Transactional
    public void eliminarLogicamente(Integer id) {
        Cuentas cuenta = obtenerPorId(id);
        cuenta.setEstado(EstadoGeneralCuentasEnum.INACTIVO);
        cuentasRepositorio.save(cuenta);
    }

    public ComisionesCargosCuentas asignarComisionACuenta(ComisionesCargosCuentas comisionCuenta) {
        return comisionesCargosCuentasRepositorio.save(comisionCuenta);
    }

    public ServiciosAsociadosCuentas asignarServicioACuenta(ServiciosAsociadosCuentas servicioCuenta) {
        return serviciosAsociadosCuentasRepositorio.save(servicioCuenta);
    }
} 
