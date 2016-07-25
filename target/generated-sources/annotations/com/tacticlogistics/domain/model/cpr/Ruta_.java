package com.tacticlogistics.domain.model.cpr;

import com.tacticlogistics.domain.model.common.valueobjects.UbicacionEmbeddable;
import com.tacticlogistics.domain.model.tms.Conductor;
import com.tacticlogistics.domain.model.tms.Transportadora;
import com.tacticlogistics.domain.model.tms.Vehiculo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ruta.class)
public abstract class Ruta_ {

	public static volatile SingularAttribute<Ruta, String> codigoDispositivoMovilSeguimiento;
	public static volatile SingularAttribute<Ruta, String> placaRemolque;
	public static volatile SingularAttribute<Ruta, Vehiculo> vehiculo;
	public static volatile SingularAttribute<Ruta, UbicacionEmbeddable> origen;
	public static volatile SingularAttribute<Ruta, Conductor> conductor;
	public static volatile SingularAttribute<Ruta, Transportadora> transportadora;
	public static volatile SingularAttribute<Ruta, CortePlaneacionRuta> corte;
	public static volatile SingularAttribute<Ruta, String> numeroContenedor;
	public static volatile SingularAttribute<Ruta, String> usuarioActualizacion;
	public static volatile SingularAttribute<Ruta, Date> fechaActualizacion;
	public static volatile SingularAttribute<Ruta, Integer> id;
	public static volatile SingularAttribute<Ruta, Date> fechaAlistamiento;
	public static volatile SingularAttribute<Ruta, String> placa;
	public static volatile SingularAttribute<Ruta, Boolean> activo;

}

