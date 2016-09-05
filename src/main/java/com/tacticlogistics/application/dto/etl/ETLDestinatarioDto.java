package com.tacticlogistics.application.dto.etl;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.util.HashSet;
import java.util.Set;

public class ETLDestinatarioDto {
	private String clienteCodigo;
	private String canalCodigoAlterno;

	private String tipoIdentificacionCodigoAlterno;
	private String numeroIdentificacion;
	private String digitoVerificacion;
	private String codigo;
	private String nombre;
	private String nombreComercial;

	private String ciudadCodigoAlterno;
	private String ciudadNombreAlterno;
	private String direccion;
	private String direccionIndicaciones;

	private String contactoNombres;
	private String contactoEmail;
	private String contactoTelefonos;

	private boolean activo;

	private Set<ETLDestinoOrigenDto> destinosOrigenes;

	public ETLDestinatarioDto() {
		super();
		setClienteCodigo("");
		setSegmentoCodigoAlterno("");

		setTipoIdentificacionCodigoAlterno("");
		setNumeroIdentificacion("");
		setDigitoVerificacion("");
		setCodigo("");
		setNombre("");
		setNombreComercial("");

		setCiudadCodigoAlterno("");
		setCiudadNombreAlterno("");
		setDireccion("");
		setDireccionIndicaciones("");

		setContactoNombres("");
		setContactoEmail("");
		setContactoTelefonos("");

		setActivo(true);

		setDestinosOrigenes(new HashSet<>());
	}

	public String getClienteCodigo() {
		return clienteCodigo;
	}

	public String getCanalCodigoAlterno() {
		return canalCodigoAlterno;
	}

	public String getTipoIdentificacionCodigoAlterno() {
		return tipoIdentificacionCodigoAlterno;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public String getDigitoVerificacion() {
		return digitoVerificacion;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public String getCiudadCodigoAlterno() {
		return ciudadCodigoAlterno;
	}

	public String getCiudadNombreAlterno() {
		return ciudadNombreAlterno;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getDireccionIndicaciones() {
		return direccionIndicaciones;
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

	public Set<ETLDestinoOrigenDto> getDestinosOrigenes() {
		return destinosOrigenes;
	}

	public void setClienteCodigo(String clienteCodigo) {
		this.clienteCodigo = clienteCodigo;
	}

	public void setSegmentoCodigoAlterno(String value) {
		this.canalCodigoAlterno = coalesce(value, "");
	}

	public void setTipoIdentificacionCodigoAlterno(String value) {
		this.tipoIdentificacionCodigoAlterno = coalesce(value, "");
	}

	public void setNumeroIdentificacion(String value) {
		this.numeroIdentificacion = coalesce(value, "");
	}

	public void setDigitoVerificacion(String value) {
		this.digitoVerificacion = coalesce(value, "");
	}

	public void setCodigo(String value) {
		this.codigo = coalesce(value, "");
	}

	public void setNombre(String value) {
		this.nombre = coalesce(value, "");
	}

	public void setNombreComercial(String value) {
		this.nombreComercial = coalesce(value, "");
	}

	public void setCiudadCodigoAlterno(String value) {
		this.ciudadCodigoAlterno = coalesce(value, "");
	}

	public void setCiudadNombreAlterno(String value) {
		this.ciudadNombreAlterno = coalesce(value, "");
	}

	public void setDireccion(String value) {
		this.direccion = coalesce(value, "");
	}

	public void setDireccionIndicaciones(String value) {
		this.direccionIndicaciones = coalesce(value, "");
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

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	protected void setDestinosOrigenes(Set<ETLDestinoOrigenDto> destinosOrigenes) {
		this.destinosOrigenes = destinosOrigenes;
	}

	@Override
	public String toString() {
		return "ETLDestinatarioDto [" + (clienteCodigo != null ? "clienteCodigo=" + clienteCodigo + ", " : "")
				+ (canalCodigoAlterno != null ? "canalCodigoAlterno=" + canalCodigoAlterno + ", " : "")
				+ (tipoIdentificacionCodigoAlterno != null
						? "tipoIdentificacionCodigoAlterno=" + tipoIdentificacionCodigoAlterno + ", " : "")
				+ (numeroIdentificacion != null ? "numeroIdentificacion=" + numeroIdentificacion + ", " : "")
				+ (digitoVerificacion != null ? "digitoVerificacion=" + digitoVerificacion + ", " : "")
				+ (codigo != null ? "codigo=" + codigo + ", " : "") + (nombre != null ? "nombre=" + nombre + ", " : "")
				+ (nombreComercial != null ? "nombreComercial=" + nombreComercial + ", " : "")
				+ (ciudadCodigoAlterno != null ? "ciudadCodigoAlterno=" + ciudadCodigoAlterno + ", " : "")
				+ (ciudadNombreAlterno != null ? "ciudadNombreAlterno=" + ciudadNombreAlterno + ", " : "")
				+ (direccion != null ? "direccion=" + direccion + ", " : "")
				+ (direccionIndicaciones != null ? "direccionIndicaciones=" + direccionIndicaciones + ", " : "")
				+ (contactoNombres != null ? "contactoNombres=" + contactoNombres + ", " : "")
				+ (contactoEmail != null ? "contactoEmail=" + contactoEmail + ", " : "")
				+ (contactoTelefonos != null ? "contactoTelefonos=" + contactoTelefonos + ", " : "") + "activo="
				+ activo + ", " + (destinosOrigenes != null ? "destinosOrigenes=" + destinosOrigenes : "") + "]";
	}
}
