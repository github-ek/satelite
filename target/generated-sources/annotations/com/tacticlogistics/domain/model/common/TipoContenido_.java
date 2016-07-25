package com.tacticlogistics.domain.model.common;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TipoContenido.class)
public abstract class TipoContenido_ {

	public static volatile SingularAttribute<TipoContenido, String> descripcion;
	public static volatile SingularAttribute<TipoContenido, String> codigo;
	public static volatile SingularAttribute<TipoContenido, String> usuarioActualizacion;
	public static volatile SingularAttribute<TipoContenido, Date> fechaActualizacion;
	public static volatile SingularAttribute<TipoContenido, Integer> id;
	public static volatile SingularAttribute<TipoContenido, String> nombre;
	public static volatile SingularAttribute<TipoContenido, Integer> ordinal;
	public static volatile SingularAttribute<TipoContenido, Boolean> activo;

}

