package com.tacticlogistics.presentation.api.oms;

import java.util.List;

import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.infrastructure.services.Basic;

public class CambiarEstadoOrdenDto {
	private Integer usuarioId;
	private List<Integer> ids;
	private EstadoOrdenType nuevoEstadoId;
	private String notas;

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public EstadoOrdenType getNuevoEstadoId() {
		return nuevoEstadoId;
	}

	public void setNuevoEstadoId(EstadoOrdenType nuevoEstadoId) {
		this.nuevoEstadoId = nuevoEstadoId;
	}

	public String getNotas() {
		return notas;
	}
	public void setNotas(String notas) {
		this.notas = Basic.coalesce(notas, "");
	}
}
