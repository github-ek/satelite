package com.tacticlogistics.domain.model.cpr;

import com.tacticlogistics.domain.model.wms.Bodega;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CentroPlaneacionRuta.class)
public abstract class CentroPlaneacionRuta_ {

	public static volatile SingularAttribute<CentroPlaneacionRuta, String> codigo;
	public static volatile SetAttribute<CentroPlaneacionRuta, Bodega> bodegas;
	public static volatile SingularAttribute<CentroPlaneacionRuta, String> usuarioActualizacion;
	public static volatile SingularAttribute<CentroPlaneacionRuta, Date> fechaActualizacion;
	public static volatile SetAttribute<CentroPlaneacionRuta, PlanificadorRuta> planificadoresRutas;
	public static volatile SingularAttribute<CentroPlaneacionRuta, Integer> id;
	public static volatile SetAttribute<CentroPlaneacionRuta, String> codigosCiudades;
	public static volatile SingularAttribute<CentroPlaneacionRuta, String> nombre;
	public static volatile SingularAttribute<CentroPlaneacionRuta, Boolean> activo;

}

