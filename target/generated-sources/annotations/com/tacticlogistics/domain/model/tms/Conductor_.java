package com.tacticlogistics.domain.model.tms;

import com.tacticlogistics.domain.model.common.IdentificacionType;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Conductor.class)
public abstract class Conductor_ {

	public static volatile SingularAttribute<Conductor, String> apellidos;
	public static volatile SingularAttribute<Conductor, String> codigo;
	public static volatile SingularAttribute<Conductor, String> numeroIdentificacion;
	public static volatile SingularAttribute<Conductor, IdentificacionType> identificacionType;
	public static volatile SingularAttribute<Conductor, String> usuarioActualizacion;
	public static volatile SingularAttribute<Conductor, Date> fechaActualizacion;
	public static volatile SingularAttribute<Conductor, Integer> id;
	public static volatile SingularAttribute<Conductor, Transportadora> transportadora;
	public static volatile SingularAttribute<Conductor, String> nombres;
	public static volatile SingularAttribute<Conductor, Boolean> activo;

}

