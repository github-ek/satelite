package com.tacticlogistics.domain.model.crm;

import com.tacticlogistics.domain.model.common.UsoUbicacionType;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.OmsDireccion;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DestinoOrigen.class)
public abstract class DestinoOrigen_ {

	public static volatile SingularAttribute<DestinoOrigen, String> codigo;
	public static volatile SingularAttribute<DestinoOrigen, Contacto> contacto;
	public static volatile SingularAttribute<DestinoOrigen, Integer> destinatarioRemitenteId;
	public static volatile SingularAttribute<DestinoOrigen, UsoUbicacionType> usoUbicacionType;
	public static volatile SingularAttribute<DestinoOrigen, OmsDireccion> direccion;
	public static volatile SingularAttribute<DestinoOrigen, String> usuarioActualizacion;
	public static volatile SingularAttribute<DestinoOrigen, Date> fechaActualizacion;
	public static volatile SingularAttribute<DestinoOrigen, Integer> id;
	public static volatile SingularAttribute<DestinoOrigen, String> nombre;
	public static volatile SingularAttribute<DestinoOrigen, Boolean> activo;

}

