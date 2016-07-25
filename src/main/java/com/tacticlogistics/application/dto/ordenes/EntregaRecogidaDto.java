package com.tacticlogistics.application.dto.ordenes;

import java.util.Date;

public class EntregaRecogidaDto {
    private Date fechaMinima;

    private Date fechaMaxima;

    private Date fechaEntregaMaximaSegunPromesaServicio;

    private String horaMinima;

    private String horaMaxima;

    public Date getFechaMinima() {
        return fechaMinima;
    }

    public void setFechaMinima(Date fechaMinima) {
        this.fechaMinima = fechaMinima;
    }

    public Date getFechaMaxima() {
        return fechaMaxima;
    }

    public void setFechaMaxima(Date fechaMaxima) {
        this.fechaMaxima = fechaMaxima;
    }

    public Date getFechaEntregaMaximaSegunPromesaServicio() {
        return fechaEntregaMaximaSegunPromesaServicio;
    }

    public void setFechaEntregaMaximaSegunPromesaServicio(Date fechaEntregaMaximaSegunPromesaServicio) {
        this.fechaEntregaMaximaSegunPromesaServicio = fechaEntregaMaximaSegunPromesaServicio;
    }

    public String getHoraMinima() {
        return horaMinima;
    }

    public void setHoraMinima(String horaMinima) {
        this.horaMinima = horaMinima;
    }

    public String getHoraMaxima() {
        return horaMaxima;
    }

    public void setHoraMaxima(String horaMaxima) {
        this.horaMaxima = horaMaxima;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrdenDatosEntregaRecogidaViewModel [");
        if (fechaMinima != null)
            builder.append("fechaMinima=").append(fechaMinima).append(", ");
        if (fechaMaxima != null)
            builder.append("fechaMaxima=").append(fechaMaxima).append(", ");
        if (fechaEntregaMaximaSegunPromesaServicio != null)
            builder.append("fechaEntregaMaximaSegunPromesaServicio=").append(fechaEntregaMaximaSegunPromesaServicio)
                    .append(", ");
        if (horaMinima != null)
            builder.append("horaMinima=").append(horaMinima).append(", ");
        if (horaMaxima != null)
            builder.append("horaMaxima=").append(horaMaxima);
        builder.append("]");
        return builder.toString();
    }

}