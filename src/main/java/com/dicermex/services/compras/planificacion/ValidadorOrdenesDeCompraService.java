package com.dicermex.services.compras.planificacion;

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

import com.dicermex.services.compras.planificacion.reglas.Regla;
import com.dicermex.services.compras.planificacion.reglas.almacenamiento.ReglaBodegaDestino;
import com.dicermex.services.compras.planificacion.reglas.almacenamiento.ReglaBodegaOrigen;
import com.dicermex.services.compras.planificacion.reglas.almacenamiento.ReglaCatidadesPlanificadas;
import com.dicermex.services.compras.planificacion.reglas.almacenamiento.ReglaLineas;
import com.dicermex.services.compras.planificacion.reglas.almacenamiento.ReglaProductos;
import com.dicermex.services.compras.planificacion.reglas.destinatario.ReglaTercero;
import com.dicermex.services.compras.planificacion.reglas.distribucion.ReglaCitaEntrega;
import com.dicermex.services.compras.planificacion.reglas.distribucion.ReglaCitaRecogida;
import com.dicermex.services.compras.planificacion.reglas.distribucion.ReglaPuntoEntrega;
import com.dicermex.services.compras.planificacion.reglas.distribucion.ReglaPuntoRecogida;
import com.dicermex.services.compras.planificacion.reglas.estados.ReglaTransicionConfirmadaAceptada;
import com.tacticlogistics.application.dto.common.MensajesDTO;
import com.tacticlogistics.domain.model.common.valueobjects.MensajeEmbeddable;
import com.tacticlogistics.domain.model.crm.Servicio;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.oms.Orden;
import com.tacticlogistics.infrastructure.persistence.crm.ServicioRepository;
import com.tacticlogistics.infrastructure.persistence.oms.OrdenRepository;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ValidadorOrdenesDeCompraService {

	private static final String CODIGO_SERVICIO = "COMPRAS";

	@Autowired
	private ServicioRepository servicioRepository;

	@Autowired
	private OrdenRepository ordenRepository;

	private static Servicio servicio;

	// TODO PASAR A Servicio Generico de Ordenes
	// TODO PROCESMIENTO POR BLOQUES DE TAMAÑOS PEQUEÑOS
	@Transactional(readOnly = false)
	public void validarOrdenesConfirmadas() {
		Set<Regla<Orden>> reglas = getReglas();
		List<Orden> ordenes = getOrdenesConfirmadas();

		if (ordenes.size() > 0) {

			for (val orden : ordenes) {
				log.info("Validado numeroOrden:{}", orden.getNumeroOrden());
				MensajesDTO<?> mensajes = validarOrdenConfirmada(reglas, orden);
				registrarValidaciones(orden, mensajes);

				if (pasoValidaciones(orden, mensajes)) {
					orden.aceptar(LocalDateTime.now(), "VALIDADOR TC", "");
				}

				ordenRepository.save(orden);
			}
			ordenRepository.flush();
		}
	}

	private List<Orden> getOrdenesConfirmadas() {
		Servicio servicio = new Servicio();
		servicio.setId(this.getServicio().getId());

		// @formatter:off
		Orden probe = Orden
			.builder()
			.servicio(servicio)
			.estadoOrden(EstadoOrdenType.CONFIRMADA)
			.build();
		
		ExampleMatcher matcher = matching()
			.withIgnorePaths("requiereConfirmacionCitaEntrega")  
			.withIgnorePaths("requiereConfirmacionCitaRecogida")
			.withIgnorePaths("servicio.admiteBodegasComoDestino")
			.withIgnorePaths("servicio.admiteBodegasComoOrigen")
			.withIgnorePaths("servicio.admiteDireccionesComoDestino")
			.withIgnorePaths("servicio.admiteDireccionesComoOrigen")
			.withIgnorePaths("servicio.activo")
			.withIgnorePaths("servicio.ordinal");
		//@formatter:on

		List<Orden> ordenes = ordenRepository.findAll(Example.of(probe, matcher));
		return ordenes;
	}

	private MensajesDTO<?> validarOrdenConfirmada(final Set<Regla<Orden>> reglas, final Orden orden) {
		MensajesDTO<?> mensajes = new MensajesDTO<>();
		for (val regla : reglas) {
			mensajes.addAll(regla.validar(orden));
		}
		return mensajes;
	}

	private void registrarValidaciones(final Orden orden, MensajesDTO<?> mensajes) {
		mensajes.getMensajes().forEach(e -> {
			// @formatter:off
			orden.addMensaje(new MensajeEmbeddable(
						e.getSeveridad(), 
						e.getCodigo(), 
						e.getTexto()));
			// @formatter:on
		});
	}

	private boolean pasoValidaciones(final Orden orden, MensajesDTO<?> mensajes) {
		boolean resultado = mensajes.hasErrors();

		return resultado;
	}

	private Set<Regla<Orden>> getReglas() {
		Set<Regla<Orden>> reglas = new HashSet<>();

		reglas.add(new ReglaTransicionConfirmadaAceptada());
		reglas.add(new ReglaTercero());

		reglas.add(new ReglaCitaRecogida());
		reglas.add(new ReglaPuntoRecogida());

		reglas.add(new ReglaCitaEntrega());
		reglas.add(new ReglaPuntoEntrega());

		// TODO REGLA RECAUDO DEBE SER NULL PARA OC
		// TODO REGLA TIEMPO MINIMO/MAXIMO PARA PRESTAR EL SERVICIO
		// TODO REGLAS TIPO DE VEHICULO Y VALOR FLETE

		reglas.add(new ReglaLineas());
		reglas.add(new ReglaCatidadesPlanificadas());
		reglas.add(new ReglaProductos());

		reglas.add(new ReglaBodegaOrigen());
		reglas.add(new ReglaBodegaDestino());

		return reglas;
	}

	private Servicio getServicio() {
		if (servicio == null) {
			servicio = servicioRepository.findByCodigoIgnoringCase(CODIGO_SERVICIO);
		}
		return servicio;
	}
}
