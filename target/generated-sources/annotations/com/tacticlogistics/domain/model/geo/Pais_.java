package com.tacticlogistics.domain.model.geo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pais.class)
public abstract class Pais_ {

	public static volatile SingularAttribute<Pais, String> codigo;
	public static volatile SingularAttribute<Pais, BigDecimal> longitud;
	public static volatile SingularAttribute<Pais, BigDecimal> latitud;
	public static volatile SingularAttribute<Pais, String> usuarioActualizacion;
	public static volatile SingularAttribute<Pais, Date> fechaActualizacion;
	public static volatile SingularAttribute<Pais, Integer> id;
	public static volatile SingularAttribute<Pais, String> nombre;
	public static volatile SingularAttribute<Pais, Integer> ordinal;
	public static volatile SingularAttribute<Pais, Boolean> activo;

}

