package com.banquito.core.cuentas.modelo;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tasas_intereses")
public class TasasIntereses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tasa_interes", nullable = false)
    private Integer id;

    @Column(name = "base_calculo", nullable = false, length = 15)
    private String baseCalculo;

    @Column(name = "metodo_calculo", nullable = false, length = 20)
    private String metodoCalculo;

    @Column(name = "frecuencia_capitalizacion", nullable = false, length = 15)
    private String frecuenciaCapitalizacion;

    @Column(name = "fecha_inicio_vigencia", nullable = false)
    private LocalDate fechaInicioVigencia;

    @Column(name = "fecha_fin_vigencia", nullable = false)
    private LocalDate fechaFinVigencia;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @OneToMany(mappedBy = "idTasaInteres")
    private Set<Cuentas> cuentas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idTasaInteres")
    private Set<TasasPlazos> tasasPlazos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idTasaInteres")
    private Set<TasasSaldos> tasasSaldos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idTasaInteresPorDefecto")
    private Set<TiposCuentas> tiposCuentas = new LinkedHashSet<>();

    public TasasIntereses() {}

    public TasasIntereses(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBaseCalculo() {
        return baseCalculo;
    }

    public void setBaseCalculo(String baseCalculo) {
        this.baseCalculo = baseCalculo;
    }

    public String getMetodoCalculo() {
        return metodoCalculo;
    }

    public void setMetodoCalculo(String metodoCalculo) {
        this.metodoCalculo = metodoCalculo;
    }

    public String getFrecuenciaCapitalizacion() {
        return frecuenciaCapitalizacion;
    }

    public void setFrecuenciaCapitalizacion(String frecuenciaCapitalizacion) {
        this.frecuenciaCapitalizacion = frecuenciaCapitalizacion;
    }

    public LocalDate getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(LocalDate fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public LocalDate getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(LocalDate fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
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

    public Set<Cuentas> getCuentas() {
        return cuentas;
    }

    public void setCuentas(Set<Cuentas> cuentas) {
        this.cuentas = cuentas;
    }

    public Set<TasasPlazos> getTasasPlazos() {
        return tasasPlazos;
    }

    public void setTasasPlazos(Set<TasasPlazos> tasasPlazos) {
        this.tasasPlazos = tasasPlazos;
    }

    public Set<TasasSaldos> getTasasSaldos() {
        return tasasSaldos;
    }

    public void setTasasSaldos(Set<TasasSaldos> tasasSaldos) {
        this.tasasSaldos = tasasSaldos;
    }

    public Set<TiposCuentas> getTiposCuentas() {
        return tiposCuentas;
    }

    public void setTiposCuentas(Set<TiposCuentas> tiposCuentas) {
        this.tiposCuentas = tiposCuentas;
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
        TasasIntereses other = (TasasIntereses) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TasasIntereses [id=" + id + ", baseCalculo=" + baseCalculo + ", metodoCalculo=" + metodoCalculo + ", frecuenciaCapitalizacion=" + frecuenciaCapitalizacion + ", fechaInicioVigencia=" + fechaInicioVigencia + ", fechaFinVigencia=" + fechaFinVigencia + ", estado=" + estado + ", version=" + version + "]";
    }

}