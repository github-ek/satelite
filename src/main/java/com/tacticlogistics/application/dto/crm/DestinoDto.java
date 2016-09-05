package com.tacticlogistics.application.dto.crm;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.NotImplementedException;

import com.tacticlogistics.domain.model.crm.Destino;

public class DestinoDto  {
    private Integer id;

    private Integer clienteId;
    private String clienteCodigo;
    private String clienteNombre;

    private Integer canalId;
    private String canalCodigo;
    private String canalNombre;

    private Integer destinatarioId;
    private String destinatarioCodigo;
    private String destinatarioNumeroIdentificacion;
    private String destinatarioNombre;

    private String codigo;
    private String nombre;
    private String codigoAlterno;
    private String nombreAlterno;

    private Integer ciudadId;
    private String ciudadNombreAlterno;
    private String direccion;
    private String indicacionesDireccion;
    private BigDecimal longitud;
    private BigDecimal latitud;
    private String direccionEstandarizada;

    private String contactoNombres;
    private String contactoEmail;
    private String contactoTelefonos;

    private Date fechaActualizacion;
    private String usuarioActualizacion;

    
    public DestinoDto() {
        super();
    }

    public DestinoDto(Destino model) {
        super();
        throw new NotImplementedException();
    }
    
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

    public Integer getSegmentoId() {
        return canalId;
    }

    public String getSegmentoCodigo() {
        return canalCodigo;
    }

    public String getSegmentoNombre() {
        return canalNombre;
    }

    public Integer getDestinatarioId() {
        return destinatarioId;
    }

    public String getDestinatarioCodigo() {
        return destinatarioCodigo;
    }

    public String getDestinatarioNumeroIdentificacion() {
        return destinatarioNumeroIdentificacion;
    }

    public String getDestinatarioNombre() {
        return destinatarioNombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigoAlterno() {
        return codigoAlterno;
    }

    public String getNombreAlterno() {
        return nombreAlterno;
    }

    public Integer getCiudadId() {
        return ciudadId;
    }

    public String getCiudadNombreAlterno() {
        return ciudadNombreAlterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getIndicacionesDireccion() {
        return indicacionesDireccion;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public String getDireccionEstandarizada() {
        return direccionEstandarizada;
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

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public void setClienteCodigo(String clienteCodigo) {
        this.clienteCodigo = clienteCodigo;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public void setSegmentoId(Integer canalId) {
        this.canalId = canalId;
    }

    public void setSegmentoCodigo(String canalCodigo) {
        this.canalCodigo = canalCodigo;
    }

    public void setSegmentoNombre(String canalNombre) {
        this.canalNombre = canalNombre;
    }

    public void setDestinatarioId(Integer destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public void setDestinatarioCodigo(String destinatarioCodigo) {
        this.destinatarioCodigo = destinatarioCodigo;
    }

    public void setDestinatarioNumeroIdentificacion(String destinatarioNumeroIdentificacion) {
        this.destinatarioNumeroIdentificacion = destinatarioNumeroIdentificacion;
    }

    public void setDestinatarioNombre(String destinatarioNombre) {
        this.destinatarioNombre = destinatarioNombre;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigoAlterno(String codigoAlterno) {
        this.codigoAlterno = codigoAlterno;
    }

    public void setNombreAlterno(String nombreAlterno) {
        this.nombreAlterno = nombreAlterno;
    }

    public void setCiudadId(Integer ciudadId) {
        this.ciudadId = ciudadId;
    }

    public void setCiudadNombreAlterno(String ciudadNombreAlterno) {
        this.ciudadNombreAlterno = ciudadNombreAlterno;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setIndicacionesDireccion(String indicacionesDireccion) {
        this.indicacionesDireccion = indicacionesDireccion;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public void setDireccionEstandarizada(String direccionEstandarizada) {
        this.direccionEstandarizada = direccionEstandarizada;
    }

    public void setContactoNombres(String contactoNombres) {
        this.contactoNombres = contactoNombres;
    }

    public void setContactoEmail(String contactoEmail) {
        this.contactoEmail = contactoEmail;
    }

    public void setContactoTelefonos(String contactoTelefonos) {
        this.contactoTelefonos = contactoTelefonos;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    @Override
	public String toString() {
		return "DestinoDto [" + (id != null ? "id=" + id + ", " : "")
				+ (clienteId != null ? "clienteId=" + clienteId + ", " : "")
				+ (clienteCodigo != null ? "clienteCodigo=" + clienteCodigo + ", " : "")
				+ (clienteNombre != null ? "clienteNombre=" + clienteNombre + ", " : "")
				+ (canalId != null ? "canalId=" + canalId + ", " : "")
				+ (canalCodigo != null ? "canalCodigo=" + canalCodigo + ", " : "")
				+ (canalNombre != null ? "canalNombre=" + canalNombre + ", " : "")
				+ (destinatarioId != null ? "destinatarioId=" + destinatarioId + ", " : "")
				+ (destinatarioCodigo != null ? "destinatarioCodigo=" + destinatarioCodigo + ", " : "")
				+ (destinatarioNumeroIdentificacion != null
						? "destinatarioNumeroIdentificacion=" + destinatarioNumeroIdentificacion + ", " : "")
				+ (destinatarioNombre != null ? "destinatarioNombre=" + destinatarioNombre + ", " : "")
				+ (codigo != null ? "codigo=" + codigo + ", " : "") + (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (codigoAlterno != null ? "codigoAlterno=" + codigoAlterno + ", " : "")
				+ (nombreAlterno != null ? "nombreAlterno=" + nombreAlterno + ", " : "")
				+ (ciudadId != null ? "ciudadId=" + ciudadId + ", " : "")
				+ (ciudadNombreAlterno != null ? "ciudadNombreAlterno=" + ciudadNombreAlterno + ", " : "")
				+ (direccion != null ? "direccion=" + direccion + ", " : "")
				+ (indicacionesDireccion != null ? "indicacionesDireccion=" + indicacionesDireccion + ", " : "")
				+ (longitud != null ? "longitud=" + longitud + ", " : "")
				+ (latitud != null ? "latitud=" + latitud + ", " : "")
				+ (direccionEstandarizada != null ? "direccionEstandarizada=" + direccionEstandarizada + ", " : "")
				+ (contactoNombres != null ? "contactoNombres=" + contactoNombres + ", " : "")
				+ (contactoEmail != null ? "contactoEmail=" + contactoEmail + ", " : "")
				+ (contactoTelefonos != null ? "contactoTelefonos=" + contactoTelefonos + ", " : "")
				+ (fechaActualizacion != null ? "fechaActualizacion=" + fechaActualizacion + ", " : "")
				+ (usuarioActualizacion != null ? "usuarioActualizacion=" + usuarioActualizacion : "") + "]";
	}
}
