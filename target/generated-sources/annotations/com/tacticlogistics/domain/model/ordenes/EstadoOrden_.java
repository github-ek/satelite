package com.tacticlogistics.domain.model.ordenes;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EstadoOrden.class)
public abstract class EstadoOrden_ {

	public static volatile SingularAttribute<EstadoOrden, String> descripcion;
	public static volatile SingularAttribute<EstadoOrden, String> usuarioActualizacion;
	public static volatile SingularAttribute<EstadoOrden, EstadoOrdenType> EstadoOrden;
	public static volatile SingularAttribute<EstadoOrden, Date> fechaActualizacion;
	public static volatile SingularAttribute<EstadoOrden, Integer> ordinal;
	public static volatile SingularAttribute<EstadoOrden, Boolean> activo;

}

