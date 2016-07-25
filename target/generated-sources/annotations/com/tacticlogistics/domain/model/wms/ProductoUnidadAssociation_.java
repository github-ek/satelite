package com.tacticlogistics.domain.model.wms;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProductoUnidadAssociation.class)
public abstract class ProductoUnidadAssociation_ {

	public static volatile SingularAttribute<ProductoUnidadAssociation, Boolean> unidadBase;
	public static volatile SingularAttribute<ProductoUnidadAssociation, Integer> valorAproximado;
	public static volatile SingularAttribute<ProductoUnidadAssociation, Boolean> predeterminadaEnOrdenesDeIngreso;
	public static volatile SingularAttribute<ProductoUnidadAssociation, Producto> producto;
	public static volatile SingularAttribute<ProductoUnidadAssociation, BigDecimal> alto;
	public static volatile SingularAttribute<ProductoUnidadAssociation, Boolean> habilitadaEnOrdenesDeIngreso;
	public static volatile SingularAttribute<ProductoUnidadAssociation, Unidad> unidad;
	public static volatile SingularAttribute<ProductoUnidadAssociation, BigDecimal> ancho;
	public static volatile SingularAttribute<ProductoUnidadAssociation, Boolean> habilitadaEnOrdenesDeSalida;
	public static volatile SingularAttribute<ProductoUnidadAssociation, Boolean> predeterminadaEnOrdenesDeSalida;
	public static volatile SingularAttribute<ProductoUnidadAssociation, Integer> factorConversion;
	public static volatile SingularAttribute<ProductoUnidadAssociation, BigDecimal> largo;
	public static volatile SingularAttribute<ProductoUnidadAssociation, BigDecimal> pesoBruto;
	public static volatile SingularAttribute<ProductoUnidadAssociation, Integer> nivel;

}

