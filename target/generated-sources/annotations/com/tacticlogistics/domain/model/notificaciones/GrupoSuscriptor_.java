package com.tacticlogistics.domain.model.notificaciones;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GrupoSuscriptor.class)
public abstract class GrupoSuscriptor_ {

	public static volatile SingularAttribute<GrupoSuscriptor, String> descripcion;
	public static volatile SingularAttribute<GrupoSuscriptor, String> codigo;
	public static volatile SingularAttribute<GrupoSuscriptor, String> usuarioActualizacion;
	public static volatile SingularAttribute<GrupoSuscriptor, Date> fechaActualizacion;
	public static volatile SingularAttribute<GrupoSuscriptor, Integer> id;
	public static volatile SingularAttribute<GrupoSuscriptor, String> nombre;
	public static volatile SingularAttribute<GrupoSuscriptor, Integer> ordinal;
	public static volatile SingularAttribute<GrupoSuscriptor, Boolean> activo;

}

