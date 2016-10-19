package com.tacticlogistics.application.dto.common;

import com.tacticlogistics.domain.model.common.Mensaje;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.infrastructure.services.Basic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MensajeDTO implements Mensaje {
	private final SeveridadType severidad;
	private final String codigo;
	private final String texto;
	private final String objeto;
	private final String atributo;
	private final Object data;

	public MensajeDTO(SeveridadType severidad, String texto) {
		this(severidad, "", texto, "", "", null);
	}

	public MensajeDTO(SeveridadType severidad, String codigo, String texto) {
		this(severidad, codigo, texto, "", "", null);
	}

	public MensajeDTO(SeveridadType severidad, String texto, String objeto, String atributo, Object data) {
		this(severidad, "", texto, objeto, atributo, data);
	}

	public MensajeDTO(SeveridadType severidad, String codigo, String texto, String objeto, String atributo,
			Object data) {
		super();
		this.severidad = severidad;
		this.codigo = Basic.coalesce(codigo, "");
		this.texto = Basic.coalesce(texto, "");
		this.objeto = objeto;
		this.atributo = atributo;
		this.data = data;
	}
}
