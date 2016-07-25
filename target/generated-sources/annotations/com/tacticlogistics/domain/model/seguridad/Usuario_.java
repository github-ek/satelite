package com.tacticlogistics.domain.model.seguridad;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ {

	public static volatile SingularAttribute<Usuario, String> apellidos;
	public static volatile SingularAttribute<Usuario, String> password;
	public static volatile SingularAttribute<Usuario, String> usuarioActualizacion;
	public static volatile SingularAttribute<Usuario, Date> fechaActualizacion;
	public static volatile SetAttribute<Usuario, UsuarioClienteAssociation> usuarioClienteAssociation;
	public static volatile SingularAttribute<Usuario, String> usuario;
	public static volatile SingularAttribute<Usuario, Integer> id;
	public static volatile SetAttribute<Usuario, UsuarioBodegaAssociation> usuarioBodegaAssociation;
	public static volatile SingularAttribute<Usuario, String> email;
	public static volatile SingularAttribute<Usuario, String> nombres;
	public static volatile SingularAttribute<Usuario, Boolean> activo;

}

