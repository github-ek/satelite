package com.tacticlogistics.domain.model.requerimientos;

import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.requerimientos.valueobjects.OpcionesDeRequerimientoEmbeddable;
import com.tacticlogistics.domain.model.requerimientos.valueobjects.PermisosParaPersonalizarOpcionesDeRequerimientoEmbeddable;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RequerimientoClienteAssociation.class)
public abstract class RequerimientoClienteAssociation_ {

	public static volatile SingularAttribute<RequerimientoClienteAssociation, Cliente> cliente;
	public static volatile SingularAttribute<RequerimientoClienteAssociation, TipoServicio> tipoServicio;
	public static volatile SingularAttribute<RequerimientoClienteAssociation, OpcionesDeRequerimientoEmbeddable> opciones;
	public static volatile SingularAttribute<RequerimientoClienteAssociation, Boolean> incluirSiempre;
	public static volatile SingularAttribute<RequerimientoClienteAssociation, Requerimiento> requerimiento;
	public static volatile SingularAttribute<RequerimientoClienteAssociation, PermisosParaPersonalizarOpcionesDeRequerimientoEmbeddable> permisos;

}

