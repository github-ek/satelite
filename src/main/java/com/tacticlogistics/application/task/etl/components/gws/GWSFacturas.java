package com.tacticlogistics.application.task.etl.components.gws;

import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINATARIO_CANAL_CODIGO_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINATARIO_IDENTIFICACION;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_CIUDAD_CODIGO_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_DIRECCION;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_NOMBRE;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.FECHA_ENTREGA_MAXIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.FECHA_ENTREGA_MINIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.HORA_ENTREGA_MAXIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.HORA_ENTREGA_MINIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_BODEGA_DESTINO_CODIGO_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_CANTIDAD_SOLICITADA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_PREDISTRIBUCION_DESTINO_FINAL;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_PREDISTRIBUCION_ROTULO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_PRODUCTO_CODIGO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_PRODUCTO_DESCRIPCION;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_VALOR_DECLARADO_POR_UNIDAD;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NOTAS;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NUMERO_CONSOLIDADO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NUMERO_ORDEN;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.PREFIJO_NUMERO_ORDEN;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.REQUIERE_RECAUDO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.TIPO_SERVICIO_CODIGO_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.VALOR_RECAUDO;
import static com.tacticlogistics.infrastructure.services.Basic.substringSafe;

import java.io.File;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.etl.ETLLineaOrdenDto;
import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.application.services.ordenes.OrdenesApplicationService;
import com.tacticlogistics.application.task.etl.components.ETLFlatFileStrategy;
import com.tacticlogistics.application.task.etl.readers.CharsetDetectorFileReader;
import com.tacticlogistics.application.task.etl.readers.Reader;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.infrastructure.services.Basic;

import ch.qos.logback.classic.Logger;

@Component("GWS.ORDENES")
public class GWSFacturas extends ETLFlatFileStrategy<ETLOrdenDto> {
	private static final Logger log = (Logger) LoggerFactory.getLogger(GWSFacturas.class);

	@Autowired
	private CharsetDetectorFileReader reader;

	@Autowired
	private OrdenesApplicationService ordenesService;

	// ---------------------------------------------------------------------------------------------------------------------------------------
	protected String getTipoServicioCodigo() {
		return "OVP-PRODUCTOS";
	}

	protected String getClienteCodigo() {
		return "GWS";
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected Reader<File, String> getReader() {
        return reader;
    }

	@Override
	protected String limpiar(String texto) {
		return super.limpiar(texto).replace('\r', ' ');
	}

    // ---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected List<String> getCamposEsperados() {
		List<String> list = new ArrayList<>();

		list.add(TIPO_SERVICIO_CODIGO_ALTERNO.toString());
		list.add(NUMERO_CONSOLIDADO.toString());
		list.add(PREFIJO_NUMERO_ORDEN.toString());
		list.add(NUMERO_ORDEN.toString());

		list.add(DESTINATARIO_IDENTIFICACION.toString());
		list.add(DESTINATARIO_CANAL_CODIGO_ALTERNO.toString());

		list.add(DESTINO_NOMBRE.toString());
		list.add(DESTINO_DIRECCION.toString());
		list.add(DESTINO_CIUDAD_CODIGO_ALTERNO.toString());

		list.add(IGNORAR);
		list.add(FECHA_ENTREGA_MINIMA.toString());
		list.add(FECHA_ENTREGA_MAXIMA.toString());
		list.add(HORA_ENTREGA_MINIMA.toString());
		list.add(HORA_ENTREGA_MAXIMA.toString());

		list.add(NOTAS.toString());

		list.add(LINEA_PRODUCTO_CODIGO.toString());
		list.add(LINEA_PRODUCTO_DESCRIPCION.toString());
		list.add(LINEA_CANTIDAD_SOLICITADA.toString());
		list.add(LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO.toString());
		list.add(LINEA_BODEGA_DESTINO_CODIGO_ALTERNO.toString());
		list.add(LINEA_VALOR_DECLARADO_POR_UNIDAD.toString());

		list.add(LINEA_PREDISTRIBUCION_DESTINO_FINAL.toString());
		list.add(LINEA_PREDISTRIBUCION_ROTULO.toString());

		list.add(REQUIERE_RECAUDO.toString());
		list.add(VALOR_RECAUDO.toString());

		return list;
	}

