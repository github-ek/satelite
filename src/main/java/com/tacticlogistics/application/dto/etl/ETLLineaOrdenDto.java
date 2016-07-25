package com.tacticlogistics.application.dto.etl;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;
import static com.tacticlogistics.infrastructure.services.Basic.substringSafe;

import java.util.Date;

public class ETLLineaOrdenDto {
    private Integer numeroItem;
    private String descripcion;

    private Integer cantidadSolicitada;
    private Integer cantidadPlanificada;

    private String productoCodigo;
    private String productoCodigoAlterno;

    private String unidadCodigo;
    private String unidadCodigoAlterno;

    private String tipoContenidoCodigo;
    private String tipoContenidoCodigoAlterno;

    private String bodegaOrigenCodigo;
    private String bodegaOrigenCodigoAlterno;
    private String estadoInventarioOrigen;

    private String bodegaDestinoCodigo;
    private String bodegaDestinoCodigoAlterno;
    private String estadoInventarioDestino;

    private String lote;
    private String serial;
    private String cosecha;

    private String requerimientoEstampillado;
    private String requerimientoSalud;
    private String requerimientoImporte;
    private String requerimientoDistribuido;
    private String requerimientoNutricional;
    private String requerimientoBl;
    private String requerimientoFondoCuenta;
    private String requerimientoOrigen;

    private String numeroOrdenTms;
    private Date fechaOrdenTms;

    private String predistribucionDestinoFinal;
    private String predistribucionRotulo;

    private Integer valorDeclaradoPorUnidad;

    private String notas;

    public ETLLineaOrdenDto() {
        super();

        setNumeroItem(null);
        setDescripcion("");
        
        setCantidadSolicitada(null);
        setCantidadPlanificada(null);

        setProductoCodigo("");
        setProductoCodigoAlterno("");

        setUnidadCodigo("");
        setUnidadCodigoAlterno("");

        setTipoContenidoCodigo("");
        setTipoContenidoCodigoAlterno("");

        setBodegaOrigenCodigo("");
        setBodegaOrigenCodigoAlterno("");
        setEstadoInventarioOrigen("");
        
        setBodegaDestinoCodigo("");
        setBodegaDestinoCodigoAlterno("");
        setEstadoInventarioDestino("");
        
        setLote("");
        setSerial("");
        setCosecha("");
        
        setRequerimientoEstampillado("");
        setRequerimientoSalud("");
        setRequerimientoImporte("");
        setRequerimientoDistribuido("");
        setRequerimientoNutricional("");
        setRequerimientoBl("");
        setRequerimientoFondoCuenta("");
        setRequerimientoOrigen("");
        
        setNumeroOrdenTms("");
        setFechaOrdenTms(null);

        setPredistribucionDestinoFinal("");
        setPredistribucionRotulo("");

        setValorDeclaradoPorUnidad(null);
        
        setNotas("");
    }

