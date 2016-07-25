package com.tacticlogistics.domain.model.cpr;

import com.tacticlogistics.domain.model.entregas.Entrega;
import com.tacticlogistics.domain.model.entregas.EstadoEntregaType;
import java.sql.Time;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RutaEntregaAssociation.class)
public abstract class RutaEntregaAssociation_ {

	public static volatile SingularAttribute<RutaEntregaAssociation, Date> fechaProgramadaEntrega;
	public static volatile SingularAttribute<RutaEntregaAssociation, EstadoEntregaType> estadoEntregaType;
	public static volatile SingularAttribute<RutaEntregaAssociation, Ruta> ruta;
	public static volatile SingularAttribute<RutaEntregaAssociation, Integer> consecutivoRuta;
	public static volatile SingularAttribute<RutaEntregaAssociation, String> usuarioActualizacion;
	public static volatile SingularAttribute<RutaEntregaAssociation, Date> fechaActualizacion;
	public static volatile SingularAttribute<RutaEntregaAssociation, Entrega> entrega;
	public static volatile SingularAttribute<RutaEntregaAssociation, Time> horaProgramadaEntrega;
	public static volatile SingularAttribute<RutaEntregaAssociation, Date> fechaRealEntrega;
	public static volatile SingularAttribute<RutaEntregaAssociation, Time> horaRealEntrega;
	public static volatile SingularAttribute<RutaEntregaAssociation, Boolean> activo;

}

