package com.tacticlogistics.domain.model.ordenes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.tacticlogistics.domain.model.common.TipoContenido;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;
import com.tacticlogistics.domain.model.common.valueobjects.Direccion;
import com.tacticlogistics.domain.model.common.valueobjects.MensajeEmbeddable;
import com.tacticlogistics.domain.model.crm.DestinoOrigen;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.Unidad;

@Entity
@Table(name = "LineasOrden", catalog = "ordenes", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_orden", "numeroItem" }) })

public class LineaOrden implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_linea_orden", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_orden", nullable = false)
    private Orden orden;

    private int numeroItem;

    private boolean consistente;

    @Column(nullable = false, length = 200)
    private String descripcion;

    private int cantidad;

    // ---------------------------------------------------------------------------------------------------------
    @Embedded
    private Dimensiones dimensiones;

    @Column(nullable = true)
    private Integer valorDeclaradoPorUnidad;

    // ---------------------------------------------------------------------------------------------------------
    @Column(nullable = false, length = 30)
    private String lote;

    @Column(nullable = false, length = 200)
    private String notas;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = true)
    private Producto producto;

    @Column(nullable = false, length = 50)
    private String codigoProducto;

    @Column(nullable = false, length = 50)
    private String codigoAlternoProducto;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidad", nullable = true)
    private Unidad unidad;

    @Column(nullable = false, length = 20)
    private String codigoUnidad;

    @Column(nullable = false, length = 20)
    private String codigoAlternoUnidad;

    @Column(nullable = true, length = 100)
    private String predistribucionDestinoFinal;

    @Column(nullable = true, length = 100)
    private String predistribucionRotulo;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_destino_origen_origen", nullable = true)
    private DestinoOrigen origen;

    @Column(length = 20, nullable = false)
    private String origenCodigoAlterno;

    @Column(length = 100, nullable = false)
    private String origenNombreAlterno;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "direccion", column = @Column(name = "origen_direccion", nullable = false, length = 150)),
            @AttributeOverride(name = "indicacionesDireccion", column = @Column(name = "origen_indicaciones_direccion", nullable = false, length = 150)),
            @AttributeOverride(name = "longitud", column = @Column(name = "origen_longitud", nullable = true, precision = 18, scale = 15)),
            @AttributeOverride(name = "latitud", column = @Column(name = "origen_latitud", nullable = true, precision = 18, scale = 15)),
            @AttributeOverride(name = "direccionEstandarizada", column = @Column(name = "origen_direccion_estandarizada", nullable = false, length = 150)) })
    @AssociationOverrides({
            @AssociationOverride(name = "ciudad", joinColumns = @JoinColumn(name = "id_ciudad_origen", nullable = true)) })
    private Direccion origenDireccion;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nombres", column = @Column(name = "origen_contacto_nombres", nullable = false, length = 100)),
            @AttributeOverride(name = "telefonos", column = @Column(name = "origen_contacto_telefonos", nullable = false, length = 50)),
            @AttributeOverride(name = "email", column = @Column(name = "origen_contacto_email", nullable = false, length = 100)) })
    private Contacto origenContacto;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_bodega_origen", nullable = true)
    private Bodega bodegaOrigen;

    @Column(length = 20, nullable = false)
    private String bodegaOrigenCodigoAlterno;

    @Column(length = 100, nullable = false)
    private String bodegaOrigenNombreAlterno;

    // ---------------------------------------------------------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_tipo_contenido", nullable = true)
    private TipoContenido tipoContenido;

    // ---------------------------------------------------------------------------------------------------------
    @ElementCollection
    @CollectionTable(name = "lineas_ordenes_mensajes", catalog = "ordenes", joinColumns = @JoinColumn(name = "id_linea_orden", referencedColumnName = "id_linea_orden"))
    private Set<MensajeEmbeddable> mensajes = new HashSet<MensajeEmbeddable>();

    // ---------------------------------------------------------------------------------------------------------
    @Column(nullable = false, columnDefinition = "DATETIME2(0)")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;

    @Column(nullable = false, length = 50)
    private String usuarioActualizacion;

    // ---------------------------------------------------------------------------------------------------------
    protected LineaOrden() {

    }

    public LineaOrden(Integer id) {
        super();
        this.id = id;
    }

    // ---------------------------------------------------------------------------------------------------------
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public int getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(int numeroItem) {
        this.numeroItem = numeroItem;
    }

    public boolean isConsistente() {
        return consistente;
    }

    public void setConsistente(boolean consistente) {
        this.consistente = consistente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    protected Dimensiones getInternalDimensiones() {
        if (dimensiones == null) {
            dimensiones = new Dimensiones();
        }
        return dimensiones;
    }

    protected void setInternalDimensiones(Dimensiones value) {
        if (value == null) {
            value = new Dimensiones();
        }
        dimensiones = value;
    }

    public BigDecimal getLargoPorUnidad() {
        return getInternalDimensiones().getLargoPorUnidad();
    }

    public BigDecimal getAnchoPorUnidad() {
        return getInternalDimensiones().getAnchoPorUnidad();
    }

    public BigDecimal getAltoPorUnidad() {
        return getInternalDimensiones().getAltoPorUnidad();
    }

    public BigDecimal getPesoBrutoPorUnidad() {
        return getInternalDimensiones().getPesoBrutoPorUnidad();
    }

    public void cambiarDimensiones(BigDecimal largoPorUnidad, BigDecimal anchoPorUnidad, BigDecimal altoPorUnidad,
            BigDecimal pesoBrutoPorUnidad) {
        this.setInternalDimensiones(new Dimensiones(largoPorUnidad, anchoPorUnidad, altoPorUnidad, pesoBrutoPorUnidad));
    }

    public Integer getValorDeclaradoPorUnidad() {
        return valorDeclaradoPorUnidad;
    }

    public void setValorDeclaradoPorUnidad(Integer valorDeclaradoPorUnidad) {
        this.valorDeclaradoPorUnidad = valorDeclaradoPorUnidad;
    }

    public BigDecimal getPesoBrutoVolumetricoPorUnidad() {
        return getInternalDimensiones().getPesoBrutoVolumetricoPorUnidad();
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getCodigoAlternoProducto() {
        return codigoAlternoProducto;
    }

    public void setCodigoAlternoProducto(String codigoAlternoProducto) {
        this.codigoAlternoProducto = codigoAlternoProducto;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public String getCodigoUnidad() {
        return codigoUnidad;
    }

    public void setCodigoUnidad(String codigoUnidad) {
        this.codigoUnidad = codigoUnidad;
    }

    public String getCodigoAlternoUnidad() {
        return codigoAlternoUnidad;
    }

    public void setCodigoAlternoUnidad(String codigoAlternoUnidad) {
        this.codigoAlternoUnidad = codigoAlternoUnidad;
    }

    public String getPredistribucionDestinoFinal() {
        return predistribucionDestinoFinal;
    }

    public String getPredistribucionRotulo() {
        return predistribucionRotulo;
    }

    public void setPredistribucionDestinoFinal(String predistribucionDestinoFinal) {
        this.predistribucionDestinoFinal = predistribucionDestinoFinal;
    }

    public void setPredistribucionRotulo(String predistribucionRotulo) {
        this.predistribucionRotulo = predistribucionRotulo;
    }
    
    public DestinoOrigen getOrigen() {
        return origen;
    }

    public void setOrigen(DestinoOrigen origen) {
        this.origen = origen;
    }

    public String getOrigenCodigoAlterno() {
        return origenCodigoAlterno;
    }

    public void setOrigenCodigoAlterno(String origenCodigoAlterno) {
        this.origenCodigoAlterno = origenCodigoAlterno;
    }

    public String getOrigenNombreAlterno() {
        return origenNombreAlterno;
    }

    public void setOrigenNombreAlterno(String origenNombreAlterno) {
        this.origenNombreAlterno = origenNombreAlterno;
    }

    public Direccion getOrigenDireccion() {
        if (origenDireccion == null) {
            origenDireccion = new Direccion();
        }
        return origenDireccion;
    }

    public void setOrigenDireccion(Direccion origenDireccion) {
        this.origenDireccion = origenDireccion;
    }

    public Contacto getOrigenContacto() {
        if (origenContacto == null) {
            origenContacto = new Contacto("", "", "");
        }

        return origenContacto;
    }

    public void setOrigenContacto(Contacto origenContacto) {
        this.origenContacto = origenContacto;
    }

    public Bodega getBodegaOrigen() {
        return bodegaOrigen;
    }

    public void setBodegaOrigen(Bodega bodegaOrigen) {
        this.bodegaOrigen = bodegaOrigen;
    }

    public String getBodegaOrigenCodigoAlterno() {
        return bodegaOrigenCodigoAlterno;
    }

    public void setBodegaOrigenCodigoAlterno(String bodegaOrigenCodigoAlterno) {
        this.bodegaOrigenCodigoAlterno = bodegaOrigenCodigoAlterno;
    }

    public String getBodegaOrigenNombreAlterno() {
        return bodegaOrigenNombreAlterno;
    }

    public void setBodegaOrigenNombreAlterno(String bodegaOrigenNombreAlterno) {
        this.bodegaOrigenNombreAlterno = bodegaOrigenNombreAlterno;
    }

    public TipoContenido getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(TipoContenido tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numeroItem;
        result = prime * result + ((orden == null) ? 0 : orden.hashCode());
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
        LineaOrden other = (LineaOrden) obj;
        if (numeroItem != other.numeroItem)
            return false;
        if (orden == null) {
            if (other.orden != null)
                return false;
        } else if (!orden.equals(other.orden))
            return false;
        return true;
    }

    // ---------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LineaOrden [");
        if (id != null)
            builder.append("id=").append(id).append(", ");
        if (orden != null)
            builder.append("orden=").append(orden).append(", ");
        builder.append("numeroItem=").append(numeroItem).append(", ");
        if (descripcion != null)
            builder.append("descripcion=").append(descripcion).append(", ");
        if (codigoProducto != null)
            builder.append("codigoProducto=").append(codigoProducto).append(", ");
        if (codigoAlternoProducto != null)
            builder.append("codigoAlternoProducto=").append(codigoAlternoProducto).append(", ");
        builder.append("cantidad=").append(cantidad).append(", ");
        if (codigoUnidad != null)
            builder.append("codigoUnidad=").append(codigoUnidad).append(", ");
        if (codigoAlternoUnidad != null)
            builder.append("codigoAlternoUnidad=").append(codigoAlternoUnidad).append(", ");
        if (dimensiones != null)
            builder.append("dimensiones=").append(dimensiones).append(", ");
        if (valorDeclaradoPorUnidad != null)
            builder.append("valorDeclaradoPorUnidad=").append(valorDeclaradoPorUnidad).append(", ");
        if (bodegaOrigen != null)
            builder.append("bodegaOrigen=").append(bodegaOrigen).append(", ");
        if (bodegaOrigenCodigoAlterno != null)
            builder.append("bodegaOrigenCodigoAlterno=").append(bodegaOrigenCodigoAlterno).append(", ");
        if (lote != null)
            builder.append("lote=").append(lote).append(", ");
        if (origen != null)
            builder.append("origen=").append(origen).append(", ");
        if (origenCodigoAlterno != null)
            builder.append("origenCodigoAlterno=").append(origenCodigoAlterno).append(", ");
        if (origenDireccion != null)
            builder.append("origenDireccion=").append(origenDireccion).append(", ");
        if (notas != null)
            builder.append("notas=").append(notas);
        builder.append("]");
        return builder.toString();
    }
}
