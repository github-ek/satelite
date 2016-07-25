package com.tacticlogistics.domain.model.crm;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TipoFormaPago.class)
public abstract class TipoFormaPago_ {

	public static volatile SingularAttribute<TipoFormaPago, String> descripcion;
	public static volatile SingularAttribute<TipoFormaPago, String> codigo;
	public static volatile SingularAttribute<TipoFormaPago, Boolean> requiereRecaudo;
	public static volatile SingularAttribute<TipoFormaPago, String> usuarioActualizacion;
	public static volatile SingularAttribute<TipoFormaPago, Date> fechaActualizacion;
	public static volatile SingularAttribute<TipoFormaPago, Integer> id;
	public static volatile SingularAttribute<TipoFormaPago, String> nombre;
	public static volatile SingularAttribute<TipoFormaPago, Integer> ordinal;
	public static volatile SingularAttribute<TipoFormaPago, Boolean> activo;

}

