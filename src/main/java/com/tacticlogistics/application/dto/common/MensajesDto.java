package com.tacticlogistics.application.dto.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tacticlogistics.domain.model.common.SeveridadType;

public class MensajesDto {
	private SeveridadType severidadMaxima;
	private Object data;
	private List<MensajeDto> mensajes;

	public MensajesDto() {
		super();
		this.setSeveridadMaxima(SeveridadType.INFO);
		this.setData(null);
		this.setMensajes(new ArrayList<>());
	}

	public SeveridadType getSeveridadMaxima() {
		return severidadMaxima;
	}

	public Object getData() {
		return data;
	}

	public List<MensajeDto> getMensajes() {
		return mensajes;
	}

	public void setSeveridadMaxima(SeveridadType severidadMaxima) {
		this.severidadMaxima = severidadMaxima;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setMensajes(List<MensajeDto> mensajes) {
		if (mensajes == null) {
			mensajes = new ArrayList<>();
		}
		this.mensajes = mensajes;
	}

	// ----------------------------------------------------------------------------------------------------------------
	public MensajesDto addMensaje(SeveridadType severidad, String texto) {
		this.addMensaje(severidad, null, texto);
		return this;
	}

	public MensajesDto addMensaje(SeveridadType severidad, Object data, String texto) {
		this.AddMensaje(new MensajeDto(severidad, data, texto));
		return this;
	}

	public MensajesDto addMensaje(SeveridadType severidad, String codigo, Object data, String texto, String grupo) {
		this.AddMensaje(new MensajeDto(severidad, codigo, data, texto, grupo));
		return this;
	}

	public MensajesDto addMensaje(Exception e) {
		this.addMensaje(e, null);
		return this;
	}

	public MensajesDto addMensaje(Exception e, Object data) {
		StringBuilder sb = new StringBuilder();
		sb.append("Ocurrio una excepción de tipo:").append(e.getClass().getName()).append(".");

		if (e.getMessage() != null) {
			sb.append("El mensaje de la excepción es: ").append(e.getMessage());
		}

		this.AddMensaje(new MensajeDto(SeveridadType.FATAL, data, sb.toString()));

		return this;
	}

	public MensajesDto AddMensaje(MensajeDto mensaje) {
		mensajes.add(mensaje);
		Optional<SeveridadType> severidad = mensajes.stream().map(a -> a.getSeveridad()).distinct().sorted()
				.findFirst();
		this.severidadMaxima = (severidad.isPresent()) ? severidad.get() : SeveridadType.INFO;
		return this;
	}
	
	public MensajesDto AddMensajes(MensajesDto mensajes) {
		for (MensajeDto msj: mensajes.getMensajes()) {
			this.AddMensaje(msj);
		}
		return this;
	}

	@Override
	public String toString() {
		final int maxLen = 5;
		StringBuilder builder = new StringBuilder();
		builder.append("MensajesDto [");
		if (severidadMaxima != null) {
			builder.append("severidadMaxima=").append(severidadMaxima).append(", ");
		}
		if (data != null) {
			builder.append("data=").append(data).append(", ");
		}
		if (mensajes != null) {
			builder.append("mensajes=").append(mensajes.subList(0, Math.min(mensajes.size(), maxLen)));
		}
		builder.append("]");
		return builder.toString();
	}
}
