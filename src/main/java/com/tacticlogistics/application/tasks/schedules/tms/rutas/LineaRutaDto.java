package com.tacticlogistics.application.tasks.schedules.tms.rutas;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LineaRutaDto {
    public static final String dateFormat = "yyyy-MM-dd";
    public static final String timeFormat = "HH:mm";

    @JsonIgnore
    private Integer ordenId;
    @JsonIgnore
    private Integer clienteId;
    @JsonProperty("id")
    private String numeroOrden;
    @JsonProperty("secuencia")
    private int secuencia;
    @JsonProperty("nombre")
    private String destinoNombre;
    @JsonProperty("direccion")
    private String direccion;
    @JsonProperty("cxD")
    private float cxD;
    @JsonProperty("cyD")
    private float cyD;
    @JsonProperty("cxO")
    private String cxO;
    @JsonProperty("cyO")
    private String cyO;
    @JsonProperty("transportador")
    private String placa;
    @JsonProperty("nit")
    private String clienteNumeroIdentificacion;
    @JsonProperty("fechainicial")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private LocalDate fechaInicial;
    @JsonProperty("fechafinal")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
    private LocalDate fechaFinal;
    @JsonProperty("hora")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = timeFormat)
    private LocalTime hora;
    @JsonProperty("recaudo")
    private Integer recaudo;
    @JsonProperty("correos")
    private SuscripcionDto correos;

    public LineaRutaDto() {
        super();
        this.setOrdenId(null);
        this.setNumeroOrden("");
        this.setSecuencia(0);
        this.setDestinoNombre("");
        this.setDireccion("");
        this.setCxD(0);
        this.setCyD(0);
        this.setCxO("");
        this.setCyO("");
        this.setPlaca("");
        this.setClienteNumeroIdentificacion("");
        this.setFechaInicial(null);
        this.setFechaFinal(null);
        this.setHora(null);
        this.setRecaudo(0);
        this.setCorreos(null);
    }

	public Integer getOrdenId() {
		return ordenId;
	}

	protected Integer getClienteId() {
		return clienteId;
	}

	public String getNumeroOrden() {
        return numeroOrden;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public String getDestinoNombre() {
        return destinoNombre;
    }

    public String getDireccion() {
        return direccion;
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

    public String getPlaca() {
        return placa;
    }

    public String getClienteNumeroIdentificacion() {
        return clienteNumeroIdentificacion;
    }

    public LocalDate getFechaInicial() {
        return fechaInicial;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public LocalTime getHora() {
        return hora;
    }

    public Integer getRecaudo() {
        return recaudo;
    }

    public SuscripcionDto getCorreos() {
        return correos;
    }
    
	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public void setOrdenId(Integer ordenId) {
		this.ordenId = ordenId;
	}
	
    public void setNumeroOrden(String value) {
        this.numeroOrden = coalesce(value,"");
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public void setDestinoNombre(String value) {
        this.destinoNombre = coalesce(value,"");
    }

    public void setDireccion(String value) {
        this.direccion = coalesce(value,"");
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

    public void setPlaca(String value) {
        this.placa = coalesce(value,"");
    }

    public void setClienteNumeroIdentificacion(String value) {
        this.clienteNumeroIdentificacion = coalesce(value,"");
    }

    public void setFechaInicial(LocalDate fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void setHora(LocalTime value) {
        this.hora = value;
    }

    public void setRecaudo(Integer recaudo) {
        this.recaudo = recaudo;
    }

    public void setCorreos(SuscripcionDto correos) {
        this.correos = correos;
    }

    @Override
	public String toString() {
		return "LineaRutaDto [ordenId=" + ordenId + ", "
				+ (numeroOrden != null ? "numeroOrden=" + numeroOrden + ", " : "") + "secuencia=" + secuencia + ", "
				+ (destinoNombre != null ? "destinoNombre=" + destinoNombre + ", " : "")
				+ (direccion != null ? "direccion=" + direccion + ", " : "") + "cxD=" + cxD + ", cyD=" + cyD + ", "
				+ (cxO != null ? "cxO=" + cxO + ", " : "") + (cyO != null ? "cyO=" + cyO + ", " : "")
				+ (placa != null ? "placa=" + placa + ", " : "")
				+ (clienteNumeroIdentificacion != null
						? "clienteNumeroIdentificacion=" + clienteNumeroIdentificacion + ", " : "")
				+ (fechaInicial != null ? "fechaInicial=" + fechaInicial + ", " : "")
				+ (fechaFinal != null ? "fechaFinal=" + fechaFinal + ", " : "")
				+ (hora != null ? "hora=" + hora + ", " : "") + (recaudo != null ? "recaudo=" + recaudo + ", " : "")
				+ (correos != null ? "correos=" + correos : "") + "]";
	}
}
