package com.tacticlogistics.domain.model.entregas;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CambioEstadoEntrega.class)
public abstract class CambioEstadoEntrega_ {

	public static volatile SingularAttribute<CambioEstadoEntrega, String> usuarioDesde;
	public static volatile SingularAttribute<CambioEstadoEntrega, Date> fechaHasta;
	public static volatile SingularAttribute<CambioEstadoEntrega, Date> fechaDesde;
	public static volatile SingularAttribute<CambioEstadoEntrega, String> usuarioHasta;
	public static volatile SingularAttribute<CambioEstadoEntrega, String> usuarioActualizacion;
	public static volatile SingularAttribute<CambioEstadoEntrega, Date> fechaActualizacion;
	public static volatile SingularAttribute<CambioEstadoEntrega, Entrega> entrega;
	public static volatile SingularAttribute<CambioEstadoEntrega, Integer> id;
	public static volatile SingularAttribute<CambioEstadoEntrega, EstadoEntregaType> estadoEntrega;

}

