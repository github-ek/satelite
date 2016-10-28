package com.tacticlogistics.application.dto.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tacticlogistics.domain.model.common.SeveridadType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class MensajesDTO<T> {

	private T data;

	@Setter(AccessLevel.NONE)
	private List<MensajeDTO> mensajes = new ArrayList<>();

	// ---------------------------------------------------------------------------------------------------------
	public MensajesDTO() {
		this.setData(null);
	}

	// ---------------------------------------------------------------------------------------------------------
	public SeveridadType getSeveridadMaxima() {
		// @formatter:off
		Optional<SeveridadType> severidad = mensajes
				.stream()
				.map(a -> a.getSeveridad())
				.distinct()
				.sorted()
				.findFirst();
		// @formatter:on
		return (severidad.isPresent()) ? severidad.get() : SeveridadType.INFO;
	}

	public boolean hasErrors() {
		switch (this.getSeveridadMaxima()) {
		case FATAL:
		case ERROR:
			return true;
		default:
			return false;
		}
	}

	// ---------------------------------------------------------------------------------------------------------
	public List<MensajeDTO> getMensajes() {
		return Collections.unmodifiableList(mensajes);
	}

	public int size() {
		return mensajes.size();
	}

	@JsonIgnore
	public boolean isEmpty() {
		return mensajes.isEmpty();
	}

	public void clear() {
		mensajes.clear();
	}

	public boolean add(SeveridadType severidad, String texto) {
		return this.add(severidad, "", texto, "", "", null);
	}

	public boolean add(SeveridadType severidad, String texto, String objeto, String atributo, Object data) {
		return this.add(severidad, "", texto, objeto, atributo, data);
	}

	public boolean add(SeveridadType severidad, String codigo, String texto, String objeto, String atributo,
			Object data) {
		return this.add(new MensajeDTO(severidad, codigo, texto, objeto, atributo, data));
	}

	public boolean add(Exception e) {
		return this.add(e, "", "", null);
	}

	public boolean add(Exception e, String objeto, String atributo, Object data) {
		StringBuilder sb = new StringBuilder();
		sb.append("Ocurrio una excepción de tipo:").append(e.getClass().getName()).append(".");

		if (e.getMessage() != null) {
			sb.append("El mensaje de la excepción es: ").append(e.getMessage());
		}

		return this.add(new MensajeDTO(SeveridadType.ERROR, sb.toString(), objeto, atributo, data));
	}

	public boolean add(MensajeDTO e) {
		return mensajes.add(e);
	}

	public boolean addAll(Collection<? extends MensajeDTO> c) {
		return mensajes.addAll(c);
	}

	public boolean addAll(MensajesDTO<?> dto) {
		return this.mensajes.addAll(dto.mensajes);
	}
}
