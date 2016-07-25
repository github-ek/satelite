package com.tacticlogistics.domain.model.requerimientos.valueobjects;

import javax.persistence.Embeddable;

@Embeddable
public class PermisosParaPersonalizarOpcionesDeRequerimientoEmbeddable {

	private boolean puedePersonalizarOpcionesDeArchivosAdjuntos;

	//--------------------------------------------------------------------------------------------------------------------------------------
	private boolean puedePersonalizarOpcionesParaDispositivosMoviles;
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	private boolean puedePersonalizarOpcionesParaRequerimientosDeCumplimiento;
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	private boolean puedePersonalizarOpcionesParaRequerimientosDocumentales;

	public boolean isPuedePersonalizarOpcionesDeArchivosAdjuntos() {
		return puedePersonalizarOpcionesDeArchivosAdjuntos;
	}

	public void setPuedePersonalizarOpcionesDeArchivosAdjuntos(boolean puedePersonalizarOpcionesDeArchivosAdjuntos) {
		this.puedePersonalizarOpcionesDeArchivosAdjuntos = puedePersonalizarOpcionesDeArchivosAdjuntos;
	}

	public boolean isPuedePersonalizarOpcionesParaDispositivosMoviles() {
		return puedePersonalizarOpcionesParaDispositivosMoviles;
	}

	public void setPuedePersonalizarOpcionesParaDispositivosMoviles(
			boolean puedePersonalizarOpcionesParaDispositivosMoviles) {
		this.puedePersonalizarOpcionesParaDispositivosMoviles = puedePersonalizarOpcionesParaDispositivosMoviles;
	}

	public boolean isPuedePersonalizarOpcionesParaRequerimientosDeCumplimiento() {
		return puedePersonalizarOpcionesParaRequerimientosDeCumplimiento;
	}

	public void setPuedePersonalizarOpcionesParaRequerimientosDeCumplimiento(
			boolean puedePersonalizarOpcionesParaRequerimientosDeCumplimiento) {
		this.puedePersonalizarOpcionesParaRequerimientosDeCumplimiento = puedePersonalizarOpcionesParaRequerimientosDeCumplimiento;
	}

	public boolean isPuedePersonalizarOpcionesParaRequerimientosDocumentales() {
		return puedePersonalizarOpcionesParaRequerimientosDocumentales;
	}

	public void setPuedePersonalizarOpcionesParaRequerimientosDocumentales(
			boolean puedePersonalizarOpcionesParaRequerimientosDocumentales) {
		this.puedePersonalizarOpcionesParaRequerimientosDocumentales = puedePersonalizarOpcionesParaRequerimientosDocumentales;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(
				"PermisosParaPersonalizarOpcionesDeRequerimientoEmbeddable [puedePersonalizarOpcionesDeArchivosAdjuntos=")
				.append(puedePersonalizarOpcionesDeArchivosAdjuntos)
				.append(", puedePersonalizarOpcionesParaDispositivosMoviles=")
				.append(puedePersonalizarOpcionesParaDispositivosMoviles)
				.append(", puedePersonalizarOpcionesParaRequerimientosDeCumplimiento=")
				.append(puedePersonalizarOpcionesParaRequerimientosDeCumplimiento)
				.append(", puedePersonalizarOpcionesParaRequerimientosDocumentales=")
				.append(puedePersonalizarOpcionesParaRequerimientosDocumentales).append("]");
		return builder.toString();
	}


}
