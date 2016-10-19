package com.tacticlogistics.application.dto.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tacticlogistics.clientes.dicermex.compras.erp.prealertas.dto.LineaOrdenDeCompraDto;
import com.tacticlogistics.clientes.dicermex.compras.erp.prealertas.dto.OrdenDeCompraDTO;
import com.tacticlogistics.domain.model.common.SeveridadType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class MensajesDto {

	@Setter(AccessLevel.NONE)
	private List<MensajeDTO> mensajes = new ArrayList<>();

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

	public MensajeDTO get(int index) {
		return mensajes.get(index);
	}

	public int size() {
		return mensajes.size();
	}

	@JsonIgnore
	public boolean isEmpty() {
		return mensajes.isEmpty();
	}

	public Stream<MensajeDTO> stream() {
		return mensajes.stream();
	}

	public void forEach(Consumer<? super MensajeDTO> action) {
		mensajes.forEach(action);
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

	public boolean addAll(MensajesDto dto) {
		return this.mensajes.addAll(dto.mensajes);
	}
}
