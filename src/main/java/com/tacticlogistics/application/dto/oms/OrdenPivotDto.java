package com.tacticlogistics.application.dto.oms;

import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.oms.EstadoAlistamientoType;
import com.tacticlogistics.domain.model.oms.EstadoDistribucionType;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;

public class OrdenPivotDto {

	private Integer id;
	private String numeroOrden;
	@JsonIgnore
	private String numeroConsolidado;

	// ---------------------------------------------------------------------------------------------------------
	private EstadoOrdenType estadoOrden;
	private EstadoDistribucionType estadoDistribucion;
	private EstadoAlistamientoType estadoAlistamiento;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private int clienteId;
	private String clienteCodigo;
	@JsonIgnore
	private String clienteNombre;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Integer tipoServicioId;
	@JsonIgnore
	private String tipoServicioCodigo;
	@JsonIgnore
	private String tipoServicioCodigoAlterno;
	private String tipoServicioNombre;

	private boolean requiereServicioDistribucion;

	private boolean requiereServicioAlistamiento;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Integer ciudadDestinoId;
	private String ciudadDestinoNombre;
	private String destinoDireccion;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Integer ciudadOrigenId;
	private String ciudadOrigenNombre;
	private String origenDireccion;

	// ---------------------------------------------------------------------------------------------------------
	private boolean requiereConfirmacionCitaEntrega;

	private Date fechaEntregaMinima;
	private Date fechaEntregaMaxima;
	private Time horaEntregaMinima;
	private Time horaEntregaMaxima;
	private Time horaEntregaMinimaAdicional;
	private Time horaEntregaMaximaAdicional;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Integer canalId;
	@JsonIgnore
	private String canalCodigo;
	@JsonIgnore
	private String canalCodigoAlterno;
	private String canalNombre;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Integer destinatarioId;
	private String destinatarioIdentificacion;
	private String destinatarioNombre;
	@JsonIgnore
	private Contacto destinatarioContacto;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Integer destinoId;
	private String destinoNombre;
	@JsonIgnore
	private Contacto destinoContacto;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Integer origenId;
	private String origenNombre;
	@JsonIgnore
	private Contacto origenContacto;

	// ---------------------------------------------------------------------------------------------------------
	private String notasRequerimientosDistribucion;
	private String notasRequerimientosAlistamiento;
	private Integer valorRecaudo;

	// ---------------------------------------------------------------------------------------------------------
	private String notasConfirmacion;
	private Date fechaConfirmacion;
	private String usuarioConfirmacion;

	// ---------------------------------------------------------------------------------------------------------
	private Date fechaAlistamientoPlanificada;
	@JsonIgnore
	private Time horaAlistamientoPlanificadaMinima;
	@JsonIgnore
	private Time horaAlistamientoPlanificadaMaxima;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Integer tipoVehiculoPlanificadoId;
	@JsonIgnore
	private String tipoVehiculoPlanificadoCodigo;
	@JsonIgnore
	private String tipoVehiculoPlanificadoNombre;
	@JsonIgnore
	private Integer valorFletePlanificado;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private String notasAprobacionPlanificacion;
	private Date fechaAprobacionPlanificacion;
	private String usuarioAprobacionPlanificacion;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Integer rutaId;
	@JsonIgnore
	private Date fechaAsignacionRuta;
	@JsonIgnore
	private String usuarioAsignacionRuta;

	// ---------------------------------------------------------------------------------------------------------
	@JsonIgnore
	private Date fechaCreacion;
	@JsonIgnore
	private String usuarioCreacion;
	
	private Date fechaActualizacion;
	private String usuarioActualizacion;

	// ---------------------------------------------------------------------------------------------------------
	private String notasAnulacion;
	@JsonIgnore
	private Integer causalAnulacionId;
	private String causalAnulacionCodigo;
	private String causalAnulacionNombre;
	private String usuarioAnulacion;
	private Date fechaAnulacion;

