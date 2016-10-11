package com.tacticlogistics.application.tasks.etl.components.dicermex;

import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.LINEA_BODEGA_DESTINO_CODIGO_ALTERNO;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.LINEA_CANTIDAD_SOLICITADA;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.LINEA_PRODUCTO_CODIGO;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.NUMERO_ORDEN;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.PREFIJO_NUMERO_ORDEN;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacticlogistics.application.dto.etl.ETLLineaOrdenDto;
import com.tacticlogistics.application.tasks.etl.components.ETLFlatFileStrategy;
import com.tacticlogistics.application.tasks.etl.readers.CharsetDetectorFileReader;
import com.tacticlogistics.application.tasks.etl.readers.Reader;
import com.tacticlogistics.infrastructure.services.Basic;

@Component
public class DICERMEXLineasFactura extends ETLFlatFileStrategy<List<ETLLineaOrdenDto>> {

    private static final Logger log = LoggerFactory.getLogger(DICERMEXLineasFactura.class);

	@Autowired
	private CharsetDetectorFileReader reader;

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected Reader<File, String> getReader() {
        return reader;
    }

    public Map<String, List<ETLLineaOrdenDto>> procesarLineas(File file) {
        Map<String, List<ETLLineaOrdenDto>> map = new HashMap<>();

        setArchivo(file);
        setMensajes(null);
        log.info("\n");
        log.info("-------------------------------------------------------------------------------------------------");
        log.info("INICIO {}", getArchivo().getName());
        log.info("-------------------------------------------------------------------------------------------------");

        boolean procesar = false;
        try {
            procesar = preProcesarArchivo();
        } catch (RuntimeException e) {
            onPreProcesarArchivoError(e);
            return null;
        }

        if (procesar) {
            try {
                map = preCargar(transformar(preTransformar(limpiar(extraer()))));
            } catch (RuntimeException e) {
                onProcesarArchivoError(e);
            }

            try {
                postProcesarArchivo();
            } catch (RuntimeException e) {
                onPostProcesarArchivoError(e);
            }

            try {
                backUp();
            } catch (RuntimeException e) {
                log.error("Durante backUp()", e);
            }

        }

        log.info("-------------------------------------------------------------------------------------------------");
        log.info("FIN {}", getArchivo().getName());
        log.info("-------------------------------------------------------------------------------------------------");
        log.info("\n");

        setMensajes(null);
        setArchivo(null);

        return map;
    }

    @Override
	protected String limpiar(String texto) {
    	return super.limpiar(texto).replace('\r', ' ');
	}

	@Override
    protected List<String> getCamposEsperados() {
        List<String> list = new ArrayList<>();

        list.add(PREFIJO_NUMERO_ORDEN.toString());
        list.add(NUMERO_ORDEN.toString());

        list.add(LINEA_PRODUCTO_CODIGO.toString());
        list.add(LINEA_CANTIDAD_SOLICITADA.toString());
        list.add(IGNORAR.toString());
        list.add(LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO.toString());
        list.add(LINEA_BODEGA_DESTINO_CODIGO_ALTERNO.toString());

        return list;
    }

    @Override
    protected boolean generarEncabezadoConLosNombresDeLosCamposEsperados() {
        return true;
    }

    @Override
    protected String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex) {
        StringBuffer sb = new StringBuffer();

        sb.append(getValorCampo(PREFIJO_NUMERO_ORDEN, campos, mapNameToIndex)).append("-")
                .append(getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex));

        return sb.toString();
    }

    @Override
    protected void adicionar(String key, Map<String, List<ETLLineaOrdenDto>> map, String[] campos,
            Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {

        if (!map.containsKey(key)) {
            List<ETLLineaOrdenDto> list = new ArrayList<>();
            map.put(key, list);
        }
    }

    @Override
    protected void modificar(String key, Map<String, List<ETLLineaOrdenDto>> map, String[] campos,
            Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {
        if (map.containsKey(key)) {
            String value;
            Integer integerValue;

            ETLLineaOrdenDto dto = new ETLLineaOrdenDto();

            value = getValorCampo(LINEA_PRODUCTO_CODIGO, campos, mapNameToIndex);
            dto.setProductoCodigo(value);

            integerValue = null;
            value = getValorCampo(LINEA_CANTIDAD_SOLICITADA, campos, mapNameToIndex);
            try {
                integerValue = Basic.toEntero(value, null, getFormatoEntero());
            } catch (ParseException e) {
                logParseException(key, LINEA_CANTIDAD_SOLICITADA, value, getFormatoEntero().toPattern());
            }
            dto.setCantidadSolicitada(integerValue);

            dto.setUnidadCodigo("UN");
            //value = getValorCampo(LINEA_UNIDAD_CODIGO_ALTERNO, campos, mapNameToIndex);
            //dto.setUnidadCodigoAlterno(value);

            value = getValorCampo(LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO, campos, mapNameToIndex);
            dto.setBodegaOrigenCodigoAlterno(value);

            value = getValorCampo(LINEA_BODEGA_DESTINO_CODIGO_ALTERNO, campos, mapNameToIndex);
            dto.setBodegaDestinoCodigoAlterno(value);

            map.get(key).add(dto);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void cargar(Map<String, List<ETLLineaOrdenDto>> map) {

    }
}
