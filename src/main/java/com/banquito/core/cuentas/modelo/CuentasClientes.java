package com.banquito.core.cuentas.modelo;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

import com.banquito.core.cuentas.enums.EstadoCuentaClienteEnum;

@Entity
@Table(name = "cuentas_clientes", schema = "cuentas")
public class CuentasClientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta_cliente", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuenta", nullable = false)
    private Cuentas idCuenta;

    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente; // Assuming this is an Integer ID for Clientes, not a full entity reference

    @Column(name = "numero_cuenta", nullable = false, length = 10)
    private String numeroCuenta;

    @Column(name = "saldo_disponible", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoDisponible;

    @Column(name = "saldo_contable", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoContable;

    @Column(name = "fecha_apertura", nullable = false)
    private Instant fechaApertura;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoCuentaClienteEnum estado = EstadoCuentaClienteEnum.ACTIVO;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    public CuentasClientes() {
    }

    public CuentasClientes(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cuentas getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Cuentas idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public BigDecimal getSaldoContable() {
        return saldoContable;
    }

    public void setSaldoContable(BigDecimal saldoContable) {
        this.saldoContable = saldoContable;
    }

    public Instant getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Instant fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public EstadoCuentaClienteEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuentaClienteEnum estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CuentasClientes other = (CuentasClientes) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CuentasClientes [id=" + id + ", idCuenta=" + idCuenta + ", idCliente=" + idCliente + ", numeroCuenta="
                + numeroCuenta + ", saldoDisponible=" + saldoDisponible + ", saldoContable=" + saldoContable
                + ", fechaApertura=" + fechaApertura + ", estado=" + estado + ", version=" + version + "]";
    }

}