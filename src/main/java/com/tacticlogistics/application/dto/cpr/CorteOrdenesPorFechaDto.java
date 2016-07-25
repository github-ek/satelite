package com.tacticlogistics.application.dto.cpr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CorteOrdenesPorFechaDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date fecha;

    private List<String> emails;

    public CorteOrdenesPorFechaDto(Date fecha, List<String> emails) {
        super();
        this.setFecha(new Date());
        this.setEmails(new ArrayList<>());
    }

    public Date getFecha() {
        return fecha;
    }

    public List<String> getEmails() {
        return emails;
    }

    protected CorteOrdenesPorFechaDto() {
        super();
    }

    protected void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    protected void setEmails(List<String> emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        final int maxLen = 5;
        StringBuilder builder = new StringBuilder();
        builder.append("CorteOrdenesPorFechaDto [");
        if (fecha != null) {
            builder.append("fecha=").append(fecha).append(", ");
        }
        if (emails != null) {
            builder.append("emails=").append(emails.subList(0, Math.min(emails.size(), maxLen)));
        }
        builder.append("]");
        return builder.toString();
    }

}
