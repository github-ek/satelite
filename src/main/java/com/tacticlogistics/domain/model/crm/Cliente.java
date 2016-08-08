package com.tacticlogistics.domain.model.crm;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
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

import com.tacticlogistics.domain.model.common.IdentificacionType;
import com.tacticlogistics.domain.model.notificaciones.Suscriptor;
import com.tacticlogistics.domain.model.requerimientos.RequerimientoClienteAssociation;

@Entity
@Table(name = "Clientes", catalog = "crm")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false, length = 20, unique = true)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String codigoAlternoWms;

    @Column(nullable = false, length = 2)
    @Enumerated(EnumType.STRING)
    private IdentificacionType identificacionType;

    @Column(nullable = false, length = 20, unique = true)
    private String numeroIdentificacion;

    @Column(nullable = false, length = 1)
    private String digitoVerificacion;

    @Column(nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(nullable = false, length = 100, unique = true)
    private String nombreComercial;

    private boolean activo;

    @Column(nullable = false, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    @Column(nullable = false, length = 50)
    private String usuarioActualizacion;

    // ---------------------------------------------------------------------------------------------------------
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteId")
    private Set<ClienteTipoServicioAssociation> clienteTipoServicioAssociation = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteId")
    private Set<ClienteCanalAssociation> clienteCanalAssociation = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteId")
    private Set<DestinatarioRemitente> destinatariosRemitentes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteId")
    private Set<ClienteCiudadAssociation> clienteCiudadAssociation = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteId")
    private Set<ClienteBodegaAssociation> clienteBodegaAssociation = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteId")
    private Set<ClienteUnidadAssociation> clienteUnidadAssociation = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteId")
    private Set<ClienteRequerimientoDistribucionAssociation> clienteRequerimientoDistribucionAssociation = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteId")
    private Set<ClienteRequerimientoAlistamientoAssociation> clienteRequerimientoAlistamientoAssociation = new HashSet<>();

    // ---------------------------------------------------------------------------------------------------------
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private Set<Suscriptor> suscriptores = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private Set<RequerimientoClienteAssociation> requerimientoClienteAssociation = new HashSet<>();

    // ---------------------------------------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCodigoAlternoWms() {
        return codigoAlternoWms;
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

    public boolean isActivo() {
        return activo;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public Set<ClienteTipoServicioAssociation> getClienteTipoServicioAssociation() {
        return clienteTipoServicioAssociation;
    }

    public Set<ClienteCanalAssociation> getClienteCanalAssociation() {
        return clienteCanalAssociation;
    }

    public Set<DestinatarioRemitente> getDestinatariosRemitentes() {
        return destinatariosRemitentes;
    }

    public Set<ClienteCiudadAssociation> getClienteCiudadAssociation() {
        return clienteCiudadAssociation;
    }

    public Set<ClienteBodegaAssociation> getClienteBodegaAssociation() {
        return clienteBodegaAssociation;
    }

    public Set<ClienteUnidadAssociation> getClienteUnidadAssociation() {
        return clienteUnidadAssociation;
    }

    public Set<ClienteRequerimientoDistribucionAssociation> getClienteRequerimientoDistribucionAssociation() {
        return clienteRequerimientoDistribucionAssociation;
    }

    public Set<ClienteRequerimientoAlistamientoAssociation> getClienteRequerimientoAlistamientoAssociation() {
        return clienteRequerimientoAlistamientoAssociation;
    }

    public Set<Suscriptor> getSuscriptores() {
        return suscriptores;
    }

    public Set<RequerimientoClienteAssociation> getRequerimientoClienteAssociation() {
        return requerimientoClienteAssociation;
    }

    // ---------------------------------------------------------------------------------------------------------
    protected Cliente() {
        super();

        this.setCodigo("");
        this.setCodigoAlternoWms("");
        this.setNumeroIdentificacion("");
        this.setDigitoVerificacion("");
        this.setNombre("");
        this.setNombreComercial("");
        this.setActivo(true);
        this.setFechaActualizacion(new Date());
        this.setUsuarioActualizacion("");

        this.setClienteTipoServicioAssociation(new HashSet<>());
        this.setClienteCanalAssociation(new HashSet<>());
        this.setDestinatariosRemitentes(new HashSet<>());
        this.setClienteCiudadAssociation(new HashSet<>());
        this.setClienteBodegaAssociation(new HashSet<>());
        this.setClienteUnidadAssociation(new HashSet<>());
        this.setClienteRequerimientoDistribucionAssociation(new HashSet<>());
        this.setClienteRequerimientoAlistamientoAssociation(new HashSet<>());
        this.setSuscriptores(new HashSet<>());
        this.setRequerimientoClienteAssociation(new HashSet<>());
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    protected void setCodigo(String value) {
        this.codigo = coalesce(value, "");
    }

    protected void setCodigoAlternoWms(String value) {
        this.codigoAlternoWms = coalesce(value, "");
    }

    protected void setIdentificacionType(IdentificacionType identificacionType) {
        this.identificacionType = identificacionType;
    }

    protected void setNumeroIdentificacion(String value) {
        this.numeroIdentificacion = coalesce(value, "");
    }

    protected void setDigitoVerificacion(String value) {
        this.digitoVerificacion = coalesce(value, "");
    }

    protected void setNombre(String value) {
        this.nombre = coalesce(value, "");
    }

    protected void setNombreComercial(String value) {
        this.nombreComercial = coalesce(value, "");
    }

    protected void setActivo(boolean activo) {
        this.activo = activo;
    }

    protected void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    protected void setUsuarioActualizacion(String value) {
        this.usuarioActualizacion = coalesce(value, "");
    }

    // ---------------------------------------------------------------------------------------------------------
    protected void setClienteTipoServicioAssociation(Set<ClienteTipoServicioAssociation> set) {
        if (set == null) {
            set = new HashSet<>();
        }
        this.clienteTipoServicioAssociation = set;
    }

    protected void setClienteCanalAssociation(Set<ClienteCanalAssociation> set) {
        if (set == null) {
            set = new HashSet<>();
        }
        this.clienteCanalAssociation = set;
    }

    protected void setDestinatariosRemitentes(Set<DestinatarioRemitente> set) {
        if (set == null) {
            set = new HashSet<>();
        }
        this.destinatariosRemitentes = set;
    }

    protected void setClienteCiudadAssociation(Set<ClienteCiudadAssociation> set) {
        if (set == null) {
            set = new HashSet<>();
        }
        this.clienteCiudadAssociation = set;
    }

    protected void setClienteBodegaAssociation(Set<ClienteBodegaAssociation> set) {
        if (set == null) {
            set = new HashSet<>();
        }
        this.clienteBodegaAssociation = set;
    }

    protected void setClienteUnidadAssociation(Set<ClienteUnidadAssociation> set) {
        if (set == null) {
            set = new HashSet<>();
        }
        this.clienteUnidadAssociation = set;
    }

    protected void setClienteRequerimientoDistribucionAssociation(
            Set<ClienteRequerimientoDistribucionAssociation> set) {
        if (set == null) {
            set = new HashSet<>();
        }
        this.clienteRequerimientoDistribucionAssociation = set;
    }

    protected void setClienteRequerimientoAlistamientoAssociation(Set<ClienteRequerimientoAlistamientoAssociation> set) {
        if (set == null) {
            set = new HashSet<>();
        }
        this.clienteRequerimientoAlistamientoAssociation = set;
    }

    protected void setSuscriptores(Set<Suscriptor> set) {
        if (set == null) {
            set = new HashSet<>();
        }
        this.suscriptores = set;
    }

    protected void setRequerimientoClienteAssociation(Set<RequerimientoClienteAssociation> set) {
        if (set == null) {
            set = new HashSet<>();
        }
        this.requerimientoClienteAssociation = set;
    }

    // ---------------------------------------------------------------------------------------------------------
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
        Cliente other = (Cliente) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Cliente [");
        if (id != null)
            builder.append("id=").append(id).append(", ");
        if (codigo != null)
            builder.append("codigo=").append(codigo).append(", ");
        if (identificacionType != null)
            builder.append("identificacionType=").append(identificacionType).append(", ");
        if (numeroIdentificacion != null)
            builder.append("numeroIdentificacion=").append(numeroIdentificacion).append(", ");
        if (nombre != null)
            builder.append("nombre=").append(nombre).append(", ");
        builder.append("activo=").append(activo).append("]");
        return builder.toString();
    }

}
