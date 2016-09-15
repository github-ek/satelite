package com.tacticlogistics.application.dto.etl;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;
import static com.tacticlogistics.infrastructure.services.Basic.substringSafe;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.tacticlogistics.domain.model.common.valueobjects.Contacto;

public class ETLOrdenDto {
	private String numeroOrden;
	private LocalDate fechaOrden;
	private String numeroOrdenCompra;

	private String clienteCodigo;
	private String tipoServicioCodigo;
	private String tipoServicioCodigoAlterno;

	private boolean requiereServicioDistribucion;

	private String destinoCiudadNombreAlterno;
	private String destinoDireccion;
	private String destinoIndicaciones;

	private String origenCiudadNombreAlterno;
	private String origenDireccion;
	private String origenIndicaciones;

	private boolean requiereConfirmacionCitaEntrega;
	private LocalDate fechaEntregaSugeridaMinima;
	private LocalDate fechaEntregaSugeridaMaxima;
	private LocalTime horaEntregaSugeridaMinima;
	private LocalTime horaEntregaSugeridaMaxima;

	private boolean requiereConfirmacionCitaRecogida;
	private LocalDate fechaRecogidaSugeridaMinima;
	private LocalDate fechaRecogidaSugeridaMaxima;
	private LocalTime horaRecogidaSugeridaMinima;
	private LocalTime horaRecogidaSugeridaMaxima;

	private String canalCodigoAlterno;

	private String destinatarioNumeroIdentificacion;
	private String destinatarioNombre;
	private String destinatarioContactoNombres;
	private String destinatarioContactoEmail;
	private String destinatarioContactoTelefonos;

	private String destinoCodigo;
	private String destinoNombre;
	private String destinoContactoNombres;
	private String destinoContactoEmail;
	private String destinoContactoTelefonos;

	private String origenCodigo;
	private String origenNombre;
	private String origenContactoNombres;
	private String origenContactoEmail;
	private String origenContactoTelefonos;

	private Set<String> requerimientosDistribucion;
	private String notasRequerimientosDistribucion;

	private Set<String> requerimientosAlistamiento;
	private String notasRequerimientosAlistamiento;

	private Integer valorRecaudo;

	private String notasConfirmacion;
	private String usuarioConfirmacion;
	private LocalDateTime fechaConfirmacion;

	private Set<ETLLineaOrdenDto> lineas;

	public ETLOrdenDto() {
		super();

		setNumeroOrden("");
		setFechaOrden(null);
		setNumeroOrdenCompra("");

		setClienteCodigo("");

		setTipoServicioCodigo("");
		setTipoServicioCodigoAlterno("");

		setRequiereServicioDistribucion(true);

		setDestinoCiudadNombreAlterno("");
		setDestinoDireccion("");
		setDestinoIndicaciones("");

		setOrigenCiudadNombreAlterno("");
		setOrigenDireccion("");
		setOrigenIndicaciones("");

		setRequiereConfirmacionCitaEntrega(false);
		setFechaEntregaSugeridaMinima(null);
		setFechaEntregaSugeridaMaxima(null);
		setHoraEntregaSugeridaMinima(null);
		setHoraEntregaSugeridaMaxima(null);

		setRequiereConfirmacionCitaRecogida(false);
		setFechaRecogidaSugeridaMinima(null);
		setFechaRecogidaSugeridaMaxima(null);
		setHoraRecogidaSugeridaMinima(null);
		setHoraRecogidaSugeridaMaxima(null);

		setCanalCodigoAlterno("");

		setDestinatarioNumeroIdentificacion("");
		setDestinatarioNombre("");
		setDestinatarioContactoNombres("");
		setDestinatarioContactoEmail("");
		setDestinatarioContactoTelefonos("");

		setDestinoCodigo("");
		setDestinoNombre("");
		setDestinoContactoNombres("");
		setDestinoContactoEmail("");
		setDestinoContactoTelefonos("");

		setOrigenCodigo("");
		setOrigenNombre("");
		setOrigenContactoNombres("");
		setOrigenContactoEmail("");
		setOrigenContactoTelefonos("");

		setRequerimientosDistribucion(new HashSet<>());
		setCodigosAlternosRequerimientosDistribucion("");
		setNotasRequerimientosDistribucion("");

		setRequerimientosAlistamiento(new HashSet<>());
		setCodigosAlternosRequerimientosAlistamiento("");
		setNotasRequerimientosAlistamiento("");

		setValorRecaudo(null);

		setNotasConfirmacion("");
		setUsuarioConfirmacion("");
		setFechaConfirmacion(null);

		setLineas(new HashSet<>());
	}

