package com.tacticlogistics.application.task.etl.deprecated.components.gws;

import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINATARIO_NUMERO_IDENTIFICACION;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_CIUDAD_NOMBRE_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_DIRECCION;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_NOMBRE;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.FECHA_SUGERIDA_ENTREGA_MAXIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.FECHA_SUGERIDA_ENTREGA_MINIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.HORA_SUGERIDA_ENTREGA_MAXIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.HORA_SUGERIDA_ENTREGA_MINIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_BODEGA_DESTINO_CODIGO_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_CANTIDAD_SOLICITADA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_PRODUCTO_CODIGO_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_PRODUCTO_DESCRIPCION;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_VALOR_DECLARADO_POR_UNIDAD;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NOTAS_APROBACION_CLIENTE;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NUMERO_CONSOLIDADO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NUMERO_ORDEN;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.PREFIJO_NUMERO_ORDEN;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.CANAL_CODIGO_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.TIPO_SERVICIO_CODIGO_ALTERNO;
import static com.tacticlogistics.infrastructure.services.Basic.substringSafe;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.etl.ETLLineaOrdenDto;
import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.application.services.ordenes.OrdenesApplicationService;
import com.tacticlogistics.application.task.etl.components.ETLFlatFileStrategy;
import com.tacticlogistics.application.task.etl.components.gws.GWSMaestroClientes;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.infrastructure.services.Basic;


public class GWSPedidos extends ETLFlatFileStrategy<ETLOrdenDto> {
    @Autowired
    private OrdenesApplicationService ordenesService;

    // ---------------------------------------------------------------------------------------------------------------------------------------
    protected String getTipoServicioCodigoAlterno() {
        return "OVP-PRODUCTOS";
    }

