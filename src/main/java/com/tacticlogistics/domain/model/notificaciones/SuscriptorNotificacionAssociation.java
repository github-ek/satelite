package com.tacticlogistics.domain.model.notificaciones;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "suscriptores_notificaciones", catalog = "notificaciones")
public class SuscriptorNotificacionAssociation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_suscriptor", nullable = false)
	private Suscriptor suscriptor;

	@Id
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_notificacion", nullable = false)
	private Notificacion notificacion;

	protected SuscriptorNotificacionAssociation() {

	}

	public Suscriptor getSuscriptor() {
		return suscriptor;
	}

	public void setSuscriptor(Suscriptor suscriptor) {
		this.suscriptor = suscriptor;
	}

	public Notificacion getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(Notificacion notificacion) {
		this.notificacion = notificacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((notificacion == null) ? 0 : notificacion.hashCode());
		result = prime * result + ((suscriptor == null) ? 0 : suscriptor.hashCode());
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
		SuscriptorNotificacionAssociation other = (SuscriptorNotificacionAssociation) obj;
		if (notificacion == null) {
			if (other.notificacion != null)
				return false;
		} else if (!notificacion.equals(other.notificacion))
			return false;
		if (suscriptor == null) {
			if (other.suscriptor != null)
				return false;
		} else if (!suscriptor.equals(other.suscriptor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SuscriptorNotificacionAssociation [");
		if (suscriptor != null)
			builder.append("suscriptor=").append(suscriptor).append(", ");
		if (notificacion != null)
			builder.append("notificacion=").append(notificacion);
		builder.append("]");
		return builder.toString();
	}

}
