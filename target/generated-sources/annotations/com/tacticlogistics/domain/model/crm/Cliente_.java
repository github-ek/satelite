package com.tacticlogistics.domain.model.crm;

import com.tacticlogistics.domain.model.common.IdentificacionType;
import com.tacticlogistics.domain.model.notificaciones.Suscriptor;
import com.tacticlogistics.domain.model.requerimientos.RequerimientoClienteAssociation;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cliente.class)
public abstract class Cliente_ {

	public static volatile SingularAttribute<Cliente, String> digitoVerificacion;
	public static volatile SingularAttribute<Cliente, String> codigo;
	public static volatile SingularAttribute<Cliente, String> codigoAlternoWms;
	public static volatile SingularAttribute<Cliente, String> numeroIdentificacion;
	public static volatile SetAttribute<Cliente, ClienteRequerimientoDistribucionAssociation> clienteRequerimientoDistribucionAssociation;
	public static volatile SetAttribute<Cliente, ClienteUnidadAssociation> clienteUnidadAssociation;
	public static volatile SetAttribute<Cliente, RequerimientoClienteAssociation> requerimientoClienteAssociation;
	public static volatile SetAttribute<Cliente, ClienteCanalAssociation> clienteCanalAssociation;
	public static volatile SetAttribute<Cliente, ClienteRequerimientoMaquilaAssociation> clienteRequerimientoMaquilaAssociation;
	public static volatile SingularAttribute<Cliente, String> nombre;
	public static volatile SetAttribute<Cliente, ClienteTipoServicioAssociation> clienteTipoServicioAssociation;
	public static volatile SetAttribute<Cliente, Suscriptor> suscriptores;
	public static volatile SetAttribute<Cliente, DestinatarioRemitente> destinatariosRemitentes;
	public static volatile SingularAttribute<Cliente, IdentificacionType> identificacionType;
	public static volatile SingularAttribute<Cliente, String> usuarioActualizacion;
	public static volatile SingularAttribute<Cliente, String> nombreComercial;
	public static volatile SingularAttribute<Cliente, Date> fechaActualizacion;
	public static volatile SetAttribute<Cliente, ClienteCiudadAssociation> clienteCiudadAssociation;
	public static volatile SingularAttribute<Cliente, Integer> id;
	public static volatile SingularAttribute<Cliente, Boolean> activo;
	public static volatile SetAttribute<Cliente, ClienteBodegaAssociation> clienteBodegaAssociation;

}

