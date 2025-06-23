package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.modelo.Transferencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransferenciasRepositorio extends JpaRepository<Transferencias, Integer> {

    Optional<Transferencias> findByIdTransaccion_Id(Integer idTransaccionId);

    // Puede ser útil para obtener todas las transferencias de una cuenta cliente destino
    List<Transferencias> findByIdCuentaClienteDestino_Id(Integer idCuentaClienteDestino);
}