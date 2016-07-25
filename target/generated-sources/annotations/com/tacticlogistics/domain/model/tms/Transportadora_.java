package com.tacticlogistics.domain.model.tms;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transportadora.class)
public abstract class Transportadora_ {

	public static volatile SingularAttribute<Transportadora, String> codigo;
	public static volatile SingularAttribute<Transportadora, String> usuarioActualizacion;
	public static volatile SingularAttribute<Transportadora, Date> fechaActualizacion;
	public static volatile SingularAttribute<Transportadora, Integer> id;
	public static volatile SingularAttribute<Transportadora, TipoTransportadora> tipoTransportadora;
	public static volatile SingularAttribute<Transportadora, String> nombre;
	public static volatile SingularAttribute<Transportadora, Boolean> activo;

}