	public String getNumeroOrden() {
		return numeroOrden;
	}

	public LocalDate getFechaOrden() {
		return fechaOrden;
	}

	public String getNumeroOrdenCompra() {
		return numeroOrdenCompra;
	}

	public String getClienteCodigo() {
		return clienteCodigo;
	}

	public String getTipoServicioCodigo() {
		return tipoServicioCodigo;
	}

	public String getTipoServicioCodigoAlterno() {
		return tipoServicioCodigoAlterno;
	}

	public boolean isRequiereServicioDistribucion() {
		return requiereServicioDistribucion;
	}

	public String getDestinoCiudadNombreAlterno() {
		return destinoCiudadNombreAlterno;
	}

	public String getDestinoDireccion() {
		return destinoDireccion;
	}

	public String getDestinoIndicaciones() {
		return destinoIndicaciones;
	}

	public String getOrigenCiudadNombreAlterno() {
		return origenCiudadNombreAlterno;
	}

	public String getOrigenDireccion() {
		return origenDireccion;
	}

	public String getOrigenIndicaciones() {
		return origenIndicaciones;
	}

	public boolean isRequiereConfirmacionCitaEntrega() {
		return requiereConfirmacionCitaEntrega;
	}

	public LocalDate getFechaEntregaSugeridaMinima() {
		return fechaEntregaSugeridaMinima;
	}

	public LocalDate getFechaEntregaSugeridaMaxima() {
		return fechaEntregaSugeridaMaxima;
	}

	public LocalTime getHoraEntregaSugeridaMinima() {
		return horaEntregaSugeridaMinima;
	}

	public LocalTime getHoraEntregaSugeridaMaxima() {
		return horaEntregaSugeridaMaxima;
	}

	public boolean isRequiereConfirmacionCitaRecogida() {
		return requiereConfirmacionCitaRecogida;
	}

	public LocalDate getFechaRecogidaSugeridaMinima() {
		return fechaRecogidaSugeridaMinima;
	}

	public LocalDate getFechaRecogidaSugeridaMaxima() {
		return fechaRecogidaSugeridaMaxima;
	}

	public LocalTime getHoraRecogidaSugeridaMinima() {
		return horaRecogidaSugeridaMinima;
	}

	public LocalTime getHoraRecogidaSugeridaMaxima() {
		return horaRecogidaSugeridaMaxima;
	}

	public String getCanalCodigoAlterno() {
		return canalCodigoAlterno;
	}

	public String getDestinatarioNumeroIdentificacion() {
		return destinatarioNumeroIdentificacion;
	}

	public String getDestinatarioNombre() {
		return destinatarioNombre;
	}

	public String getDestinatarioContactoNombres() {
		return destinatarioContactoNombres;
	}

	public Contacto getDestinatarioContacto() {
		return new Contacto(getDestinatarioContactoNombres(), getDestinatarioContactoEmail(), getDestinatarioContactoTelefonos());
	}


	public String getDestinatarioContactoEmail() {
		return destinatarioContactoEmail;
	}

	public String getDestinatarioContactoTelefonos() {
		return destinatarioContactoTelefonos;
	}

	public String getDestinoCodigo() {
		return destinoCodigo;
	}

	public String getDestinoNombre() {
		return destinoNombre;
	}

	public Contacto getDestinoContacto() {
		return new Contacto(getDestinoContactoNombres(), getDestinoContactoEmail(), getDestinoContactoTelefonos());
	}

	public String getDestinoContactoNombres() {
		return destinoContactoNombres;
	}

	public String getDestinoContactoEmail() {
		return destinoContactoEmail;
	}

	public String getDestinoContactoTelefonos() {
		return destinoContactoTelefonos;
	}

	public String getOrigenCodigo() {
		return origenCodigo;
	}
	
