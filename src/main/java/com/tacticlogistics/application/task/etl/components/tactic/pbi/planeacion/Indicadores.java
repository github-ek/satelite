package com.tacticlogistics.application.task.etl.components.tactic.pbi.planeacion;

import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.VALOR_RECAUDO;
import static com.tacticlogistics.infrastructure.services.Basic.substringSafe;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.task.etl.components.ETLFlatFileStrategy;
import com.tacticlogistics.application.task.etl.readers.ExcelWorkSheetReader;
import com.tacticlogistics.application.task.etl.readers.Reader;
import com.tacticlogistics.infrastructure.services.Basic;

@Component("TACTIC.PBI.INDICADORES")
public class Indicadores extends ETLFlatFileStrategy<List<Map<String, Object>>> {
	public static final int NUMERO_MAXIMO_COLUMNAS = 63;

	private static final Logger log = LoggerFactory.getLogger(Indicadores.class);

	// ---------------------------------------------------------------------------------------------------------------------------------------
	public static final String FECHA_ORDEN = "FECHA ORDEN";
	public static final String FECHA_SOLICITUD = "FECHA SOLICITUD";
	public static final String CORTE = "CORTE";
	public static final String ESTADO = "ESTADO";
	public static final String CLIENTE = "CLIENTE";
	public static final String FACTURA = "FACTURA";
	public static final String ORDEN_DE_COMPRA = "ORDEN DE COMPRA";
	public static final String FECHA_FACTURA = "FECHA FACTURA";
	public static final String BL = "BL";
	public static final String CONTENEDOR = "CONTENEDOR";
	public static final String ID_DESPACHO = "ID DESPACHO";
	public static final String TIPO_DE_DISTRIBUCION = "TIPO DE DISTRIBUCION";
	public static final String TIPO_DE_VEHICULO = "TIPO DE VEHICULO";
	public static final String CODIGO = "CODIGO";
	public static final String PRODUCTO = "PRODUCTO";
	public static final String EMBALAJE = "EMBALAJE";
	public static final String CANTIDAD_A_CARGAR_UNIDADES = "CANTIDAD A CARGAR UNIDADES";
	public static final String CANTIDAD_A_CARGAR_CAJAS = "CANTIDAD A CARGAR CAJAS";
	public static final String UNIDADES_ADICIONALES = "UNIDADES ADICIONALES";
	public static final String UM = "UM";
	public static final String PESO_KG = "PESO KG";
	public static final String LOTE = "LOTE";
	public static final String VALOR_DECLARADO = "VALOR DECLARADO";
	public static final String CIUDAD_ORIGEN = "CIUDAD ORIGEN";
	public static final String DIRECCION_ORIGEN = "DIRECCION ORIGEN";
	public static final String DESTINATARIO = "DESTINATARIO";
	public static final String NIT = "NIT";
	public static final String CANAL_DE_ENTREGA = "CANAL DE ENTREGA";
	public static final String CROSS_DOCK = "CROSS DOCK";
	public static final String PUNTO = "PUNTO";
	public static final String CIUDAD_DESTINO = "CIUDAD DESTINO";
	public static final String DIRECCION_ENTREGA = "DIRECCION ENTREGA";
	public static final String CITA_ENTREGA = "CITA ENTREGA";
	public static final String HORA_ENTREGA = "HORA ENTREGA";
	public static final String REQUIERE_COBRO = "REQUIERE COBRO?";
	public static final String FECHA_DE_LLEGADA_AL_CLIENTE = "FECHA DE LLEGADA AL CLIENTE";
	public static final String FALTANTE = "FALTANTE";
	public static final String SOBRANTE = "SOBRANTE";
	public static final String AVERIAS = "AVERIAS";
	public static final String DEVOLUCIONES = "DEVOLUCIONES";
	public static final String CAUSAL = "CAUSAL";
	public static final String DOCUMENTACION = "DOCUMENTACION";
	public static final String OBSERVACION_CUMPLIDO = "OBSERVACION CUMPLIDO";
	public static final String COMENTARIOS = "COMENTARIOS";
	public static final String RESPONSABLE_CAUSAL = "RESPONSABLE CAUSAL";
	public static final String FACTURADO = "FACTURADO";
	public static final String OTIF_TIEMPO = "OTIF TIEMPO";
	public static final String OTIF_CANTIDAD = "OTIF CANTIDAD";
	public static final String OTIF_CALIDAD = "OTIF CALIDAD";
	public static final String OTIF_DOCUMENTACION = "OTIF DOCUMENTACION";
	public static final String OTIF_TOTAL = "OTIF  TOTAL";
	public static final String OTIF = "OTIF";
	public static final String PEDIDOS = "PEDIDOS";
	public static final String DIA = "DIA";
	public static final String SEMANA = "SEMANA";
	public static final String MES = "MES";
	public static final String FILL_RATE_1 = "FILL RATE 1";
	public static final String FILL_RATE = "FILL RATE";

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Autowired
	private ExcelWorkSheetReader reader;

