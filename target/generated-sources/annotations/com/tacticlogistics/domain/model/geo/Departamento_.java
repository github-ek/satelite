package com.tacticlogistics.domain.model.geo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Departamento.class)
public abstract class Departamento_ {

	public static volatile SingularAttribute<Departamento, String> codigo;
	public static volatile SingularAttribute<Departamento, BigDecimal> longitud;
	public static volatile SingularAttribute<Departamento, BigDecimal> latitud;
	public static volatile SingularAttribute<Departamento, String> usuarioActualizacion;
	public static volatile SingularAttribute<Departamento, Date> fechaActualizacion;
	public static volatile SingularAttribute<Departamento, Integer> id;
	public static volatile SingularAttribute<Departamento, String> nombre;
	public static volatile SingularAttribute<Departamento, Integer> ordinal;
	public static volatile SingularAttribute<Departamento, Boolean> activo;
	public static volatile SingularAttribute<Departamento, Pais> pais;

}

