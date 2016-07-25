package com.tacticlogistics.domain.model.seguridad;

import com.tacticlogistics.domain.model.wms.Bodega;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UsuarioBodegaAssociation.class)
public abstract class UsuarioBodegaAssociation_ {

	public static volatile SingularAttribute<UsuarioBodegaAssociation, Boolean> predeterminada;
	public static volatile SingularAttribute<UsuarioBodegaAssociation, Usuario> usuario;
	public static volatile SingularAttribute<UsuarioBodegaAssociation, Bodega> bodega;

}

