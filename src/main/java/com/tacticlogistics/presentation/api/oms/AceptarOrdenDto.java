package com.tacticlogistics.presentation.api.oms;

import java.util.List;

import com.tacticlogistics.infrastructure.services.Basic;

public class AceptarOrdenDto {
	private Integer usuarioId;
	private List<Integer> ids;
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

	public String getNotas() {
		return notas;
	}
	public void setNotas(String notas) {
		this.notas = Basic.coalesce(notas, "");
	}
}
