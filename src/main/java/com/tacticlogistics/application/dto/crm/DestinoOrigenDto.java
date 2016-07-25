package com.tacticlogistics.application.dto.crm;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.NotImplementedException;

import com.tacticlogistics.domain.model.crm.DestinoOrigen;

public class DestinoOrigenDto  {
    private Integer id;

    private Integer clienteId;
    private String clienteCodigo;
    private String clienteNombre;

    private Integer canalId;
    private String canalCodigo;
    private String canalNombre;

    private Integer destinatarioRemitenteId;
    private String destinatarioRemitenteCodigo;
    private String destinatarioRemitenteNumeroIdentificacion;
    private String destinatarioRemitenteNombre;

    private String codigo;
    private String nombre;
    private String codigoAlterno;
    private String nombreAlterno;

    private Integer ciudadId;
    private String ciudadNombreAlterno;
    private String direccion;
    private String indicacionesDireccion;
    private BigDecimal longitud;
    private BigDecimal latitud;
    private String direccionEstandarizada;

    private String contactoNombres;
    private String contactoEmail;
    private String contactoTelefonos;

    private Date fechaActualizacion;
    private String usuarioActualizacion;

    
    public DestinoOrigenDto() {
        super();
    }

    public DestinoOrigenDto(DestinoOrigen model) {
        super();
        throw new NotImplementedException();
    }
    
