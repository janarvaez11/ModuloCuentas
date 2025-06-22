package com.banquito.core.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.cuentas.modelo.TiposCuentas;

@Repository
public interface TiposCuentasRepositorio extends JpaRepository<TiposCuentas, Integer> {
}