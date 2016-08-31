package com.tacticlogistics.presentation.api.oms;

import java.util.List;

import com.tacticlogistics.domain.model.oms.EstadoOrdenType;

public class AnularOrdenDto {
	private Integer usuarioId;
	private List<Integer> ids;
	private int causalId;
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

	public int getCausalId() {
		return causalId;
	}

	public void setCausalId(int causalId) {
		this.causalId = causalId;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}
}
