package com.banquito.core.cuentas.modelo;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;

import jakarta.persistence.*;


@Entity
@Table(name = "exenciones_cuentas")
public class ExencionesCuentas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exencion", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comision", nullable = false)
    private ComisionesCargos idComision;

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

    public ExencionesCuentas() {}

    public ExencionesCuentas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ComisionesCargos getIdComision() {
        return idComision;
    }

    public void setIdComision(ComisionesCargos idComision) {
        this.idComision = idComision;
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
        ExencionesCuentas other = (ExencionesCuentas) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ExencionesCuentas [id=" + id + ", idComision=" + idComision + ", nombre=" + nombre + ", descripcion=" + descripcion + ", estado=" + estado + ", version=" + version + "]";
    }

}