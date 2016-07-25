package com.tacticlogistics.application.dto.cpr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RealizarCorteDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date fecha;

    private List<String> numeroOrdenList;

    public RealizarCorteDto(Date fecha, List<String> emails) {
        super();
        this.setFecha(new Date());
        this.setNumeroOrdenList(new ArrayList<>());
    }

    public Date getFecha() {
        return fecha;
    }

    public List<String> getNumeroOrdenList() {
        return numeroOrdenList;
    }

    protected void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    protected void setNumeroOrdenList(List<String> numeroOrdenList) {
        this.numeroOrdenList = numeroOrdenList;
    }

    protected RealizarCorteDto() {
        super();
    }

    @Override
    public String toString() {
        final int maxLen = 5;
        StringBuilder builder = new StringBuilder();
        builder.append("RealizarCorteDto [");
        if (fecha != null) {
            builder.append("fecha=").append(fecha).append(", ");
        }
        if (numeroOrdenList != null) {
            builder.append("numeroOrdenList=")
                    .append(numeroOrdenList.subList(0, Math.min(numeroOrdenList.size(), maxLen)));
        }
        builder.append("]");
        return builder.toString();
    }

}
