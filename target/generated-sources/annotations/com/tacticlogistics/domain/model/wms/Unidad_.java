package com.tacticlogistics.domain.model.wms;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Unidad.class)
public abstract class Unidad_ {

	public static volatile SingularAttribute<Unidad, String> descripcion;
	public static volatile SingularAttribute<Unidad, String> codigo;
	public static volatile SingularAttribute<Unidad, String> usuarioActualizacion;
	public static volatile SingularAttribute<Unidad, Date> fechaActualizacion;
	public static volatile SingularAttribute<Unidad, Integer> id;
	public static volatile SingularAttribute<Unidad, String> nombre;
	public static volatile SingularAttribute<Unidad, Integer> ordinal;
	public static volatile SingularAttribute<Unidad, Boolean> activo;

}

