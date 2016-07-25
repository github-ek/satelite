package com.tacticlogistics.domain.model.requerimientos;

import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.requerimientos.valueobjects.OpcionesDeRequerimientoEmbeddable;
import com.tacticlogistics.domain.model.requerimientos.valueobjects.PermisosParaPersonalizarOpcionesDeRequerimientoEmbeddable;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RequerimientoTipoServicioAssociation.class)
public abstract class RequerimientoTipoServicioAssociation_ {

	public static volatile SingularAttribute<RequerimientoTipoServicioAssociation, TipoServicio> tipoServicio;
	public static volatile SingularAttribute<RequerimientoTipoServicioAssociation, OpcionesDeRequerimientoEmbeddable> opciones;
	public static volatile SingularAttribute<RequerimientoTipoServicioAssociation, Requerimiento> requerimiento;
	public static volatile SingularAttribute<RequerimientoTipoServicioAssociation, PermisosParaPersonalizarOpcionesDeRequerimientoEmbeddable> permisos;
	public static volatile SingularAttribute<RequerimientoTipoServicioAssociation, Integer> ordinal;

}

