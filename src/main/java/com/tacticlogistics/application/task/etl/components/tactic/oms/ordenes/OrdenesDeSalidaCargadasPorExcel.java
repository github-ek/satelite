package com.tacticlogistics.application.task.etl.components.tactic.oms.ordenes;

import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.CLIENTE_CODIGO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINATARIO_NOMBRE;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINATARIO_NUMERO_IDENTIFICACION;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_CIUDAD_NOMBRE_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_CONTACTO_EMAIL;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_CONTACTO_NOMBRES;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_CONTACTO_TELEFONOS;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_DIRECCION;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_NOMBRE;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.FECHA_SUGERIDA_ENTREGA_MAXIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.FECHA_SUGERIDA_ENTREGA_MINIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.HORA_SUGERIDA_ENTREGA_MAXIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.HORA_SUGERIDA_ENTREGA_MINIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.ID_CARGA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_ESTADO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_CANTIDAD_SOLICITADA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_ESTAMPILLADO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_LOTE;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_PRODUCTO_CODIGO_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_PRODUCTO_DESCRIPCION;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_UNIDAD_CODIGO_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.LINEA_VALOR_DECLARADO_POR_UNIDAD;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NOTAS_APROBACION_CLIENTE;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NUMERO_CONSOLIDADO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NUMERO_ORDEN;

import java.sql.Time;
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
import com.tacticlogistics.application.task.etl.components.ETLOrdenesExcelFileStrategy;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.infrastructure.services.Basic;

@Component("TACTIC.OMS.EXCEL.SALIDAS")
public class OrdenesDeSalidaCargadasPorExcel extends ETLOrdenesExcelFileStrategy {
    @Autowired
    private OrdenesApplicationService ordenesService;

    // ---------------------------------------------------------------------------------------------------------------------------------------
    protected String getTipoServicioCodigo() {
        return "OVP-PRODUCTOS";
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected List<String> getCamposEsperados() {
        List<String> list = new ArrayList<>();

        list.add(CLIENTE_CODIGO.toString());
        list.add(NUMERO_CONSOLIDADO.toString());
        list.add(NUMERO_ORDEN.toString());

        list.add(DESTINATARIO_NUMERO_IDENTIFICACION.toString());
        list.add(DESTINATARIO_NOMBRE.toString());
        list.add(DESTINO_CIUDAD_NOMBRE_ALTERNO.toString());
        list.add(DESTINO_DIRECCION.toString());
        list.add(DESTINO_NOMBRE.toString());
        list.add(DESTINO_CONTACTO_NOMBRES.toString());
        list.add(DESTINO_CONTACTO_TELEFONOS.toString());
        list.add(DESTINO_CONTACTO_EMAIL.toString());

        list.add(FECHA_SUGERIDA_ENTREGA_MINIMA.toString());
        list.add(FECHA_SUGERIDA_ENTREGA_MAXIMA.toString());
        list.add(HORA_SUGERIDA_ENTREGA_MINIMA.toString());
        list.add(HORA_SUGERIDA_ENTREGA_MAXIMA.toString());
        list.add(NOTAS_APROBACION_CLIENTE.toString());

        list.add(LINEA_PRODUCTO_CODIGO_ALTERNO.toString());
        list.add(LINEA_PRODUCTO_DESCRIPCION.toString());
        list.add(LINEA_CANTIDAD_SOLICITADA.toString());
        list.add(LINEA_UNIDAD_CODIGO_ALTERNO.toString());
        list.add(LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO.toString());
        list.add(LINEA_ESTADO.toString());
        
        list.add(LINEA_LOTE.toString());
        list.add(LINEA_ESTAMPILLADO.toString());

        list.add(LINEA_VALOR_DECLARADO_POR_UNIDAD.toString());

        list.add(ID_CARGA.toString());

        return list;
    }

    @Override
    // ---------------------------------------------------------------------------------------------------------------------------------------
    protected String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex) {
        String value;

        value = getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex).trim();

