package com.tacticlogistics.domain.model.entregas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EstadoEntrega.class)
public abstract class EstadoEntrega_ {

	public static volatile SingularAttribute<EstadoEntrega, String> descripcion;
	public static volatile SingularAttribute<EstadoEntrega, String> usuarioActualizacion;
	public static volatile SingularAttribute<EstadoEntrega, EstadoEntregaType> EstadoEntrega;
	public static volatile SingularAttribute<EstadoEntrega, Date> fechaActualizacion;
	public static volatile SingularAttribute<EstadoEntrega, Integer> ordinal;
	public static volatile SingularAttribute<EstadoEntrega, Boolean> activo;

}

