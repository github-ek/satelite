package com.tacticlogistics.application.dto.common;

public class ItemGenerico<ID> {
    private ID id;
    private String codigo;
    private String descripcion;

    public ItemGenerico(ID id, String codigo, String descripcion) {
        super();
        this.setId(id);
        this.setCodigo(codigo);
        this.setDescripcion(descripcion);
    }

    public ID getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    protected void setId(ID id) {
        this.id = id;
    }

    protected void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    protected void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
