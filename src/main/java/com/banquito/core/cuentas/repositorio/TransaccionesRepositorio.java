package com.banquito.core.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.cuentas.modelo.Transacciones;

@Repository
public interface TransaccionesRepositorio extends JpaRepository<Transacciones, Integer> {
    
}
