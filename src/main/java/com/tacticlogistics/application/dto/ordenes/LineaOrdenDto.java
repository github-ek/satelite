package com.tacticlogistics.application.dto.ordenes;

import java.math.BigDecimal;
import java.util.Date;

import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;

public class LineaOrdenDto {
    private Integer idOrden;
    private Integer idLineaOrden;
    private Integer numeroItem;

    private String descripcion;
    private Integer cantidad;
    private Integer valorDeclaradoPorUnidad;

    private Integer producto;
    private String codigoProducto;
    private String nombreProducto;
    private String codigoProductoAlterno;
    private String nombreProductoAlterno;

    private Integer unidad;
    private String codigoUnidad;
    private String nombreUnidad;
    private String codigoUnidadAlterno;

    private Dimensiones dimensiones;

    private Integer bodega;
    private String codigoBodega;
    private String nombreBodega;
    private String codigoBodegaAlterno;
    private String nombreBodegaAlterno;

    private Integer origen;
    private String origenCodigo;
    private String origenNombre;
    private String origenCodigoAlterno;
    private String origenNombreAlterno;

    private Integer ciudad;
    private String ciudadNombreAlterno;
    private String direccion;
    private String indicacionesDireccion;
    private BigDecimal longitud;
    private BigDecimal latitud;
    private String direccionEstandarizada;

    private String nombre;
    private String email;
    private String telefonos;

    private String lote;
    private Integer disponibilidad;
    private String notas;

    private Integer tipoContenido;
    private String tipoContenidoCodigo;
    private String tipoContenidoNombre;
    private String tipoContenidoCodigoAlterno;

    private Date fechaActualizacion;
    private String usuario;

