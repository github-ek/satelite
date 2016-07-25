package com.tacticlogistics.domain.model.geo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ciudad.class)
public abstract class Ciudad_ {

	public static volatile SingularAttribute<Ciudad, String> codigo;
	public static volatile SingularAttribute<Ciudad, BigDecimal> longitud;
	public static volatile SingularAttribute<Ciudad, BigDecimal> latitud;
	public static volatile SingularAttribute<Ciudad, String> usuarioActualizacion;
	public static volatile SingularAttribute<Ciudad, Date> fechaActualizacion;
	public static volatile SingularAttribute<Ciudad, Departamento> departamento;
	public static volatile SingularAttribute<Ciudad, Integer> id;
	public static volatile SingularAttribute<Ciudad, String> nombre;
	public static volatile SingularAttribute<Ciudad, String> nombreAlterno;
	public static volatile SingularAttribute<Ciudad, Integer> ordinal;
	public static volatile SingularAttribute<Ciudad, Boolean> activo;

}

