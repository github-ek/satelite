package com.tacticlogistics.domain.model.common.valueobjects;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class UbicacionEmbeddable {
	@Embedded
	private Direccion direccion;

	@Embedded
	private Contacto contacto;

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UbicacionEmbeddable [");
		if (direccion != null)
			builder.append("direccion=").append(direccion).append(", ");
		if (contacto != null)
			builder.append("contacto=").append(contacto);
		builder.append("]");
		return builder.toString();
	}

}