	@Override
	protected boolean generarEncabezadoConLosNombresDeLosCamposEsperados() {
		return true;
	}

	@Override
	protected boolean ignorarRegistroDespuesDeSerSeparadoPorCampos(String[] campos,
			Map<String, Integer> mapNameToIndex) {
		return "".equals(getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex).trim());
	}

	@Override
	protected String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex) {
		StringBuffer sb = new StringBuffer();

		sb.append(getValorCampo(PREFIJO_NUMERO_ORDEN, campos, mapNameToIndex)).append("-")
				.append(getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex));

		return sb.toString();
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void adicionar(String key, Map<String, ETLOrdenDto> map, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {

		if (!map.containsKey(key)) {
			String value;
			Date dateValue;
			Time timeValue;
			Integer integerValue;

			ETLOrdenDto dto = new ETLOrdenDto();

			dto.setClienteCodigo(getClienteCodigo());

			dto.setTipoServicioCodigo(getTipoServicioCodigo());
			dto.setTipoServicioCodigoAlterno(getValorCampo(TIPO_SERVICIO_CODIGO_ALTERNO, campos, mapNameToIndex));

			value = getValorCampo(NUMERO_CONSOLIDADO, campos, mapNameToIndex);
			dto.setNumeroConsolidado(value);

			dto.setNumeroOrden(key);

			value = GWSMaestroClientes
					.decodeCanalCodigoAlterno(getValorCampo(DESTINATARIO_CANAL_CODIGO_ALTERNO, campos, mapNameToIndex));
			dto.setCanalCodigoAlterno(value);

			value = getValorCampo(DESTINATARIO_IDENTIFICACION, campos, mapNameToIndex);
			dto.setDestinatarioNumeroIdentificacion(value);

			value = getValorCampo(DESTINO_NOMBRE, campos, mapNameToIndex);
			dto.setDestinoNombre(value);

			value = GWSMaestroClientes
					.decodeCiudadNombreAlterno(getValorCampo(DESTINO_CIUDAD_CODIGO_ALTERNO, campos, mapNameToIndex));
			dto.setDestinoCiudadNombreAlterno(value);

			value = getValorCampo(DESTINO_DIRECCION, campos, mapNameToIndex);
			dto.setDestinoDireccion(value);

			dateValue = null;
			value = getValorCampo(FECHA_ENTREGA_MINIMA, campos, mapNameToIndex);
			value = substringSafe(value, 0, 10);
			try {
				dateValue = Basic.toFecha(value, null, getFormatoFechaCorta());
			} catch (ParseException e) {
				logParseException(key, FECHA_ENTREGA_MINIMA, value, getFormatoFechaCorta().toPattern());
			}
			dto.setFechaEntregaSugeridaMinima(dateValue);

			dateValue = null;
			value = getValorCampo(FECHA_ENTREGA_MAXIMA, campos, mapNameToIndex);
			value = substringSafe(value, 0, 10);
			try {
				dateValue = Basic.toFecha(value, null, getFormatoFechaCorta());
			} catch (ParseException e) {
				logParseException(key, FECHA_ENTREGA_MAXIMA, value, getFormatoFechaCorta().toPattern());
			}
			dto.setFechaEntregaSugeridaMaxima(dateValue);

			timeValue = null;
			value = getValorCampo(HORA_ENTREGA_MINIMA, campos, mapNameToIndex);
			value = substringSafe(value, 0, 5);
			try {
				timeValue = Basic.toHora(value, null, getFormatoHoraHHmm());
			} catch (ParseException e) {
				logParseException(key, HORA_ENTREGA_MINIMA, value, getFormatoHoraHHmm().toPattern());
			}
			dto.setHoraEntregaSugeridaMinima(timeValue);

			timeValue = null;
			value = getValorCampo(HORA_ENTREGA_MAXIMA, campos, mapNameToIndex);
			value = substringSafe(value, 0, 5);
			try {
				timeValue = Basic.toHora(value, null, getFormatoHoraHHmm());
			} catch (ParseException e) {
				logParseException(key, HORA_ENTREGA_MAXIMA, value, getFormatoHoraHHmm().toPattern());
			}
			dto.setHoraEntregaSugeridaMaxima(timeValue);

			value = getValorCampo(NOTAS, campos, mapNameToIndex);
			dto.setNotasConfirmacion(value);

			integerValue = null;
			value = getValorCampo(REQUIERE_RECAUDO, campos, mapNameToIndex, "");
			if (value.equalsIgnoreCase("RECAUDO")) {
				value = getValorCampo(VALOR_RECAUDO, campos, mapNameToIndex);
				try {
					integerValue = Basic.toEntero(value, null, getCantidadSolicitadaFormat());
				} catch (ParseException e) {
					logParseException(key, VALOR_RECAUDO, value, getCantidadSolicitadaFormat().toPattern());
				}
			}
			dto.setValorRecaudo(integerValue);

			value = getClienteCodigo();
			dto.setUsuarioConfirmacion(value);

			map.put(key, dto);
		}
	}

	@Override
	protected void modificar(String key, Map<String, ETLOrdenDto> map, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {
		if (map.containsKey(key)) {
			String value;
			Integer integerValue;

			ETLLineaOrdenDto dto = new ETLLineaOrdenDto();

			value = getValorCampo(LINEA_PRODUCTO_DESCRIPCION, campos, mapNameToIndex);
			dto.setDescripcion(value);

			integerValue = null;
			value = getValorCampo(LINEA_CANTIDAD_SOLICITADA, campos, mapNameToIndex);
			try {
				integerValue = Basic.toEntero(value, null, getCantidadSolicitadaFormat());
			} catch (ParseException e) {
				logParseException(key, LINEA_CANTIDAD_SOLICITADA, value, getCantidadSolicitadaFormat().toPattern());
			}
			dto.setCantidadSolicitada(integerValue);

			value = getValorCampo(LINEA_PRODUCTO_CODIGO, campos, mapNameToIndex);
			dto.setProductoCodigo(value);
			dto.setUnidadCodigo("UN");

			value = getValorCampo(LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO, campos, mapNameToIndex);
			dto.setBodegaOrigenCodigoAlterno(value);

			value = getValorCampo(LINEA_BODEGA_DESTINO_CODIGO_ALTERNO, campos, mapNameToIndex);
			dto.setBodegaDestinoCodigoAlterno(value);

			integerValue = null;
			value = getValorCampo(LINEA_VALOR_DECLARADO_POR_UNIDAD, campos, mapNameToIndex);
			value = value.replaceAll("[$\\s]+", "");
			try {
				integerValue = Basic.toEntero(value, null, getCantidadSolicitadaFormat());
			} catch (ParseException e) {
				logParseException(key, LINEA_VALOR_DECLARADO_POR_UNIDAD, value,
						getCantidadSolicitadaFormat().toPattern());
			}
			dto.setValorDeclaradoPorUnidad(integerValue);

			value = getValorCampo(LINEA_PREDISTRIBUCION_DESTINO_FINAL, campos, mapNameToIndex);
			dto.setPredistribucionDestinoFinal(value);

			if (!dto.getPredistribucionDestinoFinal().equals("")) {
				value = getValorCampo(LINEA_PREDISTRIBUCION_ROTULO, campos, mapNameToIndex);
			} else {
				value = "";
			}
			dto.setPredistribucionRotulo(value);

			map.get(key).getLineas().add(dto);
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	@Transactional(readOnly = false)
	protected void cargar(Map<String, ETLOrdenDto> map) {
		for (Entry<String, ETLOrdenDto> entry : map.entrySet()) {
			ETLOrdenDto dto = entry.getValue();
			try {
				Orden orden = ordenesService.saveOrdenDespachosSecundaria(dto);
				logInfo(dto.getNumeroOrden(), "", "OK");
			} catch (Exception e) {
				logError(dto.getNumeroOrden(), "", e.getMessage());
			}
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	private DecimalFormat cantidadSolicitadaFormat = null;

	public DecimalFormat getCantidadSolicitadaFormat() {
		if (cantidadSolicitadaFormat == null) {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator('.');
			symbols.setDecimalSeparator(',');

			cantidadSolicitadaFormat = new DecimalFormat("###,###,##0.######", symbols);
		}
		return cantidadSolicitadaFormat;
	}
}
