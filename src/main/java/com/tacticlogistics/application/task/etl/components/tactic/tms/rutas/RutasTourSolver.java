package com.tacticlogistics.application.task.etl.components.tactic.tms.rutas;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto.LineaRutaDto;
import com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto.RutaDto;
import com.tacticlogistics.application.task.etl.readers.ExcelWorkSheetReader;
import com.tacticlogistics.application.task.etl.readers.Reader;
import com.tacticlogistics.infrastructure.services.Basic;

@Component("TACTIC.TMS.RUTAS.TOURSOLVER")
public class RutasTourSolver extends Rutas {
    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Autowired
    private ExcelWorkSheetReader reader;

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public Pattern getPattern() {
        return PATTERN_XLS;
    }

    @Override
    protected Reader<File, String> getReader() {
        return reader;
    }

    @Override
    protected List<String> getCamposEsperados() {
        List<String> list = new ArrayList<>();

        list.add(IDENTIFICADOR_VEHICULO);
        list.add(SECUENCIA);
        list.add(LATITUD);
        list.add(LONGITUD);
        list.add(IDENTIFICADOR_ENTREGA);
        list.add(HORA);
        list.add(NUMERO_DOCUMENTO_ENTREGA);
        list.add(ENTREGA_ID);
        list.add(DESTINATARIO_NOMBRE);
        list.add(DESTINO_DIRECCION);
        list.add(DESTINO_BARRIO);
        list.add(DESTINO_NOMBRE);
        list.add(FECHA_ENTREGA_PLANEADA);
        list.add(CLIENTE_CODIGO);

        return list;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void preProcesarDirectorio() {
        super.preProcesarDirectorio();
        ((ExcelWorkSheetReader) getReader()).setWorkSheetName("Informe");
    }

    @Override
    protected boolean ignorarRegistroDespuesDeSerSeparadoPorCampos(String[] campos,
            Map<String, Integer> mapNameToIndex) {
        if (super.ignorarRegistroDespuesDeSerSeparadoPorCampos(campos, mapNameToIndex)) {
            return true;
        }
        String value;
        value = getValorCampo(IDENTIFICADOR_ENTREGA, campos, mapNameToIndex);

        if (value.isEmpty() || value.equals("INICIO") || value.equals("ESPERA") || value.equals("FIN")) {
            return true;
        }

        return false;
    }

    @Override
    protected String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex) {
        StringBuffer sb = new StringBuffer();
        sb.append(getValorCampo(IDENTIFICADOR_VEHICULO, campos, mapNameToIndex));

        return sb.toString().toLowerCase();
    }

    @Override
    protected void cargarDestinoNombre(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex) {
        String value;
        StringBuffer sb;

        sb = new StringBuffer(getValorCampo(DESTINATARIO_NOMBRE, campos, mapNameToIndex));
        value = getValorCampo(DESTINO_NOMBRE, campos, mapNameToIndex);
        if (sb.length() + value.length() > 0) {
            sb.append(" - ");
        }
        sb.append(value);
        dto.setDestinoNombre(sb.toString());
    }

    @Override
    protected void cargarDestinoDireccion(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex) {
        String value;
        StringBuffer sb;

        sb = new StringBuffer(getValorCampo(DESTINO_DIRECCION, campos, mapNameToIndex));
        value = getValorCampo(DESTINO_BARRIO, campos, mapNameToIndex);
        if (sb.length() + value.length() > 0) {
            sb.append(" - ");
        }
        sb.append(value);
        dto.setDestinoDireccion(sb.toString());
    }
    
    @Override
    protected void cargarDatosCliente(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex) {
        String value;
        value = getValorCampo(CLIENTE_CODIGO, campos, mapNameToIndex);
        dto.setClienteCodigo(value);
    }

    @Override
    protected void cargarDatosVehiculo(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex) {
        String value;
        value = getValorCampo(IDENTIFICADOR_VEHICULO, campos, mapNameToIndex);
        dto.setIdentificadorVehiculo(value);
    }

    @Override
    protected void cargarFechaHoraEntrega(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex) {
        String value;
        Float floatValue;
        Date dateValue;
        floatValue = null;

        value = getValorCampo(HORA, campos, mapNameToIndex);
        value = value.replace(",", ".");
        try {
            DateFormat format = new SimpleDateFormat("HH:mm");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            floatValue = getFormatoCoordenada().parse(value).floatValue();
            if (floatValue >= 1.0) {
                throw new RuntimeException("La hora suminitrada supera las 24 horas");
            }
            Date time = new Date((long) ((24L * 60L * 60L * 1000L) * floatValue));
            value = format.format(time);
        } catch (ParseException e) {
            value = "";
            logParseException(key, HORA, value, getFormatoHoraHHmm().toPattern());
        } catch (RuntimeException e) {
            value = "";
            logParseException(key, HORA, value, getFormatoHoraHHmm().toPattern());
        }
        dto.setHora(value);

        dateValue = null;
        value = getValorCampo(FECHA_ENTREGA_PLANEADA, campos, mapNameToIndex);
        try {
            dateValue = Basic.toFecha(value, null, getFormatoFechaCorta());
        } catch (ParseException e) {
            logParseException(key, FECHA_ENTREGA_PLANEADA, value, getFormatoFechaCorta().toPattern());
        }
        dto.setFechaFinal(dateValue);
        dto.setFechaInicial(dateValue);
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
                if (keyValue.getKey().equals(lineas[i].getClienteCodigo())) {
                    lineas[i].setClienteCodigo(keyValue.getKey());
                    lineas[i].setClienteNumeroIdentificacion(keyValue.getValue());
                    break;
                } else {
                    if (keyValue.getValue().equals(lineas[i].getClienteCodigo())) {
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
        String numeroPlaca = ruta.getIdentificadorMovil().toUpperCase();
        if (vehiculos.containsKey(numeroPlaca)) {
            ruta.setIdentificadorMovil(vehiculos.get(numeroPlaca));
        }
    }
}
