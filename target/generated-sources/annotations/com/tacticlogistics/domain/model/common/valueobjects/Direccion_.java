package com.tacticlogistics.domain.model.common.valueobjects;

import com.tacticlogistics.domain.model.geo.Ciudad;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Direccion.class)
public abstract class Direccion_ {

	public static volatile SingularAttribute<Direccion, BigDecimal> longitud;
	public static volatile SingularAttribute<Direccion, BigDecimal> latitud;
	public static volatile SingularAttribute<Direccion, String> direccionEstandarizada;
	public static volatile SingularAttribute<Direccion, Ciudad> ciudad;
	public static volatile SingularAttribute<Direccion, String> direccion;
	public static volatile SingularAttribute<Direccion, String> indicacionesDireccion;

}

