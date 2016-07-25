package com.tacticlogistics.domain.model.crm;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.tacticlogistics.domain.model.common.IdentificacionType;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.OmsDireccion;
import com.tacticlogistics.domain.model.wms.DestinatarioRemitenteUnidadAssociation;

@Entity
@Table(name = "destinatarios_remitentes", catalog = "crm", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_cliente", "numeroIdentificacion" }) })
public class DestinatarioRemitente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_destinatario_remitente", unique = true, nullable = false)
    private Integer id;

    @Column(name = "id_cliente", nullable = false, insertable = true, updatable = false)
    private Integer clienteId;

    @Column(name = "id_segmento", nullable = false, insertable = true, updatable = false)
    private Integer canalId;

    @Column(nullable = false, length = 20)
    private String codigo;

    @Column(nullable = false, length = 2)
    @Enumerated(EnumType.STRING)
    private IdentificacionType identificacionType;

    @Column(nullable = false, length = 20)
    private String numeroIdentificacion;

    @Column(nullable = false, length = 1)
    private String digitoVerificacion;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String nombreComercial;

    @Embedded
    private OmsDireccion direccion;

    @Embedded
    private Contacto contacto;

    private boolean activo;

    @Column(nullable = false, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    @Column(nullable = false, length = 50)
    private String usuarioActualizacion;

    // ---------------------------------------------------------------------------------------------------------
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destinatarioRemitenteId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DestinoOrigen> destinosOrigenesAssociation = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destinatarioRemitenteId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DestinatarioRemitenteUnidadAssociation> destinatarioRemitenteUnidadAssociation = new HashSet<>();

    // ---------------------------------------------------------------------------------------------------------
    public DestinatarioRemitente() {
        this.setCodigo("");
        this.setIdentificacionType(IdentificacionType.NI);
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

    public Integer getCanalId() {
        return canalId;
    }

    public String getCodigo() {
        return codigo;
    }

    public IdentificacionType getIdentificacionType() {
        return identificacionType;
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

    public OmsDireccion getDireccion() {
        return direccion;
    }

    public Contacto getContacto() {
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

    public Set<DestinoOrigen> getDestinosOrigenesAssociation() {
        return destinosOrigenesAssociation;
    }

    public Set<DestinatarioRemitenteUnidadAssociation> getDestinatarioRemitenteUnidadAssociation() {
        return destinatarioRemitenteUnidadAssociation;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setId(Integer id) {
        this.id = id;
    }

    // TODO
    public void setClienteId(Integer value) {
        this.clienteId = value;
    }

    public void setCanalId(Integer value) {
        this.canalId = value;
    }

    public void setCodigo(String value) {
        this.codigo = coalesce(value, "");
    }

    public void setIdentificacionType(IdentificacionType value) {
        this.identificacionType = value;
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

    public void setDireccion(OmsDireccion value) {
        if (value == null) {
            value = new OmsDireccion();
        }
        this.direccion = value;
    }

    public void setContacto(Contacto value) {
        if (value == null) {
            value = new Contacto();
        }
        this.contacto = value;
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

    protected void setDestinosOrigenesAssociation(Set<DestinoOrigen> destinosOrigenesAssociation) {
        this.destinosOrigenesAssociation = destinosOrigenesAssociation;
    }

    protected void setDestinatarioRemitenteUnidadAssociation(
            Set<DestinatarioRemitenteUnidadAssociation> destinatarioRemitenteUnidadAssociation) {
        this.destinatarioRemitenteUnidadAssociation = destinatarioRemitenteUnidadAssociation;
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
        DestinatarioRemitente other = (DestinatarioRemitente) obj;
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
        StringBuilder builder = new StringBuilder();
        builder.append("DestinatarioRemitente [");
        if (id != null) {
            builder.append("id=").append(id).append(", ");
        }
        if (clienteId != null) {
            builder.append("clienteId=").append(clienteId).append(", ");
        }
        if (canalId != null) {
            builder.append("canalId=").append(canalId).append(", ");
        }
        if (numeroIdentificacion != null) {
            builder.append("numeroIdentificacion=").append(numeroIdentificacion).append(", ");
        }
        if (nombre != null) {
            builder.append("nombre=").append(nombre);
        }
        builder.append("]");
        return builder.toString();
    }
}