	// ---------------------------------------------------------------------------------------------------------------------------------------
	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	@Override
	public Pattern getPattern() {
		return PATTERN_XLS;
	}

	@Override
	protected Reader<File, String> getReader() {
		return reader;
	}

	@Override
	protected String limpiar(String texto) {
		return Basic.limpiarCaracterEspecialDeEspacioDeExcel(super.limpiar(texto));
	}

	@Override
	protected void limpiarCampos(String[] campos, Map<String, Integer> mapNameToIndex) {
		// TODO Utilizar Programacion Functional
		super.limpiarCampos(campos, mapNameToIndex);

		for (int j = 0; j < campos.length; j++) {
			if ("NULL".equalsIgnoreCase(campos[j])) {
				campos[j] = null;
			}
		}
	}

	@Override
	protected List<String> getCamposEsperados() {
		List<String> list = new ArrayList<>();

		list.add(FECHA_ORDEN);
		list.add(FECHA_SOLICITUD);
		list.add(CORTE);
		list.add(ESTADO);
		list.add(CLIENTE);
		list.add(FACTURA);
		list.add(ORDEN_DE_COMPRA);
		list.add(FECHA_FACTURA);
		list.add(BL);
		list.add(CONTENEDOR);
		list.add(ID_DESPACHO);
		list.add(TIPO_DE_DISTRIBUCION);
		list.add(TIPO_DE_VEHICULO);
		list.add(CODIGO);
		list.add(PRODUCTO);
		list.add(EMBALAJE);
		list.add(CANTIDAD_A_CARGAR_UNIDADES);
		list.add(CANTIDAD_A_CARGAR_CAJAS);
		list.add(UNIDADES_ADICIONALES);
		list.add(UM);
		list.add(PESO_KG);
		list.add(LOTE);
		list.add(VALOR_DECLARADO);
		list.add(CIUDAD_ORIGEN);
		list.add(DIRECCION_ORIGEN);
		list.add(DESTINATARIO);
		list.add(NIT);
		list.add(CANAL_DE_ENTREGA);
		list.add(CROSS_DOCK);
		list.add(PUNTO);
		list.add(CIUDAD_DESTINO);
		list.add(DIRECCION_ENTREGA);
		list.add(CITA_ENTREGA);
		list.add(HORA_ENTREGA);
		list.add(REQUIERE_COBRO);
		list.add(FECHA_DE_LLEGADA_AL_CLIENTE);
		list.add(FALTANTE);
		list.add(SOBRANTE);
		list.add(AVERIAS);
		list.add(DEVOLUCIONES);
		list.add(CAUSAL);
		list.add(DOCUMENTACION);
		list.add(OBSERVACION_CUMPLIDO);
		list.add(COMENTARIOS);
		list.add(RESPONSABLE_CAUSAL);
		list.add(FACTURADO);
		list.add(OTIF_TIEMPO);
		list.add(OTIF_CANTIDAD);
		list.add(OTIF_CALIDAD);
		list.add(OTIF_DOCUMENTACION);
		list.add(OTIF_TOTAL);
		list.add(OTIF);
		list.add(PEDIDOS);
		list.add(DIA);
		list.add(SEMANA);
		list.add(MES);
		list.add(FILL_RATE_1);
		list.add(FILL_RATE);

		return list;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void preProcesarDirectorio() {
		super.preProcesarDirectorio();
		((ExcelWorkSheetReader) getReader()).setWorkSheetName("BASE");
	}

	@Override
	protected String extraer() {
		try {
			return ((ExcelWorkSheetReader) getReader()).read2(getArchivo(), NUMERO_MAXIMO_COLUMNAS);
		} catch (IOException e) {
			throw new RuntimeException("Error al extraer el contenido del archivo: " + getArchivo().getAbsolutePath());
		}
	}

	@Override
	protected boolean ignorarRegistroAntesDeSerSeparadoPorCampos(String registro) {
		if (super.ignorarRegistroAntesDeSerSeparadoPorCampos(registro)) {
			return true;
		}

		return registro.equalsIgnoreCase("NULL");
	}

	@Override
	protected boolean ignorarRegistroDespuesDeSerSeparadoPorCampos(String[] campos,
			Map<String, Integer> mapNameToIndex) {
		if (super.ignorarRegistroDespuesDeSerSeparadoPorCampos(campos, mapNameToIndex)) {
			return true;
		}

		String value;
		value = getValorCampo(CLIENTE, campos, mapNameToIndex);

		if (value.isEmpty()) {
			return true;
		}

		return false;
	}

	@Override
	protected String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex) {
		StringBuffer sb = new StringBuffer();
		sb.append(getValorCampo(CLIENTE, campos, mapNameToIndex));

		return sb.toString().toUpperCase();
	}

