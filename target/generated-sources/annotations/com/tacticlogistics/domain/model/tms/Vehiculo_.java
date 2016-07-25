package com.tacticlogistics.domain.model.tms;

import com.tacticlogistics.domain.model.wms.Bodega;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Vehiculo.class)
public abstract class Vehiculo_ {

	public static volatile SingularAttribute<Vehiculo, DispositivoMovilSeguimiento> dispositivoMovilSeguimiento;
	public static volatile SingularAttribute<Vehiculo, String> usuarioActualizacion;
	public static volatile SingularAttribute<Vehiculo, Date> fechaActualizacion;
	public static volatile SingularAttribute<Vehiculo, Integer> id;
	public static volatile SingularAttribute<Vehiculo, TipoVehiculo> tipoVehiculo;
	public static volatile SingularAttribute<Vehiculo, Conductor> conductor;
	public static volatile SingularAttribute<Vehiculo, Transportadora> transportadora;
	public static volatile SingularAttribute<Vehiculo, Bodega> bodega;
	public static volatile SingularAttribute<Vehiculo, String> placa;
	public static volatile SingularAttribute<Vehiculo, Boolean> activo;

}

