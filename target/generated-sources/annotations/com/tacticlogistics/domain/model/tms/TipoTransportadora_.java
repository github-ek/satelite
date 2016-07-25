package com.tacticlogistics.domain.model.tms;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TipoTransportadora.class)
public abstract class TipoTransportadora_ {

	public static volatile SingularAttribute<TipoTransportadora, String> descripcion;
	public static volatile SingularAttribute<TipoTransportadora, String> codigo;
	public static volatile SingularAttribute<TipoTransportadora, String> usuarioActualizacion;
	public static volatile SingularAttribute<TipoTransportadora, Date> fechaActualizacion;
	public static volatile SingularAttribute<TipoTransportadora, Integer> id;
	public static volatile SingularAttribute<TipoTransportadora, String> nombre;
	public static volatile SingularAttribute<TipoTransportadora, Boolean> activo;

}

