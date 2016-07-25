package com.tacticlogistics.domain.model.wms;

import com.tacticlogistics.domain.model.common.valueobjects.OmsDireccion;
import com.tacticlogistics.domain.model.crm.ClienteBodegaAssociation;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bodega.class)
public abstract class Bodega_ {

	public static volatile SingularAttribute<Bodega, String> codigo;
	public static volatile SetAttribute<Bodega, ClienteBodegaAssociation> bodegaClienteAssociation;
	public static volatile SingularAttribute<Bodega, Boolean> manejaRenta;
	public static volatile SingularAttribute<Bodega, OmsDireccion> direccion;
	public static volatile SingularAttribute<Bodega, String> usuarioActualizacion;
	public static volatile SingularAttribute<Bodega, Date> fechaActualizacion;
	public static volatile SingularAttribute<Bodega, Integer> id;
	public static volatile SingularAttribute<Bodega, String> nombre;
	public static volatile SingularAttribute<Bodega, Boolean> activo;

}

