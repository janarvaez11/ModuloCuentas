package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.modelo.Cuentas;
import com.banquito.core.cuentas.servicio.CuentaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaControlador {

    private final CuentaServicio cuentaServicio;

    public CuentaControlador(CuentaServicio cuentaServicio) {
        this.cuentaServicio = cuentaServicio;
    }

    @PostMapping
    public ResponseEntity<Cuentas> crearCuenta(@RequestBody Cuentas cuenta) {
        return ResponseEntity.ok(cuentaServicio.crearCuenta(cuenta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuentas> obtenerCuentaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(cuentaServicio.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Cuentas>> listarCuentas() {
        return ResponseEntity.ok(cuentaServicio.listarPrimeras100());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuentas> actualizarCuenta(@PathVariable Integer id, @RequestBody Cuentas cuenta) {
        return ResponseEntity.ok(cuentaServicio.actualizarCuenta(cuenta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Integer id) {
        cuentaServicio.eliminarLogicamente(id);
        return ResponseEntity.noContent().build();
    }
} 
