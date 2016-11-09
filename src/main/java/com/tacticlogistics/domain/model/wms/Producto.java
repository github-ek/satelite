package com.tacticlogistics.domain.model.wms;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.tacticlogistics.domain.model.crm.Cliente;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Productos", catalog = "wms"
, uniqueConstraints = {@UniqueConstraint(columnNames = { "id_cliente","codigo" })} )
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@Column(nullable = false, length = 50)
	@NotNull
	private String codigo;

	@Column(nullable = false, length = 250)
	@NotNull
	private String nombre;

	@Column(nullable = false, length = 50)
	@NotNull
	private String codigoEan;

	private boolean existeEnWms;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="producto", cascade = CascadeType.ALL, orphanRemoval = true)
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	private List<ProductoUnidadAssociation> productoUnidadAssociation;

	private boolean activo;

	@Column(nullable = false)
	private LocalDateTime fechaActualizacion;

	@Column(nullable = false, length = 50)
	@NotNull
	private String usuarioActualizacion;


	protected List<ProductoUnidadAssociation> getHuellas() {
		if (this.productoUnidadAssociation == null) {
			this.productoUnidadAssociation = new ArrayList<>();
		}
		return productoUnidadAssociation;
	}
	
	public List<ProductoUnidadAssociation> getProductoUnidadAssociation() {
		return Collections.unmodifiableList(getHuellas());
	}
}
