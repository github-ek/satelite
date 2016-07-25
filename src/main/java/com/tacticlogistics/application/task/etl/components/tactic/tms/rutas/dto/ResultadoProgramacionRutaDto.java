package com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultadoProgramacionRutaDto {
    @JsonProperty("ruta")
    private String identificadorMovil;
    @JsonProperty("status")
    private String status;
    @JsonProperty("error")
    private String error;

    public ResultadoProgramacionRutaDto() {
        this("", "", "");
    }

    public ResultadoProgramacionRutaDto(String identificadorMovil, String status, String error) {
        super();
        this.setIdentificadorMovil(identificadorMovil);
        this.setStatus(status);
        this.setError(error);
    }

    public String getIdentificadorMovil() {
        return identificadorMovil;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    protected void setIdentificadorMovil(String value) {
        this.identificadorMovil = coalesce(value, "").toLowerCase();
    }

    protected void setStatus(String value) {
        this.status = coalesce(value, "");
    }

    protected void setError(String value) {
        this.error = coalesce(value, "");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResultadoProgramacionRuta [");
        if (identificadorMovil != null) {
            builder.append("identificadorMovil=").append(identificadorMovil).append(", ");
        }
        if (status != null) {
            builder.append("status=").append(status).append(", ");
        }
        if (error != null) {
            builder.append("error=").append(error);
        }
        builder.append("]");
        return builder.toString();
    }
}
