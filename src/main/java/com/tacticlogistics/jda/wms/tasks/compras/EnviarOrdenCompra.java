package com.tacticlogistics.jda.wms.tasks.compras;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.dicermex.services.compras.wms.alertas.AlertasWmsService;
import com.dicermex.services.compras.wms.alertas.ResultadoAlertaDto;
import com.dicermex.services.compras.wms.alertas.ResultadoAlertaType;
import com.j256.ormlite.dao.Dao;
import com.tacticlogistics.domain.model.oms.LineaOrden;
import com.tacticlogistics.domain.model.oms.Orden;
import com.tacticlogistics.jda.wms.db.entities.Control;
import com.tacticlogistics.jda.wms.db.entities.receiving.PurchaseOrder;
import com.tacticlogistics.jda.wms.db.entities.receiving.PurchaseOrderLine;

@Component
@SuppressWarnings("unused")
public class EnviarOrdenCompra {

	private static final Logger log = LoggerFactory.getLogger(EnviarOrdenCompra.class);
	private static final String EVENT_PURCHASE_ORDER = "UC_PO_MANAGEMENT";
	private static final String ACTION_CREATE = "A";
	private static final String ACTION_UPDATE = "R";
	private static final String ACTION_DELETE = "D";
	private static final int STATUS_CREATED = 1;
	private static final int STATUS_SUCCESS = 2;
	private static final int STATUS_ERROR = 3;
	private static final int STATUS_CONFIRMED = 4;
	private static final int STATUS_SENT_FAIL = 5;

	@Value("${wms.directorio.hostin}")
	private String directorioHostIn;

	@Autowired
	private AlertasWmsService ocService;

	@Autowired
	Dao<Control, BigInteger> controlDao;
	@Autowired
	Dao<PurchaseOrder, BigInteger> purchaseOrderDao;
	@Autowired
	Dao<PurchaseOrderLine, BigInteger> purchaseOrderLineDao;

	//@Scheduled(fixedRate = 1000 * 60 * 1)
	public void enviarOrdenesWMS() {
		try {

			List<PurchaseOrder> ordenes = getOrdenesDeCompraHabilitadas();

			ordenes = consolidarLineasOrden(ordenes);

			log.info("Ordenes Compra:" + ordenes.size());

			for (PurchaseOrder oc : ordenes) {
				writePurchaseOrderInWMS(oc, ACTION_CREATE);
			}

			actualizarOrdenes(ordenes);

		} catch (SQLException e) {
			log.error(e.getMessage());
		}
	}

	//@Scheduled(fixedRate = 1000 * 60 * 1)
	public void confirmarOrdenesCreadas() {
		try {
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("status", STATUS_SUCCESS);
			
			List<Control> transactionsOK = controlDao.queryForFieldValuesArgs(args);
			log.info("Ordenes OK:" + transactionsOK.size());

			List<ResultadoAlertaDto> resultados = new ArrayList<>();
			for (Control co : transactionsOK) {
				args = new HashMap<String, Object>();
				args.put("transaction_id", co.getTransaction_id());
				PurchaseOrder oc = purchaseOrderDao.queryForFieldValues(args).get(0);
				
				resultados.add(ResultadoAlertaDto.builder().numeroOrdenWms(oc.getInvnum())
						.resultado(ResultadoAlertaType.OK).build());
			}
			
			//ocService.confirmarResultadoDeAlertasAlWms(resultados);
			
			for (Control co : transactionsOK) {
				co.setStatus(STATUS_CONFIRMED);
				controlDao.update(co);
			}

		} catch (SQLException e) {
			log.error(e.getMessage());
		}
	}

