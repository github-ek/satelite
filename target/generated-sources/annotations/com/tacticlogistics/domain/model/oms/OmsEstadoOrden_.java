package com.tacticlogistics.domain.model.oms;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OmsEstadoOrden.class)
public abstract class OmsEstadoOrden_ {

	public static volatile SingularAttribute<OmsEstadoOrden, String> descripcion;
	public static volatile SingularAttribute<OmsEstadoOrden, String> usuarioActualizacion;
	public static volatile SingularAttribute<OmsEstadoOrden, EstadoOrdenType> EstadoOrden;
	public static volatile SingularAttribute<OmsEstadoOrden, Date> fechaActualizacion;
	public static volatile SingularAttribute<OmsEstadoOrden, Integer> ordinal;
	public static volatile SingularAttribute<OmsEstadoOrden, Boolean> activo;

}

