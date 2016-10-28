package com.tacticlogistics.application.dto.ordenes;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import com.tacticlogistics.domain.model.oms.EstadoOrdenType;

public class DestinatarioDto {
    // private Integer idOrden;
    // ---------------------------------------------------------------------------------------------------------
    private Integer servicio;
    private String codigoServicio;
    private String nombreServicio;

    // ---------------------------------------------------------------------------------------------------------
    private Integer cliente;
    private String codigoCliente;
    private String nombreCliente;

    // ---------------------------------------------------------------------------------------------------------
    private String numeroOrden;
    private EstadoOrdenType estadoOrdenType;

    // ---------------------------------------------------------------------------------------------------------
    private Integer causalAnulacion;
    private String codigoCausalAnulacion;
    private String nombreCausalAnulacion;

    // ---------------------------------------------------------------------------------------------------------
    private Integer canal;
    private String codigoSegmento;
    private String nombreSegmento;
    private String codigoAlternoSegmento;

    // ---------------------------------------------------------------------------------------------------------
    private Integer destinatario;
    private String codigoDestinatario;
    private String numeroIdentificacionDestinatario;
    private String nombreDestinatario;
    private String nombreComercialDestinatario;

    private String codigoAlternoDestinatario;
    private String numeroIdentificacionAlternoDestinatario;
    private String nombreAlternoDestinatario;

    // ---------------------------------------------------------------------------------------------------------
    private String nombre;
    private String email;
    private String telefonos;

    // ---------------------------------------------------------------------------------------------------------
    private String usuario;

    // ---------------------------------------------------------------------------------------------------------
    public DestinatarioDto() {
        super();
        this.setCodigoServicio("");
        this.setNombreServicio("");
        this.setCodigoCliente("");
        this.setNombreCliente("");
        this.setNumeroOrden("");
        this.setCodigoCausalAnulacion("");
        this.setNombreCausalAnulacion("");
        this.setCodigoSegmento("");
        this.setNombreSegmento("");
        this.setCodigoAlternoSegmento("");
        this.setCodigoDestinatario("");
        this.setNumeroIdentificacionDestinatario("");
        this.setNombreDestinatario("");
        this.setNombreComercialDestinatario("");
        this.setCodigoAlternoDestinatario("");
        this.setNumeroIdentificacionAlternoDestinatario("");
        this.setNombreAlternoDestinatario("");
        this.setNombre("");
        this.setEmail("");
        this.setTelefonos("");
        this.setUsuario("");
    }

    // ---------------------------------------------------------------------------------------------------------
    public Integer getServicio() {
        return servicio;
    }

