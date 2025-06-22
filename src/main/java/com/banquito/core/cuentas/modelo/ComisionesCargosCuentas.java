package com.banquito.core.cuentas.modelo;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "comisiones_cargos_cuentas")
public class ComisionesCargosCuentas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comision_cargo_cuenta", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuenta", nullable = false)
    private Cuentas idCuenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comision_cargo", nullable = false)
    private ComisionesCargos idComisionCargo;

    @Column(name = "fecha_asignacion", nullable = false)
    private Instant fechaAsignacion;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    public ComisionesCargosCuentas() {}

    public ComisionesCargosCuentas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cuentas getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Cuentas idCuenta) {
        this.idCuenta = idCuenta;
    }

    public ComisionesCargos getIdComisionCargo() {
        return idComisionCargo;
    }

    public void setIdComisionCargo(ComisionesCargos idComisionCargo) {
        this.idComisionCargo = idComisionCargo;
    }

    public Instant getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Instant fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
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
        ComisionesCargosCuentas other = (ComisionesCargosCuentas) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ComisionesCargosCuentas [id=" + id + ", idCuenta=" + idCuenta + ", idComisionCargo=" + idComisionCargo + ", fechaAsignacion=" + fechaAsignacion + ", estado=" + estado + ", version=" + version + "]";
    }

}