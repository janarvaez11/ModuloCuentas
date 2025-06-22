package com.banquito.core.cuentas.modelo;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tasas_plazos")
public class TasasPlazos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plazo", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tasa_interes", nullable = false)
    private TasasIntereses idTasaInteres;

    @Column(name = "plazo_minimo", nullable = false, precision = 15, scale = 2)
    private BigDecimal plazoMinimo;

    @Column(name = "plazo_maximo", nullable = false, precision = 15, scale = 2)
    private BigDecimal plazoMaximo;

    @Column(name = "tasa", nullable = false, precision = 15, scale = 2)
    private BigDecimal tasa;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    public TasasPlazos() {
    }

    public TasasPlazos(Integer id) {
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

    public BigDecimal getPlazoMinimo() {
        return plazoMinimo;
    }

    public void setPlazoMinimo(BigDecimal plazoMinimo) {
        this.plazoMinimo = plazoMinimo;
    }

    public BigDecimal getPlazoMaximo() {
        return plazoMaximo;
    }

    public void setPlazoMaximo(BigDecimal plazoMaximo) {
        this.plazoMaximo = plazoMaximo;
    }

    public BigDecimal getTasa() {
        return tasa;
    }

    public void setTasa(BigDecimal tasa) {
        this.tasa = tasa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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
        TasasPlazos other = (TasasPlazos) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TasasPlazos [id=" + id + ", idTasaInteres=" + idTasaInteres + ", plazoMinimo=" + plazoMinimo + ", plazoMaximo=" + plazoMaximo + ", tasa=" + tasa + ", estado=" + estado + ", version=" + version + "]";
    }

}