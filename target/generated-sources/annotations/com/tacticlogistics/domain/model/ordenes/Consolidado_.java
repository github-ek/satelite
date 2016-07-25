package com.tacticlogistics.domain.model.ordenes;

import com.tacticlogistics.domain.model.crm.Cliente;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Consolidado.class)
public abstract class Consolidado_ {

	public static volatile SingularAttribute<Consolidado, Cliente> cliente;
	public static volatile SetAttribute<Consolidado, Orden> ordenes;
	public static volatile SingularAttribute<Consolidado, String> numeroDocumentoConsolidadoCliente;
	public static volatile SingularAttribute<Consolidado, String> usuarioActualizacion;
	public static volatile SingularAttribute<Consolidado, Date> fechaActualizacion;
	public static volatile SingularAttribute<Consolidado, Integer> id;

}

