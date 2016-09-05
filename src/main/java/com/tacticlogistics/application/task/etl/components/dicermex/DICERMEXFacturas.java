package com.tacticlogistics.application.task.etl.components.dicermex;

import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINATARIO_CANAL_CODIGO_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINATARIO_IDENTIFICACION;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINATARIO_NOMBRE;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_CONTACTO_EMAIL;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_CONTACTO_TELEFONOS;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_DIRECCION;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_NOMBRE;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NOTAS;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.NUMERO_ORDEN;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.PREFIJO_NUMERO_ORDEN;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.REQUERIMIENTOS_DISTRIBUCION;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.FECHA_MAXIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.FECHA_MINIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.HORA_MAXIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.HORA_MINIMA;

import java.io.File;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.etl.ETLLineaOrdenDto;
import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.application.services.ordenes.OrdenesApplicationService;
import com.tacticlogistics.application.task.etl.components.ETLFlatFileStrategy;
import com.tacticlogistics.application.task.etl.readers.FlatFileReaderUTF16;
import com.tacticlogistics.application.task.etl.readers.Reader;
import com.tacticlogistics.domain.model.calendario.Calendario;
import com.tacticlogistics.domain.model.calendario.DiaDeSemanaType;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.infrastructure.persistence.calendario.CalendarioRepository;
import com.tacticlogistics.infrastructure.services.Basic;

import ch.qos.logback.classic.Logger;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.DESTINO_CIUDAD_NOMBRE_ALTERNO;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.FECHA_MAXIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.FECHA_MINIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.HORA_MAXIMA;
import static com.tacticlogistics.application.task.etl.OrdenDtoAtributos.HORA_MINIMA;

@Component("DICERMEX.")
public class DICERMEXFacturas extends ETLFlatFileStrategy<ETLOrdenDto> {
    private static final Logger log = (Logger) LoggerFactory.getLogger(DICERMEXFacturas.class);

    private static final Pattern PATTERN = Pattern
            .compile("(?i:((\\d{8})-(\\d{4})\\.)?ESBOrdenesDeDespacho(Parcial)?_(\\d+)\\.txt)");

    @Autowired
    private FlatFileReaderUTF16 reader;

    @Autowired
    private OrdenesApplicationService ordenesService;

    @Autowired
    private DICERMEXLineasFactura lineasOrdenStrategy;

    // ---------------------------------------------------------------------------------------------------------------------------------------
    protected String getTipoServicioCodigo() {
        return "OVP-PRODUCTOS";
    }