    public LineaOrdenDto() {
        super();
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Integer getIdLineaOrden() {
        return idLineaOrden;
    }

    public void setIdLineaOrden(Integer idLineaOrden) {
        this.idLineaOrden = idLineaOrden;
    }

    public Integer getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(Integer numeroItem) {
        this.numeroItem = numeroItem;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getValorDeclaradoPorUnidad() {
        return valorDeclaradoPorUnidad;
    }

    public void setValorDeclaradoPorUnidad(Integer valorDeclaradoPorUnidad) {
        this.valorDeclaradoPorUnidad = valorDeclaradoPorUnidad;
    }

    public Integer getProducto() {
        return producto;
    }

    public void setProducto(Integer producto) {
        this.producto = producto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCodigoProductoAlterno() {
        return codigoProductoAlterno;
    }

    public void setCodigoProductoAlterno(String codigoProductoAlterno) {
        this.codigoProductoAlterno = codigoProductoAlterno;
    }

    public String getNombreProductoAlterno() {
        return nombreProductoAlterno;
    }

    public void setNombreProductoAlterno(String nombreProductoAlterno) {
        this.nombreProductoAlterno = nombreProductoAlterno;
    }

    public Integer getUnidad() {
        return unidad;
    }

    public void setUnidad(Integer unidad) {
        this.unidad = unidad;
    }

    public String getCodigoUnidad() {
        return codigoUnidad;
    }

    public void setCodigoUnidad(String codigoUnidad) {
        this.codigoUnidad = codigoUnidad;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getCodigoUnidadAlterno() {
        return codigoUnidadAlterno;
    }

    public void setCodigoUnidadAlterno(String codigoUnidadAlterno) {
        this.codigoUnidadAlterno = codigoUnidadAlterno;
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

    public void cambiarDimensiones(
            BigDecimal largoPorUnidad, 
            BigDecimal anchoPorUnidad, 
            BigDecimal altoPorUnidad,
            BigDecimal pesoBrutoPorUnidad) {
        this.setInternalDimensiones(new Dimensiones(
                largoPorUnidad, 
                anchoPorUnidad, 
                altoPorUnidad, 
                pesoBrutoPorUnidad));
    }

    public Integer getBodega() {
        return bodega;
    }

    public void setBodega(Integer bodega) {
        this.bodega = bodega;
    }

    public String getCodigoBodega() {
        return codigoBodega;
    }

    public void setCodigoBodega(String codigoBodega) {
        this.codigoBodega = codigoBodega;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    public String getCodigoBodegaAlterno() {
        return codigoBodegaAlterno;
    }

    public void setCodigoBodegaAlterno(String codigoBodegaAlterno) {
        this.codigoBodegaAlterno = codigoBodegaAlterno;
    }

    public String getNombreBodegaAlterno() {
        return nombreBodegaAlterno;
    }

    public void setNombreBodegaAlterno(String nombreBodegaAlterno) {
        this.nombreBodegaAlterno = nombreBodegaAlterno;
    }

    public Integer getOrigen() {
        return origen;
    }

    public void setOrigen(Integer origen) {
        this.origen = origen;
    }

    public String getOrigenCodigo() {
        return origenCodigo;
    }

    public void setOrigenCodigo(String origenCodigo) {
        this.origenCodigo = origenCodigo;
    }

    public String getOrigenNombre() {
        return origenNombre;
    }

    public void setOrigenNombre(String origenNombre) {
        this.origenNombre = origenNombre;
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

    public Integer getCiudad() {
        return ciudad;
    }

    public void setCiudad(Integer ciudad) {
        this.ciudad = ciudad;
    }

    public String getCiudadNombreAlterno() {
        return ciudadNombreAlterno;
    }

    public void setCiudadNombreAlterno(String ciudadNombreAlterno) {
        this.ciudadNombreAlterno = ciudadNombreAlterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIndicacionesDireccion() {
        return indicacionesDireccion;
    }

    public void setIndicacionesDireccion(String indicacionesDireccion) {
        this.indicacionesDireccion = indicacionesDireccion;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public String getDireccionEstandarizada() {
        return direccionEstandarizada;
    }

    public void setDireccionEstandarizada(String direccionEstandarizada) {
        this.direccionEstandarizada = direccionEstandarizada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Integer getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Integer disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Integer getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(Integer tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public String getTipoContenidoCodigo() {
        return tipoContenidoCodigo;
    }

    public void setTipoContenidoCodigo(String tipoContenidoCodigo) {
        this.tipoContenidoCodigo = tipoContenidoCodigo;
    }

    public String getTipoContenidoNombre() {
        return tipoContenidoNombre;
    }

    public void setTipoContenidoNombre(String tipoContenidoNombre) {
        this.tipoContenidoNombre = tipoContenidoNombre;
    }

    public String getTipoContenidoCodigoAlterno() {
        return tipoContenidoCodigoAlterno;
    }

    public void setTipoContenidoCodigoAlterno(String tipoContenidoCodigoAlterno) {
        this.tipoContenidoCodigoAlterno = tipoContenidoCodigoAlterno;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LineaOrdenViewModel [");
        if (idOrden != null)
            builder.append("idOrden=").append(idOrden).append(", ");
        if (idLineaOrden != null)
            builder.append("idLineaOrden=").append(idLineaOrden).append(", ");
        if (numeroItem != null)
            builder.append("numeroItem=").append(numeroItem).append(", ");
        if (descripcion != null)
            builder.append("descripcion=").append(descripcion).append(", ");
        if (cantidad != null)
            builder.append("cantidad=").append(cantidad).append(", ");
        if (producto != null)
            builder.append("producto=").append(producto).append(", ");
        if (codigoProductoAlterno != null)
            builder.append("codigoProductoAlterno=").append(codigoProductoAlterno).append(", ");
        if (nombreProductoAlterno != null)
            builder.append("nombreProductoAlterno=").append(nombreProductoAlterno).append(", ");
        if (unidad != null)
            builder.append("unidad=").append(unidad).append(", ");
        if (codigoUnidadAlterno != null)
            builder.append("codigoUnidadAlterno=").append(codigoUnidadAlterno).append(", ");
        if (bodega != null)
            builder.append("bodega=").append(bodega).append(", ");
        if (codigoBodegaAlterno != null)
            builder.append("codigoBodegaAlterno=").append(codigoBodegaAlterno).append(", ");
        if (nombreBodegaAlterno != null)
            builder.append("nombreBodegaAlterno=").append(nombreBodegaAlterno).append(", ");
        if (origen != null)
            builder.append("origen=").append(origen).append(", ");
        if (origenCodigoAlterno != null)
            builder.append("origenCodigoAlterno=").append(origenCodigoAlterno).append(", ");
        if (origenNombreAlterno != null)
            builder.append("origenNombreAlterno=").append(origenNombreAlterno).append(", ");
        if (ciudad != null)
            builder.append("ciudad=").append(ciudad).append(", ");
        if (ciudadNombreAlterno != null)
            builder.append("ciudadNombreAlterno=").append(ciudadNombreAlterno).append(", ");
        if (direccion != null)
            builder.append("direccion=").append(direccion).append(", ");
        if (indicacionesDireccion != null)
            builder.append("indicacionesDireccion=").append(indicacionesDireccion).append(", ");
        if (lote != null)
            builder.append("lote=").append(lote).append(", ");
        if (disponibilidad != null)
            builder.append("disponibilidad=").append(disponibilidad).append(", ");
        if (notas != null)
            builder.append("notas=").append(notas).append(", ");
        if (fechaActualizacion != null)
            builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
        if (usuario != null)
            builder.append("usuario=").append(usuario);
        builder.append("]");
        return builder.toString();
    }

}
