package com.tacticlogistics.domain.model.cpr;

import java.sql.Time;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CortePlaneacionRuta.class)
public abstract class CortePlaneacionRuta_ {

	public static volatile SingularAttribute<CortePlaneacionRuta, Time> horaFin;
	public static volatile SingularAttribute<CortePlaneacionRuta, PlanificadorRuta> planificadorRuta;
	public static volatile SingularAttribute<CortePlaneacionRuta, Date> fechaProgramadaSalidaRutas;
	public static volatile SingularAttribute<CortePlaneacionRuta, String> usuarioActualizacion;
	public static volatile SingularAttribute<CortePlaneacionRuta, Date> fechaActualizacion;
	public static volatile SingularAttribute<CortePlaneacionRuta, Integer> id;
	public static volatile SingularAttribute<CortePlaneacionRuta, String> nombre;
	public static volatile SingularAttribute<CortePlaneacionRuta, Time> horaInicio;
	public static volatile SingularAttribute<CortePlaneacionRuta, CentroPlaneacionRuta> centroPlaneacionRuta;
	public static volatile SingularAttribute<CortePlaneacionRuta, Boolean> activo;

}

