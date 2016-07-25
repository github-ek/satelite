package com.tacticlogistics.domain.model.oms;

import com.tacticlogistics.domain.model.common.TipoContenido;
import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;
import com.tacticlogistics.domain.model.common.valueobjects.MensajeEmbeddable;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.Unidad;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OmsLineaOrden.class)
public abstract class OmsLineaOrden_ {

	public static volatile SingularAttribute<OmsLineaOrden, String> descripcion;
	public static volatile SingularAttribute<OmsLineaOrden, Dimensiones> dimensiones;
	public static volatile SingularAttribute<OmsLineaOrden, String> lote;
	public static volatile SingularAttribute<OmsLineaOrden, String> notas;
	public static volatile SingularAttribute<OmsLineaOrden, Integer> ordenId;
	public static volatile SingularAttribute<OmsLineaOrden, String> bodegaDestinoCodigoAlterno;
	public static volatile SingularAttribute<OmsLineaOrden, String> numeroOrdenWmsOrigen;
	public static volatile SingularAttribute<OmsLineaOrden, String> unidadCodigoAlterno;
	public static volatile SetAttribute<OmsLineaOrden, MensajeEmbeddable> mensajes;
	public static volatile SingularAttribute<OmsLineaOrden, Integer> id;
	public static volatile SingularAttribute<OmsLineaOrden, Integer> numeroItem;
	public static volatile SingularAttribute<OmsLineaOrden, String> requerimientoFondoCuenta;
	public static volatile SingularAttribute<OmsLineaOrden, String> unidadCodigo;
	public static volatile SingularAttribute<OmsLineaOrden, Integer> valorDeclaradoPorUnidad;
	public static volatile SingularAttribute<OmsLineaOrden, Integer> cantidadSobrante;
	public static volatile SingularAttribute<OmsLineaOrden, Integer> cantidadNoEntregada;
	public static volatile SingularAttribute<OmsLineaOrden, String> requerimientoOrigen;
	public static volatile SingularAttribute<OmsLineaOrden, Integer> cantidadEntregada;
	public static volatile SingularAttribute<OmsLineaOrden, Date> fechaActualizacion;
	public static volatile SingularAttribute<OmsLineaOrden, String> requerimientoEstampillado;
	public static volatile SingularAttribute<OmsLineaOrden, String> productoCodigoAlterno;
	public static volatile SingularAttribute<OmsLineaOrden, String> requerimientoNutricional;
	public static volatile SingularAttribute<OmsLineaOrden, Bodega> bodegaOrigen;
	public static volatile SingularAttribute<OmsLineaOrden, String> bodegaOrigenCodigo;
	public static volatile SingularAttribute<OmsLineaOrden, Producto> producto;
	public static volatile SingularAttribute<OmsLineaOrden, String> usuarioCreacion;
	public static volatile SingularAttribute<OmsLineaOrden, String> requerimientoSalud;
	public static volatile SingularAttribute<OmsLineaOrden, String> tipoContenidoCodigoAlterno;
	public static volatile SingularAttribute<OmsLineaOrden, Integer> cantidadSobranteLegalizada;
	public static volatile SingularAttribute<OmsLineaOrden, String> productoCodigo;
	public static volatile SingularAttribute<OmsLineaOrden, String> predistribucionRotulo;
	public static volatile SingularAttribute<OmsLineaOrden, String> usuarioActualizacion;
	public static volatile SingularAttribute<OmsLineaOrden, String> bodegaDestinoCodigo;
	public static volatile SingularAttribute<OmsLineaOrden, Integer> cantidadNoEntregadaLegalizada;
	public static volatile SingularAttribute<OmsLineaOrden, String> predistribucionDestinoFinal;
	public static volatile SingularAttribute<OmsLineaOrden, String> tipoContenidoCodigo;
	public static volatile SingularAttribute<OmsLineaOrden, String> bodegaOrigenCodigoAlterno;
	public static volatile SingularAttribute<OmsLineaOrden, String> requerimientoImporte;
	public static volatile SingularAttribute<OmsLineaOrden, String> estadoInventarioOrigenId;
	public static volatile SingularAttribute<OmsLineaOrden, String> numeroOrdenWmsDestino;
	public static volatile SingularAttribute<OmsLineaOrden, Bodega> bodegaDestino;
	public static volatile SingularAttribute<OmsLineaOrden, String> requerimientoBl;
	public static volatile SingularAttribute<OmsLineaOrden, Date> fechaOrdenTms;
	public static volatile SingularAttribute<OmsLineaOrden, Integer> cantidadSolicitada;
	public static volatile SingularAttribute<OmsLineaOrden, String> cosecha;
	public static volatile SingularAttribute<OmsLineaOrden, String> estadoInventarioDestinoId;
	public static volatile SingularAttribute<OmsLineaOrden, Unidad> unidad;
	public static volatile SingularAttribute<OmsLineaOrden, TipoContenido> tipoContenido;
	public static volatile SingularAttribute<OmsLineaOrden, String> serial;
	public static volatile SingularAttribute<OmsLineaOrden, Date> fechaCreacion;
	public static volatile SingularAttribute<OmsLineaOrden, String> numeroOrdenTms;
	public static volatile SingularAttribute<OmsLineaOrden, Integer> cantidadPlanificada;
	public static volatile SingularAttribute<OmsLineaOrden, Integer> cantidadAlistada;
	public static volatile SingularAttribute<OmsLineaOrden, String> requerimientoDistribuido;

}

