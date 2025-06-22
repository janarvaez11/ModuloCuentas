package com.banquito.core.cuentas.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "comisiones_cargos")
public class ComisionesCargos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comision_cargo", nullable = false)
    private Integer id;

    @Column(name = "tipo_comision", nullable = false, length = 25)
    private String tipoComision;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio", nullable = false)
    private ServiciosAsociados idServicio;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "base_calculo", nullable = false, length = 15)
    private String baseCalculo;

    @Column(name = "monto", nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(name = "frecuencia", nullable = false, length = 15)
    private String frecuencia;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @OneToMany(mappedBy = "idComisionCargo")
    private Set<ComisionesCargosCuentas> comisionesCargosCuentas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idComision")
    private Set<ExencionesCuentas> exencionesCuentas = new LinkedHashSet<>();

    public ComisionesCargos() {}

    public ComisionesCargos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoComision() {
        return tipoComision;
    }

    public void setTipoComision(String tipoComision) {
        this.tipoComision = tipoComision;
    }

    public ServiciosAsociados getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(ServiciosAsociados idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBaseCalculo() {
        return baseCalculo;
    }

    public void setBaseCalculo(String baseCalculo) {
        this.baseCalculo = baseCalculo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
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

    public Set<ComisionesCargosCuentas> getComisionesCargosCuentas() {
        return comisionesCargosCuentas;
    }

    public void setComisionesCargosCuentas(Set<ComisionesCargosCuentas> comisionesCargosCuentas) {
        this.comisionesCargosCuentas = comisionesCargosCuentas;
    }

    public Set<ExencionesCuentas> getExencionesCuentas() {
        return exencionesCuentas;
    }

    public void setExencionesCuentas(Set<ExencionesCuentas> exencionesCuentas) {
        this.exencionesCuentas = exencionesCuentas;
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
        ComisionesCargos other = (ComisionesCargos) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ComisionesCargos [id=" + id + ", tipoComision=" + tipoComision + ", idServicio=" + idServicio + ", nombre=" + nombre + ", baseCalculo=" + baseCalculo + ", monto=" + monto + ", frecuencia=" + frecuencia + ", estado=" + estado + ", version=" + version + "]";
    }

}