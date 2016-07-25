package com.tacticlogistics.application.dto.seguridad;

import java.util.List;

import com.tacticlogistics.application.dto.wms.BodegaDto;

public class RespuestaLoginDto {
	private final boolean ok;
	private final String mensaje;
	private final UserDto usuario;
	private final List<BodegaDto> bodegas;

	/**
	 * @param usuario
	 * @param oK
	 */
	public RespuestaLoginDto(boolean ok, String mensaje, UserDto usuario, List<BodegaDto> bodegas) {
		super();
		this.ok = ok;
		this.mensaje = mensaje;
		this.usuario = usuario;
		this.bodegas = bodegas;
	}

	public boolean isOk() {
		return this.ok;
	}

	public String getMensaje() {
		return mensaje;
	}

	public UserDto getUsuario() {
		return usuario;
	}

	public List<BodegaDto> getBodegas() {
		return bodegas;
	}
}
