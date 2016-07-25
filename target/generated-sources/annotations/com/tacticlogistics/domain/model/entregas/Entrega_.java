package com.tacticlogistics.domain.model.entregas;

import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.IntervaloDeFechas;
import com.tacticlogistics.domain.model.common.valueobjects.IntervaloDeHoras;
import com.tacticlogistics.domain.model.common.valueobjects.UbicacionEmbeddable;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;
import com.tacticlogistics.domain.model.crm.DestinoOrigen;
import com.tacticlogistics.domain.model.crm.TipoFormaPago;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.domain.model.wms.Bodega;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Entrega.class)
public abstract class Entrega_ {

	public static volatile SingularAttribute<Entrega, TipoServicio> tipoServicio;
	public static volatile SingularAttribute<Entrega, Bodega> bodegaOrigen;
	public static volatile SetAttribute<Entrega, CambioEstadoEntrega> cambios;
	public static volatile SingularAttribute<Entrega, DestinoOrigen> origen;
	public static volatile SingularAttribute<Entrega, String> numeroDocumentoEntregaCliente;
	public static volatile SingularAttribute<Entrega, String> usuarioActualizacion;
	public static volatile SingularAttribute<Entrega, CausalAnulacionEntrega> causalAnulacion;
	public static volatile SingularAttribute<Entrega, Integer> id;
	public static volatile SingularAttribute<Entrega, Orden> orden;
	public static volatile SingularAttribute<Entrega, Canal> canal;
	public static volatile SingularAttribute<Entrega, BigDecimal> volumenTotal;
	public static volatile SingularAttribute<Entrega, EstadoEntregaType> estadoEntrega;
	public static volatile SetAttribute<Entrega, LineaEntrega> lineas;
	public static volatile SingularAttribute<Entrega, Contacto> contacto;
	public static volatile SingularAttribute<Entrega, BigDecimal> pesoBrutoTotal;
	public static volatile SingularAttribute<Entrega, Boolean> requiereMaquila;
	public static volatile SingularAttribute<Entrega, Date> fechaMaximaEntregaSegunPromesaServicio;
	public static volatile SingularAttribute<Entrega, IntervaloDeFechas> intervaloDeFechasConfirmado;
	public static volatile SingularAttribute<Entrega, DestinatarioRemitente> destinatario;
	public static volatile SingularAttribute<Entrega, Cliente> cliente;
	public static volatile SingularAttribute<Entrega, Integer> valorDeclaradoTotal;
	public static volatile SingularAttribute<Entrega, Date> fechaActualizacion;
	public static volatile SingularAttribute<Entrega, IntervaloDeHoras> intervaloDeHorasConfirmado;
	public static volatile SingularAttribute<Entrega, UbicacionEmbeddable> ubicacionOrigen;
	public static volatile SingularAttribute<Entrega, TipoFormaPago> tipoFormaPago;

}

