package com.tacticlogistics.domain.model.ordenes;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CambioEstadoOrden.class)
public abstract class CambioEstadoOrden_ {

	public static volatile SingularAttribute<CambioEstadoOrden, EstadoOrdenType> estadoOrden;
	public static volatile SingularAttribute<CambioEstadoOrden, String> usuarioDesde;
	public static volatile SingularAttribute<CambioEstadoOrden, Date> fechaHasta;
	public static volatile SingularAttribute<CambioEstadoOrden, Date> fechaDesde;
	public static volatile SingularAttribute<CambioEstadoOrden, String> usuarioHasta;
	public static volatile SingularAttribute<CambioEstadoOrden, String> usuarioActualizacion;
	public static volatile SingularAttribute<CambioEstadoOrden, Date> fechaActualizacion;
	public static volatile SingularAttribute<CambioEstadoOrden, Integer> id;
	public static volatile SingularAttribute<CambioEstadoOrden, Orden> orden;

}

