package com.tacticlogistics.domain.model.notificaciones;

import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.crm.Cliente;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Suscriptor.class)
public abstract class Suscriptor_ {

	public static volatile SingularAttribute<Suscriptor, Cliente> cliente;
	public static volatile SingularAttribute<Suscriptor, Contacto> contacto;
	public static volatile SingularAttribute<Suscriptor, GrupoSuscriptor> grupoSuscriptor;
	public static volatile SetAttribute<Suscriptor, SuscriptorNotificacionAssociation> suscriptorNotificacionAssociation;
	public static volatile SingularAttribute<Suscriptor, Integer> id;

}

