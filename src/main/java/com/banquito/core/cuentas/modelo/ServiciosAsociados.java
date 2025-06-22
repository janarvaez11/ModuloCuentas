package com.banquito.core.cuentas.modelo;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;

@Entity
@Table(name = "servicios_asociados")
public class ServiciosAsociados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoGeneralCuentasEnum estado = EstadoGeneralCuentasEnum.ACTIVO;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @OneToMany(mappedBy = "idServicio")
    private Set<ComisionesCargos> comisionesCargos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idServicio")
    private Set<ServiciosAsociadosCuentas> serviciosAsociadosCuentas = new LinkedHashSet<>();

    public ServiciosAsociados() {
    }

    public ServiciosAsociados(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Set<ComisionesCargos> getComisionesCargos() {
        return comisionesCargos;
    }

    public void setComisionesCargos(Set<ComisionesCargos> comisionesCargos) {
        this.comisionesCargos = comisionesCargos;
    }

    public Set<ServiciosAsociadosCuentas> getServiciosAsociadosCuentas() {
        return serviciosAsociadosCuentas;
    }

    public void setServiciosAsociadosCuentas(Set<ServiciosAsociadosCuentas> serviciosAsociadosCuentas) {
        this.serviciosAsociadosCuentas = serviciosAsociadosCuentas;
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
        ServiciosAsociados other = (ServiciosAsociados) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ServiciosAsociados [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", estado="
                + estado + ", version=" + version + ", comisionesCargos=" + comisionesCargos
                + ", serviciosAsociadosCuentas=" + serviciosAsociadosCuentas + "]";
    }

}