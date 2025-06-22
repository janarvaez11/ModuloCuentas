package com.banquito.core.cuentas.modelo;

//import com.banquito.core.general.modelo.Monedas;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

import com.banquito.core.cuentas.enums.EstadoGeneralCuentasEnum;
import com.banquito.core.cuentas.enums.TipoClienteEnum;

@Entity
@Table(name = "tipos_cuentas")
public class TiposCuentas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_cuenta", nullable = false)
    private Integer id;

    @Column(name = "id_moneda", nullable = false)
    private String idMoneda; // Assuming this is an Integer ID for Monedas, not a full entity reference
    /*
     * @ManyToOne(fetch = FetchType.LAZY)
     * 
     * @JoinColumn(name = "id_moneda", nullable = false)
     * private Monedas idMoneda;
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tasa_interes_por_defecto", nullable = false)
    private TasasIntereses idTasaInteresPorDefecto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @Column(name = "requisitos_apertura", nullable = false, length = 300)
    private String requisitosApertura;

    @Column(name = "tipo_cliente", nullable = false, length = 15)
    private TipoClienteEnum tipoCliente;

    @Column(name = "fecha_creacion", nullable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_modificacion", nullable = false)
    private Instant fechaModificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoGeneralCuentasEnum estado = EstadoGeneralCuentasEnum.ACTIVO;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @OneToMany(mappedBy = "idTipoCuenta")
    private Set<Cuentas> cuentas = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(String idMoneda) {
        this.idMoneda = idMoneda;
    }

    public TasasIntereses getIdTasaInteresPorDefecto() {
        return idTasaInteresPorDefecto;
    }

    public void setIdTasaInteresPorDefecto(TasasIntereses idTasaInteresPorDefecto) {
        this.idTasaInteresPorDefecto = idTasaInteresPorDefecto;
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

    public String getRequisitosApertura() {
        return requisitosApertura;
    }

    public void setRequisitosApertura(String requisitosApertura) {
        this.requisitosApertura = requisitosApertura;
    }

    public TipoClienteEnum getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoClienteEnum tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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
        TiposCuentas other = (TiposCuentas) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TiposCuentas [id=" + id + ", idMoneda=" + idMoneda + ", idTasaInteresPorDefecto="
                + idTasaInteresPorDefecto + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", requisitosApertura=" + requisitosApertura + ", tipoCliente=" + tipoCliente + ", fechaCreacion="
                + fechaCreacion + ", fechaModificacion=" + fechaModificacion + ", estado=" + estado + ", version="
                + version + ", cuentas=" + cuentas + "]";
    }

}