package com.tacticlogistics.application.task.etl.components.inverleoka;

import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINATARIO_NOMBRE;
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
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_LOTE;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_PREDISTRIBUCION_DESTINO_FINAL;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_PREDISTRIBUCION_ROTULO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_PRODUCTO_CODIGO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_PRODUCTO_DESCRIPCION;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NOTAS_APROBACION_CLIENTE;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NUMERO_CONSOLIDADO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NUMERO_ORDEN;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.PREFIJO_NUMERO_ORDEN;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.REQUIERE_RECAUDO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.VALOR_RECAUDO;
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
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.infrastructure.services.Basic;

@Component("INVERLEOKA.FACTURAS")
public class InverleokaFacturas extends ETLFlatFileStrategy<ETLOrdenDto> {
    @Autowired
    private OrdenesApplicationService ordenesService;

    // ---------------------------------------------------------------------------------------------------------------------------------------
    protected String getTipoServicioCodigoAlterno() {
        return "OVP-PRODUCTOS";
    }

    protected String getClienteCodigoAlterno() {
        return "INVERLEOKA";
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected String limpiar(String texto) {
        return super.limpiar(texto).replace('\r', ' ');
    }

    @Override
    protected List<String> getCamposEsperados() {
        List<String> list = new ArrayList<>();

        list.add(NUMERO_ORDEN.toString());

        list.add(DESTINATARIO_NUMERO_IDENTIFICACION.toString());
        list.add(DESTINATARIO_NOMBRE.toString());
        list.add(DESTINO_CIUDAD_NOMBRE_ALTERNO.toString());
        list.add(DESTINO_DIRECCION.toString());

        list.add(LINEA_PRODUCTO_CODIGO.toString());
        list.add(LINEA_PRODUCTO_DESCRIPCION.toString());
        list.add(LINEA_CANTIDAD_SOLICITADA.toString());
        list.add(LINEA_LOTE.toString());

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

            dto.setClienteCodigo(getClienteCodigoAlterno());
            dto.setTipoServicioCodigoAlterno(getTipoServicioCodigoAlterno());

            value = getValorCampo(NUMERO_CONSOLIDADO, campos, mapNameToIndex);
            dto.setNumeroConsolidado(value);

            dto.setNumeroOrden(key);

            value = getValorCampo(DESTINATARIO_NUMERO_IDENTIFICACION, campos, mapNameToIndex);
            dto.setDestinatarioNumeroIdentificacion(value);

//            value = GWSMaestroClientes
//                    .decodeCanalCodigoAlterno(getValorCampo(SEGMENTO_CODIGO_ALTERNO, campos, mapNameToIndex));
//            dto.setCanalCodigoAlterno(value);

            value = getValorCampo(DESTINO_NOMBRE, campos, mapNameToIndex);
            dto.setDestinoNombre(value);

            value = getValorCampo(DESTINO_DIRECCION, campos, mapNameToIndex);
            dto.setDestinoDireccion(value);

//            value = GWSMaestroClientes
//                    .decodeCiudadNombreAlterno(getValorCampo(DESTINO_CIUDAD_NOMBRE_ALTERNO, campos, mapNameToIndex));
//            dto.setDestinoCiudadNombreAlterno(value);

            dateValue = null;
            value = getValorCampo(FECHA_SUGERIDA_ENTREGA_MINIMA, campos, mapNameToIndex);
            value = substringSafe(value, 0, 10);
            try {
                dateValue = Basic.toFecha(value, null, getFormatoFechaCorta());
            } catch (ParseException e) {
                logParseException(key, FECHA_SUGERIDA_ENTREGA_MINIMA, value, getFormatoFechaCorta().toPattern());
            }
            dto.setFechaEntregaSugeridaMinima(dateValue);

            dateValue = null;
            value = getValorCampo(FECHA_SUGERIDA_ENTREGA_MAXIMA, campos, mapNameToIndex);
            value = substringSafe(value, 0, 10);
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
            value = substringSafe(value, 0, 5);
            try {
                timeValue = Basic.toHora(value, null, getFormatoHoraHHmm());
            } catch (ParseException e) {
                logParseException(key, HORA_SUGERIDA_ENTREGA_MAXIMA, value, getFormatoHoraHHmm().toPattern());
            }
            dto.setHoraEntregaSugeridaMaxima(timeValue);

            value = getValorCampo(NOTAS_APROBACION_CLIENTE, campos, mapNameToIndex);
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

            value = getValorCampo(LINEA_PRODUCTO_CODIGO, campos, mapNameToIndex);
            dto.setProductoCodigo(value);

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

//            integerValue = null;
//            value = getValorCampo(LINEA_VALOR_DECLARADO_POR_UNIDAD, campos, mapNameToIndex);
//            value = value.replaceAll("[$\\s]+", "");
//            try {
//                integerValue = Basic.toEntero(value, null, getCantidadSolicitadaFormat());
//            } catch (ParseException e) {
//                logParseException(key, LINEA_VALOR_DECLARADO_POR_UNIDAD, value,
//                        getCantidadSolicitadaFormat().toPattern());
//            }
//            dto.setValorDeclaradoPorUnidad(integerValue);

            value = getValorCampo(LINEA_PREDISTRIBUCION_DESTINO_FINAL, campos, mapNameToIndex);
            dto.setPredistribucionDestinoFinal(value);

            value = getValorCampo(LINEA_PREDISTRIBUCION_ROTULO, campos, mapNameToIndex);
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
                if(dto.getNumeroOrden().contains("FAC_BAQ-1013390")){
                    System.out.println("");
                }
                if(dto.getNumeroOrden().contains("FAC_BAQ-1013393")){
                    System.out.println("");
                }
                
                Orden orden = ordenesService.saveOrden2(dto);
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