	public String getOrigenNombre() {
		return origenNombre;
	}

	public Contacto getOrigenContacto() {
		return new Contacto(getOrigenContactoNombres(), getOrigenContactoEmail(), getOrigenContactoTelefonos());
	}

	public String getOrigenContactoNombres() {
		return origenContactoNombres;
	}

	public String getOrigenContactoEmail() {
		return origenContactoEmail;
	}

	public String getOrigenContactoTelefonos() {
		return origenContactoTelefonos;
	}

	public String getNotasRequerimientosDistribucion() {
		return notasRequerimientosDistribucion;
	}

	public Set<String> getRequerimientosDistribucion() {
		return requerimientosDistribucion;
	}

	public String getNotasRequerimientosAlistamiento() {
		return notasRequerimientosAlistamiento;
	}

	public Set<String> getRequerimientosAlistamiento() {
		return requerimientosAlistamiento;
	}

	public Integer getValorRecaudo() {
		return valorRecaudo;
	}

	public String getNotasConfirmacion() {
		return notasConfirmacion;
	}

	public String getUsuarioConfirmacion() {
		return usuarioConfirmacion;
	}

	public LocalDateTime getFechaConfirmacion() {
		return fechaConfirmacion;
	}

	public Set<ETLLineaOrdenDto> getLineas() {
		return lineas;
	}

	public void setNumeroOrden(String value) {
		this.numeroOrden = substringSafe(coalesce(value, "").trim(), 0, 20);
	}

	public void setFechaOrden(LocalDate fechaOrden) {
		this.fechaOrden = fechaOrden;
	}
	
	public void setNumeroOrdenCompra(String value) {
		this.numeroOrdenCompra = substringSafe(coalesce(value, "").trim(), 0, 20);
	}

	public void setClienteCodigo(String value) {
		this.clienteCodigo = substringSafe(coalesce(value, "").trim(), 0, 20);
	}

	public void setTipoServicioCodigo(String value) {
		this.tipoServicioCodigo = substringSafe(coalesce(value, "").trim(), 0, 20);
	}

	public void setTipoServicioCodigoAlterno(String value) {
		this.tipoServicioCodigoAlterno = substringSafe(coalesce(value, ""), 0, 50);
	}

	public void setRequiereServicioDistribucion(boolean requiereServicioDistribucion) {
		this.requiereServicioDistribucion = requiereServicioDistribucion;
	}

	public void setDestinoCiudadNombreAlterno(String value) {
		this.destinoCiudadNombreAlterno = substringSafe(coalesce(value, "").trim(), 0, 100);
	}

	public void setDestinoDireccion(String value) {
		this.destinoDireccion = substringSafe(coalesce(value, "").trim(), 0, 150);
	}

	public void setDestinoIndicaciones(String value) {
		this.destinoIndicaciones = substringSafe(coalesce(value, "").trim(), 0, 150);
	}

	public void setOrigenCiudadNombreAlterno(String value) {
		this.origenCiudadNombreAlterno = substringSafe(coalesce(value, "").trim(), 0, 100);
	}

	public void setOrigenDireccion(String value) {
		this.origenDireccion = substringSafe(coalesce(value, "").trim(), 0, 150);
	}

	public void setOrigenIndicaciones(String value) {
		this.origenIndicaciones = substringSafe(coalesce(value, "").trim(), 0, 150);
	}

	public void setRequiereConfirmacionCitaEntrega(boolean requiereConfirmacionCitaEntrega) {
		this.requiereConfirmacionCitaEntrega = requiereConfirmacionCitaEntrega;
	}

	public void setFechaEntregaSugeridaMinima(LocalDate fechaEntregaSugeridaMinima) {
		this.fechaEntregaSugeridaMinima = fechaEntregaSugeridaMinima;
	}

	public void setFechaEntregaSugeridaMaxima(LocalDate fechaEntregaSugeridaMaxima) {
		this.fechaEntregaSugeridaMaxima = fechaEntregaSugeridaMaxima;
	}

	public void setHoraEntregaSugeridaMinima(LocalTime horaEntregaSugeridaMinima) {
		this.horaEntregaSugeridaMinima = horaEntregaSugeridaMinima;
	}

