package com.tacticlogistics.domain.model.calendario;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Calendario.class)
public abstract class Calendario_ {

	public static volatile SingularAttribute<Calendario, Date> fecha;
	public static volatile SingularAttribute<Calendario, String> usuarioActualizacion;
	public static volatile SingularAttribute<Calendario, Date> fechaActualizacion;
	public static volatile SingularAttribute<Calendario, Integer> id;
	public static volatile SingularAttribute<Calendario, DiaDeSemanaType> dia;
	public static volatile SingularAttribute<Calendario, Boolean> diaHabil;

}

