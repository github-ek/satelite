package com.tacticlogistics.application.dto.wms;

import java.util.Date;

public class ProductoDto {
	private Integer id;

	private Integer clienteId;
	private String clienteCodigo;
	private String clienteNombre;

	private String codigo;
	private String nombre;

	private String codigoAlterno;
	private String nombreAlterno;

	private Integer valorAproximado;
	private boolean provisional;       

	private Date fechaActualizacion;
	private String usuarioActualizacion;

	private ProductoUnidadDto unidadNivel1;
	private ProductoUnidadDto unidadNivel2;
	private ProductoUnidadDto unidadNivel3;

	
	public ProductoDto(Integer id, String clienteNombre, String codigo, String nombre, String codigoAlterno,
			String nombreAlterno) {
		super();
		this.id = id;
		this.clienteNombre = clienteNombre;
		this.codigo = codigo;
		this.nombre = nombre;
		this.codigoAlterno = codigoAlterno;
		this.nombreAlterno = nombreAlterno;
	}

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

	public String getCodigoAlterno() {
		return codigoAlterno;
	}

	public void setCodigoAlterno(String codigoAlterno) {
		this.codigoAlterno = codigoAlterno;
	}

	public String getNombreAlterno() {
		return nombreAlterno;
	}

	public void setNombreAlterno(String nombreAlterno) {
		this.nombreAlterno = nombreAlterno;
	}

	public Integer getValorAproximado() {
		return valorAproximado;
	}

	public void setValorAproximado(Integer valorAproximado) {
		this.valorAproximado = valorAproximado;
	}

	public boolean isProvisional() {
		return provisional;
	}

	public void setProvisional(boolean provisional) {
		this.provisional = provisional;
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

	

	protected ProductoUnidadDto getUnidadNivel1() {
        return unidadNivel1;
    }

    protected ProductoUnidadDto getUnidadNivel2() {
        return unidadNivel2;
    }

    protected ProductoUnidadDto getUnidadNivel3() {
        return unidadNivel3;
    }

    protected void setUnidadNivel1(ProductoUnidadDto unidadNivel1) {
        this.unidadNivel1 = unidadNivel1;
    }

    protected void setUnidadNivel2(ProductoUnidadDto unidadNivel2) {
        this.unidadNivel2 = unidadNivel2;
    }

    protected void setUnidadNivel3(ProductoUnidadDto unidadNivel3) {
        this.unidadNivel3 = unidadNivel3;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProductoDto [");
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
        if (valorAproximado != null) {
            builder.append("valorAproximado=").append(valorAproximado).append(", ");
        }
        builder.append("provisional=").append(provisional).append(", ");
        if (fechaActualizacion != null) {
            builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
        }
        if (usuarioActualizacion != null) {
            builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
        }
        if (unidadNivel1 != null) {
            builder.append("unidadNivel1=").append(unidadNivel1).append(", ");
        }
        if (unidadNivel2 != null) {
            builder.append("unidadNivel2=").append(unidadNivel2).append(", ");
        }
        if (unidadNivel3 != null) {
            builder.append("unidadNivel3=").append(unidadNivel3);
        }
        builder.append("]");
        return builder.toString();
    }

    protected ProductoDto() {
        super();
    }
	
	

}
