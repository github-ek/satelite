package com.tacticlogistics.domain.model.oms;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OmsDireccionGeoreferenciada.class)
public abstract class OmsDireccionGeoreferenciada_ {

	public static volatile SingularAttribute<OmsDireccionGeoreferenciada, String> usuarioGeoreferenciacion;
	public static volatile SingularAttribute<OmsDireccionGeoreferenciada, String> zona;
	public static volatile SingularAttribute<OmsDireccionGeoreferenciada, Date> fechaGeoreferenciacion;
	public static volatile SingularAttribute<OmsDireccionGeoreferenciada, EstadoGeoReferenciacionType> estadoGeoReferenciacion;
	public static volatile SingularAttribute<OmsDireccionGeoreferenciada, String> barrio;
	public static volatile SingularAttribute<OmsDireccionGeoreferenciada, String> direccionEstandarizada;
	public static volatile SingularAttribute<OmsDireccionGeoreferenciada, BigDecimal> cx;
	public static volatile SingularAttribute<OmsDireccionGeoreferenciada, Integer> tipoGeoReferenciacionId;
	public static volatile SingularAttribute<OmsDireccionGeoreferenciada, BigDecimal> cy;
	public static volatile SingularAttribute<OmsDireccionGeoreferenciada, String> direccionSugerida;
	public static volatile SingularAttribute<OmsDireccionGeoreferenciada, String> localidad;

}

