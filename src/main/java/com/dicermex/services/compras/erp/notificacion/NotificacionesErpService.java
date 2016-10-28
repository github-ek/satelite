package com.dicermex.services.compras.erp.notificacion;

import static org.springframework.data.domain.ExampleMatcher.matching;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.Servicio;
import com.tacticlogistics.domain.model.oms.EstadoAlmacenamientoType;
import com.tacticlogistics.domain.model.oms.EstadoNotificacionType;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.oms.LineaOrden;
import com.tacticlogistics.domain.model.oms.Orden;
import com.tacticlogistics.infrastructure.persistence.crm.ServicioRepository;
import com.tacticlogistics.infrastructure.persistence.oms.OrdenRepository;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
// TODO INCLUIR FECHA Y HORA DE notificacion AL erp
// TODO INCLUIR COOLUMNA PARA EL MENSAJE DE ERROR
public class NotificacionesErpService {

	private static final String CODIGO_CLIENTE = "DICERMEX";

	private static final String CODIGO_SERVICIO = "COMPRAS";

	@Autowired
	private ServicioRepository servicioRepository;

	@Autowired
	private OrdenRepository ordenRepository;

	private static Servicio servicio;

	public List<Orden> getOrdenesPendientesPorNotificarAlErp() {
		Servicio servicio = new Servicio();
		servicio.setId(this.getServicio().getId());

		//@formatter:off
		Orden probe = Orden
				.builder()
				.clienteCodigo(CODIGO_CLIENTE)
				.servicio(servicio)
				.requiereServicioDistribucion(false)
				.estadoOrden(EstadoOrdenType.ENTREGADA)
				.estadoAlmacenamiento(EstadoAlmacenamientoType.PROCESADA)
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

		for (Orden e : ordenes) {
			Cliente cliente = e.getCliente();
			log.info("load {}", cliente.getCodigoAlternoWms());
			List<LineaOrden> lineas = e.getLineas();
			for (LineaOrden l : lineas) {
				log.info("load {}", l.getNumeroItem());
			}
		}
		return ordenes;
	}

	@Transactional(readOnly = false)
	public void notificarOrdenesDeCompraAlErp(List<Integer> ordenesId) {
		for (Integer id : ordenesId) {
			Orden compra = ordenRepository.findOne(id);
			if (compra != null) {
				compra.setEstadoOrden(EstadoOrdenType.EJECUCION);
				compra.setEstadoNotificacion(EstadoNotificacionType.NOTIFICACION);
				compra.setDatosActualizacion(LocalDateTime.now(), "INTEGRACION TC-ERP");
				for (val e : compra.getLineas()) {
					e.setNumeroOrdenWmsDestino("TC-" + compra.getId() + '-' + compra.getNumeroOrden());
				}
				ordenRepository.save(compra);
			}
		}
	}

	@Transactional(readOnly = false)
	public void confirmarResultadoDeNotificacionesAlErp(List<ResultadoAlertaDto> resultados) {
		for (val e : resultados) {
			String partes[] = e.getNumeroOrdenWms().split("-");
			int id = Integer.parseInt(partes[1]);

			Orden compra = ordenRepository.findOne(id);
			if (compra != null) {
				if (e.getResultado() == ResultadoAlertaType.OK) {
					compra.setEstadoAlmacenamiento(EstadoAlmacenamientoType.ALERTADA);
				} else {
					compra.setEstadoAlmacenamiento(EstadoAlmacenamientoType.ALERTADA_CON_ERRORES);
				}
				compra.setDatosActualizacion(LocalDateTime.now(), "INTEGRACION TC-WMS");
				ordenRepository.save(compra);
			}
		}
	}

	private Servicio getServicio() {
		if (servicio == null) {
			servicio = servicioRepository.findByCodigoIgnoringCase(CODIGO_SERVICIO);
		}
		return servicio;
	}
}
