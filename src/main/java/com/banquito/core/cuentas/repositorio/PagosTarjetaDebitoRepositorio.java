package com.banquito.core.cuentas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.cuentas.modelo.PagosTarjetaDebito;
@Repository
public interface PagosTarjetaDebitoRepositorio extends JpaRepository<PagosTarjetaDebito, Integer> {
    
    // Aquí puedes agregar métodos personalizados si es necesario
    // Por ejemplo, para buscar pagos por estado o fecha específica
    
}
