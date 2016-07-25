package com.tacticlogistics.domain.model.wms;

import com.tacticlogistics.domain.model.crm.Cliente;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Producto.class)
public abstract class Producto_ {

	public static volatile SingularAttribute<Producto, Cliente> cliente;
	public static volatile SingularAttribute<Producto, String> codigo;
	public static volatile SingularAttribute<Producto, String> nombreLargo;
	public static volatile SetAttribute<Producto, ProductoBodegaAssociation> productoBodegaAssociation;
	public static volatile SingularAttribute<Producto, String> usuarioActualizacion;
	public static volatile SingularAttribute<Producto, Date> fechaActualizacion;
	public static volatile SingularAttribute<Producto, Boolean> existeEnWms;
	public static volatile SingularAttribute<Producto, Integer> id;
	public static volatile SingularAttribute<Producto, String> codigoAlterno;
	public static volatile SingularAttribute<Producto, String> nombre;
	public static volatile SetAttribute<Producto, ProductoUnidadAssociation> productoUnidadAssociation;
	public static volatile SingularAttribute<Producto, Boolean> activo;

}

