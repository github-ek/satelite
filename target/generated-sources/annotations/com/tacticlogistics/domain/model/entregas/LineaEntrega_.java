package com.tacticlogistics.domain.model.entregas;

import com.tacticlogistics.domain.model.ordenes.LineaOrden;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.Unidad;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LineaEntrega.class)
public abstract class LineaEntrega_ {

	public static volatile SingularAttribute<LineaEntrega, Integer> cantidadNoConforme;
	public static volatile SingularAttribute<LineaEntrega, Integer> cantidadConforme;
	public static volatile SingularAttribute<LineaEntrega, BigDecimal> volumen;
	public static volatile SingularAttribute<LineaEntrega, LineaOrden> lineaOrden;
	public static volatile SingularAttribute<LineaEntrega, String> lote;
	public static volatile SingularAttribute<LineaEntrega, Integer> cantidadFaltante;
	public static volatile SingularAttribute<LineaEntrega, String> notas;
	public static volatile SingularAttribute<LineaEntrega, Producto> producto;
	public static volatile SingularAttribute<LineaEntrega, Integer> cantidadSobrante;
	public static volatile SingularAttribute<LineaEntrega, Unidad> unidad;
	public static volatile SingularAttribute<LineaEntrega, Integer> cantidadOriginal;
	public static volatile SingularAttribute<LineaEntrega, BigDecimal> pesoBrutoVolumetrico;
	public static volatile SingularAttribute<LineaEntrega, String> usuarioActualizacion;
	public static volatile SingularAttribute<LineaEntrega, Date> fechaActualizacion;
	public static volatile SingularAttribute<LineaEntrega, BigDecimal> pesoBruto;
	public static volatile SingularAttribute<LineaEntrega, Entrega> entrega;
	public static volatile SingularAttribute<LineaEntrega, Integer> id;
	public static volatile SingularAttribute<LineaEntrega, Integer> numeroItem;

}

