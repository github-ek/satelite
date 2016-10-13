package com.tacticlogistics.clientes.dicermex.compras.wms.recibos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.infrastructure.persistence.clientes.dicermex.OrdenDeCompraRepository;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.crm.TipoServicioRepository;
import com.tacticlogistics.infrastructure.persistence.ordenes.OrdenRepository;
import com.tacticlogistics.infrastructure.persistence.wms.BodegaRepository;
import com.tacticlogistics.infrastructure.persistence.wms.ProductoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class RecibosService {

	private static final String CODIGO_CLIENTE = "DICERMEX";

	private static final String CODIGO_SERVICIO = "COMPRAS";

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private TipoServicioRepository tipoServicioRepository;

	@Autowired
	private BodegaRepository bodegaRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private OrdenRepository ordenRepository;

	@Autowired
	private OrdenDeCompraRepository ordenDeCompraRepository;

	private static Cliente cliente;

	private static TipoServicio servicio;

	@Transactional(readOnly = false)
	public List<AcuseReciboDto> confirmarReciboDeOrdenesDeCompra(List<ResultadoReciboDto> resultados) {
		// @formatter:off
		return resultados
				.stream()
				.map(r -> AcuseReciboDto
					.builder()
					.id(r.getId())
					.error(false)
					.mensaje("")
					.build()
				)
				.collect(Collectors.toList());
		// @formatter:on
	}

	@SuppressWarnings("unused")
	private Cliente getCliente() {
		if (cliente == null) {
			cliente = clienteRepository.findByCodigoIgnoringCase(CODIGO_CLIENTE);
		}
		return cliente;
	}

	@SuppressWarnings("unused")
	private TipoServicio getServicio() {
		if (servicio == null) {
			servicio = tipoServicioRepository.findByCodigoIgnoringCase(CODIGO_SERVICIO);
		}
		return servicio;
	}
}
