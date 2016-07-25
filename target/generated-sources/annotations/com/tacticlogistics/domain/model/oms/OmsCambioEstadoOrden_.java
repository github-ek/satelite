package com.tacticlogistics.domain.model.oms;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OmsCambioEstadoOrden.class)
public abstract class OmsCambioEstadoOrden_ {

	public static volatile SingularAttribute<OmsCambioEstadoOrden, EstadoOrdenType> estadoOrden;
	public static volatile SingularAttribute<OmsCambioEstadoOrden, String> usuarioDesde;
	public static volatile SingularAttribute<OmsCambioEstadoOrden, Date> fechaHasta;
	public static volatile SingularAttribute<OmsCambioEstadoOrden, Date> fechaDesde;
	public static volatile SingularAttribute<OmsCambioEstadoOrden, String> usuarioHasta;
	public static volatile SingularAttribute<OmsCambioEstadoOrden, String> usuarioActualizacion;
	public static volatile SingularAttribute<OmsCambioEstadoOrden, Date> fechaActualizacion;
	public static volatile SingularAttribute<OmsCambioEstadoOrden, Integer> id;
	public static volatile SingularAttribute<OmsCambioEstadoOrden, OmsOrden> orden;

}