	//@Scheduled(fixedRate = 1000 * 60 * 1)
	public void confirmarOrdenesError() {
		try {
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("status", STATUS_ERROR);
			
			List<Control> transactionsOK = controlDao.queryForFieldValuesArgs(args);
			log.info("Ordenes OK:" + transactionsOK.size());

			List<ResultadoAlertaDto> resultados = new ArrayList<>();
			for (Control co : transactionsOK) {
				args = new HashMap<String, Object>();
				args.put("transaction_id", co.getTransaction_id());
				PurchaseOrder oc = purchaseOrderDao.queryForFieldValues(args).get(0);
				
				resultados.add(ResultadoAlertaDto.builder().numeroOrdenWms(oc.getInvnum())
						.resultado(ResultadoAlertaType.ERROR).build());
			}
			
			//ocService.confirmarResultadoDeAlertasAlWms(resultados);
			
			for (Control co : transactionsOK) {
				co.setStatus(STATUS_SENT_FAIL);
				controlDao.update(co);
			}

		} catch (SQLException e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * Create transaction in WMS intermediate tables
	 * 1. create lines
	 * 2. create header
	 * 3. create control transaction
	 */
	private void writePurchaseOrderInWMS(PurchaseOrder oc, String transactionType) throws SQLException {
		System.out.println("===== Beginning transaction WMS =====");
		
		//Create control transaction to get a Transaction_id
		Control c = new Control();
		c.setStatus(-1); //Status must be invalid at this point
		c.setEvent_id(EVENT_PURCHASE_ORDER);
		c.setWh_id(oc.getWh_id());
		c.setTrntyp(transactionType);
		c.setCreate_date(new Date(new java.util.Date().getTime()));
		
		controlDao.create(c);
		System.out.println("Transaction Number: " + c.getTransaction_id());

		for (PurchaseOrderLine line : oc.getLines()) {
			purchaseOrderLineDao.create(line);
		}

		purchaseOrderDao.create(oc);
		
		//If everything else worked, update transaction status
		c.setStatus(STATUS_CREATED);
		controlDao.update(c);
	}

	private List<PurchaseOrder> getOrdenesDeCompraHabilitadas() {
		List<Orden> ordenes = ocService.getOrdenesPendientesPorAlertarAlWms();

		// @formatter:off
		return ordenes.stream().map(orden -> {
			PurchaseOrder oc = new PurchaseOrder();
			String bodegaDestinoCodigo = orden.getLineas().stream().findFirst().get().getBodegaDestinoCodigo();

			oc.orderId = orden.getId();
			oc.setInvnum("TC-" + orden.getId() + '-' + orden.getNumeroOrden());
			oc.setSupnum(orden.getCliente().getNumeroIdentificacion());
			oc.setClient_id(orden.getCliente().getCodigoAlternoWms());
			oc.setWh_id(bodegaDestinoCodigo);
			oc.setInvtyp("REC");
			oc.setRimsts("OPEN");

			for (LineaOrden e : orden.getLineas()) {
				PurchaseOrderLine ocLinea = new PurchaseOrderLine();

				ocLinea.setInvlin(e.getNumeroItem() + "");
				ocLinea.setExpqty(e.getCantidadPlanificada());
				ocLinea.setPrtnum(e.getProductoCodigo());
				ocLinea.setRcvsts(e.getEstadoInventarioDestinoId());
				ocLinea.setLotnum(e.getLote());
				ocLinea.setInv_attr_str1(e.getRequerimientoEstampillado());

				oc.addLine(ocLinea);
			}

			return oc;
		}).collect(Collectors.toList());
		// @formatter:on
	}

	private void actualizarOrdenes(List<PurchaseOrder> ordenes) {
		// @formatter:off
		List<Integer> ordenesId = ordenes
				.stream()
				.map(orden -> orden.orderId)
				.collect(Collectors.toList());
		// @formatter:on

		//ocService.alertarOrdenesDeCompraAlWms(ordenesId);
	}

	private void writeFiles(Orden oc) throws Exception {
		OutputFormat format = OutputFormat.createPrettyPrint();

		FileOutputStream fileOut = new FileOutputStream(
				new File(directorioHostIn + "\\" + oc.getOrdenOriginalId() + ".xml"));

		XMLWriter writer;

		writer = new XMLWriter(fileOut, format);

		writer.write(formatDocument(oc));

		fileOut.close();

		writer.close();

		File triggerFile = new File(directorioHostIn + "\\" + oc.getOrdenOriginalId() + ".trg");

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
	public List<PurchaseOrder> consolidarLineasOrden(List<PurchaseOrder> ordenes) {
		List<PurchaseOrder> result = new ArrayList<>();

		for (PurchaseOrder oc : ordenes) {
			if (result.contains(oc)) {
				int pos = result.indexOf(oc);
				result.get(pos).appendLines(oc.getLines());
			} else {
				result.add(oc);
			}
		}

		return result;
	}

	public Document formatDocument(Orden orden) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("UC_RA_INB_IFD");
		Element controlSegment = root.addElement("CTRL_SEG");

		controlSegment.addElement("TRNNAM").addText("ORDCOMP_ESB");
		controlSegment.addElement("TRNVER").addText("ESB2015.1");
		controlSegment.addElement("WHSE_ID").addText(orden.getLineas().stream().findFirst().get().getBodegaDestinoCodigo());

		Element header = controlSegment.addElement("HEADER_SEG");

		header.addElement("SEGNAM").addText("CABECERA");
		header.addElement("TRNTYP").addText("A");
		header.addElement("INVNUM").addText("TC-" + orden.getId() + '-' + orden.getNumeroOrden());
		header.addElement("SUPNUM").addText(orden.getCliente().getNumeroIdentificacion());
		header.addElement("INVTYP").addText("RET");
		header.addElement("CLIENT_ID").addText(orden.getCliente().getCodigoAlternoWms());
		header.addElement("RIMSTS").addText("OPEN");

		for (LineaOrden linea : orden.getLineas()) {
			Element line = header.addElement("LINE_SEG");

			line.addElement("SEGNAM").addText("LINEA");
			line.addElement("INVLIN").addText(linea.getNumeroItem() + "");
			line.addElement("INVSLN").addText("0000");
			line.addElement("PRTNUM").addText(linea.getProductoCodigo());
			line.addElement("EXPQTY").addText(linea.getCantidadPlanificada() + "");
			line.addElement("RCVSTS").addText(linea.getEstadoInventarioDestinoId());
		}
		return document;
	}
}
