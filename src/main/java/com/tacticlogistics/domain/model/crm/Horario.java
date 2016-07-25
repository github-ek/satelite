package com.tacticlogistics.domain.model.crm;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Horarios", catalog = "crm")
public class Horario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_horario", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_grupo_horario", nullable = false)
	private GrupoHorario grupoHorario;

	@ElementCollection
	@CollectionTable(name = "horarios_codigos_ciudades", catalog = "crm"
		,joinColumns = @JoinColumn(name = "id_horario", referencedColumnName = "id_horario") )
	@Column(name = "codigo_ciudad", nullable = false, length = 20)
	private Set<String> codigosCiudades = new HashSet<String>();

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_destinatario_remitente", nullable = true)
	private DestinatarioRemitente destinatarioRemitente;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_destino_origen", nullable = true)
	private DestinoOrigen destinoOrigen;	
	
	//TODO
	//Se buscan los horarios activos del cliente
	//Si coincide con el destinoOrigen -> (APLICA EL HORARIO)
	//Sino
	//	Se buscan los horarios que tengan destinoOrigen null y coincidan el destintarioRemitente
	//		Si existen codigos de ciudad asociados al horario
	//			Si la ciudad del destino coincide por aproximación con el de una de las ciudades del horario -> (APLICA EL HORARIO)
	//			Sino -> (NO APLICA EL HORARIO)
	//		Sino -> (APLICA EL HORARIO)
	//	Sino
	//		Se buscan los horarios que tengan destinoOrigen y destintarioRemitente null y tengan codigos de ciudad asociados 
	//			Si la ciudad del destino coincide por aproximación con el de una de las ciudades del horario -> (APLICA EL HORARIO)
	//			Sino -> (NO APLICA EL HORARIO)
	//		Sino 
	//			Se buscan los horarios que tengan destinoOrigen y destintarioRemitente null y no tengan codigos de ciudad asociados -> (APLICA EL HORARIO)
	// where id_cliente = @id_cliente and activo = 1 
	//	and
	//	(
	//	a (id_destino_origen = @id_destino_origen)
	//	|| 
	//  b (id_destino_origen is null)
	//	||
	//	c (id_destino_origen is null && id_destinatario = @id_destinatario)
	//	||
	// 	d (id_destinatario is null )
	//	||
	//	e (condigoCiudad.size() == 0)
	//	||
	//	f (condigoCiudad.contains(@codigo_ciudad)
	//	)
	//	Si a 
	//	Si empty(a) 			-> (b && c && f)
	//	Si empty(b && c && f) 	-> (b && c && e) 
	//	Si empty(b && c && e)	-> (b && d && f)
	//	Si empty(b && d && f)	-> (b && d && e)
	//)  
}
