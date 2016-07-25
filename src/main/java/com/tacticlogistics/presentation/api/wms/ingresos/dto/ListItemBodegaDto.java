package com.tacticlogistics.presentation.api.wms.ingresos.dto;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListItemBodegaDto {
    @JsonProperty("bodegaId")
    private Integer id;
    @JsonProperty("bodegaCodigo")
    private String codigo;
    @JsonProperty("bodegaNombre")
    private String nombre;
    @JsonProperty("bodegaCiudadNombre")
    private String ciudadNombre;
    @JsonProperty("bodegaDireccion")
    private String direccion;

    public ListItemBodegaDto(Integer id, String codigo, String nombre, String ciudadNombre, String direccion) {
        super();
        this.setId(id);
        this.setCodigo(codigo);
        this.setNombre(nombre);
        this.setCiudadNombre(ciudadNombre);
        this.setDireccion(direccion);
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

    public String getCiudadNombre() {
        return ciudadNombre;
    }

    public String getDireccion() {
        return direccion;
    }

    protected void setId(Integer value) {
        this.id = value;
    }

    protected void setCodigo(String value) {
        this.codigo = coalesce(value, "");
    }

    protected void setNombre(String value) {
        this.nombre = coalesce(value, "");
    }

    protected void setCiudadNombre(String value) {
        this.ciudadNombre = coalesce(value, "");
    }

    protected void setDireccion(String value) {
        this.direccion = coalesce(value, "");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ListItemBodegaDto [");
        if (id != null) {
            builder.append("id=").append(id).append(", ");
        }
        if (codigo != null) {
            builder.append("codigo=").append(codigo).append(", ");
        }
        if (nombre != null) {
            builder.append("nombre=").append(nombre).append(", ");
        }
        if (ciudadNombre != null) {
            builder.append("ciudadNombre=").append(ciudadNombre).append(", ");
        }
        if (direccion != null) {
            builder.append("direccion=").append(direccion);
        }
        builder.append("]");
        return builder.toString();
    }
}