    public String getCodigoServicio() {
        return codigoServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public Integer getCliente() {
        return cliente;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public EstadoOrdenType getEstadoOrdenType() {
        return estadoOrdenType;
    }

    public Integer getCausalAnulacion() {
        return causalAnulacion;
    }

    public String getCodigoCausalAnulacion() {
        return codigoCausalAnulacion;
    }

    public String getNombreCausalAnulacion() {
        return nombreCausalAnulacion;
    }

    public Integer getSegmento() {
        return canal;
    }

    public String getCodigoSegmento() {
        return codigoSegmento;
    }

    public String getNombreSegmento() {
        return nombreSegmento;
    }

    public String getCodigoAlternoSegmento() {
        return codigoAlternoSegmento;
    }

    public Integer getDestinatario() {
        return destinatario;
    }

    public String getCodigoDestinatario() {
        return codigoDestinatario;
    }

    public String getNumeroIdentificacionDestinatario() {
        return numeroIdentificacionDestinatario;
    }

    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    public String getNombreComercialDestinatario() {
        return nombreComercialDestinatario;
    }

    public String getCodigoAlternoDestinatario() {
        return codigoAlternoDestinatario;
    }

    public String getNumeroIdentificacionAlternoDestinatario() {
        return numeroIdentificacionAlternoDestinatario;
    }

    public String getNombreAlternoDestinatario() {
        return nombreAlternoDestinatario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public String getUsuario() {
        return usuario;
    }
    // ---------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DestinatarioDto [");
        if (servicio != null) {
            builder.append("servicio=").append(servicio).append(", ");
        }
        if (codigoServicio != null) {
            builder.append("codigoServicio=").append(codigoServicio).append(", ");
        }
        if (nombreServicio != null) {
            builder.append("nombreServicio=").append(nombreServicio).append(", ");
        }
        if (cliente != null) {
            builder.append("cliente=").append(cliente).append(", ");
        }
        if (codigoCliente != null) {
            builder.append("codigoCliente=").append(codigoCliente).append(", ");
        }
        if (nombreCliente != null) {
            builder.append("nombreCliente=").append(nombreCliente).append(", ");
        }
        if (numeroOrden != null) {
            builder.append("numeroOrden=").append(numeroOrden).append(", ");
        }
        if (estadoOrdenType != null) {
            builder.append("estadoOrdenType=").append(estadoOrdenType).append(", ");
        }
        if (causalAnulacion != null) {
            builder.append("causalAnulacion=").append(causalAnulacion).append(", ");
        }
        if (codigoCausalAnulacion != null) {
            builder.append("codigoCausalAnulacion=").append(codigoCausalAnulacion).append(", ");
        }
        if (nombreCausalAnulacion != null) {
            builder.append("nombreCausalAnulacion=").append(nombreCausalAnulacion).append(", ");
        }
        if (canal != null) {
            builder.append("canal=").append(canal).append(", ");
        }
        if (codigoSegmento != null) {
            builder.append("codigoSegmento=").append(codigoSegmento).append(", ");
        }
        if (nombreSegmento != null) {
            builder.append("nombreSegmento=").append(nombreSegmento).append(", ");
        }
        if (codigoAlternoSegmento != null) {
            builder.append("codigoAlternoSegmento=").append(codigoAlternoSegmento).append(", ");
        }
        if (destinatario != null) {
            builder.append("destinatario=").append(destinatario).append(", ");
        }
        if (codigoDestinatario != null) {
            builder.append("codigoDestinatario=").append(codigoDestinatario).append(", ");
        }
        if (numeroIdentificacionDestinatario != null) {
            builder.append("numeroIdentificacionDestinatario=").append(numeroIdentificacionDestinatario).append(", ");
        }
        if (nombreDestinatario != null) {
            builder.append("nombreDestinatario=").append(nombreDestinatario).append(", ");
        }
        if (nombreComercialDestinatario != null) {
            builder.append("nombreComercialDestinatario=").append(nombreComercialDestinatario).append(", ");
        }
        if (codigoAlternoDestinatario != null) {
            builder.append("codigoAlternoDestinatario=").append(codigoAlternoDestinatario).append(", ");
        }
        if (numeroIdentificacionAlternoDestinatario != null) {
            builder.append("numeroIdentificacionAlternoDestinatario=").append(numeroIdentificacionAlternoDestinatario)
                    .append(", ");
        }
        if (nombreAlternoDestinatario != null) {
            builder.append("nombreAlternoDestinatario=").append(nombreAlternoDestinatario).append(", ");
        }
        if (nombre != null) {
            builder.append("nombre=").append(nombre).append(", ");
        }
        if (email != null) {
            builder.append("email=").append(email).append(", ");
        }
        if (telefonos != null) {
            builder.append("telefonos=").append(telefonos).append(", ");
        }
        if (usuario != null) {
            builder.append("usuario=").append(usuario);
        }
        builder.append("]");
        return builder.toString();
    }

    public void setServicio(Integer value) {
        this.servicio = value;
    }

    public void setCodigoServicio(String value) {
        this.codigoServicio = coalesce(value, "");
    }

    public void setNombreServicio(String value) {
        this.nombreServicio = coalesce(value, "");
    }

    public void setCliente(Integer value) {
        this.cliente = value;
    }

    public void setCodigoCliente(String value) {
        this.codigoCliente = coalesce(value, "");
    }

    public void setNombreCliente(String value) {
        this.nombreCliente = coalesce(value, "");
    }

    public void setNumeroOrden(String value) {
        this.numeroOrden = coalesce(value, "");
    }

    public void setEstadoOrdenType(EstadoOrdenType value) {
        this.estadoOrdenType = value;
    }

    public void setCausalAnulacion(Integer value) {
        this.causalAnulacion = value;
    }

    public void setCodigoCausalAnulacion(String value) {
        this.codigoCausalAnulacion = coalesce(value, "");
    }

    public void setNombreCausalAnulacion(String value) {
        this.nombreCausalAnulacion = coalesce(value, "");
    }

    public void setSegmento(Integer value) {
        this.canal = value;
    }

    public void setCodigoSegmento(String value) {
        this.codigoSegmento = coalesce(value, "");
    }

    public void setNombreSegmento(String value) {
        this.nombreSegmento = coalesce(value, "");
    }

    public void setCodigoAlternoSegmento(String value) {
        this.codigoAlternoSegmento = coalesce(value, "");
    }

    public void setDestinatario(Integer value) {
        this.destinatario = value;
    }

    public void setCodigoDestinatario(String value) {
        this.codigoDestinatario = coalesce(value, "");
    }

    public void setNumeroIdentificacionDestinatario(String value) {
        this.numeroIdentificacionDestinatario = coalesce(value, "");
    }

    public void setNombreDestinatario(String value) {
        this.nombreDestinatario = coalesce(value, "");
    }

    public void setNombreComercialDestinatario(String value) {
        this.nombreComercialDestinatario = coalesce(value, "");
    }

    public void setCodigoAlternoDestinatario(String value) {
        this.codigoAlternoDestinatario = coalesce(value, "");
    }

    public void setNumeroIdentificacionAlternoDestinatario(String value) {
        this.numeroIdentificacionAlternoDestinatario = coalesce(value, "");
    }

    public void setNombreAlternoDestinatario(String value) {
        this.nombreAlternoDestinatario = coalesce(value, "");
    }

    public void setNombre(String value) {
        this.nombre = coalesce(value, "");
    }

    public void setEmail(String value) {
        this.email = coalesce(value, "");
    }

    public void setTelefonos(String value) {
        this.telefonos = coalesce(value, "");
    }

    public void setUsuario(String value) {
        this.usuario = coalesce(value, "");
    }
}