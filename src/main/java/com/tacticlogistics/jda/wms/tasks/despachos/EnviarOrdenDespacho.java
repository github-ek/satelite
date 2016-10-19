package com.tacticlogistics.jda.wms.tasks.despachos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EnviarOrdenDespacho {

	private static final Logger log = LoggerFactory.getLogger(EnviarOrdenDespacho.class);

	@Value("${wms.directorio.hostin}")
	private String directorioHostIn;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Scheduled(fixedRate = 15 * 60 * 1000)
	public void enviarOrdenesWMS() {
		try {

			List<OrdenDespacho> ordenes = jdbcTemplate.query(
					"select * from wms.ordenes_venta WHERE codigo_alterno_wms = 'DICERMEX' AND fecha_confirmacion >= CAST(GETDATE() AS DATE) AND bodega_origen_codigo ='TL-BOG-SIB-01' "
//					+ " AND numero_orden IN ("
//					+ "'100-FV-00872226',"
//					+ "'100-FV-00872273',"
//					+ "'100-FV-00872275',"
//					+ "'100-FV-00872277',"
//					+ "'100-FV-00872286',"
//					+ "'100-FV-00872317',"
//					+ "'100-FV-00872319',"
//					+ "'100-FV-00872345',"
//					+ "'100-FV-00872355',"
//					+ "'100-FV-00872359',"
//					+ "'100-RNF-00019707',"
//					+ "'100-RNF-00019716',"
//					+ "'100-RNF-00019722',"
//					+ "'100-RNF-00019725',"
//					+ "'100-RNF-00019726',"
//					+ "'100-RNF-00019730',"
//					+ "'100-RNF-00019742',"
//					+ "'100-RNF-00019744',"
//					+ "'100-RNF-00019745'"
//					+ ")"
					+ "", new OrdenDespachoRowMapper());
			
			ordenes = consolidarLineasOrden(ordenes);

			log.info("Ordenes Despacho:" + ordenes.size());

			for (OrdenDespacho oc : ordenes) {
				writeFiles(oc);
			}

			actualizarOrdenes(ordenes);

		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void actualizarOrdenes(List<OrdenDespacho> ordenes) {

		for (OrdenDespacho oc : ordenes) {
			jdbcTemplate.execute("" 
		+ "  update ordenes.ordenes " 
		+ "  set id_estado_almacenamiento = 'ALERTADA'"
		+ "  where id_orden = " + oc.getIdOrden());
		}
	}

	private void writeFiles(OrdenDespacho oc) throws IOException, UnsupportedEncodingException {
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
	public List<OrdenDespacho> consolidarLineasOrden(List<OrdenDespacho> ordenes) {
		List<OrdenDespacho> result = new ArrayList<>();

		for (OrdenDespacho oc : ordenes) {
			if (result.contains(oc)) {
				int pos = result.indexOf(oc);
				result.get(pos).appendLineas(oc.getLineas());
			} else {
				result.add(oc);
			}
		}

		return result;
	}

	public Document formatDocument(OrdenDespacho oc) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("UC_X4_ORDENPEDIDO");
		Element controlSegment = root.addElement("CTRL_SEG");

		controlSegment.addElement("TRNNAM").addText("IMPORTPEDIDOS");
		controlSegment.addElement("TRNVER").addText("2015.1");
		controlSegment.addElement("WHSE_ID").addText(oc.getBodega());

		Element header = controlSegment.addElement("ORDER_SEG");

		header.addElement("SEGNAM").addText("SEG");
		header.addElement("TRNTYP").addText("A");
		header.addElement("CLIENT_ID").addText(oc.getCodigoCliente());
		header.addElement("ORDNUM").addText(oc.getNumeroOrden());
		header.addElement("ORDTYP").addText(oc.getTipoOrden());
		header.addElement("STCUST").addText(oc.getDireccionDespacho());
		header.addElement("ST_HOST_ADR_ID").addText(oc.getDireccionDespachoId());
		header.addElement("RTCUST").addText(oc.getDireccionEnvio());
		header.addElement("RT_HOST_ADR_ID").addText(oc.getDireccionEnvioId());
		header.addElement("BTCUST").addText(oc.getDireccionFacturacion());
		header.addElement("BT_HOST_ADR_ID").addText(oc.getDireccionFacturacionId());
		header.addElement("WH_ID").addText(oc.getBodega());

		for (LineaOrdenDespacho linea : oc.getLineas()) {
			Element line = header.addElement("ORDER_LINE_SEG");

			line.addElement("SEGNAM").addText("LINE_SEG");
			line.addElement("ORDNUM").addText(linea.getNumeroOrden());
			line.addElement("ORDLIN").addText(linea.getNumeroLinea() + "");
			line.addElement("ORDSLN").addText(linea.getNumeroSublinea() + "");
			line.addElement("PRTNUM").addText(linea.getCodigoProducto());
			line.addElement("PRT_CLIENT_ID").addText(oc.getCodigoCliente());
			line.addElement("LOTNUM").addText(linea.getLote());
			line.addElement("INVSTS").addText(linea.getEstadoInventario());
			line.addElement("ORDQTY").addText(linea.getCantidadSolicitad() + "");
			line.addElement("SPLFLG").addText(linea.isDividirEnvio() ? "1" : "0");
			line.addElement("WH_ID").addText(oc.getBodega());
			line.addElement("INVSTS_PRG").addText(linea.getEstadoInventario());
			line.addElement("CARCOD").addText(linea.getTransportista());
			line.addElement("SRVLVL").addText(linea.getNivelServicio());
		}
		return document;
	}
}
