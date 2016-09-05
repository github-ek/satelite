package com.tacticlogistics.domain.model.oms;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.tacticlogistics.common.ddd.AssertionConcern;
import com.tacticlogistics.domain.model.geo.EstadoGeoReferenciacionType;

@Embeddable
public class OmsDireccionGeoreferenciada extends AssertionConcern {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    @Column(name = "id_estado_georeferenciacion", nullable = false, length = 50)
    @NotNull
    private EstadoGeoReferenciacionType estadoGeoReferenciacion;

    @Column(name = "id_tipo_georeferenciacion")
    private Integer tipoGeoReferenciacionId;

    @Column(nullable = true, precision = 18, scale = 15)
    private BigDecimal cx;

    @Column(nullable = true, precision = 18, scale = 15)
    private BigDecimal cy;

    @Column(nullable = false, length = 150)
    @NotNull
    private String direccionEstandarizada;

    @Column(nullable = false, length = 150)
    @NotNull
    private String direccionSugerida;

    @Column(nullable = false, length = 100)
    @NotNull
    private String zona;

    @Column(nullable = false, length = 100)
    @NotNull
    private String localidad;

    @Column(nullable = false, length = 100)
    @NotNull
    private String barrio;

    @Column(nullable = true, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaGeoreferenciacion;

    @Column(nullable = false, length = 50)
    @NotNull
    private String usuarioGeoreferenciacion;

    // ---------------------------------------------------------------------------------------------------------
    public OmsDireccionGeoreferenciada() {
        this(EstadoGeoReferenciacionType.SIN_SOLICITUD,null,null,null,"","","","","",null,"");
    }

    public OmsDireccionGeoreferenciada(
    		EstadoGeoReferenciacionType estadoGeoReferenciacion,
            Integer tipoGeoReferenciacionId, 
            BigDecimal cx, 
            BigDecimal cy, 
            String direccionEstandarizada,
            String direccionSugerida, 
            String zona, 
            String localidad, 
            String barrio, 
            Date fechaGeoreferenciacion,
            String usuarioGeoreferenciacion) {
        super();
        this.setEstadoGeoReferenciacion(estadoGeoReferenciacion);
        this.setTipoGeoReferenciacionId(tipoGeoReferenciacionId);
        this.setCx(cx);
        this.setCy(cy);
        this.setDireccionEstandarizada(direccionEstandarizada);
        this.setDireccionSugerida(direccionSugerida);
        this.setZona(zona);
        this.setLocalidad(localidad);
        this.setBarrio(barrio);
        this.fechaGeoreferenciacion = fechaGeoreferenciacion;
        this.setUsuarioGeoreferenciacion(usuarioGeoreferenciacion);
    }

    // ---------------------------------------------------------------------------------------------------------
    public EstadoGeoReferenciacionType getEstadoGeoReferenciacion() {
        return estadoGeoReferenciacion;
    }

    public Integer getTipoGeoReferenciacionId() {
        return tipoGeoReferenciacionId;
    }

    public BigDecimal getCx() {
        return cx;
    }

    public BigDecimal getCy() {
        return cy;
    }

    public String getDireccionEstandarizada() {
        return direccionEstandarizada;
    }

    public String getDireccionSugerida() {
        return direccionSugerida;
    }

    public String getZona() {
        return zona;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getBarrio() {
        return barrio;
    }

    public Date getFechaGeoreferenciacion() {
        return fechaGeoreferenciacion;
    }

    public String getUsuarioGeoreferenciacion() {
        return usuarioGeoreferenciacion;
    }

    // ---------------------------------------------------------------------------------------------------------
    public void setEstadoGeoReferenciacion(EstadoGeoReferenciacionType value) {
        this.estadoGeoReferenciacion = coalesce(value, EstadoGeoReferenciacionType.SIN_SOLICITUD);
    }

    public void setTipoGeoReferenciacionId(Integer tipoGeoReferenciacionId) {
        this.tipoGeoReferenciacionId = tipoGeoReferenciacionId;
    }

    public void setCx(BigDecimal cx) {
        this.cx = cx;
    }

    public void setCy(BigDecimal cy) {
        this.cy = cy;
    }

    public void setDireccionEstandarizada(String value) {
        this.direccionEstandarizada = coalesce(value, "").trim();
    }

    public void setDireccionSugerida(String value) {
        this.direccionSugerida = coalesce(value, "").trim();
    }

    public void setZona(String value) {
        this.zona = coalesce(value, "").trim();
    }

    public void setLocalidad(String value) {
        this.localidad = coalesce(value, "").trim();
    }

    public void setBarrio(String value) {
        this.barrio = coalesce(value, "").trim();
    }

    public void setFechaGeoreferenciacion(Date fechaGeoreferenciacion) {
        this.fechaGeoreferenciacion = fechaGeoreferenciacion;
    }

    public void setUsuarioGeoreferenciacion(String value) {
        this.usuarioGeoreferenciacion = coalesce(value, "").trim();
    }

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cx == null) ? 0 : cx.hashCode());
        result = prime * result + ((cy == null) ? 0 : cy.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OmsDireccionGeoreferenciada other = (OmsDireccionGeoreferenciada) obj;
        if (cx == null) {
            if (other.cx != null)
                return false;
        } else if (!cx.equals(other.cx))
            return false;
        if (cy == null) {
            if (other.cy != null)
                return false;
        } else if (!cy.equals(other.cy))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OmsDireccionGeoReferenciada [");
        if (estadoGeoReferenciacion != null) {
            builder.append("estadoGeoReferenciacion=").append(estadoGeoReferenciacion).append(", ");
        }
        if (tipoGeoReferenciacionId != null) {
            builder.append("tipoGeoReferenciacionId=").append(tipoGeoReferenciacionId).append(", ");
        }
        if (cx != null) {
            builder.append("cx=").append(cx).append(", ");
        }
        if (cy != null) {
            builder.append("cy=").append(cy).append(", ");
        }
        if (direccionEstandarizada != null) {
            builder.append("direccionEstandarizada=").append(direccionEstandarizada).append(", ");
        }
        if (fechaGeoreferenciacion != null) {
            builder.append("fechaGeoreferenciacion=").append(fechaGeoreferenciacion).append(", ");
        }
        if (usuarioGeoreferenciacion != null) {
            builder.append("usuarioGeoreferenciacion=").append(usuarioGeoreferenciacion);
        }
        builder.append("]");
        return builder.toString();
    }
}
