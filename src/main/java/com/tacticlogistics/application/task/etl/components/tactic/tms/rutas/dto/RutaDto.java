package com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RutaDto {
    @JsonProperty("ruta")
    private String identificadorMovil;
    @JsonProperty("primaria")
    private boolean distribucionPrimaria;
    @JsonProperty("clientes")
    private List<LineaRutaDto> lineas;
    private List<DocumentoDto> documentos = new ArrayList<>();

    public RutaDto() {
        super();
        this.setIdentificadorMovil("");
        this.setDistribucionPrimaria(false);
        this.setLineas(new ArrayList<>());
        this.setDocumentos(new ArrayList<>());
    }

    public RutaDto(String identificadorMovil, boolean distribucionPrimaria) {
        this();
        this.setIdentificadorMovil(identificadorMovil);
        this.setDistribucionPrimaria(distribucionPrimaria);
    }

    public String getIdentificadorMovil() {
        return identificadorMovil;
    }

    public boolean isDistribucionPrimaria() {
        return distribucionPrimaria;
    }

    public List<LineaRutaDto> getLineas() {
        if (lineas == null) {
            lineas = new ArrayList<>();
        }
        return lineas;
    }

    public List<DocumentoDto> getDocumentos() {
        if (documentos == null) {
            documentos = new ArrayList<>();
        }
        return documentos;
    }

    public void setIdentificadorMovil(String value) {
        this.identificadorMovil = coalesce(value, "").toLowerCase();
    }

    public void setDistribucionPrimaria(boolean value) {
        this.distribucionPrimaria = value;
    }

    public void setLineas(List<LineaRutaDto> value) {
        if (value == null) {
            value = new ArrayList<>();
        }
        this.lineas = value;
    }

    public void setDocumentos(List<DocumentoDto> value) {
        if (value == null) {
            value = new ArrayList<>();
        }
        this.documentos = value;
    }

    @Override
    public String toString() {
        final int maxLen = 5;
        StringBuilder builder = new StringBuilder();
        builder.append("Ruta [");
        if (identificadorMovil != null) {
            builder.append("identificadorMovil=").append(identificadorMovil).append(", ");
        }
        builder.append("distribucionPrimaria=").append(distribucionPrimaria).append(", ");
        if (lineas != null) {
            builder.append("lineas=").append(lineas.subList(0, Math.min(lineas.size(), maxLen))).append(", ");
        }
        if (documentos != null) {
            builder.append("documentos=").append(documentos.subList(0, Math.min(documentos.size(), maxLen)));
        }
        builder.append("]");
        return builder.toString();
    }
}
