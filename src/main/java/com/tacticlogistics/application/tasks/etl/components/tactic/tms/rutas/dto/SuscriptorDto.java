package com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas.dto;

import com.tacticlogistics.infrastructure.services.Basic;

public class SuscriptorDto {
    private Integer clienteId;
    private String clienteCodigo;
    private String email;
    private String nombres;
    private String telefono;

    public SuscriptorDto(Integer clienteId, String clienteCodigo, String email, String nombres, String telefono) {
        super();
        this.setClienteId(clienteId);
        this.setClienteCodigo(clienteCodigo);
        this.setEmail(email);
        this.setNombres(nombres);
        this.setTelefono(telefono);
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public String getClienteCodigo() {
        return clienteCodigo;
    }

    public String getEmail() {
        return email;
    }

    public String getNombres() {
        return nombres;
    }

    public String getTelefono() {
        return telefono;
    }

    protected void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    protected void setClienteCodigo(String value) {
        this.clienteCodigo = Basic.coalesce(value, "");
    }

    protected void setEmail(String value) {
        this.email = Basic.coalesce(value, "");
    }

    protected void setNombres(String value) {
        this.nombres = Basic.coalesce(value, "");
    }

    protected void setTelefono(String value) {
        this.telefono = Basic.coalesce(value, "");
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SuscriptorDto [");
        if (clienteId != null) {
            builder.append("clienteId=").append(clienteId).append(", ");
        }
        if (clienteCodigo != null) {
            builder.append("clienteCodigo=").append(clienteCodigo).append(", ");
        }
        if (email != null) {
            builder.append("email=").append(email).append(", ");
        }
        if (nombres != null) {
            builder.append("nombres=").append(nombres).append(", ");
        }
        if (telefono != null) {
            builder.append("telefono=").append(telefono);
        }
        builder.append("]");
        return builder.toString();
    }
}
