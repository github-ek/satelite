package com.tacticlogistics.domain.model.tms;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TipoVehiculo.class)
public abstract class TipoVehiculo_ {

	public static volatile SingularAttribute<TipoVehiculo, String> codigo;
	public static volatile SingularAttribute<TipoVehiculo, String> usuarioActualizacion;
	public static volatile SingularAttribute<TipoVehiculo, Date> fechaActualizacion;
	public static volatile SingularAttribute<TipoVehiculo, Integer> id;
	public static volatile SingularAttribute<TipoVehiculo, String> nombre;
	public static volatile SingularAttribute<TipoVehiculo, Boolean> requiereRemolque;
	public static volatile SingularAttribute<TipoVehiculo, Boolean> activo;

}

