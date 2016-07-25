package com.tacticlogistics.domain.model.ordenes;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CausalAnulacionOrden.class)
public abstract class CausalAnulacionOrden_ {

	public static volatile SingularAttribute<CausalAnulacionOrden, String> descripcion;
	public static volatile SingularAttribute<CausalAnulacionOrden, String> codigo;
	public static volatile SingularAttribute<CausalAnulacionOrden, String> usuarioActualizacion;
	public static volatile SingularAttribute<CausalAnulacionOrden, Date> fechaActualizacion;
	public static volatile SingularAttribute<CausalAnulacionOrden, Integer> id;
	public static volatile SingularAttribute<CausalAnulacionOrden, String> nombre;
	public static volatile SingularAttribute<CausalAnulacionOrden, Integer> ordinal;
	public static volatile SingularAttribute<CausalAnulacionOrden, Boolean> activo;

}

