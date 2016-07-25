package com.tacticlogistics.domain.model.cpr;

import com.tacticlogistics.domain.model.seguridad.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PlanificadorRuta.class)
public abstract class PlanificadorRuta_ {

	public static volatile SingularAttribute<PlanificadorRuta, String> usuarioActualizacion;
	public static volatile SingularAttribute<PlanificadorRuta, Date> fechaActualizacion;
	public static volatile SingularAttribute<PlanificadorRuta, Usuario> usuario;
	public static volatile SingularAttribute<PlanificadorRuta, Integer> id;
	public static volatile SingularAttribute<PlanificadorRuta, CentroPlaneacionRuta> centroPlaneacionRuta;
	public static volatile SingularAttribute<PlanificadorRuta, Boolean> activo;

}

