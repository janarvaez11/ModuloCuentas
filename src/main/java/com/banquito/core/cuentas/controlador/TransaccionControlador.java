package com.banquito.core.cuentas.controlador;

import com.banquito.core.cuentas.controlador.dto.TransaccionDTO;
import com.banquito.core.cuentas.modelo.PagosCheques;
import com.banquito.core.cuentas.modelo.PagosTarjetaDebito;
import com.banquito.core.cuentas.modelo.Transacciones;
import com.banquito.core.cuentas.modelo.Transferencias;
import com.banquito.core.cuentas.servicio.TransaccionServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionControlador {

    private final TransaccionServicio servicio;

    public TransaccionControlador(TransaccionServicio servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<Transacciones> registrarTransaccion(@RequestBody TransaccionDTO dto) {
        return ResponseEntity.ok(servicio.registrarTransaccion(null));
    }

    @PostMapping("/cheque")
    public ResponseEntity<PagosCheques> registrarPagoCheque(@RequestBody PagosCheques pago) {
        return ResponseEntity.ok(servicio.registrarPagoCheque(pago));
    }

    @PostMapping("/tarjeta")
    public ResponseEntity<PagosTarjetaDebito> registrarPagoTarjeta(@RequestBody PagosTarjetaDebito pago) {
        return ResponseEntity.ok(servicio.registrarPagoTarjeta(pago));
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Transferencias> registrarTransferencia(@RequestBody Transferencias transferencia) {
        return ResponseEntity.ok(servicio.registrarTransferencia(transferencia));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacciones> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Transacciones>> listarUltimas100() {
        return ResponseEntity.ok(servicio.listarUltimas100());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLogicamente(@PathVariable Integer id) {
        servicio.eliminarLogicamente(id);
        return ResponseEntity.ok().build();
    }
}
