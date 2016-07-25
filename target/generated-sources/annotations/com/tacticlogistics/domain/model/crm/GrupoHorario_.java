package com.tacticlogistics.domain.model.crm;

import com.tacticlogistics.domain.model.common.valueobjects.IntervaloDeHoras;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GrupoHorario.class)
public abstract class GrupoHorario_ {

	public static volatile SingularAttribute<GrupoHorario, String> descripcion;
	public static volatile SingularAttribute<GrupoHorario, Boolean> aplicaSabado;
	public static volatile SingularAttribute<GrupoHorario, Boolean> aplicaDomingo;
	public static volatile SingularAttribute<GrupoHorario, Boolean> aplicaMartes;
	public static volatile SingularAttribute<GrupoHorario, String> nombre;
	public static volatile SingularAttribute<GrupoHorario, Cliente> cliente;
	public static volatile SingularAttribute<GrupoHorario, Boolean> aplicaLunes;
	public static volatile SingularAttribute<GrupoHorario, Boolean> aplicaMiercoles;
	public static volatile SingularAttribute<GrupoHorario, Boolean> aplicaFestivos;
	public static volatile SingularAttribute<GrupoHorario, IntervaloDeHoras> intervalo;
	public static volatile SingularAttribute<GrupoHorario, Boolean> aplicaJueves;
	public static volatile SingularAttribute<GrupoHorario, String> usuarioActualizacion;
	public static volatile SingularAttribute<GrupoHorario, Boolean> aplicaViernes;
	public static volatile SingularAttribute<GrupoHorario, Date> fechaActualizacion;
	public static volatile SingularAttribute<GrupoHorario, Integer> id;
	public static volatile SingularAttribute<GrupoHorario, Integer> ordinal;
	public static volatile SingularAttribute<GrupoHorario, Boolean> activo;

}

