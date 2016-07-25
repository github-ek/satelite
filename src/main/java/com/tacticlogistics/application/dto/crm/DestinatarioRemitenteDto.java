package com.tacticlogistics.application.dto.crm;

import java.util.Date;
import java.util.List;

import com.tacticlogistics.domain.model.common.IdentificacionType;

public class DestinatarioRemitenteDto {
	private Integer id;

	private Integer clienteId;
	private String clienteCodigo;
	private String clienteNombre;

	private IdentificacionType identificacionType;
	private String numeroIdentificacion;
	private String digitoVerificacion;
	private String codigo;
	private String nombre;
	private String nombreComercial;

	private String contactoNombres;
	private String contactoEmail;
	private String contactoTelefonos;

	private List<Integer> canales;

	private Date fechaActualizacion;
	private String usuarioActualizacion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public String getClienteCodigo() {
		return clienteCodigo;
	}

	public void setClienteCodigo(String clienteCodigo) {
		this.clienteCodigo = clienteCodigo;
	}

	public String getClienteNombre() {
		return clienteNombre;
	}

	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	public IdentificacionType getIdentificacionType() {
		return identificacionType;
	}

	public void setIdentificacionType(IdentificacionType identificacionType) {
		this.identificacionType = identificacionType;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getDigitoVerificacion() {
		return digitoVerificacion;
	}

	public void setDigitoVerificacion(String digitoVerificacion) {
		this.digitoVerificacion = digitoVerificacion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getContactoNombres() {
		return contactoNombres;
	}

	public void setContactoNombres(String contactoNombres) {
		this.contactoNombres = contactoNombres;
	}

	public String getContactoEmail() {
		return contactoEmail;
	}

	public void setContactoEmail(String contactoEmail) {
		this.contactoEmail = contactoEmail;
	}

	public String getContactoTelefonos() {
		return contactoTelefonos;
	}

	public void setContactoTelefonos(String contactoTelefonos) {
		this.contactoTelefonos = contactoTelefonos;
	}

	public List<Integer> getSegmentos() {
		return canales;
	}

	public void setSegmentos(List<Integer> value) {
		this.canales = value;
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

    @Override
    public String toString() {
        final int maxLen = 5;
        StringBuilder builder = new StringBuilder();
        builder.append("DestinatarioRemitenteDto [");
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
        if (identificacionType != null) {
            builder.append("identificacionType=").append(identificacionType).append(", ");
        }
        if (numeroIdentificacion != null) {
            builder.append("numeroIdentificacion=").append(numeroIdentificacion).append(", ");
        }
        if (digitoVerificacion != null) {
            builder.append("digitoVerificacion=").append(digitoVerificacion).append(", ");
        }
        if (codigo != null) {
            builder.append("codigo=").append(codigo).append(", ");
        }
        if (nombre != null) {
            builder.append("nombre=").append(nombre).append(", ");
        }
        if (nombreComercial != null) {
            builder.append("nombreComercial=").append(nombreComercial).append(", ");
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
        if (canales != null) {
            builder.append("canales=").append(canales.subList(0, Math.min(canales.size(), maxLen))).append(", ");
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
