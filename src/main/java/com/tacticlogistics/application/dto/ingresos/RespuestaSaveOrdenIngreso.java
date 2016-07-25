package com.tacticlogistics.application.dto.ingresos;

public class RespuestaSaveOrdenIngreso {
	private final boolean OK;
	private final Integer Id;
	private final Integer token;
	
	public RespuestaSaveOrdenIngreso(boolean oK, Integer id, Integer token) {
		super();
		OK = oK;
		Id = id;
		this.token = token;
	}

	public boolean isOK() {
		return OK;
	}

	public Integer getId() {
		return Id;
	}

	public Integer getToken() {
		return token;
	}

	
}