        if (value.isEmpty()) {
            StringBuffer sb = new StringBuffer();

            sb.append(getValorCampo(CLIENTE_CODIGO, campos, mapNameToIndex)).append("-")
                    .append(getValorCampo(DESTINATARIO_NUMERO_IDENTIFICACION, campos, mapNameToIndex)).append("-")
                    .append(getValorCampo(DESTINO_CIUDAD_NOMBRE_ALTERNO, campos, mapNameToIndex)).append("-")
                    .append(getValorCampo(DESTINO_DIRECCION, campos, mapNameToIndex)).append("-")
                    .append(getValorCampo(FECHA_SUGERIDA_ENTREGA_MAXIMA, campos, mapNameToIndex));

            value = sb.toString();
        }

        return value;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void adicionar(String key, Map<String, ETLOrdenDto> map, String[] campos,
            Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {

        if (!map.containsKey(key)) {
            String value;
            Date dateValue;
            Time timeValue;

            ETLOrdenDto dto = new ETLOrdenDto();

            value = getValorCampo(CLIENTE_CODIGO, campos, mapNameToIndex);
            dto.setClienteCodigo(value);

            dto.setTipoServicioCodigo(getTipoServicioCodigo());
            dto.setTipoServicioCodigoAlterno(getTipoServicioCodigo());

            value = getValorCampo(NUMERO_CONSOLIDADO, campos, mapNameToIndex);
            dto.setNumeroConsolidado(value);

            value = getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex);
            dto.setNumeroOrden(value);

            value = getValorCampo(DESTINATARIO_NUMERO_IDENTIFICACION, campos, mapNameToIndex);
            dto.setDestinatarioNumeroIdentificacion(value);

            value = getValorCampo(DESTINATARIO_NOMBRE, campos, mapNameToIndex);
            dto.setDestinatarioNombre(value);

            value = getValorCampo(DESTINO_CIUDAD_NOMBRE_ALTERNO, campos, mapNameToIndex);
            dto.setDestinoCiudadNombreAlterno(value);

            value = getValorCampo(DESTINO_DIRECCION, campos, mapNameToIndex);
            dto.setDestinoDireccion(value);

            dateValue = null;
            value = getValorCampo(FECHA_SUGERIDA_ENTREGA_MAXIMA, campos, mapNameToIndex);
            try {
                dateValue = Basic.toFecha(value, null, getFormatoFechaCorta());
            } catch (ParseException e) {
                logParseException(key, FECHA_SUGERIDA_ENTREGA_MAXIMA, value, getFormatoFechaCorta().toPattern());
            }
            dto.setFechaEntregaSugeridaMaxima(dateValue);

            dateValue = null;
            value = getValorCampo(FECHA_SUGERIDA_ENTREGA_MINIMA, campos, mapNameToIndex);
            try {
                dateValue = Basic.toFecha(value, dto.getFechaEntregaSugeridaMaxima(), getFormatoFechaCorta());
            } catch (ParseException e) {
                logParseException(key, FECHA_SUGERIDA_ENTREGA_MINIMA, value, getFormatoFechaCorta().toPattern());
            }
            dto.setFechaEntregaSugeridaMinima(dateValue);

            timeValue = null;
            value = getValorCampo(HORA_SUGERIDA_ENTREGA_MAXIMA, campos, mapNameToIndex);
            try {
                timeValue = Basic.toHora(value, null, getFormatoHoraHH());
            } catch (ParseException e) {
                logParseException(key, HORA_SUGERIDA_ENTREGA_MAXIMA, value, getFormatoHoraHH().toPattern());
            }
            dto.setHoraEntregaSugeridaMaxima(timeValue);

            timeValue = null;
            value = getValorCampo(HORA_SUGERIDA_ENTREGA_MINIMA, campos, mapNameToIndex);
            try {
                Time defaultTimeValue = Basic.toHora("05", null, getFormatoHoraHH());
                timeValue = Basic.toHora(value, defaultTimeValue, getFormatoHoraHH());
            } catch (ParseException e) {
                logParseException(key, HORA_SUGERIDA_ENTREGA_MINIMA, value, getFormatoHoraHH().toPattern());
            }
            dto.setHoraEntregaSugeridaMinima(timeValue);

            value = getValorCampo(NOTAS_APROBACION_CLIENTE, campos, mapNameToIndex);
            dto.setNotasConfirmacion(value);

            value = getValorCampoUsuarioAprobacion(campos, mapNameToIndex);
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

            value = getValorCampo(LINEA_PRODUCTO_CODIGO_ALTERNO.toString(), campos, mapNameToIndex);
            value = value.replaceFirst("^[`'´]", "");
            dto.setProductoCodigo(value);

            value = getValorCampo(LINEA_PRODUCTO_DESCRIPCION.toString(), campos, mapNameToIndex);
            dto.setDescripcion(value);

            integerValue = null;
            value = getValorCampo(LINEA_CANTIDAD_SOLICITADA, campos, mapNameToIndex);
            try {
                integerValue = Basic.toEntero(value, null, getFormatoEntero());
            } catch (ParseException e) {
                logParseException(key, LINEA_CANTIDAD_SOLICITADA, value, getFormatoEntero().toPattern());
            }
            dto.setCantidadSolicitada(integerValue);

            value = getValorCampo(LINEA_UNIDAD_CODIGO_ALTERNO, campos, mapNameToIndex);
            dto.setUnidadCodigo(value);

            value = getValorCampo(LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO, campos, mapNameToIndex);
            dto.setBodegaOrigenCodigo(value);
            dto.setBodegaOrigenCodigoAlterno(value);

            value = getValorCampo(LINEA_ESTADO, campos, mapNameToIndex);
            dto.setEstadoInventarioOrigen(value);

            value = getValorCampo(LINEA_LOTE, campos, mapNameToIndex);
            dto.setLote(value);

            value = getValorCampo(LINEA_ESTAMPILLADO.toString(), campos, mapNameToIndex);
            dto.setRequerimientoEstampillado(value);

            integerValue = null;
            value = getValorCampo(LINEA_VALOR_DECLARADO_POR_UNIDAD, campos, mapNameToIndex);
            value = value.replaceAll("[$\\s]+", "");
            try {
                integerValue = Basic.toEntero(value, null, getFormatoModeda());
            } catch (ParseException e) {
                logParseException(key, LINEA_VALOR_DECLARADO_POR_UNIDAD, value, getFormatoModeda().toPattern());
            }
            dto.setValorDeclaradoPorUnidad(integerValue);

            map.get(key).getLineas().add(dto);
        }
    }

    protected String getValorCampoUsuarioAprobacion(String[] campos, Map<String, Integer> mapNameToIndex) {
        String value = getValorCampo(ID_CARGA, campos, mapNameToIndex);
        String list[] = getArchivo().getName().split("_");
        if (list.length >= 3) {
            value = list[2] + "(" + value + ")";
        }
        return value;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    @Transactional(readOnly = false)
    protected void cargar(Map<String, ETLOrdenDto> map) {
        for (Entry<String, ETLOrdenDto> entry : map.entrySet()) {
            ETLOrdenDto dto = entry.getValue();
            try {
                Orden orden = ordenesService.saveOrden2(dto);
                if (orden != null) {
                    logInfo(dto.getNumeroOrden(), "", "OK");
                } else {
                    logWarning(dto.getNumeroOrden(), "",
                            "Una  solicitud para el mismo cliente con el mismo numero ya se encuentra registrada.");
                }
            } catch (Exception e) {
                logError(dto.getNumeroOrden(), "", e.getMessage());
            }
        }
    }

}
