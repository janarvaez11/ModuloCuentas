package com.banquito.core.cuentas.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.banquito.core.cuentas.enums.EstadoEspecificoTransaccionEnum;

import java.time.Instant;

@Entity
@Table(name = "pagos_cheques")
public class PagosCheques {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago_cheque", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_transaccion", nullable = false)
    private Transacciones idTransaccion;

    @Column(name = "numero_cheque", nullable = false, length = 6)
    private String numeroCheque;

    @Column(name = "banco_emisor", nullable = false, length = 50)
    private String bancoEmisor;

    @Column(name = "fecha_emision", nullable = false)
    private Instant fechaEmision;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoEspecificoTransaccionEnum estado = EstadoEspecificoTransaccionEnum.ENVIADO;

    @Version
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    public PagosCheques() {
    }

    public PagosCheques(Integer id) {
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

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public String getBancoEmisor() {
        return bancoEmisor;
    }

    public void setBancoEmisor(String bancoEmisor) {
        this.bancoEmisor = bancoEmisor;
    }

    public Instant getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Instant fechaEmision) {
        this.fechaEmision = fechaEmision;
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
        PagosCheques other = (PagosCheques) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PagosCheques [id=" + id + ", idTransaccion=" + idTransaccion + ", numeroCheque=" + numeroCheque
                + ", bancoEmisor=" + bancoEmisor + ", fechaEmision=" + fechaEmision + ", estado=" + estado
                + ", version=" + version + "]";
    }

}