package com.tacticlogistics.application.task.etl.components.tactic.oms.ordenes;

import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.CLIENTE_CODIGO;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.FECHA_ENTREGA_MAXIMA;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.FECHA_ENTREGA_MINIMA;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.HORA_ENTREGA_MAXIMA;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.HORA_ENTREGA_MINIMA;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.ID_CARGA;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.LINEA_BL;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.LINEA_BODEGA_DESTINO_CODIGO;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.LINEA_CANTIDAD_SOLICITADA;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.LINEA_ESTADO_DESTINO;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.LINEA_ESTAMPILLADO;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.LINEA_FONDOCUENTA;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.LINEA_LOTE;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.LINEA_PRODUCTO_CODIGO;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.LINEA_PRODUCTO_DESCRIPCION;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.NOTAS;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.NUMERO_CONSOLIDADO;
import static com.tacticlogistics.application.task.etl.MacroExcelOrdenDtoAtributos.NUMERO_ORDEN;

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

@Component("TACTIC.OMS.EXCEL.INGRESOS")
public class OrdenesDeIngresoCargadasPorExcel extends ETLOrdenesExcelFileStrategy {
    @Autowired
    private OrdenesApplicationService ordenesService;

    protected String getTipoServicioCodigo() {
        return "OC";
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected List<String> getCamposEsperados() {
        List<String> list = new ArrayList<>();

        list.add(CLIENTE_CODIGO.toString());
        list.add(NUMERO_CONSOLIDADO.toString());
        list.add(NUMERO_ORDEN.toString());
        
        //ORIGEN_CIUDAD_CODIGO
        //ORIGEN_DIRECCION
        
        list.add(FECHA_ENTREGA_MINIMA.toString());
        list.add(FECHA_ENTREGA_MAXIMA.toString());
        list.add(HORA_ENTREGA_MINIMA.toString());
        list.add(HORA_ENTREGA_MAXIMA.toString());
        list.add(NOTAS.toString());

        list.add(LINEA_PRODUCTO_DESCRIPCION.toString());
        list.add(LINEA_CANTIDAD_SOLICITADA.toString());

        list.add(LINEA_PRODUCTO_CODIGO.toString());
        //list.add(LINEA_UNIDAD_CODIGO_ALTERNO.toString());
        list.add(LINEA_BODEGA_DESTINO_CODIGO.toString());
        list.add(LINEA_ESTADO_DESTINO.toString());

        list.add(LINEA_LOTE.toString());
        list.add(LINEA_ESTAMPILLADO.toString());
        list.add(LINEA_BL.toString());
        list.add(LINEA_FONDOCUENTA.toString());
        
        list.add(ID_CARGA.toString());

        return list;
    }

    @Override
    // ---------------------------------------------------------------------------------------------------------------------------------------
    protected String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex) {
        String value;

        value = getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex).trim();

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

            dateValue = null;
            value = getValorCampo(FECHA_ENTREGA_MAXIMA, campos, mapNameToIndex);
            try {
                dateValue = Basic.toFecha(value, null, getFormatoFechaCorta());
            } catch (ParseException e) {
                logParseException(key, FECHA_ENTREGA_MAXIMA, value, getFormatoFechaCorta().toPattern());
            }
            dto.setFechaEntregaSugeridaMaxima(dateValue);

            dateValue = null;
            value = getValorCampo(FECHA_ENTREGA_MINIMA, campos, mapNameToIndex);
            try {
                dateValue = Basic.toFecha(value, dto.getFechaEntregaSugeridaMaxima(), getFormatoFechaCorta());
            } catch (ParseException e) {
                logParseException(key, FECHA_ENTREGA_MINIMA, value, getFormatoFechaCorta().toPattern());
            }
            dto.setFechaEntregaSugeridaMinima(dateValue);

            timeValue = null;
            value = getValorCampo(HORA_ENTREGA_MAXIMA, campos, mapNameToIndex);
            try {
                timeValue = Basic.toHora(value, null, getFormatoHoraHH());
            } catch (ParseException e) {
                logParseException(key, HORA_ENTREGA_MAXIMA, value, getFormatoHoraHH().toPattern());
            }
            dto.setHoraEntregaSugeridaMaxima(timeValue);

            timeValue = null;
            value = getValorCampo(HORA_ENTREGA_MINIMA, campos, mapNameToIndex);
            try {
                Time defaultTimeValue = Basic.toHora("05", null, getFormatoHoraHH());
                timeValue = Basic.toHora(value, defaultTimeValue, getFormatoHoraHH());
            } catch (ParseException e) {
                logParseException(key, HORA_ENTREGA_MINIMA, value, getFormatoHoraHH().toPattern());
            }
            dto.setHoraEntregaSugeridaMinima(timeValue);

            value = getValorCampo(NOTAS, campos, mapNameToIndex);
            dto.setNotasConfirmacion(value);

            value = getValorCampoUsuarioAprobacion(campos, mapNameToIndex);
            dto.setUsuarioConfirmacion(value);

            //value = getValorCampo(BODEGA_DESTINO_CODIGO, campos, mapNameToIndex);
            //dto.setBodegaDestinoCodigo(value);

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

            value = getValorCampo(LINEA_PRODUCTO_CODIGO.toString(), campos, mapNameToIndex);
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

            value = "UN";//getValorCampo(LINEA_UNIDAD_CODIGO_ALTERNO, campos, mapNameToIndex);
            dto.setUnidadCodigo(value);

            value = getValorCampo(LINEA_BODEGA_DESTINO_CODIGO, campos, mapNameToIndex);
            dto.setBodegaDestinoCodigo(value);
            dto.setBodegaDestinoCodigoAlterno(value);

            value = getValorCampo(LINEA_ESTADO_DESTINO, campos, mapNameToIndex);
            dto.setEstadoInventarioDestino(value);

            value = getValorCampo(LINEA_LOTE, campos, mapNameToIndex);
            dto.setLote(value);

            value = getValorCampo(LINEA_ESTAMPILLADO.toString(), campos, mapNameToIndex);
            dto.setRequerimientoEstampillado(value);
            
            value = getValorCampo(LINEA_BL.toString(), campos, mapNameToIndex);
            dto.setRequerimientoBl(value);

            value = getValorCampo(LINEA_FONDOCUENTA.toString(), campos, mapNameToIndex);
            dto.setRequerimientoFondoCuenta(value);

            map.get(key).getLineas().add(dto);
        }
    }

    protected String getValorCampoUsuarioAprobacion(String[] campos, Map<String, Integer> mapNameToIndex) {
        String value = getValorCampo(ID_CARGA, campos, mapNameToIndex).replace("INGRESOS_", "");
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
                Orden orden = ordenesService.saveOrdenDespachosSecundaria(dto);
                if(orden != null){
                    logInfo(dto.getNumeroOrden(), "", "OK");
                }else{
                    logWarning(dto.getNumeroOrden(), "", "Una  solicitud para el mismo cliente con el mismo numero ya se encuentra registrada.");
                }
            } catch (Exception e) {
                logError(dto.getNumeroOrden(), "", e.getMessage());
            }
        }
    }

}
