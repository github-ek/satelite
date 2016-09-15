package com.tacticlogistics.application.tasks.schedules.tms.rutas;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuscripcionDto {
    @JsonProperty("finaliza_ruta")
    private List<String> finalizaRuta = new ArrayList<>();
    @JsonProperty("siguiente_destino")
    private List<String> siguienteDestino = new ArrayList<>();

    public List<String> getFinalizaRuta() {
        return finalizaRuta;
    }

    public void setFinalizaRuta(List<String> finalizaRuta) {
        this.finalizaRuta = finalizaRuta;
    }

    public List<String> getSiguienteDestino() {
        return siguienteDestino;
    }

    public void setSiguienteDestino(List<String> siguienteDestino) {
        this.siguienteDestino = siguienteDestino;
    }

    @Override
    public String toString() {
        final int maxLen = 5;
        StringBuilder builder = new StringBuilder();
        builder.append("Suscripcion [");
        if (finalizaRuta != null) {
            builder.append("finalizaRuta=").append(finalizaRuta.subList(0, Math.min(finalizaRuta.size(), maxLen)))
                    .append(", ");
        }
        if (siguienteDestino != null) {
            builder.append("siguienteDestino=")
                    .append(siguienteDestino.subList(0, Math.min(siguienteDestino.size(), maxLen)));
        }
        builder.append("]");
        return builder.toString();
    }
    
    
}
