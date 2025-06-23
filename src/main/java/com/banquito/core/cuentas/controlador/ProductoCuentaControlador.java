package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.modelo.*;
import com.banquito.core.cuentas.servicio.ProductoCuentaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto-cuenta")
public class ProductoCuentaControlador {

    private final ProductoCuentaServicio productoCuentaServicio;

    public ProductoCuentaControlador(ProductoCuentaServicio productoCuentaServicio) {
        this.productoCuentaServicio = productoCuentaServicio;
    }

    @PostMapping("/tipos")
    public ResponseEntity<TiposCuentas> crearTipoCuenta(@RequestBody TiposCuentas tipoCuenta) {
        return ResponseEntity.ok(productoCuentaServicio.crearTipoCuenta(tipoCuenta));
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<TiposCuentas>> listarTiposCuenta() {
        return ResponseEntity.ok(productoCuentaServicio.listarTiposCuenta());
    }

    @PostMapping("/tasas-interes")
    public ResponseEntity<TasasIntereses> crearTasaInteres(@RequestBody TasasIntereses tasa) {
        return ResponseEntity.ok(productoCuentaServicio.crearTasaInteres(tasa));
    }

    @PostMapping("/tasas-plazos")
    public ResponseEntity<TasasPlazos> asociarTasaPlazo(@RequestBody TasasPlazos tasaPlazo) {
        return ResponseEntity.ok(productoCuentaServicio.asociarTasaPlazo(tasaPlazo));
    }

    @PostMapping("/tasas-saldos")
    public ResponseEntity<TasasSaldos> asociarTasaSaldo(@RequestBody TasasSaldos tasaSaldo) {
        return ResponseEntity.ok(productoCuentaServicio.asociarTasaSaldo(tasaSaldo));
    }

    @PostMapping("/servicios-asociados")
    public ResponseEntity<ServiciosAsociados> crearServicioAsociado(@RequestBody ServiciosAsociados servicio) {
        return ResponseEntity.ok(productoCuentaServicio.crearServicioAsociado(servicio));
    }

    @PostMapping("/comisiones")
    public ResponseEntity<ComisionesCargos> asociarComisionServicio(@RequestBody ComisionesCargos comision) {
        return ResponseEntity.ok(productoCuentaServicio.asociarComisionServicio(comision));
    }

    @PostMapping("/exenciones")
    public ResponseEntity<ExencionesCuentas> crearExencionComision(@RequestBody ExencionesCuentas exencion) {
        return ResponseEntity.ok(productoCuentaServicio.crearExencionComision(exencion));
    }

    @GetMapping("/tipos/{id}")
    public ResponseEntity<TiposCuentas> consultarTipoCuentaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(productoCuentaServicio.consultarTipoCuentaPorId(id));
    }
} 
