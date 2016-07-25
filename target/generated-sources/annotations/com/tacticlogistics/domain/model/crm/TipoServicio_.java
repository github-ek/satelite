package com.tacticlogistics.domain.model.crm;

import com.tacticlogistics.domain.model.requerimientos.RequerimientoClienteAssociation;
import com.tacticlogistics.domain.model.requerimientos.RequerimientoTipoServicioAssociation;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TipoServicio.class)
public abstract class TipoServicio_ {

	public static volatile SingularAttribute<TipoServicio, String> descripcion;
	public static volatile SingularAttribute<TipoServicio, String> codigo;
	public static volatile SingularAttribute<TipoServicio, Boolean> admitePaquetesPorLinea;
	public static volatile SetAttribute<TipoServicio, RequerimientoClienteAssociation> requerimientoClienteAssociation;
	public static volatile SetAttribute<TipoServicio, RequerimientoTipoServicioAssociation> requerimientoTipoServicioAssociation;
	public static volatile SingularAttribute<TipoServicio, Boolean> admiteBodegasComoDestino;
	public static volatile SingularAttribute<TipoServicio, String> nombre;
	public static volatile SingularAttribute<TipoServicio, TipoDistribucion> tipoDistribucion;
	public static volatile SingularAttribute<TipoServicio, String> usuarioActualizacion;
	public static volatile SingularAttribute<TipoServicio, Date> fechaActualizacion;
	public static volatile SingularAttribute<TipoServicio, Integer> id;
	public static volatile SingularAttribute<TipoServicio, Boolean> admiteBodegasComoOrigen;
	public static volatile SingularAttribute<TipoServicio, Boolean> registrarDestinoEnLaOrden;
	public static volatile SingularAttribute<TipoServicio, Boolean> admiteProductosPorLinea;
	public static volatile SingularAttribute<TipoServicio, Integer> ordinal;
	public static volatile SingularAttribute<TipoServicio, Boolean> activo;

}

