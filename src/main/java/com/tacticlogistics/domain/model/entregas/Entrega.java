package com.tacticlogistics.domain.model.entregas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.IntervaloDeFechas;
import com.tacticlogistics.domain.model.common.valueobjects.IntervaloDeHoras;
import com.tacticlogistics.domain.model.common.valueobjects.UbicacionEmbeddable;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;
import com.tacticlogistics.domain.model.crm.DestinoOrigen;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.TipoFormaPago;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.domain.model.wms.Bodega;

@Entity
@Table(name = "Entregas", catalog = "ordenes")
public class Entrega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_entrega", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_tipo_servicio", nullable = false)
	private TipoServicio tipoServicio;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@Column(nullable = false, length = 30)
	private String numeroDocumentoEntregaCliente;

	@Enumerated(EnumType.STRING)
	@Column(name = "id_estado_entrega", nullable = false, length = 20)
	private EstadoEntregaType estadoEntrega;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_causal_anulacion_entrega", nullable = true)
	private CausalAnulacionEntrega causalAnulacion;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_orden", nullable = false)
	private Orden orden;

	// ---------------------------------------------------------------------------------------------------------
	// ---------------------------------------------------------------------------------------------------------
	@Embedded
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_segmento", nullable = false)
	private Canal canal;

	@Embedded
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_destinatario_remitente", nullable = false)
	private DestinatarioRemitente destinatario;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "nombres", column = @Column(name = "destinatario_contacto_nombres")),
			@AttributeOverride(name = "telefonos", column = @Column(name = "destinatario_contacto_telefonos")),
			@AttributeOverride(name = "email", column = @Column(name = "destinatario_contacto_email")) })
	private Contacto contacto;

	// ---------------------------------------------------------------------------------------------------------
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_destino_origen_origen", nullable = true)
	private DestinoOrigen origen;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_bodega_origen", nullable = true)
	private Bodega bodegaOrigen;

	// ---------------------------------------------------------------------------------------------------------
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "direccion.ciudad", column = @Column(name = "id_ciudad_origen") ),
			@AttributeOverride(name = "direccion.direccion", column = @Column(name = "direccion_origen") ),
			@AttributeOverride(name = "direccion.indicacionesDireccion", column = @Column(name = "indicacionesDireccion_origen") ),
			@AttributeOverride(name = "direccion.direccionEstandarizada", column = @Column(name = "direccionEstandarizada_origen") ),
			@AttributeOverride(name = "direccion.longitud", column = @Column(name = "longitud_origen") ),
			@AttributeOverride(name = "direccion.latitud", column = @Column(name = "latitud_origen") ),
			@AttributeOverride(name = "contacto.nombres", column = @Column(name = "contacto_nombres_origen") ),
			@AttributeOverride(name = "contacto.telefonos", column = @Column(name = "contacto_telefonos_origen") ),
			@AttributeOverride(name = "contacto.email", column = @Column(name = "contacto_email_origen") ) })
	private UbicacionEmbeddable ubicacionOrigen;

	// ---------------------------------------------------------------------------------------------------------
	@Embedded
	private IntervaloDeFechas intervaloDeFechasConfirmado;

	@Embedded
	private IntervaloDeHoras intervaloDeHorasConfirmado;

	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaMaximaEntregaSegunPromesaServicio;

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = false, columnDefinition = "DECIMAL(12,4)")
	private BigDecimal volumenTotal;

	@Column(nullable = false, columnDefinition = "DECIMAL(12,4)")
	private BigDecimal pesoBrutoTotal;

	@Column(nullable = true)
	private Integer valorDeclaradoTotal;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_tipo_forma_pago", nullable = true)
	private TipoFormaPago tipoFormaPago;

	private boolean requiereMaquila;

	// ---------------------------------------------------------------------------------------------------------
	// TODO EVALUAR INCLUIR ALGUNOS DE LO USUARIOS QUE APROBARON LA ENTREGA

	// ---------------------------------------------------------------------------------------------------------
	@Column(nullable = false, columnDefinition = "DATETIME2(0)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;

	@Column(nullable = false, length = 50)
	private String usuarioActualizacion;

	// ---------------------------------------------------------------------------------------------------------
	@OneToMany(fetch = FetchType.LAZY,mappedBy="entrega")
	private Set<LineaEntrega> lineas = new HashSet<LineaEntrega>(0);
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="entrega")
	private Set<CambioEstadoEntrega> cambios = new HashSet<CambioEstadoEntrega>(0);

	protected Entrega() {

	}

	// ---------------------------------------------------------------------------------------------------------
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoServicio getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(TipoServicio tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNumeroDocumentoEntregaCliente() {
		return numeroDocumentoEntregaCliente;
	}

	public void setNumeroDocumentoEntregaCliente(String numeroDocumentoEntregaCliente) {
		this.numeroDocumentoEntregaCliente = numeroDocumentoEntregaCliente;
	}

	public EstadoEntregaType getEstadoEntrega() {
		return estadoEntrega;
	}

	public void setEstadoEntrega(EstadoEntregaType estadoEntrega) {
		this.estadoEntrega = estadoEntrega;
	}

	public CausalAnulacionEntrega getCausalAnulacion() {
		return causalAnulacion;
	}

	public void setCausalAnulacion(CausalAnulacionEntrega causalAnulacion) {
		this.causalAnulacion = causalAnulacion;
	}

	public Orden getOrden() {
		return orden;
	}

	public void setOrden(Orden orden) {
		this.orden = orden;
	}

	public DestinatarioRemitente getRemitente() {
		return destinatario;
	}

	public void setRemitente(DestinatarioRemitente remitente) {
		this.destinatario= remitente;
	}

	public DestinoOrigen getOrigen() {
		return origen;
	}

	public void setOrigen(DestinoOrigen origen) {
		this.origen = origen;
	}

	public Bodega getBodegaOrigen() {
		return bodegaOrigen;
	}

	public void setBodegaOrigen(Bodega bodegaOrigen) {
		this.bodegaOrigen = bodegaOrigen;
	}

	public UbicacionEmbeddable getUbicacionOrigen() {
		return ubicacionOrigen;
	}

	public void setUbicacionOrigen(UbicacionEmbeddable ubicacionOrigen) {
		this.ubicacionOrigen = ubicacionOrigen;
	}

	public IntervaloDeFechas getIntervaloDeFechasConfirmado() {
		return intervaloDeFechasConfirmado;
	}

	public void setIntervaloDeFechasConfirmado(IntervaloDeFechas intervaloDeFechasConfirmado) {
		this.intervaloDeFechasConfirmado = intervaloDeFechasConfirmado;
	}

	public IntervaloDeHoras getIntervaloDeHorasConfirmado() {
		return intervaloDeHorasConfirmado;
	}

	public void setIntervaloDeHorasConfirmado(IntervaloDeHoras intervaloDeHorasConfirmado) {
		this.intervaloDeHorasConfirmado = intervaloDeHorasConfirmado;
	}

	public Date getFechaMaximaEntregaSegunPromesaServicio() {
		return fechaMaximaEntregaSegunPromesaServicio;
	}

	public void setFechaMaximaEntregaSegunPromesaServicio(Date fechaMaximaEntregaSegunPromesaServicio) {
		this.fechaMaximaEntregaSegunPromesaServicio = fechaMaximaEntregaSegunPromesaServicio;
	}

	public BigDecimal getVolumenTotal() {
		return volumenTotal;
	}

	public void setVolumenTotal(BigDecimal volumenTotal) {
		this.volumenTotal = volumenTotal;
	}

	public BigDecimal getPesoBrutoTotal() {
		return pesoBrutoTotal;
	}

	public void setPesoBrutoTotal(BigDecimal pesoBrutoTotal) {
		this.pesoBrutoTotal = pesoBrutoTotal;
	}

	public BigDecimal getPesoBrutoVolumetricoTotal() {
		if (volumenTotal == null || pesoBrutoTotal == null ) {
			return null;
		}
		return volumenTotal.multiply(pesoBrutoTotal);
	}

	public Integer getValorDeclaradoTotal() {
		return valorDeclaradoTotal;
	}

	public void setValorDeclaradoTotal(Integer valorDeclaradoTotal) {
		this.valorDeclaradoTotal = valorDeclaradoTotal;
	}

	public TipoFormaPago getTipoFormaPago() {
		return tipoFormaPago;
	}

	public void setTipoFormaPago(TipoFormaPago tipoFormaPago) {
		this.tipoFormaPago = tipoFormaPago;
	}

	public boolean isRequiereMaquila() {
		return requiereMaquila;
	}

	public void setRequiereMaquila(boolean requiereMaquila) {
		this.requiereMaquila = requiereMaquila;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getUsuarioActualizacion() {
		return usuarioActualizacion;
	}

	public void setUsuarioActualizacion(String usuarioActualizacion) {
		this.usuarioActualizacion = usuarioActualizacion;
	}

	public Set<LineaEntrega> getLineas() {
		return lineas;
	}

	public void setLineas(Set<LineaEntrega> lineas) {
		this.lineas = lineas;
	}

	public Set<CambioEstadoEntrega> getCambios() {
		return cambios;
	}

	public void setCambios(Set<CambioEstadoEntrega> cambios) {
		this.cambios = cambios;
	}

	// ---------------------------------------------------------------------------------------------------------
	@Override
	public String toString() {
		final int maxLen = 5;
		StringBuilder builder = new StringBuilder();
		builder.append("Entrega [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (tipoServicio != null)
			builder.append("tipoServicio=").append(tipoServicio).append(", ");
		if (cliente != null)
			builder.append("cliente=").append(cliente).append(", ");
		if (numeroDocumentoEntregaCliente != null)
			builder.append("numeroDocumentoEntregaCliente=").append(numeroDocumentoEntregaCliente).append(", ");
		if (estadoEntrega != null)
			builder.append("estadoEntrega=").append(estadoEntrega).append(", ");
		if (causalAnulacion != null)
			builder.append("causalAnulacion=").append(causalAnulacion).append(", ");
		if (orden != null)
			builder.append("orden=").append(orden).append(", ");
		if (destinatario != null)
			builder.append("remitente=").append(destinatario).append(", ");
		if (origen != null)
			builder.append("origen=").append(origen).append(", ");
		if (bodegaOrigen != null)
			builder.append("bodegaOrigen=").append(bodegaOrigen).append(", ");
		if (ubicacionOrigen != null)
			builder.append("ubicacionOrigen=").append(ubicacionOrigen).append(", ");
		if (intervaloDeFechasConfirmado != null)
			builder.append("intervaloDeFechasConfirmado=").append(intervaloDeFechasConfirmado).append(", ");
		if (intervaloDeHorasConfirmado != null)
			builder.append("intervaloDeHorasConfirmado=").append(intervaloDeHorasConfirmado).append(", ");
		if (fechaMaximaEntregaSegunPromesaServicio != null)
			builder.append("fechaMaximaEntregaSegunPromesaServicio=").append(fechaMaximaEntregaSegunPromesaServicio)
					.append(", ");
		if (volumenTotal != null)
			builder.append("volumenTotal=").append(volumenTotal).append(", ");
		if (pesoBrutoTotal != null)
			builder.append("pesoBrutoTotal=").append(pesoBrutoTotal).append(", ");
		if (valorDeclaradoTotal != null)
			builder.append("valorDeclaradoTotal=").append(valorDeclaradoTotal).append(", ");
		if (tipoFormaPago != null)
			builder.append("tipoFormaPago=").append(tipoFormaPago).append(", ");
		builder.append("requiereMaquila=").append(requiereMaquila).append(", ");
		if (fechaActualizacion != null)
			builder.append("fechaActualizacion=").append(fechaActualizacion).append(", ");
		if (usuarioActualizacion != null)
			builder.append("usuarioActualizacion=").append(usuarioActualizacion).append(", ");
		if (lineas != null)
			builder.append("lineas=").append(toString(lineas, maxLen)).append(", ");
		if (cambios != null)
			builder.append("cambios=").append(toString(cambios, maxLen));
		builder.append("]");
		return builder.toString();
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((fechaMaximaEntregaSegunPromesaServicio == null) ? 0
				: fechaMaximaEntregaSegunPromesaServicio.hashCode());
		result = prime * result
				+ ((numeroDocumentoEntregaCliente == null) ? 0 : numeroDocumentoEntregaCliente.hashCode());
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entrega other = (Entrega) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (fechaMaximaEntregaSegunPromesaServicio == null) {
			if (other.fechaMaximaEntregaSegunPromesaServicio != null)
				return false;
		} else if (!fechaMaximaEntregaSegunPromesaServicio.equals(other.fechaMaximaEntregaSegunPromesaServicio))
			return false;
		if (numeroDocumentoEntregaCliente == null) {
			if (other.numeroDocumentoEntregaCliente != null)
				return false;
		} else if (!numeroDocumentoEntregaCliente.equals(other.numeroDocumentoEntregaCliente))
			return false;
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		return true;
	}
}
