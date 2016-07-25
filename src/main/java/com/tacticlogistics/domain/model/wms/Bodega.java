package com.tacticlogistics.domain.model.wms;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tacticlogistics.domain.model.common.valueobjects.OmsDireccion;
import com.tacticlogistics.domain.model.crm.ClienteBodegaAssociation;

@Entity
@Table(name = "Bodegas", catalog = "wms")
public class Bodega implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bodega", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false, length = 20, unique = true)
    private String codigo;

    @Column(nullable = false, length = 100, unique = true)
    private String nombre;

    @Embedded
    private OmsDireccion direccion;

    private boolean manejaRenta;

    private boolean activo;

    @Column(nullable = false, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    @Column(nullable = false, length = 50)
    private String usuarioActualizacion;

    // ---------------------------------------------------------------------------------------------------------
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bodegaId")
    private Set<ClienteBodegaAssociation> bodegaClienteAssociation = new HashSet<ClienteBodegaAssociation>(0);

    protected Bodega() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public OmsDireccion getDireccion() {
        if (direccion == null) {
            direccion = new OmsDireccion();
        }
        return direccion;
    }

    public void setDireccion(OmsDireccion value) {
        if (value == null) {
            value = new OmsDireccion();
        }
        this.direccion = value;
    }

    public boolean isManejaRenta() {
        return manejaRenta;
    }

    public void setManejaRenta(boolean manejaRenta) {
        this.manejaRenta = manejaRenta;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    public Set<ClienteBodegaAssociation> getBodegaClienteAssociation() {
        return bodegaClienteAssociation;
    }

    public void setBodegaClienteAssociation(Set<ClienteBodegaAssociation> bodegaClienteAssociation) {
        this.bodegaClienteAssociation = bodegaClienteAssociation;
    }

    @Override
    public String toString() {
        final int maxLen = 5;
        StringBuilder builder = new StringBuilder();
        builder.append("Bodega [");
        if (id != null)
            builder.append("id=").append(id).append(", ");
        if (codigo != null)
            builder.append("codigo=").append(codigo).append(", ");
        if (nombre != null)
            builder.append("nombre=").append(nombre).append(", ");
        if (direccion != null)
            builder.append("direccion=").append(direccion).append(", ");
        if (fechaActualizacion != null)
            builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
        if (usuarioActualizacion != null)
            builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
        if (bodegaClienteAssociation != null)
            builder.append("bodegaClienteAssociation=").append(toString(bodegaClienteAssociation, maxLen));
        builder.append("]");
        return builder.toString();
    }

    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
            if (i > 0)
                builder.append(", ");
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
        Bodega other = (Bodega) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }
}
