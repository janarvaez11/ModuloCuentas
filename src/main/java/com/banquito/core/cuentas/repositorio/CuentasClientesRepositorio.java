package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.modelo.CuentasClientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CuentasClientesRepositorio extends JpaRepository<CuentasClientes, Integer> {

    Optional<CuentasClientes> findByNumeroCuenta(String numeroCuenta);
    Optional<CuentasClientes> findByIdClienteAndNumeroCuenta(Integer idCliente, String numeroCuenta);
    List<CuentasClientes> findByIdCuenta_Id(Integer idCuenta); // Puede ser útil para listar todas las CuentasClientes de una cuenta específica
}