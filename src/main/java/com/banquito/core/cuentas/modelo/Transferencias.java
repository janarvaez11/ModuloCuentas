package com.banquito.core.cuentas.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.banquito.core.cuentas.enums.EstadoEspecificoTransaccionEnum;

@Entity
@Table(name = "transferencias", schema = "cuentas")
public class Transferencias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transferencia", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_transaccion", nullable = false)
    private Transacciones idTransaccion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_cuenta_cliente_destino", nullable = false)
    private CuentasClientes idCuentaClienteDestino;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoEspecificoTransaccionEnum estado = EstadoEspecificoTransaccionEnum.ENVIADO;

    @Version
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    public Transferencias() {
    }

    public Transferencias(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Transacciones getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Transacciones idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public CuentasClientes getIdCuentaClienteDestino() {
        return idCuentaClienteDestino;
    }

    public void setIdCuentaClienteDestino(CuentasClientes idCuentaClienteDestino) {
        this.idCuentaClienteDestino = idCuentaClienteDestino;
    }

    public EstadoEspecificoTransaccionEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoEspecificoTransaccionEnum estado) {
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
        Transferencias other = (Transferencias) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Transferencias [id=" + id + ", idTransaccion=" + idTransaccion + ", idCuentaClienteDestino="
                + idCuentaClienteDestino + ", estado=" + estado + ", version=" + version + "]";
    }

}