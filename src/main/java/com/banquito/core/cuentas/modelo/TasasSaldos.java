package com.banquito.core.cuentas.modelo;

import jakarta.persistence.*;

import java.math.BigDecimal;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;

@Entity
@Table(name = "tasas_saldos")
public class TasasSaldos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_saldo", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tasa_interes", nullable = false)
    private TasasIntereses idTasaInteres;

    @Column(name = "saldo_minimo", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoMinimo;

    @Column(name = "saldo_maximo", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoMaximo;

    @Column(name = "tasa", nullable = false, precision = 15, scale = 2)
    private BigDecimal tasa;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoGeneralCuentasEnum estado = EstadoGeneralCuentasEnum.ACTIVO;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    public TasasSaldos() {
    }

    public TasasSaldos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TasasIntereses getIdTasaInteres() {
        return idTasaInteres;
    }

    public void setIdTasaInteres(TasasIntereses idTasaInteres) {
        this.idTasaInteres = idTasaInteres;
    }

    public BigDecimal getSaldoMinimo() {
        return saldoMinimo;
    }

    public void setSaldoMinimo(BigDecimal saldoMinimo) {
        this.saldoMinimo = saldoMinimo;
    }

    public BigDecimal getSaldoMaximo() {
        return saldoMaximo;
    }

    public void setSaldoMaximo(BigDecimal saldoMaximo) {
        this.saldoMaximo = saldoMaximo;
    }

    public BigDecimal getTasa() {
        return tasa;
    }

    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    public EstadoGeneralCuentasEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoGeneralCuentasEnum estado) {
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
        TasasSaldos other = (TasasSaldos) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TasasSaldos [id=" + id + ", idTasaInteres=" + idTasaInteres + ", saldoMinimo=" + saldoMinimo
                + ", saldoMaximo=" + saldoMaximo + ", tasa=" + tasa + ", estado=" + estado + ", version=" + version
                + "]";
    }

}