	// ---------------------------------------------------------------------------------------------------------
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(String numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public String getNumeroConsolidado() {
		return numeroConsolidado;
	}

	public void setNumeroConsolidado(String numeroConsolidado) {
		this.numeroConsolidado = numeroConsolidado;
	}

	public EstadoOrdenType getEstadoOrden() {
		return estadoOrden;
	}

	public void setEstadoOrden(EstadoOrdenType estadoOrden) {
		this.estadoOrden = estadoOrden;
	}

	public EstadoDistribucionType getEstadoDistribucion() {
		return estadoDistribucion;
	}

	public void setEstadoDistribucion(EstadoDistribucionType estadoDistribucion) {
		this.estadoDistribucion = estadoDistribucion;
	}

	public EstadoAlistamientoType getEstadoAlistamiento() {
		return estadoAlistamiento;
	}

	public void setEstadoAlistamiento(EstadoAlistamientoType estadoAlistamiento) {
		this.estadoAlistamiento = estadoAlistamiento;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public String getClienteCodigo() {
		return clienteCodigo;
	}

	public void setClienteCodigo(String clienteCodigo) {
		this.clienteCodigo = clienteCodigo;
	}

	public String getClienteNombre() {
		return clienteNombre;
	}

	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	public Integer getTipoServicioId() {
		return tipoServicioId;
	}

	public void setTipoServicioId(Integer tipoServicioId) {
		this.tipoServicioId = tipoServicioId;
	}

	public String getTipoServicioCodigo() {
		return tipoServicioCodigo;
	}

	public void setTipoServicioCodigo(String tipoServicioCodigo) {
		this.tipoServicioCodigo = tipoServicioCodigo;
	}

	public String getTipoServicioCodigoAlterno() {
		return tipoServicioCodigoAlterno;
	}

	public void setTipoServicioCodigoAlterno(String tipoServicioCodigoAlterno) {
		this.tipoServicioCodigoAlterno = tipoServicioCodigoAlterno;
	}

	public String getTipoServicioNombre() {
		return tipoServicioNombre;
	}

	public void setTipoServicioNombre(String tipoServicioNombre) {
		this.tipoServicioNombre = tipoServicioNombre;
	}

	public boolean isRequiereServicioDistribucion() {
		return requiereServicioDistribucion;
	}

	public void setRequiereServicioDistribucion(boolean requiereServicioDistribucion) {
		this.requiereServicioDistribucion = requiereServicioDistribucion;
	}

	public boolean isRequiereServicioAlistamiento() {
		return requiereServicioAlistamiento;
	}

	public void setRequiereServicioAlistamiento(boolean requiereServicioAlistamiento) {
		this.requiereServicioAlistamiento = requiereServicioAlistamiento;
	}

	public void setCiudadDestino(Ciudad ciudad) {
		if (ciudad != null) {
			this.setCiudadDestinoId(ciudad.getId());
			this.setCiudadDestinoNombre(ciudad.getNombreAlterno());
		} else {
			this.setCiudadDestinoId(null);
			this.setCiudadDestinoNombre("");
		}
	}

	public Integer getCiudadDestinoId() {
		return ciudadDestinoId;
	}

	public void setCiudadDestinoId(Integer ciudadDestinoId) {
		this.ciudadDestinoId = ciudadDestinoId;
	}

	public String getCiudadDestinoNombre() {
		return ciudadDestinoNombre;
	}

	public void setCiudadDestinoNombre(String ciudadDestinoNombre) {
		this.ciudadDestinoNombre = ciudadDestinoNombre;
	}

	public String getDestinoDireccion() {
		return destinoDireccion;
	}

	public void setDestinoDireccion(String destinoDireccion) {
		this.destinoDireccion = destinoDireccion;
	}

	public void setCiudadOrigen(Ciudad ciudad) {
		if (ciudad != null) {
			this.setCiudadOrigenId(ciudad.getId());
			this.setCiudadOrigenNombre(ciudad.getNombreAlterno());
		} else {
			this.setCiudadOrigenId(null);
			this.setCiudadOrigenNombre("");
		}
	}

	public Integer getCiudadOrigenId() {
		return ciudadOrigenId;
	}

	public void setCiudadOrigenId(Integer ciudadOrigenId) {
		this.ciudadOrigenId = ciudadOrigenId;
	}

	public String getCiudadOrigenNombre() {
		return ciudadOrigenNombre;
	}

	public void setCiudadOrigenNombre(String ciudadOrigenNombre) {
		this.ciudadOrigenNombre = ciudadOrigenNombre;
	}

	public String getOrigenDireccion() {
		return origenDireccion;
	}

	public void setOrigenDireccion(String origenDireccion) {
		this.origenDireccion = origenDireccion;
	}

	public boolean isRequiereConfirmacionCitaEntrega() {
		return requiereConfirmacionCitaEntrega;
	}

	public void setRequiereConfirmacionCitaEntrega(boolean requiereConfirmacionCitaEntrega) {
		this.requiereConfirmacionCitaEntrega = requiereConfirmacionCitaEntrega;
	}

	public Date getFechaEntregaMinima() {
		return fechaEntregaMinima;
	}

	public void setFechaEntregaMinima(Date fechaEntregaMinima) {
		this.fechaEntregaMinima = fechaEntregaMinima;
	}

	public Date getFechaEntregaMaxima() {
		return fechaEntregaMaxima;
	}

	public void setFechaEntregaMaxima(Date fechaEntregaMaxima) {
		this.fechaEntregaMaxima = fechaEntregaMaxima;
	}

	public Time getHoraEntregaMinima() {
		return horaEntregaMinima;
	}

	public void setHoraEntregaMinima(Time horaEntregaMinima) {
		this.horaEntregaMinima = horaEntregaMinima;
	}

	public Time getHoraEntregaMaxima() {
		return horaEntregaMaxima;
	}

	public void setHoraEntregaMaxima(Time horaEntregaMaxima) {
		this.horaEntregaMaxima = horaEntregaMaxima;
	}

	public Time getHoraEntregaMinimaAdicional() {
		return horaEntregaMinimaAdicional;
	}

	public void setHoraEntregaMinimaAdicional(Time horaEntregaMinimaAdicional) {
		this.horaEntregaMinimaAdicional = horaEntregaMinimaAdicional;
	}

	public Time getHoraEntregaMaximaAdicional() {
		return horaEntregaMaximaAdicional;
	}

	public void setHoraEntregaMaximaAdicional(Time horaEntregaMaximaAdicional) {
		this.horaEntregaMaximaAdicional = horaEntregaMaximaAdicional;
	}

	public void setCanal(Canal canal) {
		if (canal != null) {
			this.setCanalId(canal.getId());
			this.setCanalCodigo(canal.getCodigo());
			this.setCanalNombre(canal.getNombre());
		} else {
			this.setCanalId(null);
			this.setCanalCodigo("");
			this.setCanalNombre("");
		}
	}

	public Integer getCanalId() {
		return canalId;
	}

	public void setCanalId(Integer canalId) {
		this.canalId = canalId;
	}

	public String getCanalCodigo() {
		return canalCodigo;
	}

	public void setCanalCodigo(String canalCodigo) {
		this.canalCodigo = canalCodigo;
	}

	public String getCanalCodigoAlterno() {
		return canalCodigoAlterno;
	}

	public void setCanalCodigoAlterno(String canalCodigoAlterno) {
		this.canalCodigoAlterno = canalCodigoAlterno;
	}

	public String getCanalNombre() {
		return canalNombre;
	}

	public void setCanalNombre(String canalNombre) {
		this.canalNombre = canalNombre;
	}

	public void setDestinatario(DestinatarioRemitente destinatario) {
		if (destinatario != null) {
			this.setDestinatarioId(destinatario.getId());
		} else {
			this.setDestinatarioId(null);
		}
	}

	public Integer getDestinatarioId() {
		return destinatarioId;
	}

	public void setDestinatarioId(Integer destinatarioId) {
		this.destinatarioId = destinatarioId;
	}

	public String getDestinatarioIdentificacion() {
		return destinatarioIdentificacion;
	}

	public void setDestinatarioIdentificacion(String destinatarioIdentificacion) {
		this.destinatarioIdentificacion = destinatarioIdentificacion;
	}

	public String getDestinatarioNombre() {
		return destinatarioNombre;
	}

	public void setDestinatarioNombre(String destinatarioNombre) {
		this.destinatarioNombre = destinatarioNombre;
	}

	public Contacto getDestinatarioContacto() {
		return destinatarioContacto;
	}

	public void setDestinatarioContacto(Contacto destinatarioContacto) {
		this.destinatarioContacto = destinatarioContacto;
	}

	public Integer getDestinoId() {
		return destinoId;
	}

	public void setDestinoId(Integer destinoId) {
		this.destinoId = destinoId;
	}

	public String getDestinoNombre() {
		return destinoNombre;
	}

	public void setDestinoNombre(String destinoNombre) {
		this.destinoNombre = destinoNombre;
	}

	public Contacto getDestinoContacto() {
		return destinoContacto;
	}

	public void setDestinoContacto(Contacto destinoContacto) {
		this.destinoContacto = destinoContacto;
	}

	public Integer getOrigenId() {
		return origenId;
	}

	public void setOrigenId(Integer origenId) {
		this.origenId = origenId;
	}

	public String getOrigenNombre() {
		return origenNombre;
	}

	public void setOrigenNombre(String origenNombre) {
		this.origenNombre = origenNombre;
	}

	public Contacto getOrigenContacto() {
		return origenContacto;
	}

	public void setOrigenContacto(Contacto origenContacto) {
		this.origenContacto = origenContacto;
	}

	public String getNotasRequerimientosDistribucion() {
		return notasRequerimientosDistribucion;
	}

	public void setNotasRequerimientosDistribucion(String notasRequerimientosDistribucion) {
		this.notasRequerimientosDistribucion = notasRequerimientosDistribucion;
	}

	public String getNotasRequerimientosAlistamiento() {
		return notasRequerimientosAlistamiento;
	}

	public void setNotasRequerimientosAlistamiento(String notasRequerimientosAlistamiento) {
		this.notasRequerimientosAlistamiento = notasRequerimientosAlistamiento;
	}

	public Integer getValorRecaudo() {
		return valorRecaudo;
	}

	public void setValorRecaudo(Integer valorRecaudo) {
		this.valorRecaudo = valorRecaudo;
	}

	public String getNotasConfirmacion() {
		return notasConfirmacion;
	}

	public void setNotasConfirmacion(String notasConfirmacion) {
		this.notasConfirmacion = notasConfirmacion;
	}

	public Date getFechaConfirmacion() {
		return fechaConfirmacion;
	}

	public void setFechaConfirmacion(Date fechaConfirmacion) {
		this.fechaConfirmacion = fechaConfirmacion;
	}

	public String getUsuarioConfirmacion() {
		return usuarioConfirmacion;
	}

	public void setUsuarioConfirmacion(String usuarioConfirmacion) {
		this.usuarioConfirmacion = usuarioConfirmacion;
	}

	public Date getFechaAlistamientoPlanificada() {
		return fechaAlistamientoPlanificada;
	}

	public void setFechaAlistamientoPlanificada(Date fechaAlistamientoPlanificada) {
		this.fechaAlistamientoPlanificada = fechaAlistamientoPlanificada;
	}

	public Time getHoraAlistamientoPlanificadaMinima() {
		return horaAlistamientoPlanificadaMinima;
	}

	public void setHoraAlistamientoPlanificadaMinima(Time horaAlistamientoPlanificadaMinima) {
		this.horaAlistamientoPlanificadaMinima = horaAlistamientoPlanificadaMinima;
	}

	public Time getHoraAlistamientoPlanificadaMaxima() {
		return horaAlistamientoPlanificadaMaxima;
	}

	public void setHoraAlistamientoPlanificadaMaxima(Time horaAlistamientoPlanificadaMaxima) {
		this.horaAlistamientoPlanificadaMaxima = horaAlistamientoPlanificadaMaxima;
	}

	public Integer getTipoVehiculoPlanificadoId() {
		return tipoVehiculoPlanificadoId;
	}

	public void setTipoVehiculoPlanificadoId(Integer tipoVehiculoPlanificadoId) {
		this.tipoVehiculoPlanificadoId = tipoVehiculoPlanificadoId;
	}

	public String getTipoVehiculoPlanificadoCodigo() {
		return tipoVehiculoPlanificadoCodigo;
	}

	public void setTipoVehiculoPlanificadoCodigo(String tipoVehiculoPlanificadoCodigo) {
		this.tipoVehiculoPlanificadoCodigo = tipoVehiculoPlanificadoCodigo;
	}

	public String getTipoVehiculoPlanificadoNombre() {
		return tipoVehiculoPlanificadoNombre;
	}

	public void setTipoVehiculoPlanificadoNombre(String tipoVehiculoPlanificadoNombre) {
		this.tipoVehiculoPlanificadoNombre = tipoVehiculoPlanificadoNombre;
	}

	public Integer getValorFletePlanificado() {
		return valorFletePlanificado;
	}

	public void setValorFletePlanificado(Integer valorFletePlanificado) {
		this.valorFletePlanificado = valorFletePlanificado;
	}

	public String getNotasAprobacionPlanificacion() {
		return notasAprobacionPlanificacion;
	}

	public void setNotasAprobacionPlanificacion(String notasAprobacionPlanificacion) {
		this.notasAprobacionPlanificacion = notasAprobacionPlanificacion;
	}

	public Date getFechaAprobacionPlanificacion() {
		return fechaAprobacionPlanificacion;
	}

	public void setFechaAprobacionPlanificacion(Date fechaAprobacionPlanificacion) {
		this.fechaAprobacionPlanificacion = fechaAprobacionPlanificacion;
	}

	public String getUsuarioAprobacionPlanificacion() {
		return usuarioAprobacionPlanificacion;
	}

	public void setUsuarioAprobacionPlanificacion(String usuarioAprobacionPlanificacion) {
		this.usuarioAprobacionPlanificacion = usuarioAprobacionPlanificacion;
	}

	public Integer getRutaId() {
		return rutaId;
	}

	public void setRutaId(Integer rutaId) {
		this.rutaId = rutaId;
	}

	public Date getFechaAsignacionRuta() {
		return fechaAsignacionRuta;
	}

	public void setFechaAsignacionRuta(Date fechaAsignacionRuta) {
		this.fechaAsignacionRuta = fechaAsignacionRuta;
	}

	public String getUsuarioAsignacionRuta() {
		return usuarioAsignacionRuta;
	}

	public void setUsuarioAsignacionRuta(String usuarioAsignacionRuta) {
		this.usuarioAsignacionRuta = usuarioAsignacionRuta;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
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

	public String getNotasAnulacion() {
		return notasAnulacion;
	}

	public void setNotasAnulacion(String notasAnulacion) {
		this.notasAnulacion = notasAnulacion;
	}

	public Integer getCausalAnulacionId() {
		return causalAnulacionId;
	}

	public void setCausalAnulacionId(Integer causalAnulacionId) {
		this.causalAnulacionId = causalAnulacionId;
	}

	public String getCausalAnulacionCodigo() {
		return causalAnulacionCodigo;
	}

	public void setCausalAnulacionCodigo(String causalAnulacionCodigo) {
		this.causalAnulacionCodigo = causalAnulacionCodigo;
	}

	public String getCausalAnulacionNombre() {
		return causalAnulacionNombre;
	}

	public void setCausalAnulacionNombre(String causalAnulacionNombre) {
		this.causalAnulacionNombre = causalAnulacionNombre;
	}

	public String getUsuarioAnulacion() {
		return usuarioAnulacion;
	}

	public void setUsuarioAnulacion(String usuarioAnulacion) {
		this.usuarioAnulacion = usuarioAnulacion;
	}

	public Date getFechaAnulacion() {
		return fechaAnulacion;
	}

	public void setFechaAnulacion(Date fechaAnulacion) {
		this.fechaAnulacion = fechaAnulacion;
	}
}
