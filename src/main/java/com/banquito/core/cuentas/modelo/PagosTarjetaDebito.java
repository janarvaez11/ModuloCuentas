package com.banquito.core.cuentas.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.banquito.core.cuentas.enums.EstadoEspecificoTransaccionEnum;

@Entity
@Table(name = "pagos_tarjeta_debito")
public class PagosTarjetaDebito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago_tarjeta", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_transaccion", nullable = false)
    private Transacciones idTransaccion;

    @Column(name = "numero_tarjeta", nullable = false, length = 16)
    private String numeroTarjeta;

    @Column(name = "entidad_emisora", nullable = false, length = 100)
    private String entidadEmisora;

    @Column(name = "nombre_comercio", nullable = false, length = 100)
    private String nombreComercio;

    @Column(name = "ubicacion_comercio", nullable = false, length = 150)
    private String ubicacionComercio;

    @Column(name = "autorizacion_codigo", nullable = false, length = 6)
    private String autorizacionCodigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 15)
    private EstadoEspecificoTransaccionEnum estado = EstadoEspecificoTransaccionEnum.ENVIADO;

    @Version
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    public PagosTarjetaDebito() {
    }

    public PagosTarjetaDebito(Integer id) {
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

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getEntidadEmisora() {
        return entidadEmisora;
    }

    public void setEntidadEmisora(String entidadEmisora) {
        this.entidadEmisora = entidadEmisora;
    }

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        this.nombreComercio = nombreComercio;
    }

    public String getUbicacionComercio() {
        return ubicacionComercio;
    }

    public void setUbicacionComercio(String ubicacionComercio) {
        this.ubicacionComercio = ubicacionComercio;
    }

    public String getAutorizacionCodigo() {
        return autorizacionCodigo;
    }

    public void setAutorizacionCodigo(String autorizacionCodigo) {
        this.autorizacionCodigo = autorizacionCodigo;
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
        PagosTarjetaDebito other = (PagosTarjetaDebito) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PagosTarjetaDebito [id=" + id + ", idTransaccion=" + idTransaccion + ", numeroTarjeta=" + numeroTarjeta
                + ", entidadEmisora=" + entidadEmisora + ", nombreComercio=" + nombreComercio + ", ubicacionComercio="
                + ubicacionComercio + ", autorizacionCodigo=" + autorizacionCodigo + ", estado=" + estado + ", version="
                + version + "]";
    }

}