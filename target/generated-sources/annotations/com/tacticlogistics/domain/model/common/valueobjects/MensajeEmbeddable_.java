package com.tacticlogistics.domain.model.common.valueobjects;

import com.tacticlogistics.domain.model.common.SeveridadType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MensajeEmbeddable.class)
public abstract class MensajeEmbeddable_ {

	public static volatile SingularAttribute<MensajeEmbeddable, String> texto;
	public static volatile SingularAttribute<MensajeEmbeddable, String> codigo;
	public static volatile SingularAttribute<MensajeEmbeddable, String> grupo;
	public static volatile SingularAttribute<MensajeEmbeddable, SeveridadType> severidad;

}

