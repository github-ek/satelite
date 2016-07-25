package com.tacticlogistics.domain.model.requerimientos;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GrupoRequerimiento.class)
public abstract class GrupoRequerimiento_ {

	public static volatile SingularAttribute<GrupoRequerimiento, String> descripcion;
	public static volatile SingularAttribute<GrupoRequerimiento, String> codigo;
	public static volatile SetAttribute<GrupoRequerimiento, Requerimiento> requerimientos;
	public static volatile SingularAttribute<GrupoRequerimiento, String> usuarioActualizacion;
	public static volatile SingularAttribute<GrupoRequerimiento, Date> fechaActualizacion;
	public static volatile SingularAttribute<GrupoRequerimiento, Integer> id;
	public static volatile SingularAttribute<GrupoRequerimiento, String> nombre;
	public static volatile SingularAttribute<GrupoRequerimiento, Boolean> activo;

}

