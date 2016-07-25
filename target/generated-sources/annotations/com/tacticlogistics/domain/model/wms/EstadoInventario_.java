package com.tacticlogistics.domain.model.wms;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EstadoInventario.class)
public abstract class EstadoInventario_ {

	public static volatile SingularAttribute<EstadoInventario, String> codigo;
	public static volatile SingularAttribute<EstadoInventario, String> usuarioActualizacion;
	public static volatile SingularAttribute<EstadoInventario, Date> fechaActualizacion;
	public static volatile SingularAttribute<EstadoInventario, Integer> id;
	public static volatile SingularAttribute<EstadoInventario, String> nombre;
	public static volatile SingularAttribute<EstadoInventario, Integer> ordinal;
	public static volatile SingularAttribute<EstadoInventario, Boolean> activo;

}

