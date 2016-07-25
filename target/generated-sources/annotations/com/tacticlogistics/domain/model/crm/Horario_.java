package com.tacticlogistics.domain.model.crm;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Horario.class)
public abstract class Horario_ {

	public static volatile SingularAttribute<Horario, Cliente> cliente;
	public static volatile SingularAttribute<Horario, DestinatarioRemitente> destinatarioRemitente;
	public static volatile SingularAttribute<Horario, DestinoOrigen> destinoOrigen;
	public static volatile SingularAttribute<Horario, GrupoHorario> grupoHorario;
	public static volatile SingularAttribute<Horario, Integer> id;
	public static volatile SetAttribute<Horario, String> codigosCiudades;

}

