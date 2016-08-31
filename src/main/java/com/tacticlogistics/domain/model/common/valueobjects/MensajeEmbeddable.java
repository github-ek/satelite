package com.tacticlogistics.domain.model.common.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import com.tacticlogistics.domain.model.common.Mensaje;
import com.tacticlogistics.domain.model.common.SeveridadType;

@Embeddable
public class MensajeEmbeddable implements Mensaje {

	@Enumerated(EnumType.STRING)
	@Column(name = "id_severidad", nullable = false, length = 20)
	private SeveridadType severidad;
	@Column(nullable = false, length = 20)
	private String codigo;
	@Transient
	private String data;
	@Column(nullable = false, length = 200)
	private String texto;
	@Column(nullable = false, length = 100)
	private String grupo;

	protected MensajeEmbeddable() {
		super();
	}

	public MensajeEmbeddable(SeveridadType severidad, String codigo, String texto, String grupo) {
		super();
		this.severidad = severidad;
		this.codigo = codigo;
		this.texto = texto;
		this.grupo = grupo;
	}

	@Override
	public SeveridadType getSeveridad() {
		return severidad;
	}

	@Override
	public String getCodigo() {
		return codigo;
	}

	@Override
	public String getTexto() {
		return texto;
	}

	@Override
	public String getGrupo() {
		return grupo;
	}

	@Override
	public Object getData() {
		return data ;
	}
}
