package com.tacticlogistics.domain.model.notificaciones;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GrupoNotificacion.class)
public abstract class GrupoNotificacion_ {

	public static volatile SingularAttribute<GrupoNotificacion, String> descripcion;
	public static volatile SingularAttribute<GrupoNotificacion, String> codigo;
	public static volatile SingularAttribute<GrupoNotificacion, String> usuarioActualizacion;
	public static volatile SingularAttribute<GrupoNotificacion, Date> fechaActualizacion;
	public static volatile SingularAttribute<GrupoNotificacion, Integer> id;
	public static volatile SingularAttribute<GrupoNotificacion, String> nombre;
	public static volatile SingularAttribute<GrupoNotificacion, Integer> ordinal;
	public static volatile SingularAttribute<GrupoNotificacion, Boolean> activo;

}

