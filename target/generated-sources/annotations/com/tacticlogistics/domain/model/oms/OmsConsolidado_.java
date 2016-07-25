package com.tacticlogistics.domain.model.oms;

import com.tacticlogistics.domain.model.crm.Cliente;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OmsConsolidado.class)
public abstract class OmsConsolidado_ {

	public static volatile SingularAttribute<OmsConsolidado, Cliente> cliente;
	public static volatile SetAttribute<OmsConsolidado, OmsOrden> ordenes;
	public static volatile SingularAttribute<OmsConsolidado, String> numeroDocumentoConsolidadoCliente;
	public static volatile SingularAttribute<OmsConsolidado, String> usuarioActualizacion;
	public static volatile SingularAttribute<OmsConsolidado, Date> fechaActualizacion;
	public static volatile SingularAttribute<OmsConsolidado, Integer> id;

}

