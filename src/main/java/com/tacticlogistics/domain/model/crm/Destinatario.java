package com.tacticlogistics.domain.model.crm;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.OmsDireccion;

@Entity
@Table(name = "destinatarios", catalog = "crm", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_cliente", "numeroIdentificacion" }) })
public class Destinatario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_destinatario", unique = true, nullable = false)
    private Integer id;

    @Column(name = "id_cliente", nullable = false, insertable = true, updatable = false)
    private Integer clienteId;

    @Column(nullable = false, length = 20)
    private String codigo;

    @Column(nullable = false, length = 20)
    private String numeroIdentificacion;

    @Column(nullable = false, length = 1)
    private String digitoVerificacion;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String nombreComercial;

    @Column(name = "id_canal", nullable = false, insertable = true, updatable = false)
    private Integer canalId;

    @Embedded
    private Contacto contacto;

    @Embedded
    private OmsDireccion direccion;

    private boolean activo;

    @Column(nullable = false, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    @Column(nullable = false, length = 50)
    private String usuarioActualizacion;

    // ---------------------------------------------------------------------------------------------------------
    public Destinatario() {
        this.setCodigo("");
        this.setNumeroIdentificacion("");
        this.setDigitoVerificacion("");
        this.setNombre("");
        this.setNombreComercial("");
        this.setActivo(true);
        this.setUsuarioActualizacion("");
    }

    public Integer getId() {
        return id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public String getDigitoVerificacion() {
        return digitoVerificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public Integer getCanalId() {
        return canalId;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public OmsDireccion getDireccion() {
        return direccion;
    }

    public boolean isActivo() {
        return activo;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setId(Integer id) {
        this.id = id;
    }

    public void setClienteId(Integer value) {
        this.clienteId = value;
    }

    public void setCodigo(String value) {
        this.codigo = coalesce(value, "");
    }

    public void setNumeroIdentificacion(String value) {
        this.numeroIdentificacion = coalesce(value, "");
    }

    public void setDigitoVerificacion(String value) {
        this.digitoVerificacion = coalesce(value, "");
    }

    public void setNombre(String value) {
        this.nombre = coalesce(value, "");
    }

    public void setNombreComercial(String value) {
        this.nombreComercial = coalesce(value, "");
    }

    public void setCanalId(Integer value) {
        this.canalId = value;
    }

    public void setContacto(Contacto value) {
        if (value == null) {
            value = new Contacto();
        }
        this.contacto = value;
    }

    public void setDireccion(OmsDireccion value) {
        if (value == null) {
            value = new OmsDireccion();
        }
        this.direccion = value;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setFechaActualizacion(Date value) {
        this.fechaActualizacion = value;
    }

    public void setUsuarioActualizacion(String value) {
        this.usuarioActualizacion = coalesce(value, "");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clienteId == null) ? 0 : clienteId.hashCode());
        result = prime * result + ((numeroIdentificacion == null) ? 0 : numeroIdentificacion.hashCode());
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
        Destinatario other = (Destinatario) obj;
        if (clienteId == null) {
            if (other.clienteId != null)
                return false;
        } else if (!clienteId.equals(other.clienteId))
            return false;
        if (numeroIdentificacion == null) {
            if (other.numeroIdentificacion != null)
                return false;
        } else if (!numeroIdentificacion.equals(other.numeroIdentificacion))
            return false;
        return true;
    }

    @Override
	public String toString() {
		return "Destinatario [" + (id != null ? "id=" + id + ", " : "")
				+ (clienteId != null ? "clienteId=" + clienteId + ", " : "")
				+ (codigo != null ? "codigo=" + codigo + ", " : "")
				+ (numeroIdentificacion != null ? "numeroIdentificacion=" + numeroIdentificacion + ", " : "")
				+ (digitoVerificacion != null ? "digitoVerificacion=" + digitoVerificacion + ", " : "")
				+ (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (nombreComercial != null ? "nombreComercial=" + nombreComercial + ", " : "")
				+ (canalId != null ? "canalId=" + canalId + ", " : "")
				+ (contacto != null ? "contacto=" + contacto + ", " : "")
				+ (direccion != null ? "direccion=" + direccion + ", " : "") + "activo=" + activo + ", "
				+ (fechaActualizacion != null ? "fechaActualizacion=" + fechaActualizacion + ", " : "")
				+ (usuarioActualizacion != null ? "usuarioActualizacion=" + usuarioActualizacion : "") + "]";
	}
}