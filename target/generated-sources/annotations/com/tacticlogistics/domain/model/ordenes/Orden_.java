package com.tacticlogistics.domain.model.ordenes;

import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.Direccion;
import com.tacticlogistics.domain.model.common.valueobjects.IntervaloDeFechas;
import com.tacticlogistics.domain.model.common.valueobjects.IntervaloDeHoras;
import com.tacticlogistics.domain.model.common.valueobjects.MensajeEmbeddable;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;
import com.tacticlogistics.domain.model.crm.DestinoOrigen;
import com.tacticlogistics.domain.model.crm.TipoFormaPago;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.wms.Bodega;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Orden.class)
public abstract class Orden_ {

	public static volatile SingularAttribute<Orden, String> destinatarioNumeroIdentificacionAlterno;
	public static volatile SingularAttribute<Orden, TipoServicio> tipoServicio;
	public static volatile SingularAttribute<Orden, IntervaloDeFechas> intervaloDeFechasSugerido;
	public static volatile SingularAttribute<Orden, String> notas;
	public static volatile SetAttribute<Orden, CambioEstadoOrden> cambios;
	public static volatile SingularAttribute<Orden, String> bodegaDestinoCodigoAlterno;
	public static volatile SingularAttribute<Orden, Date> fechaAceptacion;
	public static volatile SingularAttribute<Orden, String> bodegaDestinoNombreAlterno;
	public static volatile SingularAttribute<Orden, Consolidado> consolidado;
	public static volatile SingularAttribute<Orden, Contacto> destinoContacto;
	public static volatile SingularAttribute<Orden, Direccion> destinoDireccion;
	public static volatile SingularAttribute<Orden, String> usuarioActualizacion;
	public static volatile SetAttribute<Orden, MensajeEmbeddable> mensajes;
	public static volatile SingularAttribute<Orden, Date> fechaEntregaMaximaSegunPromesaServicio;
	public static volatile SingularAttribute<Orden, Integer> valorDeclarado;
	public static volatile SingularAttribute<Orden, String> usuarioConfirmacion;
	public static volatile SingularAttribute<Orden, CausalAnulacionOrden> causalAnulacion;
	public static volatile SingularAttribute<Orden, Integer> id;
	public static volatile SingularAttribute<Orden, Canal> canal;
	public static volatile SingularAttribute<Orden, DestinoOrigen> destino;
	public static volatile SetAttribute<Orden, LineaOrden> lineas;
	public static volatile SingularAttribute<Orden, Bodega> bodegaDestino;
	public static volatile SingularAttribute<Orden, String> destinatarioCodigoAlterno;
	public static volatile SingularAttribute<Orden, String> destinatarioNombreAlterno;
	public static volatile SingularAttribute<Orden, Boolean> requiereMaquila;
	public static volatile SingularAttribute<Orden, String> destinoCodigoAlterno;
	public static volatile SingularAttribute<Orden, String> destinoNombreAlterno;
	public static volatile SingularAttribute<Orden, String> canalCodigoAlterno;
	public static volatile SingularAttribute<Orden, DestinatarioRemitente> destinatario;
	public static volatile SingularAttribute<Orden, EstadoOrdenType> estadoOrden;
	public static volatile SingularAttribute<Orden, Contacto> destinatarioContacto;
	public static volatile SingularAttribute<Orden, IntervaloDeHoras> intervaloDeHorasSugerido;
	public static volatile SingularAttribute<Orden, Cliente> cliente;
	public static volatile SingularAttribute<Orden, String> numeroDocumentoOrdenCliente;
	public static volatile SingularAttribute<Orden, String> usuarioAceptacion;
	public static volatile SingularAttribute<Orden, Date> fechaConfirmacion;
	public static volatile SingularAttribute<Orden, Date> fechaActualizacion;
	public static volatile SingularAttribute<Orden, TipoFormaPago> tipoFormaPago;

}

