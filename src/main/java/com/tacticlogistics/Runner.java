package com.tacticlogistics;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tacticlogistics.clientes.dicermex.compras.wms.recibos.LineaResultadoReciboDto;
import com.tacticlogistics.clientes.dicermex.compras.wms.recibos.RecibosService;
import com.tacticlogistics.clientes.dicermex.compras.wms.recibos.ResultadoReciboDto;

@Component
public class Runner implements CommandLineRunner {
	@Autowired
	private RecibosService service;

	@Override
	public void run(String... args) throws Exception {
		List<ResultadoReciboDto> recibos = new ArrayList<>();
		long id = 1;
		recibos.add(caso01(id++));
		recibos.add(caso02(id++));
		recibos.add(caso03(id++));
		recibos.add(caso04(id++));
		recibos.add(caso05(id++));
		recibos.add(caso06(id++));
		recibos.add(caso07(id++));

		//service.confirmarReciboDeOrdenesDeCompra(recibos);

	}

	private ResultadoReciboDto caso01(long id) {
		List<LineaResultadoReciboDto> lineas = new ArrayList<>();
		// @formatter:off
		return ResultadoReciboDto
				.builder()
				.id(BigInteger.valueOf(id))
				.numeroOrdenWms("XXX")
				.clienteCodigoWms("XXX")
				.bodegaCodigo("XXX")
				.lineas(lineas)
				.build();
		// @formatter:on
	}

	private ResultadoReciboDto caso02(long id) {
		List<LineaResultadoReciboDto> lineas = new ArrayList<>();
		// @formatter:off
		return ResultadoReciboDto
				.builder()
				.id(BigInteger.valueOf(id))
				.numeroOrdenWms("TC-1-100-OC-1111111")
				.clienteCodigoWms("XXX")
				.bodegaCodigo("XXX")
				.lineas(lineas)
				.build();
		// @formatter:on
	}

	private ResultadoReciboDto caso03(long id) {
		List<LineaResultadoReciboDto> lineas = new ArrayList<>();
		// @formatter:off
		return ResultadoReciboDto
				.builder()
				.id(BigInteger.valueOf(id))
				.numeroOrdenWms("TC-78759-100-OC-2")
				.clienteCodigoWms("XXX")
				.bodegaCodigo("XXX")
				.lineas(lineas)
				.build();
		// @formatter:on
	}

	private ResultadoReciboDto caso04(long id) {
		List<LineaResultadoReciboDto> lineas = new ArrayList<>();
		// @formatter:off
		return ResultadoReciboDto
				.builder()
				.id(BigInteger.valueOf(id))
				.numeroOrdenWms("TC-78759-100-OC-2")
				.clienteCodigoWms("DICERMEX")
				.bodegaCodigo("TL-BOG-SIB-01")
				.lineas(lineas)
				.build();
		// @formatter:on
	}

	private ResultadoReciboDto caso05(long id) {
		List<LineaResultadoReciboDto> lineas = new ArrayList<>();
		
		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(1)
				.estadoInventarioId("A")
				.productoCodigo("555")
				.cantidadPlanificada(10)
				.cantidadReal(10)
				.build()
				);

		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(1)
				.estadoInventarioId("A")
				.productoCodigo("XXX")
				.cantidadPlanificada(10)
				.cantidadReal(10)
				.build()
				);

		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(2)
				.estadoInventarioId("A")
				.productoCodigo("544")
				.cantidadPlanificada(30)
				.cantidadReal(-1)
				.build()
				);

		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(3)
				.estadoInventarioId("A")
				.productoCodigo("545")
				.cantidadPlanificada(3)
				.cantidadReal(2)
				.build()
				);
		
		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(3)
				.estadoInventarioId("NC")
				.productoCodigo("545")
				.cantidadPlanificada(3)
				.cantidadReal(0)
				.build()
				);

		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(3)
				.estadoInventarioId("PUB")
				.productoCodigo("545")
				.cantidadPlanificada(3)
				.cantidadReal(2)
				.build()
				);

		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(8000)
				.estadoInventarioId("PUB")
				.productoCodigo("547")
				.cantidadPlanificada(3)
				.cantidadReal(3)
				.build()
				);

		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(9001)
				.estadoInventarioId("PUB")
				.productoCodigo("GGG")
				.cantidadPlanificada(3)
				.cantidadReal(3)
				.build()
				);		

		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(9002)
				.estadoInventarioId("A")
				.productoCodigo("545")
				.cantidadPlanificada(3)
				.cantidadReal(3)
				.build()
				);		
		
		// @formatter:off
		return ResultadoReciboDto
				.builder()
				.id(BigInteger.valueOf(id))
				.numeroOrdenWms("TC-78759-100-OC-2")
				.clienteCodigoWms("DICERMEX")
				.bodegaCodigo("TL-BOG-SIB-01")
				.lineas(lineas)
				.build();
		// @formatter:on
	}

	private ResultadoReciboDto caso06(long id) {
		List<LineaResultadoReciboDto> lineas = new ArrayList<>();
		
		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(1)
				.estadoInventarioId("A")
				.productoCodigo("555")
				.cantidadPlanificada(10)
				.cantidadReal(10)
				.build()
				);

		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(2)
				.estadoInventarioId("A")
				.productoCodigo("544")
				.cantidadPlanificada(30)
				.cantidadReal(25)
				.build()
				);

		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(3)
				.estadoInventarioId("A")
				.productoCodigo("545")
				.cantidadPlanificada(3)
				.cantidadReal(2)
				.build()
				);
		
		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(3)
				.estadoInventarioId("NC")
				.productoCodigo("545")
				.cantidadPlanificada(3)
				.cantidadReal(1)
				.build()
				);

		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(4)
				.estadoInventarioId("A")
				.productoCodigo("546")
				.cantidadPlanificada(3)
				.cantidadReal(3)
				.build()
				);

		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(9001)
				.estadoInventarioId("A")
				.productoCodigo("547")
				.cantidadPlanificada(100)
				.cantidadReal(100)
				.build()
				);

		lineas.add(
				LineaResultadoReciboDto
				.builder()
				.numeroItem(9002)
				.estadoInventarioId("A")
				.productoCodigo("548")
				.cantidadPlanificada(3)
				.cantidadReal(3)
				.build()
				);
		
		// @formatter:off
		return ResultadoReciboDto
				.builder()
				.id(BigInteger.valueOf(id))
				.numeroOrdenWms("TC-78759-100-OC-2")
				.clienteCodigoWms("DICERMEX")
				.bodegaCodigo("TL-BOG-SIB-01")
				.lineas(lineas)
				.build();
		// @formatter:on
	}

	private ResultadoReciboDto caso07(long id) {
		// @formatter:off
		return caso06(id);
		// @formatter:on
	}

}
