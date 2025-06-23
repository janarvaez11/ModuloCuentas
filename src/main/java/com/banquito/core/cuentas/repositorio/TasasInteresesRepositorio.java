package com.banquito.core.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;
import com.banquito.core.cuentas.modelo.TasasIntereses;

import java.util.List;


@Repository
public interface TasasInteresesRepositorio extends JpaRepository<TasasIntereses, Integer> {

        List<TasasIntereses> findByEstado(EstadoGeneralCuentasEnum estado);

}