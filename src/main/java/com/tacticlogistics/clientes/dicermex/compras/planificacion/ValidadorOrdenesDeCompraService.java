package com.tacticlogistics.clientes.dicermex.compras.planificacion;

import static org.springframework.data.domain.ExampleMatcher.matching;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.clientes.dicermex.compras.almacenamiento.AlertasWmsService;
import com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.Regla;
import com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.almacenamiento.ReglaBodegaDestino;
import com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.almacenamiento.ReglaBodegaOrigen;
import com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.almacenamiento.ReglaCatidadesPlanificadas;
import com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.almacenamiento.ReglaLineas;
import com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.almacenamiento.ReglaProductos;
import com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.destinatario.ReglaTercero;
import com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.distribucion.ReglaCitaEntrega;
import com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.distribucion.ReglaCitaRecogida;
import com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.distribucion.ReglaPuntoEntrega;
import com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.distribucion.ReglaPuntoRecogida;
import com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.estados.ReglaTransicionConfirmadaAceptada;
import com.tacticlogistics.domain.model.common.valueobjects.MensajeEmbeddable;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.infrastructure.persistence.crm.TipoServicioRepository;
import com.tacticlogistics.infrastructure.persistence.ordenes.OrdenRepository;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ValidadorOrdenesDeCompraService {

	private static final String CODIGO_SERVICIO = "COMPRAS";

	@Autowired
	private TipoServicioRepository tipoServicioRepository;

	@Autowired
	private OrdenRepository ordenRepository;

	private static TipoServicio servicio;

	@Autowired
	private AlertasWmsService service;

	// TODO PASAR A Servicio Generico de Ordenes
	// TODO PROCESMIENTO POR BLOQUES DE TAMAÑOS PEQUEÑOS
	@Transactional(readOnly = false)
	public void validarOrdenesConfirmadas() {
		Set<Regla<Orden>> reglas = getReglas();
		List<Orden> ordenes = getOrdenesConfirmadas();

		if (ordenes.size() > 0) {

			for (val orden : ordenes) {
				log.info("Validado numeroOrden:{}", orden.getNumeroOrden());
				MensajesDto mensajes = validarOrdenConfirmada(reglas, orden);
				registrarValidaciones(orden, mensajes);

				if (pasoValidaciones(orden, mensajes)) {
					orden.aceptar(LocalDateTime.now(), "VALIDADOR TC", "");
				}

				ordenRepository.save(orden);
			}
			ordenRepository.flush();
		}

		List<Orden> list = service.getOrdenesPendientesPorAlertarAlWms();

		for (Orden orden : list) {
			log.info("Las siguientes ordenes deben ser prealertadas en el WMS", orden.getNumeroOrden());
		}
	}

	private List<Orden> getOrdenesConfirmadas() {
		TipoServicio servicio = new TipoServicio();
		servicio.setId(this.getServicio().getId());

		// @formatter:off
		Orden probe = Orden
			.builder()
			.tipoServicio(servicio)
			.estadoOrden(EstadoOrdenType.CONFIRMADA)
			.build();
		
		ExampleMatcher matcher = matching()
			.withIgnorePaths("requiereConfirmacionCitaEntrega")  
			.withIgnorePaths("requiereConfirmacionCitaRecogida")
			.withIgnorePaths("tipoServicio.admiteBodegasComoDestino")
			.withIgnorePaths("tipoServicio.admiteBodegasComoOrigen")
			.withIgnorePaths("tipoServicio.admiteDireccionesComoDestino")
			.withIgnorePaths("tipoServicio.admiteDireccionesComoOrigen")
			.withIgnorePaths("tipoServicio.activo")
			.withIgnorePaths("tipoServicio.ordinal");
		//@formatter:on

		List<Orden> ordenes = ordenRepository.findAll(Example.of(probe,matcher));
		return ordenes;
	}

	private MensajesDto validarOrdenConfirmada(final Set<Regla<Orden>> reglas,final Orden orden) {
		MensajesDto mensajes = new MensajesDto();
		for (val regla : reglas) {
			mensajes.AddMensajes(regla.validar(orden));
		}
		return mensajes;
	}

	private void registrarValidaciones(final Orden orden, MensajesDto mensajes) {
		for (val e : mensajes.getMensajes()) {
			// @formatter:off
			orden.getMensajes()
				.add(new MensajeEmbeddable(
						e.getSeveridad(), 
						e.getCodigo(), 
						e.getTexto(), 
						e.getGrupo()));
			// @formatter:on
		}
	}

	private boolean pasoValidaciones(final com.tacticlogistics.domain.model.ordenes.Orden orden, MensajesDto mensajes) {
		boolean OK = false;

		switch (mensajes.getSeveridadMaxima()) {
		case FATAL:
		case ERROR:
			break;
		case WARN:
		case INFO:
			OK = true;
			break;
		default:
			break;
		}

		return OK;
	}

	private Set<Regla<Orden>> getReglas() {
		Set<Regla<Orden>> reglas = new HashSet<>();
		
		reglas.add(new ReglaTransicionConfirmadaAceptada());
		reglas.add(new ReglaTercero());
		
		reglas.add(new ReglaCitaRecogida());
		reglas.add(new ReglaPuntoRecogida());

		reglas.add(new ReglaCitaEntrega());
		reglas.add(new ReglaPuntoEntrega());

		//TODO REGLA RECAUDO DEBE SER NULL PARA OC
		//TODO REGLA TIEMPO MINIMO/MAXIMO PARA PRESTAR EL SERVICIO
		//TODO REGLAS TIPO DE VEHICULO Y VALOR FLETE

		reglas.add(new ReglaLineas());
		reglas.add(new ReglaCatidadesPlanificadas());
		reglas.add(new ReglaProductos());
		
		reglas.add(new ReglaBodegaOrigen());
		reglas.add(new ReglaBodegaDestino());
		
		return reglas;
	}
	
	private TipoServicio getServicio() {
		if (servicio == null) {
			servicio = tipoServicioRepository.findByCodigoIgnoringCase(CODIGO_SERVICIO);
		}
		return servicio;
	}
}