	@Override
	protected void adicionar(String key, Map<String, List<Map<String, Object>>> map, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {

		if (!map.containsKey(key)) {
			List<Map<String, Object>> dto = new ArrayList<Map<String, Object>>();
			map.put(key, dto);
		}
	}

	@Override
	protected void modificar(String key, Map<String, List<Map<String, Object>>> map, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {
		if (map.containsKey(key)) {
			Map<String, Object> p = new HashMap<>();

			p.put("FECHA_CORTE", Basic.truncate(new Date()));
			p.put("FECHA_ORDEN", getValorCampoFechaCorta(key, FECHA_ORDEN, campos, mapNameToIndex));
			p.put("FECHA_SOLICITUD", getValorCampoFechaCorta(key, FECHA_SOLICITUD, campos, mapNameToIndex));
			p.put("FECHA_FACTURA", getValorCampoFechaCorta(key, FECHA_FACTURA, campos, mapNameToIndex));
			p.put("CITA_ENTREGA", getValorCampoFechaCorta(key, CITA_ENTREGA, campos, mapNameToIndex));
			p.put("FECHA_DE_LLEGADA_AL_CLIENTE",
					getValorCampoFechaCorta(key, FECHA_DE_LLEGADA_AL_CLIENTE, campos, mapNameToIndex));

			p.put("CORTE", getValorCampoTexto(CORTE, campos, mapNameToIndex));
			p.put("ESTADO", getValorCampoTexto(ESTADO, campos, mapNameToIndex));
			p.put("CLIENTE", getValorCampoTexto(CLIENTE, campos, mapNameToIndex));
			p.put("FACTURA", getValorCampoTexto(FACTURA, campos, mapNameToIndex));
			p.put("ORDEN_DE_COMPRA", getValorCampoTexto(ORDEN_DE_COMPRA, campos, mapNameToIndex));
			p.put("BL", getValorCampoTexto(BL, campos, mapNameToIndex));
			p.put("CONTENEDOR", getValorCampoTexto(CONTENEDOR, campos, mapNameToIndex));
			p.put("ID_DESPACHO", getValorCampoTexto(ID_DESPACHO, campos, mapNameToIndex));
			p.put("TIPO_DE_DISTRIBUCION", getValorCampoTexto(TIPO_DE_DISTRIBUCION, campos, mapNameToIndex));
			p.put("TIPO_DE_VEHICULO", getValorCampoTexto(TIPO_DE_VEHICULO, campos, mapNameToIndex));
			p.put("CODIGO", getValorCampoTexto(CODIGO, campos, mapNameToIndex));
			p.put("PRODUCTO", getValorCampoTexto(PRODUCTO, campos, mapNameToIndex));
			p.put("EMBALAJE", getValorCampoTexto(EMBALAJE, campos, mapNameToIndex));
			p.put("UM", getValorCampoTexto(UM, campos, mapNameToIndex));
			p.put("LOTE", getValorCampoTexto(LOTE, campos, mapNameToIndex));
			p.put("CIUDAD_ORIGEN", getValorCampoTexto(CIUDAD_ORIGEN, campos, mapNameToIndex));
			p.put("DIRECCION_ORIGEN", getValorCampoTexto(DIRECCION_ORIGEN, campos, mapNameToIndex));
			p.put("DESTINATARIO", getValorCampoTexto(DESTINATARIO, campos, mapNameToIndex));
			p.put("NIT", getValorCampoTexto(NIT, campos, mapNameToIndex));
			p.put("CANAL_DE_ENTREGA", getValorCampoTexto(CANAL_DE_ENTREGA, campos, mapNameToIndex));
			p.put("CROSS_DOCK", getValorCampoTexto(CROSS_DOCK, campos, mapNameToIndex));
			p.put("PUNTO", getValorCampoTexto(PUNTO, campos, mapNameToIndex));
			p.put("CIUDAD_DESTINO", getValorCampoTexto(CIUDAD_DESTINO, campos, mapNameToIndex));
			p.put("DIRECCION_ENTREGA", getValorCampoTexto(DIRECCION_ENTREGA, campos, mapNameToIndex));
			p.put("HORA_ENTREGA", getValorCampoTexto(HORA_ENTREGA, campos, mapNameToIndex));
			p.put("REQUIERE_COBRO", getValorCampoTexto(REQUIERE_COBRO, campos, mapNameToIndex));
			p.put("CAUSAL", getValorCampoTexto(CAUSAL, campos, mapNameToIndex));
			p.put("DOCUMENTACION", getValorCampoTexto(DOCUMENTACION, campos, mapNameToIndex));
			p.put("OBSERVACION_CUMPLIDO", getValorCampoTexto(OBSERVACION_CUMPLIDO, campos, mapNameToIndex));
			p.put("COMENTARIOS", getValorCampoTexto(COMENTARIOS, campos, mapNameToIndex));
			p.put("RESPONSABLE_CAUSAL", getValorCampoTexto(RESPONSABLE_CAUSAL, campos, mapNameToIndex));
			p.put("FACTURADO", getValorCampoTexto(FACTURADO, campos, mapNameToIndex));
			p.put("COMENTARIOS", getValorCampoTexto(COMENTARIOS, campos, mapNameToIndex));

			p.put("CANTIDAD_A_CARGAR_UNIDADES",
					getValorCampoBigDecimal(key, CANTIDAD_A_CARGAR_UNIDADES, campos, mapNameToIndex));
			p.put("CANTIDAD_A_CARGAR_UNIDADES",
					getValorCampoBigDecimal(key, CANTIDAD_A_CARGAR_UNIDADES, campos, mapNameToIndex));
			p.put("CANTIDAD_A_CARGAR_CAJAS",
					getValorCampoBigDecimal(key, CANTIDAD_A_CARGAR_CAJAS, campos, mapNameToIndex));
			p.put("UNIDADES_ADICIONALES", getValorCampoBigDecimal(key, UNIDADES_ADICIONALES, campos, mapNameToIndex));

			p.put("PESO_KG", getValorCampoBigDecimal(key, PESO_KG, campos, mapNameToIndex));
			p.put("VALOR_DECLARADO", getValorCampoBigDecimal(key, VALOR_DECLARADO, campos, mapNameToIndex));
			p.put("FALTANTE", getValorCampoBigDecimal(key, FALTANTE, campos, mapNameToIndex));
			p.put("SOBRANTE", getValorCampoBigDecimal(key, SOBRANTE, campos, mapNameToIndex));
			p.put("AVERIAS", getValorCampoBigDecimal(key, AVERIAS, campos, mapNameToIndex));
			p.put("DEVOLUCIONES", getValorCampoBigDecimal(key, DEVOLUCIONES, campos, mapNameToIndex));
			p.put("OTIF_TIEMPO", getValorCampoBigDecimal(key, OTIF_TIEMPO, campos, mapNameToIndex));
			p.put("OTIF_CANTIDAD", getValorCampoBigDecimal(key, OTIF_CANTIDAD, campos, mapNameToIndex));
			p.put("OTIF_CALIDAD", getValorCampoBigDecimal(key, OTIF_CALIDAD, campos, mapNameToIndex));
			p.put("OTIF_DOCUMENTACION", getValorCampoBigDecimal(key, OTIF_DOCUMENTACION, campos, mapNameToIndex));
			p.put("OTIF_TOTAL", getValorCampoBigDecimal(key, OTIF_TOTAL, campos, mapNameToIndex));
			p.put("OTIF", getValorCampoBigDecimal(key, OTIF, campos, mapNameToIndex));
			p.put("PEDIDOS", getValorCampoBigDecimal(key, PEDIDOS, campos, mapNameToIndex));
			p.put("DIA", getValorCampoBigDecimal(key, DIA, campos, mapNameToIndex));
			p.put("SEMANA", getValorCampoBigDecimal(key, SEMANA, campos, mapNameToIndex));
			p.put("MES", getValorCampoBigDecimal(key, MES, campos, mapNameToIndex));
			p.put("FILL_RATE_1", getValorCampoBigDecimal(key, FILL_RATE_1, campos, mapNameToIndex));
			p.put("FILL_RATE", getValorCampoBigDecimal(key, FILL_RATE, campos, mapNameToIndex));

			map.get(key).add(p);
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	@Transactional(readOnly = false)
	protected void cargar(Map<String, List<Map<String, Object>>> map) {
		log.info("Begin cargar");

		for (String key : map.keySet()) {

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("CLIENTE", key);
			getJdbcTemplate().update(getQueryDelete(), parameters);

			List<Map<String, Object>> list = map.get(key);
			// SqlParameterSource[] params =
			// SqlParameterSourceUtils.createBatch(list.toArray());
			// getJdbcTemplate().batchUpdate(getQueryInsert(), params);

			for (Map<String, Object> p : list) {
				namedParameterJdbcTemplate.update(getQueryInsert(), p);
			}
		}
		log.info("End cargar");
	}

	// public void saveBatch(NamedParameterJdbcTemplate template, String
	// query,final List<Map<String, Object>> list) {
	// final int batchSize = 500;
	//
	// for (int j = 0; j < list.size(); j += batchSize) {
	//
	// final List<Map<String, Object>> batchList = list.subList(j, j + batchSize
	// > list.size() ? list.size() : j + batchSize);
	//
	// template.batchUpdate(query,
	// new BatchPreparedStatementSetter() {
	// @Override
	// public void setValues(PreparedStatement ps, int i)
	// throws SQLException {
	// Map<String, Object> p = batchList.get(i);
	// ps.setP;
	// }
	//
	// @Override
	// public int getBatchSize() {
	// return batchList.size();
	// }
	// });
	//
	// }
	// }

	private String getQueryDelete() {
		return " DELETE a FROM pbi.indicadores a WHERE CLIENTE = :CLIENTE ";
	}

	private String getQueryInsert() {
		// @formatter:off
		return "" + "INSERT INTO pbi.indicadores" + "           (FECHA_CORTE" + "           ,FECHA_ORDEN"
				+ "           ,FECHA_SOLICITUD" + "           ,CORTE" + "           ,ESTADO" + "           ,CLIENTE"
				+ "           ,FACTURA" + "           ,ORDEN_DE_COMPRA" + "           ,FECHA_FACTURA" + "           ,BL"
				+ "           ,CONTENEDOR" + "           ,ID_DESPACHO" + "           ,TIPO_DE_DISTRIBUCION"
				+ "           ,TIPO_DE_VEHICULO" + "           ,CODIGO" + "           ,PRODUCTO"
				+ "           ,EMBALAJE" + "           ,CANTIDAD_A_CARGAR_UNIDADES"
				+ "           ,CANTIDAD_A_CARGAR_CAJAS" + "           ,UNIDADES_ADICIONALES" + "           ,UM"
				+ "           ,PESO_KG" + "           ,LOTE" + "           ,VALOR_DECLARADO"
				+ "           ,CIUDAD_ORIGEN" + "           ,DIRECCION_ORIGEN" + "           ,DESTINATARIO"
				+ "           ,NIT" + "           ,CANAL_DE_ENTREGA" + "           ,CROSS_DOCK" + "           ,PUNTO"
				+ "           ,CIUDAD_DESTINO" + "           ,DIRECCION_ENTREGA" + "           ,CITA_ENTREGA"
				+ "           ,HORA_ENTREGA" + "           ,REQUIERE_COBRO" + "           ,FECHA_DE_LLEGADA_AL_CLIENTE"
				+ "           ,FALTANTE" + "           ,SOBRANTE" + "           ,AVERIAS" + "           ,DEVOLUCIONES"
				+ "           ,CAUSAL" + "           ,DOCUMENTACION" + "           ,OBSERVACION_CUMPLIDO"
				+ "           ,COMENTARIOS" + "           ,RESPONSABLE_CAUSAL" + "           ,FACTURADO"
				+ "           ,OTIF_TIEMPO" + "           ,OTIF_CANTIDAD" + "           ,OTIF_CALIDAD"
				+ "           ,OTIF_DOCUMENTACION" + "           ,OTIF_TOTAL" + "           ,OTIF"
				+ "           ,PEDIDOS" + "           ,DIA" + "           ,SEMANA" + "           ,MES"
				+ "           ,FILL_RATE_1" + "           ,FILL_RATE)" + "     VALUES" + "           (:FECHA_CORTE,"
				+ "           :FECHA_ORDEN," + "           :FECHA_SOLICITUD," + "           :CORTE, "
				+ "           :ESTADO, " + "           :CLIENTE, " + "           :FACTURA, "
				+ "           :ORDEN_DE_COMPRA, " + "           :FECHA_FACTURA," + "           :BL, "
				+ "           :CONTENEDOR, " + "           :ID_DESPACHO, " + "           :TIPO_DE_DISTRIBUCION, "
				+ "           :TIPO_DE_VEHICULO, " + "           :CODIGO, " + "           :PRODUCTO, "
				+ "           :EMBALAJE," + "           :CANTIDAD_A_CARGAR_UNIDADES,"
				+ "           :CANTIDAD_A_CARGAR_CAJAS," + "           :UNIDADES_ADICIONALES," + "           :UM, "
				+ "           :PESO_KG, " + "           :LOTE, " + "           :VALOR_DECLARADO,"
				+ "           :CIUDAD_ORIGEN, " + "           :DIRECCION_ORIGEN, " + "           :DESTINATARIO, "
				+ "           :NIT, " + "           :CANAL_DE_ENTREGA, " + "           :CROSS_DOCK, "
				+ "           :PUNTO, " + "           :CIUDAD_DESTINO, " + "           :DIRECCION_ENTREGA, "
				+ "           :CITA_ENTREGA," + "           :HORA_ENTREGA, " + "           :REQUIERE_COBRO, "
				+ "           :FECHA_DE_LLEGADA_AL_CLIENTE," + "           :FALTANTE," + "           :SOBRANTE,"
				+ "           :AVERIAS," + "           :DEVOLUCIONES," + "           :CAUSAL, "
				+ "           :DOCUMENTACION, " + "           :OBSERVACION_CUMPLIDO, " + "           :COMENTARIOS, "
				+ "           :RESPONSABLE_CAUSAL, " + "           :FACTURADO, " + "           :OTIF_TIEMPO,"
				+ "           :OTIF_CANTIDAD," + "           :OTIF_CALIDAD," + "           :OTIF_DOCUMENTACION,"
				+ "           :OTIF_TOTAL," + "           :OTIF," + "           :PEDIDOS," + "           :DIA,"
				+ "           :SEMANA," + "           :MES," + "           :FILL_RATE_1," + "           :FILL_RATE)"
				+ "";
		// @formatter:on

	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	private String getValorCampoTexto(String campo, String[] campos, Map<String, Integer> mapNameToIndex) {
		return Basic.substringSafe(Basic.coalesce(getValorCampo(campo, campos, mapNameToIndex), ""), 0, 100);
	}

	private Date getValorCampoFechaCorta(String key, String campo, String[] campos,
			Map<String, Integer> mapNameToIndex) {
		Date dateValue = null;

		String value;
		value = getValorCampo(campo, campos, mapNameToIndex);
		value = substringSafe(value, 0, 10);
		try {
			dateValue = Basic.toFecha(value, null, getFormatoFechaCorta());
		} catch (ParseException e) {
			logParseException(key, campo, value, getFormatoFechaCorta().toPattern());
		}
		return dateValue;
	}

	private BigDecimal getValorCampoBigDecimal(String key, String campo, String[] campos,
			Map<String, Integer> mapNameToIndex) {
		BigDecimal decimalValue = null;

		String value = getValorCampo(campo, campos, mapNameToIndex);
		try {
			decimalValue = Basic.toBigDecimal(value, null, getCantidadSolicitadaFormat());
		} catch (ParseException e) {
			logParseException(key, VALOR_RECAUDO, value, getCantidadSolicitadaFormat().toPattern());
		}

		return decimalValue;
	}

	private SimpleDateFormat formatoFechaCorta = null;

	@Override
	protected SimpleDateFormat getFormatoFechaCorta() {
		if (formatoFechaCorta == null) {
			formatoFechaCorta = new SimpleDateFormat("dd/MM/yyyy");
		}
		return formatoFechaCorta;
	}

	private DecimalFormat cantidadSolicitadaFormat = null;

	public DecimalFormat getCantidadSolicitadaFormat() {
		if (cantidadSolicitadaFormat == null) {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');

			cantidadSolicitadaFormat = new DecimalFormat("###,###,##0.######", symbols);
			cantidadSolicitadaFormat.setParseBigDecimal(true);
		}
		return cantidadSolicitadaFormat;
	}
}
