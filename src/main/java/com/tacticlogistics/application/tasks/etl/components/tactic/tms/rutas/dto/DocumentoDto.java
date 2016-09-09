package com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas.dto;

public class DocumentoDto {
    private String nombre;
    private int obligatorio;

    public DocumentoDto(String nombre, int obligatorio) {
        super();
        this.setNombre(nombre);
        this.setObligatorio(obligatorio);
    }

    public String getNombre() {
        return nombre;
    }

    public int getObligatorio() {
        return obligatorio;
    }

    protected void setNombre(String nombre) {
        this.nombre = nombre;
    }

    protected void setObligatorio(int obligatorio) {
        this.obligatorio = obligatorio;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Documento [");
        if (nombre != null) {
            builder.append("nombre=").append(nombre).append(", ");
        }
        builder.append("obligatorio=").append(obligatorio).append("]");
        return builder.toString();
    }
}
