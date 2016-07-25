package com.tacticlogistics.domain.model.crm;

import com.tacticlogistics.domain.model.common.IdentificacionType;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.OmsDireccion;
import com.tacticlogistics.domain.model.wms.DestinatarioRemitenteUnidadAssociation;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DestinatarioRemitente.class)
public abstract class DestinatarioRemitente_ {

	public static volatile SingularAttribute<DestinatarioRemitente, String> digitoVerificacion;
	public static volatile SingularAttribute<DestinatarioRemitente, String> codigo;
	public static volatile SingularAttribute<DestinatarioRemitente, Contacto> contacto;
	public static volatile SingularAttribute<DestinatarioRemitente, String> numeroIdentificacion;
	public static volatile SetAttribute<DestinatarioRemitente, DestinoOrigen> destinosOrigenesAssociation;
	public static volatile SingularAttribute<DestinatarioRemitente, OmsDireccion> direccion;
	public static volatile SetAttribute<DestinatarioRemitente, DestinatarioRemitenteUnidadAssociation> destinatarioRemitenteUnidadAssociation;
	public static volatile SingularAttribute<DestinatarioRemitente, String> nombre;
	public static volatile SingularAttribute<DestinatarioRemitente, Integer> clienteId;
	public static volatile SingularAttribute<DestinatarioRemitente, IdentificacionType> identificacionType;
	public static volatile SingularAttribute<DestinatarioRemitente, String> usuarioActualizacion;
	public static volatile SingularAttribute<DestinatarioRemitente, String> nombreComercial;
	public static volatile SingularAttribute<DestinatarioRemitente, Date> fechaActualizacion;
	public static volatile SingularAttribute<DestinatarioRemitente, Integer> id;
	public static volatile SingularAttribute<DestinatarioRemitente, Integer> canalId;
	public static volatile SingularAttribute<DestinatarioRemitente, Boolean> activo;

}

