package com.banquito.core.cuentas.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;
import com.banquito.core.cuentas.excepcion.EntidadNoEncontradaExcepcion;
import com.banquito.core.cuentas.modelo.ComisionesCargos;
import com.banquito.core.cuentas.modelo.ExencionesCuentas;
import com.banquito.core.cuentas.modelo.ServiciosAsociados;
import com.banquito.core.cuentas.modelo.TasasIntereses;
import com.banquito.core.cuentas.modelo.TasasPlazos;
import com.banquito.core.cuentas.modelo.TasasSaldos;
import com.banquito.core.cuentas.modelo.TiposCuentas;
import com.banquito.core.cuentas.repositorio.ComisionesCargosRepositorio;
import com.banquito.core.cuentas.repositorio.ExencionesCuentasRepositorio;
import com.banquito.core.cuentas.repositorio.ServiciosAsociadosRepositorio;
import com.banquito.core.cuentas.repositorio.TasasInteresesRepositorio;
import com.banquito.core.cuentas.repositorio.TasasPlazosRepositorio;
import com.banquito.core.cuentas.repositorio.TasasSaldosRepositorio;
import com.banquito.core.cuentas.repositorio.TiposCuentasRepositorio;

@Service
public class ProductoCuentaServicio {

    private final TiposCuentasRepositorio tiposCuentasRepositorio;
    private final TasasInteresesRepositorio tasasInteresesRepositorio;
    private final TasasPlazosRepositorio tasasPlazosRepositorio;
    private final TasasSaldosRepositorio tasasSaldosRepositorio;
    private final ServiciosAsociadosRepositorio serviciosAsociadosRepositorio;
    private final ComisionesCargosRepositorio comisionesCargosRepositorio;
    private final ExencionesCuentasRepositorio exencionesCuentasRepositorio;

    public ProductoCuentaServicio(
        TiposCuentasRepositorio tiposCuentasRepositorio,
        TasasInteresesRepositorio tasasInteresesRepositorio,
        TasasPlazosRepositorio tasasPlazosRepositorio,
        TasasSaldosRepositorio tasasSaldosRepositorio,
        ServiciosAsociadosRepositorio serviciosAsociadosRepositorio,
        ComisionesCargosRepositorio comisionesCargosRepositorio,
        ExencionesCuentasRepositorio exencionesCuentasRepositorio) {
        this.tiposCuentasRepositorio = tiposCuentasRepositorio;
        this.tasasInteresesRepositorio = tasasInteresesRepositorio;
        this.tasasPlazosRepositorio = tasasPlazosRepositorio;
        this.tasasSaldosRepositorio = tasasSaldosRepositorio;
        this.serviciosAsociadosRepositorio = serviciosAsociadosRepositorio;
        this.comisionesCargosRepositorio = comisionesCargosRepositorio;
        this.exencionesCuentasRepositorio = exencionesCuentasRepositorio;
    }

    public TiposCuentas crearTipoCuenta(TiposCuentas tipoCuenta) {
        tipoCuenta.setEstado(EstadoGeneralCuentasEnum.ACTIVO);
        return tiposCuentasRepositorio.save(tipoCuenta);
    }

    public List<TiposCuentas> listarTiposCuenta() {
        return tiposCuentasRepositorio.findAll();
    }

    public TasasIntereses crearTasaInteres(TasasIntereses tasa) {
        tasa.setEstado(EstadoGeneralCuentasEnum.ACTIVO);
        return tasasInteresesRepositorio.save(tasa);
    }

    public TasasPlazos asociarTasaPlazo(TasasPlazos tasaPlazo) {
        return tasasPlazosRepositorio.save(tasaPlazo);
    }

    public TasasSaldos asociarTasaSaldo(TasasSaldos tasaSaldo) {
        return tasasSaldosRepositorio.save(tasaSaldo);
    }

    public ServiciosAsociados crearServicioAsociado(ServiciosAsociados servicio) {
        servicio.setEstado(EstadoGeneralCuentasEnum.ACTIVO);
        return serviciosAsociadosRepositorio.save(servicio);
    }

    public ComisionesCargos asociarComisionServicio(ComisionesCargos comision) {
        comision.setEstado(EstadoGeneralCuentasEnum.ACTIVO);
        return comisionesCargosRepositorio.save(comision);
    }

    public ExencionesCuentas crearExencionComision(ExencionesCuentas exencion) {
        exencion.setEstado(EstadoGeneralCuentasEnum.ACTIVO);
        return exencionesCuentasRepositorio.save(exencion);
    }

    public TiposCuentas consultarTipoCuentaPorId(Integer id) {
        return tiposCuentasRepositorio.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaExcepcion("TiposCuentas", "No se encontró el tipo de cuenta con ID: " + id));
    }
} 