	public void setHoraEntregaSugeridaMaxima(LocalTime horaEntregaSugeridaMaxima) {
		this.horaEntregaSugeridaMaxima = horaEntregaSugeridaMaxima;
	}

	public void setRequiereConfirmacionCitaRecogida(boolean requiereConfirmacionCitaRecogida) {
		this.requiereConfirmacionCitaRecogida = requiereConfirmacionCitaRecogida;
	}

	public void setFechaRecogidaSugeridaMinima(LocalDate fechaRecogidaSugeridaMinima) {
		this.fechaRecogidaSugeridaMinima = fechaRecogidaSugeridaMinima;
	}

	public void setFechaRecogidaSugeridaMaxima(LocalDate fechaRecogidaSugeridaMaxima) {
		this.fechaRecogidaSugeridaMaxima = fechaRecogidaSugeridaMaxima;
	}

	public void setHoraRecogidaSugeridaMinima(LocalTime horaRecogidaSugeridaMinima) {
		this.horaRecogidaSugeridaMinima = horaRecogidaSugeridaMinima;
	}

	public void setHoraRecogidaSugeridaMaxima(LocalTime horaRecogidaSugeridaMaxima) {
		this.horaRecogidaSugeridaMaxima = horaRecogidaSugeridaMaxima;
	}

	public void setCanalCodigoAlterno(String value) {
		this.canalCodigoAlterno = substringSafe(coalesce(value, "").trim(), 0, 50);
	}

	public void setDestinatarioNumeroIdentificacion(String value) {
		this.destinatarioNumeroIdentificacion = substringSafe(coalesce(value, "").trim(), 0, 20);
	}

	public void setDestinatarioNombre(String value) {
		this.destinatarioNombre = substringSafe(coalesce(value, "").trim(), 0, 100);
	}

	public void setDestinatarioContacto(Contacto contacto){
		this.setDestinatarioContactoNombres(contacto.getNombres());
		this.setDestinatarioContactoEmail(contacto.getEmail());
		this.setDestinatarioContactoTelefonos(contacto.getTelefonos());
	}

	protected void setDestinatarioContactoNombres(String value) {
		this.destinatarioContactoNombres = substringSafe(coalesce(value, "").trim(), 0, 100);
	}

	protected void setDestinatarioContactoEmail(String value) {
		this.destinatarioContactoEmail = substringSafe(coalesce(value, "").trim(), 0, 100);
	}

	protected void setDestinatarioContactoTelefonos(String value) {
		this.destinatarioContactoTelefonos = substringSafe(coalesce(value, "").trim(), 0, 50);
	}

	public void setDestinoCodigo(String value) {
		this.destinoCodigo = substringSafe(coalesce(value, "").trim(), 0, 20);
	}

	public void setDestinoNombre(String value) {
		this.destinoNombre = substringSafe(coalesce(value, "").trim(), 0, 100);
	}

	public void setDestinoContacto(Contacto contacto){
		this.setDestinoContactoNombres(contacto.getNombres());
		this.setDestinoContactoEmail(contacto.getEmail());
		this.setDestinoContactoTelefonos(contacto.getTelefonos());
	}
	
	public void setDestinoContactoNombres(String value) {
		this.destinoContactoNombres = substringSafe(coalesce(value, "").trim(), 0, 100);
	}

	public void setDestinoContactoEmail(String value) {
		this.destinoContactoEmail = substringSafe(coalesce(value, "").trim(), 0, 100);
	}

	public void setDestinoContactoTelefonos(String value) {
		this.destinoContactoTelefonos = substringSafe(coalesce(value, "").trim(), 0, 50);
	}

	public void setOrigenCodigo(String value) {
		this.origenCodigo = substringSafe(coalesce(value, "").trim(), 0, 20);
	}

	public void setOrigenNombre(String value) {
		this.origenNombre = substringSafe(coalesce(value, "").trim(), 0, 100);
	}

	public void setOrigenContacto(Contacto contacto){
		this.setOrigenContactoNombres(contacto.getNombres());
		this.setOrigenContactoEmail(contacto.getEmail());
		this.setOrigenContactoTelefonos(contacto.getTelefonos());
	}

