package com.tacticlogistics.application.tasks.schedules.tms.rutas;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultadosDto {
    @JsonProperty("status")
    private String status;
    @JsonProperty("rutas")
    private List<ResultadoDto> rutas;

    public ResultadosDto() {
        this("", null);
    }

    public ResultadosDto(String status, List<ResultadoDto> rutas) {
        super();
        this.setStatus(status);
        this.setRutas(rutas);
    }

    public String getStatus() {
        return status;
    }

    public List<ResultadoDto> getRutas() {
        if (rutas == null) {
            rutas = new ArrayList<>();
        }

        return rutas;
    }

    protected void setStatus(String value) {
        this.status = coalesce(value, "");
    }

    protected void setRutas(List<ResultadoDto> value) {
        if (value == null) {
            value = new ArrayList<>();
        }
        this.rutas = value;
    }

    @Override
    public String toString() {
        final int maxLen = 5;
        StringBuilder builder = new StringBuilder();
        builder.append("ResultadosProgramacionRutas [");
        if (status != null) {
            builder.append("status=").append(status).append(", ");
        }
        if (rutas != null) {
            builder.append("rutas=").append(rutas.subList(0, Math.min(rutas.size(), maxLen)));
        }
        builder.append("]");
        return builder.toString();
    }
}
