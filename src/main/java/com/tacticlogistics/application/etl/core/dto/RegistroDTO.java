package com.tacticlogistics.application.etl.core.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class RegistroDTO<T> {
	@Getter(AccessLevel.NONE)
	private String registro;
	@NonNull
	private Status estado;
	@Getter(AccessLevel.NONE)
	private String causaError;
	private T data;

	public String getRegistro() {
		if (registro == null) {
			registro = "";
		}
		return registro;
	}

	public String getCausaError() {
		if (causaError == null) {
			causaError = "";
		}
		return causaError;
	}

	public enum Status {
		IGNORADO, ACEPTADO, ERROR
	}
}
