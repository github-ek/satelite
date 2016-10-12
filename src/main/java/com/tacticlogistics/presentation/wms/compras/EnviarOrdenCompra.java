package com.tacticlogistics.presentation.wms.compras;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tacticlogistics.clientes.dicermex.compras.almacenamiento.AlertasWmsService;
import com.tacticlogistics.clientes.dicermex.compras.prealertas.PreAlertasService;
import com.tacticlogistics.domain.model.ordenes.LineaOrden;
import com.tacticlogistics.domain.model.ordenes.Orden;

@Component
public class EnviarOrdenCompra {

	private static final Logger log = LoggerFactory.getLogger(EnviarOrdenCompra.class);

	@Value("${wms.directorio.hostin}")
	private String directorioHostIn;

	@Autowired
	private AlertasWmsService ocService;

	@Scheduled(fixedRate = 1000 * 60 * 1)
	public void enviarOrdenesWMS() {
		try {

			List<OrdenCompra> ordenes = getOrdenesDeCompraHabilitadas();

			ordenes = consolidarLineasOrden(ordenes);

			log.info("Ordenes Compra:" + ordenes.size());

			for (OrdenCompra oc : ordenes) {
				writeFiles(oc);
			}

			actualizarOrdenes(ordenes);

		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private List<OrdenCompra> getOrdenesDeCompraHabilitadas() {
		List<Orden> ordenes = ocService.getOrdenesPendientesPorAlertarAlWms();
		
		// @formatter:off
		return ordenes
				.stream()
				.map(orden -> {
					OrdenCompra oc =new OrdenCompra();
					String bodegaDestinoCodigo = orden.getLineas().stream().findFirst().get().getBodegaDestinoCodigo();

					oc.setIdOrden(orden.getId());
					oc.setNumeroOrden("TC-" + orden.getId() + '-' + orden.getNumeroOrden());
					oc.setProveedor(orden.getCliente().getCodigoAlternoWms());
					oc.setClienteId(orden.getCliente().getCodigoAlternoWms());
					oc.setBodega(bodegaDestinoCodigo);
					oc.setTipoOrden("REC");
					oc.setEstadoOrden("OPEN");
					
					for (LineaOrden e : orden.getLineas()) {
						LineaOrdenCompra ocLinea = new LineaOrdenCompra();
						
						ocLinea.setNumeroLinea(e.getNumeroItem());
						ocLinea.setCantidadEsperada(e.getCantidadPlanificada());
						ocLinea.setCodigoProducto(e.getProductoCodigo());
						ocLinea.setEstadoInventarioEsperado(e.getEstadoInventarioDestinoId());
						ocLinea.setLote(e.getLote());
						ocLinea.setEstampillado(e.getRequerimientoEstampillado());

						oc.addLinea(ocLinea);
					}
					
					return oc;
				}).collect(Collectors.toList());
		// @formatter:on
	}

	private void actualizarOrdenes(List<OrdenCompra> ordenes) {
		// @formatter:off
		List<Integer> ordenesId = ordenes
				.stream()
				.map(orden -> orden.getIdOrden())
				.collect(Collectors.toList());
		// @formatter:on

		ocService.alertarOrdenesDeCompraAlWms(ordenesId);
	}

	private void writeFiles(OrdenCompra oc) throws IOException, UnsupportedEncodingException {
		OutputFormat format = OutputFormat.createPrettyPrint();

		FileOutputStream fileOut = new FileOutputStream(
				new File(directorioHostIn + "\\" + oc.getNumeroOrden() + ".xml"));

		XMLWriter writer;

		writer = new XMLWriter(fileOut, format);

		writer.write(formatDocument(oc));

		fileOut.close();

		writer.close();
		
		File triggerFile = new File(directorioHostIn + "\\" + oc.getNumeroOrden() + ".trg");
		
		triggerFile.createNewFile();
	}

	/**
	 * Consolidar las lineas de las ordenes en un solo objecto OrdenCompra esto
	 * debido a que la consulta de SQL crea un objecto por cada linea de modo
	 * que se crean muchos Ordenes con 1 sola linea cada uno.
	 * 
	 * @param ordenes
	 * @return Lista consolidada
	 */
	public List<OrdenCompra> consolidarLineasOrden(List<OrdenCompra> ordenes) {
		List<OrdenCompra> result = new ArrayList<>();

		for (OrdenCompra oc : ordenes) {
			if (result.contains(oc)) {
				int pos = result.indexOf(oc);
				result.get(pos).appendLineas(oc.getLineas());
			} else {
				result.add(oc);
			}
		}

		return result;
	}

	public Document formatDocument(OrdenCompra oc) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("UC_RA_INB_IFD");
		Element controlSegment = root.addElement("CTRL_SEG");

		controlSegment.addElement("TRNNAM").addText("ORDCOMP_ESB");
		controlSegment.addElement("TRNVER").addText("ESB2015.1");
		controlSegment.addElement("WHSE_ID").addText(oc.getBodega());

		Element header = controlSegment.addElement("HEADER_SEG");

		header.addElement("SEGNAM").addText("CABECERA");
		header.addElement("TRNTYP").addText("A");
		header.addElement("INVNUM").addText(oc.getNumeroOrden());
		header.addElement("SUPNUM").addText(oc.getProveedor());
		header.addElement("INVTYP").addText(oc.getTipoOrden());
		header.addElement("CLIENT_ID").addText(oc.getClienteId());
		header.addElement("RIMSTS").addText(oc.getEstadoOrden());

		for (LineaOrdenCompra linea : oc.getLineas()) {
			Element line = header.addElement("LINE_SEG");

			line.addElement("SEGNAM").addText("LINEA");
			line.addElement("INVLIN").addText(linea.getNumeroLinea() + "");
			line.addElement("INVSLN").addText(linea.getNumeroSublinea() + "");
			line.addElement("PRTNUM").addText(linea.getCodigoProducto());
			line.addElement("EXPQTY").addText(linea.getCantidadEsperada() + "");
			line.addElement("RCVSTS").addText(linea.getEstadoInventarioEsperado());
		}
		return document;
	}
}