    protected String getClienteCodigo() {
        return "DICERMEX";
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    @Override
    protected Reader<File, String> getReader() {
        return reader;
    }

    protected File getArchivoLineas() {
        return new File(
                getArchivo().getParent() + "\\" + getArchivo().getName().replaceAll("(?i:\\.txt)", "_DETALLE.txt"));
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void preProcesarDirectorio() {
        super.preProcesarDirectorio();
        getDiasCalendario();
    }

    @Override
    protected boolean preProcesarArchivo() {
        File archivo = getArchivoLineas();
        boolean procesar = archivo.exists();

        if (!procesar) {
            log.warn("No se encontró el archivo {}", archivo.getName());
        }

        if (procesar) {
            procesar = procesar && super.preProcesarArchivo();
        }

        return procesar;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected List<String> getCamposEsperados() {
        List<String> list = new ArrayList<>();

        list.add(IGNORAR);
        list.add(NOTAS.toString());

        list.add(PREFIJO_NUMERO_ORDEN.toString());
        list.add(NUMERO_ORDEN.toString());

        list.add(DESTINATARIO_IDENTIFICACION.toString());
        list.add(IGNORAR);
        list.add(DESTINATARIO_NOMBRE.toString());

        list.add(IGNORAR);
        list.add(DESTINO_NOMBRE.toString());

        list.add(DESTINO_CONTACTO_TELEFONOS.toString());
        list.add(DESTINO_CONTACTO_EMAIL.toString());

        list.add(DESTINO_DIRECCION.toString());
        list.add(DESTINO_CIUDAD_NOMBRE_ALTERNO.toString());

        list.add(REQUERIMIENTOS_DISTRIBUCION.toString());
        list.add(IGNORAR);
        list.add(DESTINATARIO_CANAL_CODIGO_ALTERNO.toString());

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
    protected void limpiarCampos(String[] campos, Map<String, Integer> mapNameToIndex) {
        super.limpiarCampos(campos, mapNameToIndex);

        for (int i = 0; i < campos.length; i++) {
            if (campos[i].equals("***")) {
                campos[i] = "";
            }
        }
    }

    @Override
    protected void adicionar(String key, Map<String, ETLOrdenDto> map, String[] campos,
            Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {

        if (!map.containsKey(key)) {
            String value;

            ETLOrdenDto dto = new ETLOrdenDto();

            dto.setTipoServicioCodigo(getTipoServicioCodigo());
            dto.setClienteCodigo(getClienteCodigo());

            value = getValorCampo(NOTAS, campos, mapNameToIndex);
            dto.setNotasConfirmacion(value);

            dto.setNumeroOrden(key);

            value = getValorCampo(DESTINATARIO_IDENTIFICACION, campos, mapNameToIndex);
            dto.setDestinatarioNumeroIdentificacion(value);

            value = getValorCampo(DESTINATARIO_NOMBRE, campos, mapNameToIndex);
            dto.setDestinatarioNombre(value);

            value = getValorCampo(DESTINO_NOMBRE, campos, mapNameToIndex);
            dto.setDestinoNombre(value);

            value = getValorCampo(DESTINO_CONTACTO_TELEFONOS, campos, mapNameToIndex);
            dto.setDestinoContactoTelefonos(value);

            value = getValorCampo(DESTINO_CONTACTO_EMAIL, campos, mapNameToIndex);
            dto.setDestinoContactoEmail(value);

            value = getValorCampo(DESTINO_DIRECCION, campos, mapNameToIndex);
            dto.setDestinoDireccion(value);

            value = getValorCampo(DESTINO_CIUDAD_NOMBRE_ALTERNO, campos, mapNameToIndex);
            dto.setDestinoCiudadNombreAlterno(value);

            value = getValorCampo(REQUERIMIENTOS_DISTRIBUCION, campos, mapNameToIndex);
            dto.setCodigosAlternosRequerimientosDistribucion(value);

            value = dto.getNotasConfirmacion();
            if (value.matches("@99 \\d{2}:\\d{2}\\-\\d{2}:\\d{2} @98 \\d{8}.*")) {
                cambiarDatosFechaHoraMinimaMaximaEntrega(key, value, dto);
            } else {
                if (value.matches("FECHA MINIMA \\d{8} FECHA MAXIMA \\d{8}.*")) {
                    cambiarDatosFechaMinimaMaximaEntrega(key, value, dto);
                } else {
                    if (value.matches(
                            "((ENTREGAR|LUNES|MARTES|MIERCOLES|JUEVES|VIERNES|SABADO|DOMINGO)\\s){0,1}((A|P)(\\.*)M(\\.*)).*")) {
                        inferirFechaHoraEntrega(key, value, dto);
                    }
                }
            }

            dto.setUsuarioConfirmacion(getClienteCodigo());

            map.put(key, dto);
        }
    }

    @Override
    protected void modificar(String key, Map<String, ETLOrdenDto> map, String[] campos,
            Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {
    }

    @Override
    protected Map<String, ETLOrdenDto> preCargar(Map<String, ETLOrdenDto> map) {
        map = super.preCargar(map);

        Map<String, List<ETLLineaOrdenDto>> lineas = null;

        lineasOrdenStrategy.setDirectorioEntrada(this.getDirectorioEntrada());
        lineasOrdenStrategy.setDirectorioSalida(this.getDirectorioSalida());
        lineasOrdenStrategy.setDirectorioProcesados(this.getDirectorioProcesados());
        lineasOrdenStrategy.setDirectorioErrores(this.getDirectorioErrores());

        lineas = lineasOrdenStrategy.procesarLineas(getArchivoLineas());

        for (String key : map.keySet()) {
            if (lineas.containsKey(key)) {
                map.get(key).getLineas().clear();
                map.get(key).getLineas().addAll(lineas.get(key));
            }
        }

        this.getMensajes().getMensajes().addAll(lineasOrdenStrategy.getMensajes().getMensajes());

        lineasOrdenStrategy.setDirectorioEntrada(null);
        lineasOrdenStrategy.setDirectorioSalida(null);
        lineasOrdenStrategy.setDirectorioProcesados(null);
        lineasOrdenStrategy.setDirectorioErrores(null);

        return map;
    }

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

    @Override
    protected void postProcesarDirectorio() {
        this.diasCalendario = null;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    private void cambiarDatosFechaHoraMinimaMaximaEntrega(String key, String value, ETLOrdenDto dto) {
        Date dateValue;
        Time timeValue;

        value = value.substring(0, 28);
        value = value.replaceAll("@99", "");
        value = value.replaceAll("@98", "");
        value = value.replaceAll("-", " ");
        value = value.replaceAll("  ", " ").trim();

        String[] campos = value.split(" ");

        dateValue = null;
        try {
            dateValue = Basic.toFecha(campos[2], null, getFormatoFechaCorta());
        } catch (ParseException e) {
            logParseException(key, FECHA_MAXIMA, value, getFormatoFechaCorta().toPattern());
        }
        dto.setFechaEntregaSugeridaMaxima(dateValue);
        dto.setFechaEntregaSugeridaMinima(dto.getFechaEntregaSugeridaMaxima());

        timeValue = null;
        try {
            timeValue = Basic.toHora(campos[0], null, getFormatoHoraHHmm());
        } catch (ParseException e) {
            logParseException(key, HORA_MINIMA, value, getFormatoHoraHHmm().toPattern());
        }
        dto.setHoraEntregaSugeridaMinima(timeValue);

        timeValue = null;
        try {
            timeValue = Basic.toHora(campos[1], null, getFormatoHoraHHmm());
        } catch (ParseException e) {
            logParseException(key, HORA_MAXIMA, value, getFormatoHoraHHmm().toPattern());
        }
        dto.setHoraEntregaSugeridaMaxima(timeValue);
    }

    private void cambiarDatosFechaMinimaMaximaEntrega(String key, String value, ETLOrdenDto dto) {
        Date dateValue;

        value = value.substring(0, 43);
        value = value.replaceAll("FECHA MINIMA ", "").replaceAll("FECHA MAXIMA ", "").replaceAll("  ", " ").trim();

        String[] campos = value.split(" ");

        dateValue = null;
        try {
            dateValue = Basic.toFecha(campos[1], null, getFormatoFechaCorta());
        } catch (ParseException e) {
            logParseException(key, FECHA_MAXIMA, value, getFormatoFechaCorta().toPattern());
        }
        dto.setFechaEntregaSugeridaMaxima(dateValue);

        dateValue = null;
        try {
            dateValue = Basic.toFecha(campos[0], null, getFormatoFechaCorta());
        } catch (ParseException e) {
            logParseException(key, FECHA_MINIMA, value, getFormatoFechaCorta().toPattern());
        }
        dto.setFechaEntregaSugeridaMinima(dateValue);
    }

    private void inferirFechaHoraEntrega(String key, String value, ETLOrdenDto dto) {
        value = value.replaceAll("\\.", "").replaceAll("ENTREGAR", " ").replaceAll("  ", " ").trim();

        if (value.matches("AM|PM")) {
            value = "NEXT_DAY " + value;
        }

        String[] campos = value.split(" ");
        String dia = "";
        String jornada = "";

        dia = campos[0];
        if (campos.length > 1) {
            jornada = campos[1];
        }

        List<Calendario> dias = getDiasCalendario();

        if (dia.equals("NEXT_DAY")) {
            for (Calendario d : dias) {
                if (d.isDiaHabil()) {
                    dto.setFechaEntregaSugeridaMinima(d.getFecha());
                    dto.setFechaEntregaSugeridaMaxima(d.getFecha());
                    break;
                }
            }
        } else {
            DiaDeSemanaType diaSemana;
            diaSemana = toDiaSemana(dia);

            if (diaSemana != null) {
                for (Calendario d : dias) {
                    if (d.isDiaHabil() && d.getDia().equals(diaSemana)) {
                        dto.setFechaEntregaSugeridaMinima(d.getFecha());
                        dto.setFechaEntregaSugeridaMaxima(d.getFecha());
                        break;
                    }
                }
            }
        }

        String horaMinima = null;
        String horaMaxima = null;

        switch (jornada) {
        case "AM":
            horaMinima = "05:00";
            horaMaxima = "11:00";
            break;
        case "PM":
            horaMinima = "14:00";
            horaMaxima = "17:00";
            break;
        default:
            horaMinima = "05:00";
            horaMaxima = "17:00";
            break;
        }

        Time timeValue;

        timeValue = null;
        try {
            timeValue = Basic.toHora(horaMinima, null, getFormatoHoraHHmm());
        } catch (ParseException e) {
            logParseException(key, HORA_MINIMA, value, getFormatoHoraHHmm().toPattern());
        }
        dto.setHoraEntregaSugeridaMinima(timeValue);

        timeValue = null;
        try {
            timeValue = Basic.toHora(horaMaxima, null, getFormatoHoraHHmm());
        } catch (ParseException e) {
            logParseException(key, HORA_MAXIMA, value, getFormatoHoraHHmm().toPattern());
        }
        dto.setHoraEntregaSugeridaMaxima(timeValue);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Autowired
    private CalendarioRepository calendarioRepository;

    private List<Calendario> diasCalendario = null;

    public List<Calendario> getDiasCalendario() {
        if (diasCalendario == null) {
            Date fechaDesde = Basic.addDays(Basic.truncate(new Date()), 1);
            Date fechaHasta = Basic.addDays(fechaDesde, 6);

            diasCalendario = calendarioRepository.findSemana(fechaDesde, fechaHasta);
        }
        return diasCalendario;
    }

    protected DiaDeSemanaType toDiaSemana(String value) {
        value = Basic.coalesce(value, "");

        switch (value) {
        case "LUNES":
            return DiaDeSemanaType.LUNES;
        case "MARTES":
            return DiaDeSemanaType.MARTES;
        case "MIERCOLES":
            return DiaDeSemanaType.MIERCOLES;
        case "JUEVES":
            return DiaDeSemanaType.JUEVES;
        case "VIERNES":
            return DiaDeSemanaType.VIERNES;
        case "SABADO":
            return DiaDeSemanaType.SABADO;
        case "DOMINGO":
            return DiaDeSemanaType.DOMINGO;
        default:
            break;
        }
        return null;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    private SimpleDateFormat formatoFechaCorta = null;

    @Override
    protected SimpleDateFormat getFormatoFechaCorta() {
        if (formatoFechaCorta == null) {
            formatoFechaCorta = new SimpleDateFormat("yyyyMMdd");
        }
        return formatoFechaCorta;
    }
}