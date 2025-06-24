package com.banquito.core.cuentas.repositorio;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;
import com.banquito.core.cuentas.modelo.Cuentas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentasRepositorio extends JpaRepository<Cuentas, Integer> {

    Optional<Cuentas> findByCodigoCuenta(String codigoCuenta);
    List<Cuentas> findByNombreContainingIgnoreCase(String nombre);
    List<Cuentas> findByEstado(EstadoGeneralCuentasEnum estado);
    List<Cuentas> findByIdTipoCuenta_Id(Integer idTipoCuenta); // Para buscar cuentas por el ID del tipo de cuenta
}