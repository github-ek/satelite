package com.tacticlogistics.presentation.api.wms.ingresos.dto;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListItemTipoVehiculoDto {
    @JsonProperty("tipoVehiculoId")
    private Integer id;
    @JsonProperty("tipoVehiculoCodigo")
    private String codigo;
    @JsonProperty("tipoVehiculoNombre")
    private String nombre;
    @JsonProperty("tipoVehiculoRequiereRemolque")
    private boolean requiereRemolque;

    public ListItemTipoVehiculoDto(Integer id, String codigo, String nombre, boolean requiereRemolque) {
        super();
        this.setId(id);
        this.setCodigo(codigo);
        this.setNombre(nombre);
        this.setRequiereRemolque(requiereRemolque);
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

    public boolean isRequiereRemolque() {
        return requiereRemolque;
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

    protected void setRequiereRemolque(boolean requiereRemolque) {
        this.requiereRemolque = requiereRemolque;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ListItemTipoVehiculoDto [");
        if (id != null) {
            builder.append("id=").append(id).append(", ");
        }
        if (codigo != null) {
            builder.append("codigo=").append(codigo).append(", ");
        }
        if (nombre != null) {
            builder.append("nombre=").append(nombre).append(", ");
        }
        builder.append("requiereRemolque=").append(requiereRemolque).append("]");
        return builder.toString();
    }
}
