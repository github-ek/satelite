package com.tacticlogistics.domain.model.requerimientos.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OpcionesDeRequerimientoEmbeddable {

	private int numeroMinimoDeArchivosAdjuntos;
	private int numeroMaximoDeArchivosAdjuntos;
	private int tamanoMaximoPorArchivoAdjuntos;
	private boolean permitirPersonalizarOpcionesDeArchivosAdjuntos;

	//--------------------------------------------------------------------------------------------------------------------------------------
	private boolean habilitarOpcionParaAdjuntarArchivosEnDispositivosMoviles;
	private boolean permitirPersonalizarOpcionesParaDispositivosMoviles;
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	@Column(nullable = false, length = 200)
	private String textoPreguntaRequerimientoDeCumplimiento;
	private boolean requiereNotasEnCasoDeConformidad;
	private boolean requiereNotasEnCasoDeNoConformidad;
	private boolean permitirPersonalizarOpcionesParaRequerimientosDeCumplimiento;
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	private boolean requiereNumeroDeReferenciaDelDocumento;
	private boolean requiereDocumentoFisico;
	private boolean permitirPersonalizarOpcionesParaRequerimientosDocumentales;

	public int getNumeroMinimoDeArchivosAdjuntos() {
		return numeroMinimoDeArchivosAdjuntos;
	}
	public void setNumeroMinimoDeArchivosAdjuntos(int numeroMinimoDeArchivosAdjuntos) {
		this.numeroMinimoDeArchivosAdjuntos = numeroMinimoDeArchivosAdjuntos;
	}
	public int getNumeroMaximoDeArchivosAdjuntos() {
		return numeroMaximoDeArchivosAdjuntos;
	}
	public void setNumeroMaximoDeArchivosAdjuntos(int numeroMaximoDeArchivosAdjuntos) {
		this.numeroMaximoDeArchivosAdjuntos = numeroMaximoDeArchivosAdjuntos;
	}
	public int getTamanoMaximoPorArchivoAdjuntos() {
		return tamanoMaximoPorArchivoAdjuntos;
	}
	public void setTamanoMaximoPorArchivoAdjuntos(int tamanoMaximoPorArchivoAdjuntos) {
		this.tamanoMaximoPorArchivoAdjuntos = tamanoMaximoPorArchivoAdjuntos;
	}
	public boolean isPermitirPersonalizarOpcionesDeArchivosAdjuntos() {
		return permitirPersonalizarOpcionesDeArchivosAdjuntos;
	}
	public void setPermitirPersonalizarOpcionesDeArchivosAdjuntos(boolean permitirPersonalizarOpcionesDeArchivosAdjuntos) {
		this.permitirPersonalizarOpcionesDeArchivosAdjuntos = permitirPersonalizarOpcionesDeArchivosAdjuntos;
	}
	public boolean isHabilitarOpcionParaAdjuntarArchivosEnDispositivosMoviles() {
		return habilitarOpcionParaAdjuntarArchivosEnDispositivosMoviles;
	}
	public void setHabilitarOpcionParaAdjuntarArchivosEnDispositivosMoviles(
			boolean habilitarOpcionParaAdjuntarArchivosEnDispositivosMoviles) {
		this.habilitarOpcionParaAdjuntarArchivosEnDispositivosMoviles = habilitarOpcionParaAdjuntarArchivosEnDispositivosMoviles;
	}
	public boolean isPermitirPersonalizarOpcionesParaDispositivosMoviles() {
		return permitirPersonalizarOpcionesParaDispositivosMoviles;
	}
	public void setPermitirPersonalizarOpcionesParaDispositivosMoviles(
			boolean permitirPersonalizarOpcionesParaDispositivosMoviles) {
		this.permitirPersonalizarOpcionesParaDispositivosMoviles = permitirPersonalizarOpcionesParaDispositivosMoviles;
	}
	public String getTextoPreguntaRequerimientoDeCumplimiento() {
		return textoPreguntaRequerimientoDeCumplimiento;
	}
	public void setTextoPreguntaRequerimientoDeCumplimiento(String textoPreguntaRequerimientoDeCumplimiento) {
		this.textoPreguntaRequerimientoDeCumplimiento = textoPreguntaRequerimientoDeCumplimiento;
	}
	public boolean isRequiereNotasEnCasoDeConformidad() {
		return requiereNotasEnCasoDeConformidad;
	}
	public void setRequiereNotasEnCasoDeConformidad(boolean requiereNotasEnCasoDeConformidad) {
		this.requiereNotasEnCasoDeConformidad = requiereNotasEnCasoDeConformidad;
	}
	public boolean isRequiereNotasEnCasoDeNoConformidad() {
		return requiereNotasEnCasoDeNoConformidad;
	}
	public void setRequiereNotasEnCasoDeNoConformidad(boolean requiereNotasEnCasoDeNoConformidad) {
		this.requiereNotasEnCasoDeNoConformidad = requiereNotasEnCasoDeNoConformidad;
	}
	public boolean isPermitirPersonalizarOpcionesParaRequerimientosDeCumplimiento() {
		return permitirPersonalizarOpcionesParaRequerimientosDeCumplimiento;
	}
	public void setPermitirPersonalizarOpcionesParaRequerimientosDeCumplimiento(
			boolean permitirPersonalizarOpcionesParaRequerimientosDeCumplimiento) {
		this.permitirPersonalizarOpcionesParaRequerimientosDeCumplimiento = permitirPersonalizarOpcionesParaRequerimientosDeCumplimiento;
	}
	public boolean isRequiereNumeroDeReferenciaDelDocumento() {
		return requiereNumeroDeReferenciaDelDocumento;
	}
	public void setRequiereNumeroDeReferenciaDelDocumento(boolean requiereNumeroDeReferenciaDelDocumento) {
		this.requiereNumeroDeReferenciaDelDocumento = requiereNumeroDeReferenciaDelDocumento;
	}
	public boolean isRequiereDocumentoFisico() {
		return requiereDocumentoFisico;
	}
	public void setRequiereDocumentoFisico(boolean requiereDocumentoFisico) {
		this.requiereDocumentoFisico = requiereDocumentoFisico;
	}
	public boolean isPermitirPersonalizarOpcionesParaRequerimientosDocumentales() {
		return permitirPersonalizarOpcionesParaRequerimientosDocumentales;
	}
	public void setPermitirPersonalizarOpcionesParaRequerimientosDocumentales(
			boolean permitirPersonalizarOpcionesParaRequerimientosDocumentales) {
		this.permitirPersonalizarOpcionesParaRequerimientosDocumentales = permitirPersonalizarOpcionesParaRequerimientosDocumentales;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OpcionesDeRequerimientoEmbeddable [numeroMinimoDeArchivosAdjuntos=")
				.append(numeroMinimoDeArchivosAdjuntos).append(", numeroMaximoDeArchivosAdjuntos=")
				.append(numeroMaximoDeArchivosAdjuntos).append(", tamanoMaximoPorArchivoAdjuntos=")
				.append(tamanoMaximoPorArchivoAdjuntos).append(", permitirPersonalizarOpcionesDeArchivosAdjuntos=")
				.append(permitirPersonalizarOpcionesDeArchivosAdjuntos)
				.append(", habilitarOpcionParaAdjuntarArchivosEnDispositivosMoviles=")
				.append(habilitarOpcionParaAdjuntarArchivosEnDispositivosMoviles)
				.append(", permitirPersonalizarOpcionesParaDispositivosMoviles=")
				.append(permitirPersonalizarOpcionesParaDispositivosMoviles).append(", ");
		if (textoPreguntaRequerimientoDeCumplimiento != null)
			builder.append("textoPreguntaRequerimientoDeCumplimiento=").append(textoPreguntaRequerimientoDeCumplimiento)
					.append(", ");
		builder.append("requiereNotasEnCasoDeConformidad=").append(requiereNotasEnCasoDeConformidad)
				.append(", requiereNotasEnCasoDeNoConformidad=").append(requiereNotasEnCasoDeNoConformidad)
				.append(", permitirPersonalizarOpcionesParaRequerimientosDeCumplimiento=")
				.append(permitirPersonalizarOpcionesParaRequerimientosDeCumplimiento)
				.append(", requiereNumeroDeReferenciaDelDocumento=").append(requiereNumeroDeReferenciaDelDocumento)
				.append(", requiereDocumentoFisico=").append(requiereDocumentoFisico)
				.append(", permitirPersonalizarOpcionesParaRequerimientosDocumentales=")
				.append(permitirPersonalizarOpcionesParaRequerimientosDocumentales).append("]");
		return builder.toString();
	}
}
