package com.tacticlogistics.domain.model.ordenes;

import com.tacticlogistics.domain.model.common.TipoContenido;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;
import com.tacticlogistics.domain.model.common.valueobjects.Direccion;
import com.tacticlogistics.domain.model.common.valueobjects.MensajeEmbeddable;
import com.tacticlogistics.domain.model.crm.DestinoOrigen;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.Unidad;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LineaOrden.class)
public abstract class LineaOrden_ {

	public static volatile SingularAttribute<LineaOrden, String> descripcion;
	public static volatile SingularAttribute<LineaOrden, Dimensiones> dimensiones;
	public static volatile SingularAttribute<LineaOrden, Bodega> bodegaOrigen;
	public static volatile SingularAttribute<LineaOrden, String> codigoAlternoUnidad;
	public static volatile SingularAttribute<LineaOrden, Boolean> consistente;
	public static volatile SingularAttribute<LineaOrden, String> lote;
	public static volatile SingularAttribute<LineaOrden, String> codigoUnidad;
	public static volatile SingularAttribute<LineaOrden, String> notas;
	public static volatile SingularAttribute<LineaOrden, Producto> producto;
	public static volatile SingularAttribute<LineaOrden, DestinoOrigen> origen;
	public static volatile SingularAttribute<LineaOrden, Contacto> origenContacto;
	public static volatile SingularAttribute<LineaOrden, Direccion> origenDireccion;
	public static volatile SingularAttribute<LineaOrden, String> predistribucionRotulo;
	public static volatile SetAttribute<LineaOrden, MensajeEmbeddable> mensajes;
	public static volatile SingularAttribute<LineaOrden, String> usuarioActualizacion;
	public static volatile SingularAttribute<LineaOrden, Integer> id;
	public static volatile SingularAttribute<LineaOrden, Orden> orden;
	public static volatile SingularAttribute<LineaOrden, String> codigoProducto;
	public static volatile SingularAttribute<LineaOrden, Integer> numeroItem;
	public static volatile SingularAttribute<LineaOrden, String> codigoAlternoProducto;
	public static volatile SingularAttribute<LineaOrden, String> predistribucionDestinoFinal;
	public static volatile SingularAttribute<LineaOrden, String> bodegaOrigenCodigoAlterno;
	public static volatile SingularAttribute<LineaOrden, String> origenCodigoAlterno;
	public static volatile SingularAttribute<LineaOrden, String> origenNombreAlterno;
	public static volatile SingularAttribute<LineaOrden, Integer> valorDeclaradoPorUnidad;
	public static volatile SingularAttribute<LineaOrden, Unidad> unidad;
	public static volatile SingularAttribute<LineaOrden, TipoContenido> tipoContenido;
	public static volatile SingularAttribute<LineaOrden, Date> fechaActualizacion;
	public static volatile SingularAttribute<LineaOrden, Integer> cantidad;
	public static volatile SingularAttribute<LineaOrden, String> bodegaOrigenNombreAlterno;

}

