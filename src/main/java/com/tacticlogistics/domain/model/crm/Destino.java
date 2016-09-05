package com.tacticlogistics.domain.model.crm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tacticlogistics.domain.model.common.UsoUbicacionType;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.OmsDireccion;

@Entity
@Table(name = "destinos", catalog = "crm")
public class Destino implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_destino", unique = true, nullable = false)
    private Integer id;

    @Column(name = "id_destinatario", nullable = false, insertable = true, updatable = false)
    private Integer destinatarioId;

    @Column(nullable = false, length = 20)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_uso_ubicacion", nullable = false, length = 20)
    private UsoUbicacionType usoUbicacionType;

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
    public Destino() {
        setActivo(true);
    }

    public Integer getId() {
        return id;
    }

    public Integer getDestinatarioId() {
        return destinatarioId;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public UsoUbicacionType getUsoUbicacionType() {
        return usoUbicacionType;
    }

    public OmsDireccion getDireccion() {
        if(direccion == null){
            direccion = new OmsDireccion();
        }
        return direccion;
    }

    public Contacto getContacto() {
        if(contacto == null){
            contacto = new Contacto();
        }
        return contacto;
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

    public void setDestinatarioId(Integer destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setUsoUbicacionType(UsoUbicacionType usoUbicacionType) {
        this.usoUbicacionType = usoUbicacionType;
    }

    public void setDireccion(OmsDireccion direccion) {
        this.direccion = direccion;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    protected void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        result = prime * result + ((destinatarioId == null) ? 0 : destinatarioId.hashCode());
        result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
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
        Destino other = (Destino) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        if (destinatarioId == null) {
            if (other.destinatarioId != null)
                return false;
        } else if (!destinatarioId.equals(other.destinatarioId))
            return false;
        if (direccion == null) {
            if (other.direccion != null)
                return false;
        } else if (!direccion.equals(other.direccion))
            return false;
        return true;
    }

    @Override
	public String toString() {
		return "Destino [" + (id != null ? "id=" + id + ", " : "")
				+ (destinatarioId != null ? "destinatarioId=" + destinatarioId + ", " : "")
				+ (codigo != null ? "codigo=" + codigo + ", " : "") + (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (usoUbicacionType != null ? "usoUbicacionType=" + usoUbicacionType + ", " : "")
				+ (contacto != null ? "contacto=" + contacto + ", " : "")
				+ (direccion != null ? "direccion=" + direccion + ", " : "") + "activo=" + activo + ", "
				+ (fechaActualizacion != null ? "fechaActualizacion=" + fechaActualizacion + ", " : "")
				+ (usuarioActualizacion != null ? "usuarioActualizacion=" + usuarioActualizacion : "") + "]";
	}
}
