package com.tacticlogistics.domain.model.wms;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.tacticlogistics.domain.model.common.valueobjects.Direccion;
import com.tacticlogistics.domain.model.common.valueobjects.DireccionGeoreferenciada;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "Bodegas", catalog = "wms")
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Bodega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bodega")
	private Integer id;

	@Column(nullable = false, length = 20, unique = true)
	@NotNull
	private String codigo;

	@Column(nullable = false, length = 100, unique = true)
	@NotNull
	private String nombre;

	@Column(nullable = false, length = 6, unique = true)
	@NotNull
	private String prefijoRutas;

	private int consecutivoRutas;

	@Embedded
	private Direccion direccion;

	@Embedded
	private DireccionGeoreferenciada georeferenciacion;

	private int ordinal;
	
	private boolean activo;

	@Column(nullable = true)
	@NotNull
	private LocalDateTime fechaActualizacion;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioActualizacion;

	public Direccion getDireccion() {
		if (direccion == null) {
			this.setDireccion(null);
		}
		return direccion;
	}

	protected void setDireccion(Direccion value) {
		if (value == null) {
			// @formatter:off
			value = Direccion
					.builder()
					.ciudad(null)
					.direccion("")
					.indicacionesDireccion("")
					.build();
			// @formatter:on
		}
		this.direccion = value;
	}
	
	public DireccionGeoreferenciada getGeoreferenciacion() {
		if (georeferenciacion == null) {
			this.setGeoreferenciacion(null);
		}
		return georeferenciacion;
	}

	protected void setGeoreferenciacion(DireccionGeoreferenciada value) {
		if (value == null) {
			// @formatter:off
			value = DireccionGeoreferenciada
					.builder()
					.direccionEstandarizada("")
					.cx(null)
					.cy(null)
					.build();
			// @formatter:on
		}
		this.georeferenciacion = value;
	}
}
