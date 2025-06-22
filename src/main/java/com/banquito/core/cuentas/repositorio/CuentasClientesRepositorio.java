package com.banquito.core.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.cuentas.modelo.CuentasClientes;

@Repository
public interface CuentasClientesRepositorio extends JpaRepository<CuentasClientes, Integer> {
}