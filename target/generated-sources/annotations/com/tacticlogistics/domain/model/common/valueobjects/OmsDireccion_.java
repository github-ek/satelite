package com.tacticlogistics.domain.model.common.valueobjects;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OmsDireccion.class)
public abstract class OmsDireccion_ {

	public static volatile SingularAttribute<OmsDireccion, BigDecimal> longitud;
	public static volatile SingularAttribute<OmsDireccion, BigDecimal> latitud;
	public static volatile SingularAttribute<OmsDireccion, String> direccionEstandarizada;
	public static volatile SingularAttribute<OmsDireccion, String> direccion;
	public static volatile SingularAttribute<OmsDireccion, Integer> ciudadId;
	public static volatile SingularAttribute<OmsDireccion, String> indicacionesDireccion;

}