    protected String getClienteCodigoAlterno() {
        return "GWS";
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected String limpiar(String texto) {
        return super.limpiar(texto).replace('\r', ' ');
    }

    @Override
    @Transactional(readOnly = false)
    protected void cargar(Map<String, ETLOrdenDto> map) {
        for (Entry<String, ETLOrdenDto> entry : map.entrySet()) {
            ETLOrdenDto dto = entry.getValue();
            try {
//                Orden orden = ordenesService.saveOrden(dto);
//                logInfo(dto.getNumeroOrden(), "", "OK");
//                // TODO REGISTRAR LOS ERRORES DE LA ORDEN EN EL LOG
            } catch (Exception e) {
                logError(dto.getNumeroOrden(), "", e.getMessage());
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex) {
        StringBuffer sb = new StringBuffer();

        sb.append(getValorCampo(PREFIJO_NUMERO_ORDEN, campos, mapNameToIndex)).append("-")
                .append(getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex));

        return sb.toString();
    }

    @Override
    protected void adicionar(String key, Map<String, ETLOrdenDto> map, String[] campos,
            Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {

        if (!map.containsKey(key)) {
            String value;
            Date dateValue;
            Time timeValue;

            ETLOrdenDto dto = new ETLOrdenDto();

            dto.setTipoServicioCodigoAlterno(getTipoServicioCodigoAlterno());
            dto.setClienteCodigo(getClienteCodigoAlterno());

            value = getValorCampo(NUMERO_CONSOLIDADO, campos, mapNameToIndex);
            dto.setNumeroConsolidado(value);

            dto.setNumeroOrden(key);

            value = getValorCampo(DESTINATARIO_NUMERO_IDENTIFICACION, campos, mapNameToIndex);
            dto.setDestinatarioNumeroIdentificacion(value);

            value = GWSMaestroClientes
                    .decodeCanalCodigoAlterno(getValorCampo(CANAL_CODIGO_ALTERNO, campos, mapNameToIndex));
            dto.setCanalCodigoAlterno(value);

            value = getValorCampo(DESTINO_NOMBRE, campos, mapNameToIndex);
            dto.setDestinoNombre(value);

            value = getValorCampo(DESTINO_DIRECCION, campos, mapNameToIndex);
            dto.setDestinoDireccion(value);

            value = GWSMaestroClientes
                    .decodeCiudadNombreAlterno(getValorCampo(DESTINO_CIUDAD_NOMBRE_ALTERNO, campos, mapNameToIndex));
            dto.setDestinoCiudadNombreAlterno(value);

            dateValue = null;
            value = getValorCampo(FECHA_SUGERIDA_ENTREGA_MINIMA, campos, mapNameToIndex);
            try {
                dateValue = Basic.toFecha(value, null, getFormatoFechaCorta());
            } catch (ParseException e) {
                logParseException(key, FECHA_SUGERIDA_ENTREGA_MINIMA, value, getFormatoFechaCorta().toPattern());
            }
            dto.setFechaEntregaSugeridaMinima(dateValue);

            dateValue = null;
            value = getValorCampo(FECHA_SUGERIDA_ENTREGA_MAXIMA, campos, mapNameToIndex);
            try {
                dateValue = Basic.toFecha(value, null, getFormatoFechaCorta());
            } catch (ParseException e) {
                logParseException(key, FECHA_SUGERIDA_ENTREGA_MAXIMA, value, getFormatoFechaCorta().toPattern());
            }
            dto.setFechaEntregaSugeridaMaxima(dateValue);

            timeValue = null;
            value = getValorCampo(HORA_SUGERIDA_ENTREGA_MINIMA, campos, mapNameToIndex);
            value = substringSafe(value, 0, 5);
            try {
                timeValue = Basic.toHora(value, null, getFormatoHoraHHmm());
            } catch (ParseException e) {
                logParseException(key, HORA_SUGERIDA_ENTREGA_MINIMA, value, getFormatoHoraHHmm().toPattern());
            }
            dto.setHoraEntregaSugeridaMinima(timeValue);

            timeValue = null;
            value = getValorCampo(HORA_SUGERIDA_ENTREGA_MAXIMA, campos, mapNameToIndex);
            try {
                timeValue = Basic.toHora(value, null, getFormatoHoraHHmm());
            } catch (ParseException e) {
                logParseException(key, HORA_SUGERIDA_ENTREGA_MAXIMA, value, getFormatoHoraHHmm().toPattern());
            }
            dto.setHoraEntregaSugeridaMaxima(timeValue);

            value = getValorCampo(NOTAS_APROBACION_CLIENTE, campos, mapNameToIndex);
            dto.setNotasConfirmacion(value);

            value = getClienteCodigoAlterno();
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

            value = getValorCampo(LINEA_PRODUCTO_CODIGO_ALTERNO, campos, mapNameToIndex);
            dto.setProductoCodigoAlterno(value);

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

            map.get(key).getLineas().add(dto);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected List<String> getCamposEsperados() {
        List<String> list = new ArrayList<>();

        list.add(TIPO_SERVICIO_CODIGO_ALTERNO.toString());
        list.add(NUMERO_CONSOLIDADO.toString());
        list.add(PREFIJO_NUMERO_ORDEN.toString());
        list.add(NUMERO_ORDEN.toString());

        list.add(DESTINATARIO_NUMERO_IDENTIFICACION.toString());
        list.add(CANAL_CODIGO_ALTERNO.toString());

        list.add(DESTINO_NOMBRE.toString());
        list.add(DESTINO_DIRECCION.toString());
        list.add(DESTINO_CIUDAD_NOMBRE_ALTERNO.toString());

        list.add(IGNORAR);
        list.add(FECHA_SUGERIDA_ENTREGA_MINIMA.toString());
        list.add(FECHA_SUGERIDA_ENTREGA_MAXIMA.toString());
        list.add(HORA_SUGERIDA_ENTREGA_MINIMA.toString());
        list.add(HORA_SUGERIDA_ENTREGA_MAXIMA.toString());

        list.add(NOTAS_APROBACION_CLIENTE.toString());

        list.add(LINEA_PRODUCTO_CODIGO_ALTERNO.toString());
        list.add(LINEA_PRODUCTO_DESCRIPCION.toString());
        list.add(LINEA_CANTIDAD_SOLICITADA.toString());
        list.add(LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO.toString());
        list.add(LINEA_BODEGA_DESTINO_CODIGO_ALTERNO.toString());
        list.add(LINEA_VALOR_DECLARADO_POR_UNIDAD.toString());

        return list;
    }

    @Override
    protected boolean generarEncabezadoConLosNombresDeLosCamposEsperados() {
        return true;
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
