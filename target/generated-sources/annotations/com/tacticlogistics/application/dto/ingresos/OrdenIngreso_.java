package com.tacticlogistics.application.dto.ingresos;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrdenIngreso.class)
public abstract class OrdenIngreso_ {

	public static volatile SingularAttribute<OrdenIngreso, EstadoOrdenIngresoType> estado;
	public static volatile SingularAttribute<OrdenIngreso, String> fotoPiso;
	public static volatile SingularAttribute<OrdenIngreso, String> placaRemolque;
	public static volatile SingularAttribute<OrdenIngreso, String> proveedores;
	public static volatile SingularAttribute<OrdenIngreso, String> fechaInicio;
	public static volatile SingularAttribute<OrdenIngreso, Boolean> notificado;
	public static volatile SingularAttribute<OrdenIngreso, String> numeroDocumentoCliente;
	public static volatile SingularAttribute<OrdenIngreso, String> numeroTornaguia;
	public static volatile SingularAttribute<OrdenIngreso, Integer> id;
	public static volatile SingularAttribute<OrdenIngreso, String> fotoSalida;
	public static volatile SingularAttribute<OrdenIngreso, Date> fechaIntegracionWMS;
	public static volatile ListAttribute<OrdenIngreso, LineaOrdenIngreso> productos;
	public static volatile SingularAttribute<OrdenIngreso, Date> fechaRealimentacionWMS;
	public static volatile SingularAttribute<OrdenIngreso, String> placaVehiculo;
	public static volatile SingularAttribute<OrdenIngreso, String> fotoTornaguia;
	public static volatile SingularAttribute<OrdenIngreso, String> fotoPrecinto;
	public static volatile SingularAttribute<OrdenIngreso, String> fechaFinRegistro;
	public static volatile SingularAttribute<OrdenIngreso, String> fondoCuenta;
	public static volatile SingularAttribute<OrdenIngreso, Date> fechaNotificacion;
	public static volatile SingularAttribute<OrdenIngreso, String> vehiculo;
	public static volatile SingularAttribute<OrdenIngreso, String> documentosProductoImportado;
	public static volatile SingularAttribute<OrdenIngreso, String> documentosProductoNacional;
	public static volatile SingularAttribute<OrdenIngreso, String> imagenIngreso;
	public static volatile SingularAttribute<OrdenIngreso, String> bodega;
	public static volatile SingularAttribute<OrdenIngreso, String> transportadora;
	public static volatile SingularAttribute<OrdenIngreso, String> cliente;
	public static volatile SingularAttribute<OrdenIngreso, String> numeroPrecinto;
	public static volatile SingularAttribute<OrdenIngreso, String> ciudadOrigen;
	public static volatile SingularAttribute<OrdenIngreso, String> plasticos;
	public static volatile SingularAttribute<OrdenIngreso, String> usuario;
	public static volatile SingularAttribute<OrdenIngreso, String> imagenInicioDescarga;
	public static volatile SingularAttribute<OrdenIngreso, Integer> hash;
	public static volatile SingularAttribute<OrdenIngreso, Date> fechaRecepcion;

}

