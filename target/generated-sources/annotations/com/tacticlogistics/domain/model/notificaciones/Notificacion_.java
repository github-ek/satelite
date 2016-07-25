package com.tacticlogistics.domain.model.notificaciones;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Notificacion.class)
public abstract class Notificacion_ {

	public static volatile SingularAttribute<Notificacion, String> descripcion;
	public static volatile SingularAttribute<Notificacion, String> codigo;
	public static volatile SingularAttribute<Notificacion, String> usuarioActualizacion;
	public static volatile SingularAttribute<Notificacion, Date> fechaActualizacion;
	public static volatile SingularAttribute<Notificacion, Integer> id;
	public static volatile SingularAttribute<Notificacion, String> nombre;
	public static volatile SingularAttribute<Notificacion, GrupoNotificacion> grupoNotificacion;
	public static volatile SingularAttribute<Notificacion, Integer> ordinal;
	public static volatile SingularAttribute<Notificacion, Boolean> activo;

}

