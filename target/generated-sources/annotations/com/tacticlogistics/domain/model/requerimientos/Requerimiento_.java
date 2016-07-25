package com.tacticlogistics.domain.model.requerimientos;

import com.tacticlogistics.domain.model.requerimientos.valueobjects.OpcionesDeRequerimientoEmbeddable;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Requerimiento.class)
public abstract class Requerimiento_ {

	public static volatile SingularAttribute<Requerimiento, String> descripcion;
	public static volatile SingularAttribute<Requerimiento, String> codigo;
	public static volatile SingularAttribute<Requerimiento, RequerimientoType> tipoRequerimiento;
	public static volatile SetAttribute<Requerimiento, RequerimientoClienteAssociation> requerimientoClienteAssociation;
	public static volatile SetAttribute<Requerimiento, RequerimientoTipoServicioAssociation> requerimientoTipoServicioAssociation;
	public static volatile SingularAttribute<Requerimiento, String> descripcionDeRequisitos;
	public static volatile SingularAttribute<Requerimiento, String> nombre;
	public static volatile SingularAttribute<Requerimiento, OpcionesDeRequerimientoEmbeddable> opciones;
	public static volatile SingularAttribute<Requerimiento, Boolean> aplicaSoloAlNIvelDeOrden;
	public static volatile SingularAttribute<Requerimiento, Boolean> soloPermitirUnaUnicaOcurrenciaPorOrdenOPorLinea;
	public static volatile SingularAttribute<Requerimiento, String> usuarioActualizacion;
	public static volatile SingularAttribute<Requerimiento, Date> fechaActualizacion;
	public static volatile SingularAttribute<Requerimiento, Integer> id;
	public static volatile SingularAttribute<Requerimiento, GrupoRequerimiento> grupoRequerimiento;
	public static volatile SingularAttribute<Requerimiento, Boolean> activo;

}