    public Integer getNumeroItem() {
        return numeroItem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public Integer getCantidadPlanificada() {
        return cantidadPlanificada;
    }

    public String getProductoCodigo() {
        return productoCodigo;
    }

    public String getProductoCodigoAlterno() {
        return productoCodigoAlterno;
    }

    public String getUnidadCodigo() {
        return unidadCodigo;
    }

    public String getUnidadCodigoAlterno() {
        return unidadCodigoAlterno;
    }

    public String getTipoContenidoCodigo() {
        return tipoContenidoCodigo;
    }

    public String getTipoContenidoCodigoAlterno() {
        return tipoContenidoCodigoAlterno;
    }

    public String getBodegaOrigenCodigo() {
        return bodegaOrigenCodigo;
    }

    public String getBodegaOrigenCodigoAlterno() {
        return bodegaOrigenCodigoAlterno;
    }

    public String getEstadoInventarioOrigen() {
        return estadoInventarioOrigen;
    }

    public String getBodegaDestinoCodigo() {
        return bodegaDestinoCodigo;
    }

    public String getBodegaDestinoCodigoAlterno() {
        return bodegaDestinoCodigoAlterno;
    }

    public String getEstadoInventarioDestino() {
        return estadoInventarioDestino;
    }

    public String getLote() {
        return lote;
    }

    public String getSerial() {
        return serial;
    }

    public String getCosecha() {
        return cosecha;
    }

    public String getRequerimientoEstampillado() {
        return requerimientoEstampillado;
    }

    public String getRequerimientoSalud() {
        return requerimientoSalud;
    }

    public String getRequerimientoImporte() {
        return requerimientoImporte;
    }

    public String getRequerimientoDistribuido() {
        return requerimientoDistribuido;
    }

    public String getRequerimientoNutricional() {
        return requerimientoNutricional;
    }

    public String getRequerimientoBl() {
        return requerimientoBl;
    }

    public String getRequerimientoFondoCuenta() {
        return requerimientoFondoCuenta;
    }

    public String getRequerimientoOrigen() {
        return requerimientoOrigen;
    }

    public String getNumeroOrdenTms() {
        return numeroOrdenTms;
    }

    public Date getFechaOrdenTms() {
        return fechaOrdenTms;
    }

    public String getPredistribucionDestinoFinal() {
        return predistribucionDestinoFinal;
    }

    public String getPredistribucionRotulo() {
        return predistribucionRotulo;
    }

    public Integer getValorDeclaradoPorUnidad() {
        return valorDeclaradoPorUnidad;
    }

    public String getNotas() {
        return notas;
    }

    public void setNumeroItem(Integer numeroItem) {
        this.numeroItem = numeroItem;
    }

    public void setDescripcion(String value) {
        this.descripcion = substringSafe(coalesce(value, "").trim(), 0, 300);
    }

    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public void setCantidadPlanificada(Integer cantidadPlanificada) {
        this.cantidadPlanificada = cantidadPlanificada;
    }

    public void setProductoCodigo(String value) {
        this.productoCodigo = substringSafe(coalesce(value, "").trim(), 0, 50);
    }

    public void setProductoCodigoAlterno(String value) {
        this.productoCodigoAlterno = substringSafe(coalesce(value, "").trim(), 0, 50);
    }

    public void setUnidadCodigo(String value) {
        this.unidadCodigo = substringSafe(coalesce(value, "").trim(), 0, 2);
    }

    public void setUnidadCodigoAlterno(String value) {
        this.unidadCodigoAlterno = substringSafe(coalesce(value, "").trim(), 0, 50);
    }

    public void setTipoContenidoCodigo(String value) {
        this.tipoContenidoCodigo = substringSafe(coalesce(value, "").trim(), 0, 20);
    }

    public void setTipoContenidoCodigoAlterno(String value) {
        this.tipoContenidoCodigoAlterno = substringSafe(coalesce(value, "").trim(), 0, 50);
    }

    public void setBodegaOrigenCodigo(String value) {
        this.bodegaOrigenCodigo = substringSafe(coalesce(value, "").trim(), 0, 32);
    }

    public void setBodegaOrigenCodigoAlterno(String value) {
        this.bodegaOrigenCodigoAlterno = substringSafe(coalesce(value, "").trim(), 0, 50);
    }

    public void setEstadoInventarioOrigen(String value) {
        this.estadoInventarioOrigen = substringSafe(coalesce(value, "").trim(), 0, 4);
    }

    public void setBodegaDestinoCodigo(String value) {
        this.bodegaDestinoCodigo = substringSafe(coalesce(value, "").trim(), 0, 32);
    }

    public void setBodegaDestinoCodigoAlterno(String value) {
        this.bodegaDestinoCodigoAlterno = substringSafe(coalesce(value, "").trim(), 0, 50);
    }

    public void setEstadoInventarioDestino(String value) {
        this.estadoInventarioDestino = substringSafe(coalesce(value, "").trim(), 0, 4);
    }

    public void setLote(String value) {
        this.lote = substringSafe(coalesce(value, "").trim(), 0, 30);
    }

    public void setSerial(String value) {
        this.serial = substringSafe(coalesce(value, "").trim(), 0, 30);
    }

    public void setCosecha(String value) {
        this.cosecha = substringSafe(coalesce(value, "").trim(), 0, 30);
    }

    public void setRequerimientoEstampillado(String value) {
        value = substringSafe(coalesce(value, "").trim(), 0, 2).toUpperCase();
        switch (value) {
        case "SI":
        case "NO":
        case "S":
        case "N":
            this.requerimientoEstampillado = value.substring(0, 1);
            break;
        default:
            this.requerimientoEstampillado = "";
            break;
        }
    }

    public void setRequerimientoSalud(String value) {
        this.requerimientoSalud = substringSafe(coalesce(value, "").trim(), 0, 30);
    }

    public void setRequerimientoImporte(String value) {
        this.requerimientoImporte = substringSafe(coalesce(value, "").trim(), 0, 30);
    }

    public void setRequerimientoDistribuido(String value) {
        this.requerimientoDistribuido = substringSafe(coalesce(value, "").trim(), 0, 30);
    }

    public void setRequerimientoNutricional(String value) {
        this.requerimientoNutricional = substringSafe(coalesce(value, "").trim(), 0, 30);
    }

    public void setRequerimientoBl(String value) {
        this.requerimientoBl = substringSafe(coalesce(value, "").trim(), 0, 30);
    }

    public void setRequerimientoFondoCuenta(String value) {
        this.requerimientoFondoCuenta = substringSafe(coalesce(value, "").trim(), 0, 30);
    }

    public void setRequerimientoOrigen(String value) {
        this.requerimientoOrigen = substringSafe(coalesce(value, "").trim(), 0, 30);
    }

    public void setNumeroOrdenTms(String value) {
        this.numeroOrdenTms = substringSafe(coalesce(value, "").trim(), 0, 30);
    }

    public void setFechaOrdenTms(Date fechaOrdenTms) {
        this.fechaOrdenTms = fechaOrdenTms;
    }

    public void setPredistribucionDestinoFinal(String value) {
        this.predistribucionDestinoFinal = substringSafe(coalesce(value, "").trim(), 0, 100).trim();
    }

    public void setPredistribucionRotulo(String value) {
        this.predistribucionRotulo = substringSafe(coalesce(value, "").trim(), 0, 100).trim();
    }

    public void setValorDeclaradoPorUnidad(Integer value) {
        if (value != null) {
            if (value < 0) {
                value = null;
            }
        }
        this.valorDeclaradoPorUnidad = value;
    }

    public void setNotas(String value) {
        this.notas = substringSafe(coalesce(value, "").trim(), 0, 200);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ETLLineaOrdenDto [");
        if (numeroItem != null) {
            builder.append("numeroItem=").append(numeroItem).append(", ");
        }
        if (descripcion != null) {
            builder.append("descripcion=").append(descripcion).append(", ");
        }
        if (cantidadSolicitada != null) {
            builder.append("cantidadSolicitada=").append(cantidadSolicitada).append(", ");
        }
        if (cantidadPlanificada != null) {
            builder.append("cantidadPlanificada=").append(cantidadPlanificada).append(", ");
        }
        if (productoCodigo != null) {
            builder.append("productoCodigo=").append(productoCodigo).append(", ");
        }
        if (productoCodigoAlterno != null) {
            builder.append("productoCodigoAlterno=").append(productoCodigoAlterno).append(", ");
        }
        if (unidadCodigo != null) {
            builder.append("unidadCodigo=").append(unidadCodigo).append(", ");
        }
        if (unidadCodigoAlterno != null) {
            builder.append("unidadCodigoAlterno=").append(unidadCodigoAlterno).append(", ");
        }
        if (bodegaOrigenCodigo != null) {
            builder.append("bodegaOrigenCodigo=").append(bodegaOrigenCodigo).append(", ");
        }
        if (bodegaOrigenCodigoAlterno != null) {
            builder.append("bodegaOrigenCodigoAlterno=").append(bodegaOrigenCodigoAlterno).append(", ");
        }
        if (estadoInventarioOrigen != null) {
            builder.append("estadoInventarioOrigen=").append(estadoInventarioOrigen).append(", ");
        }
        if (bodegaDestinoCodigo != null) {
            builder.append("bodegaDestinoCodigo=").append(bodegaDestinoCodigo).append(", ");
        }
        if (bodegaDestinoCodigoAlterno != null) {
            builder.append("bodegaDestinoCodigoAlterno=").append(bodegaDestinoCodigoAlterno).append(", ");
        }
        if (estadoInventarioDestino != null) {
            builder.append("estadoInventarioDestino=").append(estadoInventarioDestino).append(", ");
        }
        if (lote != null) {
            builder.append("lote=").append(lote).append(", ");
        }
        if (predistribucionDestinoFinal != null) {
            builder.append("predistribucionDestinoFinal=").append(predistribucionDestinoFinal).append(", ");
        }
        if (predistribucionRotulo != null) {
            builder.append("predistribucionRotulo=").append(predistribucionRotulo);
        }
        builder.append("]");
        return builder.toString();
    }
}