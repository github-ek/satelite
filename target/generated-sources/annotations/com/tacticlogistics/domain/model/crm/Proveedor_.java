package com.tacticlogistics.domain.model.crm;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Proveedor.class)
public abstract class Proveedor_ {

	public static volatile SingularAttribute<Proveedor, Cliente> cliente;
	public static volatile SingularAttribute<Proveedor, String> codigo;
	public static volatile SingularAttribute<Proveedor, String> usuarioActualizacion;
	public static volatile SingularAttribute<Proveedor, Date> fechaActualizacion;
	public static volatile SingularAttribute<Proveedor, Integer> id;
	public static volatile SingularAttribute<Proveedor, String> nombre;
	public static volatile SingularAttribute<Proveedor, Boolean> activo;

}

