package com.tacticlogistics.application.task.etl.components.tactic.tms.rutas;

import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.FECHA_ENTREGA_SUGERIDA_MAXIMA;
import static com.tacticlogistics.infrastructure.services.Basic.substringSafe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto.LineaRutaDto;
import com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto.RutaDto;
import com.tacticlogistics.infrastructure.services.Basic;

@Component("TACTIC.TMS.RUTAS.MANUALES")
public class RutasManuales extends Rutas {
    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected String getSeparadorCampos() {
        return ";";
    }

    @Override
    protected boolean generarEncabezadoConLosNombresDeLosCamposEsperados() {
        return true;
    }

    @Override
    protected List<String> getCamposEsperados() {
        List<String> list = new ArrayList<>();

        list.add(IDENTIFICADOR_MOVIL);
        list.add(IDENTIFICADOR_ENTREGA);
        list.add(DESTINATARIO_NOMBRE);
        list.add(DESTINO_DIRECCION);
        list.add(CLIENTE_NUMERO_IDENTIFICACION);
        list.add(SECUENCIA);
        list.add(LONGITUD);
        list.add(LATITUD);
        list.add(FECHA_HORA_ENTREGA);

        return list;
    }

    @Override
    protected boolean ignorarRegistroDespuesDeSerSeparadoPorCampos(String[] campos,
            Map<String, Integer> mapNameToIndex) {
        if (super.ignorarRegistroDespuesDeSerSeparadoPorCampos(campos, mapNameToIndex)) {
            return true;
        }
        String value;
        value = getValorCampo(IDENTIFICADOR_MOVIL, campos, mapNameToIndex);

        if (value.isEmpty()) {
            return true;
        }

        return false;
    }

    @Override
    protected String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex) {
        StringBuffer sb = new StringBuffer();
        sb.append(getValorCampo(IDENTIFICADOR_MOVIL, campos, mapNameToIndex));

        return sb.toString().toLowerCase();
    }

    @Override
    protected void cargarDestinoNombre(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex) {
        String value;
        value = getValorCampo(DESTINATARIO_NOMBRE, campos, mapNameToIndex);
        dto.setDestinoNombre(value);
    }

    @Override
    protected void cargarDestinoDireccion(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex) {
        String value;
        value = getValorCampo(DESTINO_DIRECCION, campos, mapNameToIndex);
        dto.setDestinoDireccion(value);
    }
    
    @Override
    protected void cargarDatosCliente(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex) {
        String value;
        value = getValorCampo(CLIENTE_NUMERO_IDENTIFICACION, campos, mapNameToIndex);
        dto.setClienteNumeroIdentificacion(value);
    }

    @Override
    protected void cargarDatosVehiculo(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex) {
        String value;
        value = getValorCampo(DESTINO_DIRECCION, campos, mapNameToIndex);
        dto.setDestinoDireccion(value);
    }

    @Override
    protected void cargarFechaHoraEntrega(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex) {
        String value;
        Date dateValue;
        
        dateValue = null;
        value = getValorCampo(FECHA_HORA_ENTREGA, campos, mapNameToIndex);
        value = substringSafe(value, 0, 10);
        try {
            dateValue = Basic.toFecha(value, null, getFormatoFechaCorta());
        } catch (ParseException e) {
            logParseException(key, FECHA_ENTREGA_SUGERIDA_MAXIMA, value, getFormatoFechaCorta().toPattern());
        }
        dto.setFechaFinal(dateValue);
        dto.setFechaInicial(dateValue);

        value = getValorCampo(FECHA_HORA_ENTREGA, campos, mapNameToIndex);
        value = substringSafe(value, 11, 16);
        dto.setHora(value);
    }
    
    private SimpleDateFormat formatoFechaCorta = null;

    @Override
    protected SimpleDateFormat getFormatoFechaCorta() {
        if (formatoFechaCorta == null) {
            formatoFechaCorta = new SimpleDateFormat("yyyy-MM-dd");
        }
        return formatoFechaCorta;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void configurarCliente(LineaRutaDto[] lineas, Map<String, String> mapClientes) {
        for (int i = 0; i < lineas.length; i++) {
            for (Entry<String, String> keyValue : mapClientes.entrySet()) {
                if (keyValue.getKey().equals(lineas[i].getClienteNumeroIdentificacion())) {
                    lineas[i].setClienteCodigo(keyValue.getKey());
                    lineas[i].setClienteNumeroIdentificacion(keyValue.getValue());
                    break;
                } else {
                    if (keyValue.getValue().equals(lineas[i].getClienteNumeroIdentificacion())) {
                        lineas[i].setClienteCodigo(keyValue.getKey());
                        lineas[i].setClienteNumeroIdentificacion(keyValue.getValue());
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    protected void configurarIdentificadorMovil(RutaDto ruta, Map<String, String> vehiculos) {
        for (Entry<String, String> keyValue : vehiculos.entrySet()) {
            if (keyValue.getValue().equals(ruta.getIdentificadorMovil())) {
                String value = keyValue.getKey();
                ruta.getLineas().forEach(a -> {
                    a.setIdentificadorVehiculo(value);
                });
                break;
            }
        }
    }
}
