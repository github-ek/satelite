package com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

public class LineaRutaDto {
    public static final String dateFormat = "yyyy-MM-dd";
    public static final String timeFormat = "HH:mm";

    @JsonIgnore
    private int ordenId;
    
    @JsonProperty("id")
    private String numeroDocumentoEntrega;
    private int secuencia;

    @JsonProperty("nombre")
    private String destinoNombre;

    @JsonProperty("direccion")
    private String destinoDireccion;

    @JsonProperty("cxD")
    private float cxD;
    @JsonProperty("cyD")
    private float cyD;
    @JsonProperty("cxO")
    private String cxO;
    @JsonProperty("cyO")
    private String cyO;
    @JsonProperty("transportador")
    private String identificadorVehiculo;
    @JsonIgnore
    private String clienteCodigo;
    @JsonProperty("nit")
    private String clienteNumeroIdentificacion;
    @JsonProperty("fechainicial")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaInicial;
    @JsonProperty("fechafinal")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private Date fechaFinal;
    private String hora;
    private Integer recaudo;
    private SuscripcionDto correos;

    public LineaRutaDto() {
        super();
        this.setNumeroDocumentoEntrega("");
        this.setSecuencia(0);
        this.setDestinoNombre("");
        this.setDestinoDireccion("");
        this.setCxD(0);
        this.setCyD(0);
        this.setCxO("");
        this.setCyO("");
        this.setIdentificadorVehiculo("");
        this.setClienteCodigo("");
        this.setClienteNumeroIdentificacion("");
        this.setFechaInicial(null);
        this.setFechaFinal(null);
        this.setHora("");
        this.setRecaudo(0);
        this.setCorreos(null);
    }

    
    public int getOrdenId() {
		return ordenId;
	}

	public String getNumeroDocumentoEntrega() {
        return numeroDocumentoEntrega;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public String getDestinoNombre() {
        return destinoNombre;
    }

    public String getDireccion() {
        return destinoDireccion;
    }

    public float getCxD() {
        return cxD;
    }

    public float getCyD() {
        return cyD;
    }

    public String getCxO() {
        return cxO;
    }

    public String getCyO() {
        return cyO;
    }

    public String getIdentificadorVehiculo() {
        return identificadorVehiculo;
    }

    public String getClienteCodigo() {
        return clienteCodigo;
    }

    public String getClienteNumeroIdentificacion() {
        return clienteNumeroIdentificacion;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public String getHora() {
        return hora;
    }

    public Integer getRecaudo() {
        return recaudo;
    }

    public SuscripcionDto getCorreos() {
        return correos;
    }

	public void setOrdenId(int ordenId) {
		this.ordenId = ordenId;
	}
	
    public void setNumeroDocumentoEntrega(String value) {
        this.numeroDocumentoEntrega = coalesce(value,"");
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public void setDestinoNombre(String value) {
        this.destinoNombre = coalesce(value,"");
    }

    public void setDestinoDireccion(String value) {
        this.destinoDireccion = coalesce(value,"");
    }

    public void setCxD(float cxD) {
        this.cxD = cxD;
    }

    public void setCyD(float cyD) {
        this.cyD = cyD;
    }

    public void setCxO(String value) {
        this.cxO = coalesce(value,"");
    }

    public void setCyO(String value) {
        this.cyO = coalesce(value,"");
    }

    public void setIdentificadorVehiculo(String value) {
        this.identificadorVehiculo = coalesce(value,"");
    }

    public void setClienteCodigo(String value) {
        this.clienteCodigo = coalesce(value,"");
    }

    public void setClienteNumeroIdentificacion(String value) {
        this.clienteNumeroIdentificacion = coalesce(value,"");
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void setHora(String value) {
        this.hora = coalesce(value,"");
    }

    public void setRecaudo(Integer recaudo) {
        this.recaudo = recaudo;
    }

    public void setCorreos(SuscripcionDto correos) {
        this.correos = correos;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LineaRuta [");
        if (numeroDocumentoEntrega != null) {
            builder.append("numeroDocumentoEntrega=").append(numeroDocumentoEntrega).append(", ");
        }
        builder.append("secuencia=").append(secuencia).append(", ");
        if (destinoNombre != null) {
            builder.append("destinoNombre=").append(destinoNombre).append(", ");
        }
        if (destinoDireccion != null) {
            builder.append("destinoDireccion=").append(destinoDireccion).append(", ");
        }
        builder.append("cxD=").append(cxD).append(", cyD=").append(cyD).append(", ");
        if (cxO != null) {
            builder.append("cxO=").append(cxO).append(", ");
        }
        if (cyO != null) {
            builder.append("cyO=").append(cyO).append(", ");
        }
        if (identificadorVehiculo != null) {
            builder.append("identificadorVehiculo=").append(identificadorVehiculo).append(", ");
        }
        if (clienteCodigo != null) {
            builder.append("clienteCodigo=").append(clienteCodigo).append(", ");
        }
        if (clienteNumeroIdentificacion != null) {
            builder.append("clienteNumeroIdentificacion=").append(clienteNumeroIdentificacion).append(", ");
        }
        if (fechaInicial != null) {
            builder.append("fechaInicial=").append(fechaInicial).append(", ");
        }
        if (fechaFinal != null) {
            builder.append("fechaFinal=").append(fechaFinal).append(", ");
        }
        if (hora != null) {
            builder.append("hora=").append(hora).append(", ");
        }
        if (recaudo != null) {
            builder.append("recaudo=").append(recaudo).append(", ");
        }
        if (correos != null) {
            builder.append("correos=").append(correos);
        }
        builder.append("]");
        return builder.toString();
    }
}
