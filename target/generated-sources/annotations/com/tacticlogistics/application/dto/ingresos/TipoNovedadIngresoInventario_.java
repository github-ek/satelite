package com.tacticlogistics.application.dto.ingresos;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TipoNovedadIngresoInventario.class)
public abstract class TipoNovedadIngresoInventario_ {

	public static volatile SingularAttribute<TipoNovedadIngresoInventario, String> codigo;
	public static volatile SingularAttribute<TipoNovedadIngresoInventario, String> usuarioActualizacion;
	public static volatile SingularAttribute<TipoNovedadIngresoInventario, Date> fechaActualizacion;
	public static volatile SingularAttribute<TipoNovedadIngresoInventario, Integer> id;
	public static volatile SingularAttribute<TipoNovedadIngresoInventario, String> nombre;
	public static volatile SingularAttribute<TipoNovedadIngresoInventario, Integer> ordinal;
	public static volatile SingularAttribute<TipoNovedadIngresoInventario, Boolean> activo;

}

