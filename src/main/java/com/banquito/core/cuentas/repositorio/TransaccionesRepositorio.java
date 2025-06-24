package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.modelo.Transacciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TransaccionesRepositorio extends JpaRepository<Transacciones, Integer> {

    List<Transacciones> findByIdCuentaCliente_IdOrderByFechaTransaccionDesc(Integer idCuentaClienteId);

    List<Transacciones> findByIdCuentaCliente_IdAndFechaTransaccionBetweenOrderByFechaTransaccionDesc(
            Integer idCuentaClienteId, Instant fechaInicio, Instant fechaFin);
}