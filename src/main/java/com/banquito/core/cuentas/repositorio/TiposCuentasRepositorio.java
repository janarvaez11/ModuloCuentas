package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;
import com.banquito.core.cuentas.modelo.TiposCuentas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TiposCuentasRepositorio extends JpaRepository<TiposCuentas, Integer> {

    Optional<TiposCuentas> findByNombre(String nombre);
    List<TiposCuentas> findByEstado(EstadoGeneralCuentasEnum estado);
    List<TiposCuentas> findByNombreContainingIgnoreCase(String nombre); // Para buscar por nombre
}