package com.banquito.core.cuentas.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.banquito.core.cuentas.enums.EstadoTransaccionesEnum;
import com.banquito.core.cuentas.enums.TipoTransaccionEnum;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "transacciones", schema = "cuentas")
public class Transacciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_cuenta_cliente")
    private CuentasClientes idCuentaCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transaccion", nullable = false, length = 20)
    private TipoTransaccionEnum tipoTransaccion;

    @Column(name = "monto", nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @Column(name = "fecha_transaccion", nullable = false)
    private Instant fechaTransaccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoTransaccionesEnum estado = EstadoTransaccionesEnum.PENDIENTE;

    @Version
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    @OneToMany(mappedBy = "idTransaccion")
    private Set<Transferencias> transferencias = new LinkedHashSet<>();

    public Transacciones() {
    }

    public Transacciones(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CuentasClientes getIdCuentaCliente() {
        return idCuentaCliente;
    }

    public void setIdCuentaCliente(CuentasClientes idCuentaCliente) {
        this.idCuentaCliente = idCuentaCliente;
    }

    public TipoTransaccionEnum getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(TipoTransaccionEnum tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instant getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(Instant fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    public EstadoTransaccionesEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoTransaccionesEnum estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Set<Transferencias> getTransferencias() {
        return transferencias;
    }

    public void setTransferencias(Set<Transferencias> transferencias) {
        this.transferencias = transferencias;
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
        Transacciones other = (Transacciones) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Transacciones [id=" + id + ", idCuentaCliente=" + idCuentaCliente + ", tipoTransaccion="
                + tipoTransaccion + ", monto=" + monto + ", descripcion=" + descripcion + ", fechaTransaccion="
                + fechaTransaccion + ", estado=" + estado + ", version=" + version + ", transferencias="
                + transferencias + "]";
    }



}