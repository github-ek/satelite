package com.tacticlogistics.presentation.api.wms.ingresos.dto;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListItemTransportadoraDto {
    @JsonProperty("transportadoraId")
    private Integer id;
    @JsonProperty("transportadoraCodigo")
    private String codigo;
    @JsonProperty("transportadoraNombre")
    private String nombre;
    @JsonProperty("transportadoraNumeroIdentificacion")
    private String numeroIdentificacion;

    public ListItemTransportadoraDto(Integer id, String codigo, String nombre, String numeroIdentificacion) {
        super();
        this.setId(id);
        this.setCodigo(codigo);
        this.setNombre(nombre);
        this.setNumeroIdentificacion(numeroIdentificacion);
    }

    public Integer getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    protected void setCodigo(String value) {
        this.codigo = coalesce(value, "");
    }

    protected void setNombre(String value) {
        this.nombre = coalesce(value, "");
    }

    protected void setNumeroIdentificacion(String value) {
        this.numeroIdentificacion = coalesce(value, "");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ListItemTransportadoraDto [");
        if (id != null) {
            builder.append("id=").append(id).append(", ");
        }
        if (codigo != null) {
            builder.append("codigo=").append(codigo).append(", ");
        }
        if (nombre != null) {
            builder.append("nombre=").append(nombre).append(", ");
        }
        if (numeroIdentificacion != null) {
            builder.append("numeroIdentificacion=").append(numeroIdentificacion);
        }
        builder.append("]");
        return builder.toString();
    }
}
