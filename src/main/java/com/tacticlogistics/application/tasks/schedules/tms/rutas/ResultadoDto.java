package com.tacticlogistics.application.tasks.schedules.tms.rutas;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tacticlogistics.infrastructure.services.Basic;

public class ResultadoDto {
    @JsonProperty("ruta")
    private String movil;
    @JsonProperty("status")
    private String status;
    @JsonProperty("error")
    private String error;

    public ResultadoDto() {
        this("", "", "");
    }
    

    public ResultadoDto(String movil, String status, String error) {
        super();
        this.setMovil(movil);
        this.setStatus(status);
        this.setError(error);
    }

    public String getMovil() {
        return movil;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    protected void setMovil(String value) {
        this.movil = coalesce(value, "").toLowerCase();
    }

    protected void setStatus(String value) {
        this.status = coalesce(value, "");
    }

    protected void setError(String value) {
        this.error = Basic.substringSafe(coalesce(value, ""), 0, 200);
    }

    @Override
	public String toString() {
		return "ResultadoDto [" + (movil != null ? "movil=" + movil + ", " : "")
				+ (status != null ? "status=" + status + ", " : "") + (error != null ? "error=" + error : "") + "]";
	}
}