	public void setOrigenContactoNombres(String value) {
		this.origenContactoNombres = substringSafe(coalesce(value, "").trim(), 0, 100);
	}

	public void setOrigenContactoEmail(String value) {
		this.origenContactoEmail = substringSafe(coalesce(value, "").trim(), 0, 100);
	}

	public void setOrigenContactoTelefonos(String value) {
		this.origenContactoTelefonos = substringSafe(coalesce(value, "").trim(), 0, 50);
	}

	public void setCodigosAlternosRequerimientosDistribucion(String codigos) {
		this.requerimientosDistribucion.clear();
		for (String codigo : coalesce(codigos, "").split(",")) {
			this.requerimientosDistribucion.add(codigo);
		}
	}

	public void setCodigosAlternosRequerimientosAlistamiento(String codigos) {
		this.requerimientosAlistamiento.clear();
		for (String codigo : coalesce(codigos, "").split(",")) {
			this.requerimientosAlistamiento.add(codigo);
		}
	}

	public void setRequerimientosDistribucion(Set<String> requerimientosDistribucion) {
		this.requerimientosDistribucion = requerimientosDistribucion;
	}

	public void setNotasRequerimientosDistribucion(String notasRequerimientosDistribucion) {
		this.notasRequerimientosDistribucion = notasRequerimientosDistribucion;
	}

	public void setRequerimientosAlistamiento(Set<String> requerimientosAlistamiento) {
		this.requerimientosAlistamiento = requerimientosAlistamiento;
	}

	public void setNotasRequerimientosAlistamiento(String notasRequerimientosAlistamiento) {
		this.notasRequerimientosAlistamiento = notasRequerimientosAlistamiento;
	}

	public void setValorRecaudo(Integer value) {
		if (value != null) {
			if (value <= 0) {
				value = null;
			}
		}
		this.valorRecaudo = value;
	}

	public void setNotasConfirmacion(String value) {
		this.notasConfirmacion = substringSafe(coalesce(value, "").trim(), 0, 200);
	}

	public void setUsuarioConfirmacion(String value) {
		this.usuarioConfirmacion = substringSafe(coalesce(value, "").trim(), 0, 50);
	}

	public void setFechaConfirmacion(LocalDateTime fechaConfirmacion) {
		this.fechaConfirmacion = fechaConfirmacion;
	}

	public void setLineas(Set<ETLLineaOrdenDto> lineas) {
		this.lineas = lineas;
	}

