package com.banquito.core.cuentas.modelo;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;


import com.banquito.core.cuentas.enums.BaseCalculoTasaEnum;
import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;
import com.banquito.core.cuentas.enums.FrecuenciaEnum;
import com.banquito.core.cuentas.enums.MetodoCalculoTasaEnum;

@Entity
@Table(name = "tasas_intereses", schema = "cuentas")
public class TasasIntereses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tasa_interes", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "base_calculo", nullable = false, length = 15)
    private BaseCalculoTasaEnum baseCalculo;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_calculo", nullable = false, length = 20)
    private MetodoCalculoTasaEnum metodoCalculo;

    @Enumerated(EnumType.STRING)
    @Column(name = "frecuencia_capitalizacion", nullable = false, length = 15)
    private FrecuenciaEnum frecuenciaCapitalizacion;

    @Column(name = "fecha_inicio_vigencia", nullable = false)
    private LocalDate fechaInicioVigencia;

    @Column(name = "fecha_fin_vigencia", nullable = false)
    private LocalDate fechaFinVigencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoGeneralCuentasEnum estado = EstadoGeneralCuentasEnum.ACTIVO;

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

    public TasasIntereses() {
    }

    public TasasIntereses(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BaseCalculoTasaEnum getBaseCalculo() {
        return baseCalculo;
    }

    public void setBaseCalculo(BaseCalculoTasaEnum baseCalculo) {
        this.baseCalculo = baseCalculo;
    }

    public MetodoCalculoTasaEnum getMetodoCalculo() {
        return metodoCalculo;
    }

    public void setMetodoCalculo(MetodoCalculoTasaEnum metodoCalculo) {
        this.metodoCalculo = metodoCalculo;
    }

    public FrecuenciaEnum getFrecuenciaCapitalizacion() {
        return frecuenciaCapitalizacion;
    }

    public void setFrecuenciaCapitalizacion(FrecuenciaEnum frecuenciaCapitalizacion) {
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
        return "TasasIntereses [id=" + id + ", baseCalculo=" + baseCalculo + ", metodoCalculo=" + metodoCalculo
                + ", frecuenciaCapitalizacion=" + frecuenciaCapitalizacion + ", fechaInicioVigencia="
                + fechaInicioVigencia + ", fechaFinVigencia=" + fechaFinVigencia + ", estado=" + estado + ", version="
                + version + "]";
    }

}