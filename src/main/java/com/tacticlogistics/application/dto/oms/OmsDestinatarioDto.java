package com.tacticlogistics.application.dto.oms;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.util.Date;
import java.util.List;

import com.tacticlogistics.domain.model.common.IdentificacionType;

public class OmsDestinatarioDto {
    private Integer id;

    private Integer clienteId;
    private String clienteCodigo;
    private String clienteNombre;

    private Integer canalId;
    private String canalCodigo;
    private String canalNombre;

    private String codigo;
    private IdentificacionType identificacionType;
    private String numeroIdentificacion;
    private String digitoVerificacion;
    private String nombre;
    private String nombreComercial;

    private Integer direccionCiudadId;
    private String direccionCiudadNombre;
    private String direccionDireccion;
    private String direccionIndicacionesDireccion;

    private String contactoNombres;
    private String contactoEmail;
    private String contactoTelefonos;

    private boolean activo;

    private Date fechaActualizacion;
    private String usuarioActualizacion;

    // ---------------------------------------------------------------------------------------------------------
    public OmsDestinatarioDto() {
        super();
        this.setClienteCodigo("");
        this.setClienteNombre("");
        this.setCanalCodigo("");
        this.setCanalNombre("");
        this.setCodigo("");
        this.setIdentificacionType(IdentificacionType.NI);
        this.setNumeroIdentificacion("");
        this.setDigitoVerificacion("");
        this.setNombre("");
        this.setNombreComercial("");
        this.setDireccionCiudadNombre("");
        this.setDireccionDireccion("");
        this.setDireccionIndicacionesDireccion("");
        this.setContactoNombres("");
        this.setContactoEmail("");
        this.setContactoTelefonos("");
        this.setActivo(true);
        this.setUsuarioActualizacion("");
    }

    // ---------------------------------------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public String getClienteCodigo() {
        return clienteCodigo;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public Integer getCanalId() {
        return canalId;
    }

    public String getCanalCodigo() {
        return canalCodigo;
    }

    public String getCanalNombre() {
        return canalNombre;
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

    public Integer getDireccionCiudadId() {
        return direccionCiudadId;
    }

    public String getDireccionCiudadNombre() {
        return direccionCiudadNombre;
    }

    public String getDireccionDireccion() {
        return direccionDireccion;
    }

    public String getDireccionIndicacionesDireccion() {
        return direccionIndicacionesDireccion;
    }

    public String getContactoNombres() {
        return contactoNombres;
    }

    public String getContactoEmail() {
        return contactoEmail;
    }

    public String getContactoTelefonos() {
        return contactoTelefonos;
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
    public void setId(Integer value) {
        this.id = value;
    }

    public void setClienteId(Integer value) {
        this.clienteId = value;
    }

    public void setClienteCodigo(String value) {
        this.clienteCodigo = coalesce(value, "");
    }

    public void setClienteNombre(String value) {
        this.clienteNombre = coalesce(value, "");
    }

    public void setCanalId(Integer value) {
        this.canalId = value;
    }

    public void setCanalCodigo(String value) {
        this.canalCodigo = coalesce(value, "");
    }

    public void setCanalNombre(String value) {
        this.canalNombre = coalesce(value, "");
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

    public void setDireccionCiudadId(Integer value) {
        this.direccionCiudadId = value;
    }

    public void setDireccionCiudadNombre(String value) {
        this.direccionCiudadNombre = coalesce(value, "");
    }

    public void setDireccionDireccion(String value) {
        this.direccionDireccion = coalesce(value, "");
    }

    public void setDireccionIndicacionesDireccion(String value) {
        this.direccionIndicacionesDireccion = coalesce(value, "");
    }

    public void setContactoNombres(String value) {
        this.contactoNombres = coalesce(value, "");
    }

    public void setContactoEmail(String value) {
        this.contactoEmail = coalesce(value, "");
    }

    public void setContactoTelefonos(String value) {
        this.contactoTelefonos = coalesce(value, "");
    }

    public void setActivo(boolean value) {
        this.activo = value;
    }

    public void setFechaActualizacion(Date value) {
        this.fechaActualizacion = value;
    }

    public void setUsuarioActualizacion(String value) {
        this.usuarioActualizacion = coalesce(value, "");
    }

    // ---------------------------------------------------------------------------------------------------------
    private List<Integer> canales;

    public List<Integer> getSegmentos() {
        return canales;
    }

    public void setSegmentos(List<Integer> value) {
        this.canales = value;
    }

    // ---------------------------------------------------------------------------------------------------------
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
        OmsDestinatarioDto other = (OmsDestinatarioDto) obj;
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
		return "OmsDestinatarioDto [" + (id != null ? "id=" + id + ", " : "")
				+ (clienteId != null ? "clienteId=" + clienteId + ", " : "")
				+ (clienteCodigo != null ? "clienteCodigo=" + clienteCodigo + ", " : "")
				+ (clienteNombre != null ? "clienteNombre=" + clienteNombre + ", " : "")
				+ (canalId != null ? "canalId=" + canalId + ", " : "")
				+ (canalCodigo != null ? "canalCodigo=" + canalCodigo + ", " : "")
				+ (canalNombre != null ? "canalNombre=" + canalNombre + ", " : "")
				+ (codigo != null ? "codigo=" + codigo + ", " : "")
				+ (identificacionType != null ? "identificacionType=" + identificacionType + ", " : "")
				+ (numeroIdentificacion != null ? "numeroIdentificacion=" + numeroIdentificacion + ", " : "")
				+ (digitoVerificacion != null ? "digitoVerificacion=" + digitoVerificacion + ", " : "")
				+ (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (nombreComercial != null ? "nombreComercial=" + nombreComercial + ", " : "")
				+ (direccionCiudadId != null ? "direccionCiudadId=" + direccionCiudadId + ", " : "")
				+ (direccionCiudadNombre != null ? "direccionCiudadNombre=" + direccionCiudadNombre + ", " : "")
				+ (direccionDireccion != null ? "direccionDireccion=" + direccionDireccion + ", " : "")
				+ (direccionIndicacionesDireccion != null
						? "direccionIndicacionesDireccion=" + direccionIndicacionesDireccion + ", " : "")
				+ (contactoNombres != null ? "contactoNombres=" + contactoNombres + ", " : "")
				+ (contactoEmail != null ? "contactoEmail=" + contactoEmail + ", " : "")
				+ (contactoTelefonos != null ? "contactoTelefonos=" + contactoTelefonos + ", " : "") + "activo="
				+ activo + ", " + (fechaActualizacion != null ? "fechaActualizacion=" + fechaActualizacion + ", " : "")
				+ (usuarioActualizacion != null ? "usuarioActualizacion=" + usuarioActualizacion + ", " : "")
				+ (canales != null ? "canales=" + canales : "") + "]";
	}
}
