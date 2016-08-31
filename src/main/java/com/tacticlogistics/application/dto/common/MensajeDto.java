package com.tacticlogistics.application.dto.common;

import com.tacticlogistics.domain.model.common.Mensaje;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.infrastructure.services.Basic;

public class MensajeDto implements Mensaje {
	private final SeveridadType severidad;
	private final String codigo;
	private Object data;
	private String grupo;
	private String texto;

	public MensajeDto(SeveridadType severidad, String texto) {
		this(severidad, "", null, texto, "");
	}

	public MensajeDto(SeveridadType severidad, String codigo, String texto, String grupo) {
		this(severidad, codigo, null, texto, grupo);
	}

	public MensajeDto(SeveridadType severidad, Object data, String texto) {
		this(severidad, "", data, texto, "");
	}

	public MensajeDto(SeveridadType severidad, String codigo, Object data, String texto, String grupo) {
		super();
		this.severidad = severidad;
		this.codigo = Basic.coalesce(codigo, "");
		this.data = data;
		this.texto = Basic.coalesce(texto, "");
		this.grupo = Basic.coalesce(grupo, "");
	}

	@Override
	public SeveridadType getSeveridad() {
		return severidad;
	}

	public Object getData() {
		return data;
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
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MensajeDto [");
		if (severidad != null) {
			builder.append("severidad=").append(severidad).append(", ");
		}
		if (codigo != null) {
			builder.append("codigo=").append(codigo).append(", ");
		}
		if (data != null) {
			builder.append("data=").append(data).append(", ");
		}
		if (grupo != null) {
			builder.append("grupo=").append(grupo).append(", ");
		}
		if (texto != null) {
			builder.append("texto=").append(texto);
		}
		builder.append("]");
		return builder.toString();
	}
}