    public Integer getId() {
        return id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public String getClienteCodigo() {
        return clienteCodigo;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public Integer getSegmentoId() {
        return canalId;
    }

    public String getSegmentoCodigo() {
        return canalCodigo;
    }

    public String getSegmentoNombre() {
        return canalNombre;
    }

    public Integer getDestinatarioRemitenteId() {
        return destinatarioRemitenteId;
    }

    public String getDestinatarioRemitenteCodigo() {
        return destinatarioRemitenteCodigo;
    }

    public String getDestinatarioRemitenteNumeroIdentificacion() {
        return destinatarioRemitenteNumeroIdentificacion;
    }

    public String getDestinatarioRemitenteNombre() {
        return destinatarioRemitenteNombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigoAlterno() {
        return codigoAlterno;
    }

    public String getNombreAlterno() {
        return nombreAlterno;
    }

    public Integer getCiudadId() {
        return ciudadId;
    }

    public String getCiudadNombreAlterno() {
        return ciudadNombreAlterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getIndicacionesDireccion() {
        return indicacionesDireccion;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public String getDireccionEstandarizada() {
        return direccionEstandarizada;
    }

    public String getContactoNombres() {
        return contactoNombres;
    }

    public String getContactoEmail() {
        return contactoEmail;
    }

    public String getContactoTelefonos() {
        return contactoTelefonos;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public void setClienteCodigo(String clienteCodigo) {
        this.clienteCodigo = clienteCodigo;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public void setSegmentoId(Integer canalId) {
        this.canalId = canalId;
    }

    public void setSegmentoCodigo(String canalCodigo) {
        this.canalCodigo = canalCodigo;
    }

    public void setSegmentoNombre(String canalNombre) {
        this.canalNombre = canalNombre;
    }

    public void setDestinatarioRemitenteId(Integer destinatarioRemitenteId) {
        this.destinatarioRemitenteId = destinatarioRemitenteId;
    }

    public void setDestinatarioRemitenteCodigo(String destinatarioRemitenteCodigo) {
        this.destinatarioRemitenteCodigo = destinatarioRemitenteCodigo;
    }

    public void setDestinatarioRemitenteNumeroIdentificacion(String destinatarioRemitenteNumeroIdentificacion) {
        this.destinatarioRemitenteNumeroIdentificacion = destinatarioRemitenteNumeroIdentificacion;
    }

    public void setDestinatarioRemitenteNombre(String destinatarioRemitenteNombre) {
        this.destinatarioRemitenteNombre = destinatarioRemitenteNombre;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigoAlterno(String codigoAlterno) {
        this.codigoAlterno = codigoAlterno;
    }

    public void setNombreAlterno(String nombreAlterno) {
        this.nombreAlterno = nombreAlterno;
    }

    public void setCiudadId(Integer ciudadId) {
        this.ciudadId = ciudadId;
    }

    public void setCiudadNombreAlterno(String ciudadNombreAlterno) {
        this.ciudadNombreAlterno = ciudadNombreAlterno;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setIndicacionesDireccion(String indicacionesDireccion) {
        this.indicacionesDireccion = indicacionesDireccion;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public void setDireccionEstandarizada(String direccionEstandarizada) {
        this.direccionEstandarizada = direccionEstandarizada;
    }

    public void setContactoNombres(String contactoNombres) {
        this.contactoNombres = contactoNombres;
    }

    public void setContactoEmail(String contactoEmail) {
        this.contactoEmail = contactoEmail;
    }

    public void setContactoTelefonos(String contactoTelefonos) {
        this.contactoTelefonos = contactoTelefonos;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DestinoOrigenDto [");
        if (id != null) {
            builder.append("id=").append(id).append(", ");
        }
        if (clienteId != null) {
            builder.append("clienteId=").append(clienteId).append(", ");
        }
        if (clienteCodigo != null) {
            builder.append("clienteCodigo=").append(clienteCodigo).append(", ");
        }
        if (clienteNombre != null) {
            builder.append("clienteNombre=").append(clienteNombre).append(", ");
        }
        if (canalId != null) {
            builder.append("canalId=").append(canalId).append(", ");
        }
        if (canalCodigo != null) {
            builder.append("canalCodigo=").append(canalCodigo).append(", ");
        }
        if (canalNombre != null) {
            builder.append("canalNombre=").append(canalNombre).append(", ");
        }
        if (destinatarioRemitenteId != null) {
            builder.append("destinatarioRemitenteId=").append(destinatarioRemitenteId).append(", ");
        }
        if (destinatarioRemitenteCodigo != null) {
            builder.append("destinatarioRemitenteCodigo=").append(destinatarioRemitenteCodigo).append(", ");
        }
        if (destinatarioRemitenteNumeroIdentificacion != null) {
            builder.append("destinatarioRemitenteNumeroIdentificacion=")
                    .append(destinatarioRemitenteNumeroIdentificacion).append(", ");
        }
        if (destinatarioRemitenteNombre != null) {
            builder.append("destinatarioRemitenteNombre=").append(destinatarioRemitenteNombre).append(", ");
        }
        if (codigo != null) {
            builder.append("codigo=").append(codigo).append(", ");
        }
        if (nombre != null) {
            builder.append("nombre=").append(nombre).append(", ");
        }
        if (codigoAlterno != null) {
            builder.append("codigoAlterno=").append(codigoAlterno).append(", ");
        }
        if (nombreAlterno != null) {
            builder.append("nombreAlterno=").append(nombreAlterno).append(", ");
        }
        if (ciudadId != null) {
            builder.append("ciudadId=").append(ciudadId).append(", ");
        }
        if (ciudadNombreAlterno != null) {
            builder.append("ciudadNombreAlterno=").append(ciudadNombreAlterno).append(", ");
        }
        if (direccion != null) {
            builder.append("direccion=").append(direccion).append(", ");
        }
        if (indicacionesDireccion != null) {
            builder.append("indicacionesDireccion=").append(indicacionesDireccion).append(", ");
        }
        if (longitud != null) {
            builder.append("longitud=").append(longitud).append(", ");
        }
        if (latitud != null) {
            builder.append("latitud=").append(latitud).append(", ");
        }
        if (direccionEstandarizada != null) {
            builder.append("direccionEstandarizada=").append(direccionEstandarizada).append(", ");
        }
        if (contactoNombres != null) {
            builder.append("contactoNombres=").append(contactoNombres).append(", ");
        }
        if (contactoEmail != null) {
            builder.append("contactoEmail=").append(contactoEmail).append(", ");
        }
        if (contactoTelefonos != null) {
            builder.append("contactoTelefonos=").append(contactoTelefonos).append(", ");
        }
        if (fechaActualizacion != null) {
            builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
        }
        if (usuarioActualizacion != null) {
            builder.append("usuarioActualizacion=").append(usuarioActualizacion);
        }
        builder.append("]");
        return builder.toString();
    }
}