	@Override
	public String toString() {
		return "ETLOrdenDto [" + (numeroOrden != null ? "numeroOrden=" + numeroOrden + ", " : "")
				+ (numeroOrdenCompra != null ? "numeroOrdenCompra=" + numeroOrdenCompra + ", " : "")
				+ (clienteCodigo != null ? "clienteCodigo=" + clienteCodigo + ", " : "")
				+ (tipoServicioCodigo != null ? "tipoServicioCodigo=" + tipoServicioCodigo + ", " : "")
				+ (tipoServicioCodigoAlterno != null ? "tipoServicioCodigoAlterno=" + tipoServicioCodigoAlterno + ", "
						: "")
				+ "requiereServicioDistribucion=" + requiereServicioDistribucion + ", "
				+ (destinoCiudadNombreAlterno != null
						? "destinoCiudadNombreAlterno=" + destinoCiudadNombreAlterno + ", " : "")
				+ (destinoDireccion != null ? "destinoDireccion=" + destinoDireccion + ", " : "")
				+ (destinoIndicaciones != null ? "destinoIndicaciones=" + destinoIndicaciones + ", " : "")
				+ (origenCiudadNombreAlterno != null ? "origenCiudadNombreAlterno=" + origenCiudadNombreAlterno + ", "
						: "")
				+ (origenDireccion != null ? "origenDireccion=" + origenDireccion + ", " : "")
				+ (origenIndicaciones != null ? "origenIndicaciones=" + origenIndicaciones + ", " : "")
				+ "requiereConfirmacionCitaEntrega=" + requiereConfirmacionCitaEntrega + ", "
				+ (fechaEntregaSugeridaMinima != null
						? "fechaEntregaSugeridaMinima=" + fechaEntregaSugeridaMinima + ", " : "")
				+ (fechaEntregaSugeridaMaxima != null
						? "fechaEntregaSugeridaMaxima=" + fechaEntregaSugeridaMaxima + ", " : "")
				+ (horaEntregaSugeridaMinima != null ? "horaEntregaSugeridaMinima=" + horaEntregaSugeridaMinima + ", "
						: "")
				+ (horaEntregaSugeridaMaxima != null ? "horaEntregaSugeridaMaxima=" + horaEntregaSugeridaMaxima + ", "
						: "")
				+ "requiereConfirmacionCitaRecogida=" + requiereConfirmacionCitaRecogida + ", "
				+ (fechaRecogidaSugeridaMinima != null
						? "fechaRecogidaSugeridaMinima=" + fechaRecogidaSugeridaMinima + ", " : "")
				+ (fechaRecogidaSugeridaMaxima != null
						? "fechaRecogidaSugeridaMaxima=" + fechaRecogidaSugeridaMaxima + ", " : "")
				+ (horaRecogidaSugeridaMinima != null
						? "horaRecogidaSugeridaMinima=" + horaRecogidaSugeridaMinima + ", " : "")
				+ (horaRecogidaSugeridaMaxima != null
						? "horaRecogidaSugeridaMaxima=" + horaRecogidaSugeridaMaxima + ", " : "")
				+ (canalCodigoAlterno != null ? "canalCodigoAlterno=" + canalCodigoAlterno + ", " : "")
				+ (destinatarioNumeroIdentificacion != null
						? "destinatarioNumeroIdentificacion=" + destinatarioNumeroIdentificacion + ", " : "")
				+ (destinatarioNombre != null ? "destinatarioNombre=" + destinatarioNombre + ", " : "")
				+ (destinatarioContactoNombres != null
						? "destinatarioContactoNombres=" + destinatarioContactoNombres + ", " : "")
				+ (destinatarioContactoEmail != null ? "destinatarioContactoEmail=" + destinatarioContactoEmail + ", "
						: "")
				+ (destinatarioContactoTelefonos != null
						? "destinatarioContactoTelefonos=" + destinatarioContactoTelefonos + ", " : "")
				+ (destinoNombre != null ? "destinoNombre=" + destinoNombre + ", " : "")
				+ (destinoContactoNombres != null ? "destinoContactoNombres=" + destinoContactoNombres + ", " : "")
				+ (destinoContactoEmail != null ? "destinoContactoEmail=" + destinoContactoEmail + ", " : "")
				+ (destinoContactoTelefonos != null ? "destinoContactoTelefonos=" + destinoContactoTelefonos + ", "
						: "")
				+ (origenNombre != null ? "origenNombre=" + origenNombre + ", " : "")
				+ (origenContactoNombres != null ? "origenContactoNombres=" + origenContactoNombres + ", " : "")
				+ (origenContactoEmail != null ? "origenContactoEmail=" + origenContactoEmail + ", " : "")
				+ (origenContactoTelefonos != null ? "origenContactoTelefonos=" + origenContactoTelefonos + ", " : "")
				+ (requerimientosDistribucion != null
						? "requerimientosDistribucion=" + requerimientosDistribucion + ", " : "")
				+ (notasRequerimientosDistribucion != null
						? "notasRequerimientosDistribucion=" + notasRequerimientosDistribucion + ", " : "")
				+ (requerimientosAlistamiento != null
						? "requerimientosAlistamiento=" + requerimientosAlistamiento + ", " : "")
				+ (notasRequerimientosAlistamiento != null
						? "notasRequerimientosAlistamiento=" + notasRequerimientosAlistamiento + ", " : "")
				+ (valorRecaudo != null ? "valorRecaudo=" + valorRecaudo + ", " : "")
				+ (notasConfirmacion != null ? "notasConfirmacion=" + notasConfirmacion + ", " : "")
				+ (usuarioConfirmacion != null ? "usuarioConfirmacion=" + usuarioConfirmacion + ", " : "")
				+ (fechaConfirmacion != null ? "fechaConfirmacion=" + fechaConfirmacion + ", " : "")
				+ (lineas != null ? "lineas=" + lineas : "") + "]";
	}
}