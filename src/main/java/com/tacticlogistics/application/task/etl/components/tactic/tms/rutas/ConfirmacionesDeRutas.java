package com.tacticlogistics.application.task.etl.components.tactic.tms.rutas;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.tacticlogistics.application.task.etl.components.ETLFlatFileStrategy;
import com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto.DocumentoDto;
import com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto.LineaRutaDto;
import com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto.ResultadoProgramacionRutaDto;
import com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto.ResultadosProgramacionRutasDto;
import com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto.RutaDto;
import com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto.SuscripcionDto;
import com.tacticlogistics.application.task.etl.components.tactic.tms.rutas.dto.SuscriptorDto;
import com.tacticlogistics.infrastructure.services.Basic;

public abstract class Rutas extends ETLFlatFileStrategy<RutaDto> {
    private static final Logger log = LoggerFactory.getLogger(Rutas.class);

    // ---------------------------------------------------------------------------------------------------------------------------------------
    public static final String IDENTIFICADOR_VEHICULO = "IDENTIFICADOR";
    public static final String SECUENCIA = "ORDEN";
    public static final String LATITUD = "LATITUD";
    public static final String LONGITUD = "LONGITUD";
    public static final String IDENTIFICADOR_ENTREGA = "CLIENTE";
    public static final String HORA = "HORA";
    public static final String NUMERO_DOCUMENTO_ENTREGA = "NOMBRE";
    public static final String ENTREGA_ID = "ID_SOLICITUD";
    public static final String DESTINATARIO_NOMBRE = "NOMBRE_DESTINATARIO";
    public static final String DESTINO_DIRECCION = "DIRECCION";
    public static final String DESTINO_BARRIO = "BARRIO";
    public static final String DESTINO_NOMBRE = "NOMBRE_DESTINO";
    public static final String FECHA_ENTREGA_PLANEADA = "FECHA_ENTREGA_PLANEADA";
    public static final String CLIENTE_CODIGO = "CODIGO_CLIENTE";

    public static final String IDENTIFICADOR_MOVIL = "IDENTIFICADOR_MOVIL";
    public static final String CLIENTE_NUMERO_IDENTIFICACION = "CLIENTE_NUMERO_IDENTIFICACION";
    public static final String FECHA_HORA_ENTREGA = "FECHA_HORA_ENTREGA";

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Value("${tms.rutas.programacion.apiUrl}")
    private String apiUrl;

    @Value("${tms.rutas.programacion.apiToken}")
    private String apiToken;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public String getApiUrl() {
        return apiUrl;
    }

    public String getApiToken() {
        return apiToken;
    }

    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    public Rutas() {
        super();
    }

    @Override
    protected String limpiar(String texto) {
        return Basic.limpiarCaracterEspecialDeEspacioDeExcel(super.limpiar(texto));
    }

    @Override
    protected void adicionar(String key, Map<String, RutaDto> map, String[] campos, Map<String, Integer> mapNameToIndex,
            Map<Integer, String> mapIndexToName) {
        if (!map.containsKey(key)) {
            RutaDto dto = new RutaDto(key, false);
            map.put(key, dto);
        }
    }

    @Override
    protected void modificar(String key, Map<String, RutaDto> map, String[] campos, Map<String, Integer> mapNameToIndex,
            Map<Integer, String> mapIndexToName) {

        if (map.containsKey(key)) {
            String value;
            Integer integerValue;
            Float floatValue;

            LineaRutaDto dto = new LineaRutaDto();

            integerValue = null;
            value = getValorCampo(SECUENCIA, campos, mapNameToIndex);
            try {
                integerValue = Basic.parseEntero(value, getFormatoEntero());
            } catch (ParseException e) {
                logParseException(key, SECUENCIA, value, getFormatoEntero().toPattern());
            }
            dto.setSecuencia(integerValue);

            floatValue = null;
            value = getValorCampo(LATITUD, campos, mapNameToIndex);
            value = value.replace(",", ".");
            try {
                floatValue = getFormatoCoordenada().parse(value).floatValue();
            } catch (ParseException e) {
                logParseException(key, LATITUD, value, getFormatoCoordenada().toPattern());
            }
            dto.setCyD(floatValue);

            floatValue = null;
            value = getValorCampo(LONGITUD, campos, mapNameToIndex);
            value = value.replace(",", ".");
            try {
                floatValue = getFormatoCoordenada().parse(value).floatValue();
            } catch (ParseException e) {
                logParseException(key, LONGITUD, value, getFormatoCoordenada().toPattern());
            }
            dto.setCxD(floatValue);

            value = getValorCampo(IDENTIFICADOR_ENTREGA, campos, mapNameToIndex);
            dto.setNumeroDocumentoEntrega(value);

            cargarDestinoNombre(key, dto, campos, mapNameToIndex);

            cargarDestinoDireccion(key, dto, campos, mapNameToIndex);

            cargarDatosCliente(key, dto, campos, mapNameToIndex);

            cargarDatosVehiculo(key, dto, campos, mapNameToIndex);

            cargarFechaHoraEntrega(key, dto, campos, mapNameToIndex);

            dto.setRecaudo(0);

            map.get(key).getLineas().add(dto);
        }
    }

    protected abstract void cargarDestinoNombre(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex);

    protected abstract void cargarDestinoDireccion(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex);

    protected abstract void cargarDatosCliente(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex);

    protected abstract void cargarDatosVehiculo(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex);

    protected abstract void cargarFechaHoraEntrega(String key, LineaRutaDto dto, String[] campos,
            Map<String, Integer> mapNameToIndex);

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected Map<String, RutaDto> preCargar(Map<String, RutaDto> map) {
        map = super.preCargar(map);

        // TODO PASAR A PRE PROCESO
        Map<String, String> clientes = getMapClientes(getJdbcTemplate());
        Map<String, List<SuscriptorDto>> confirmaciones = getMapSuscriptores(getJdbcTemplate(), "CONFIRMACION_ENTREGA");
        Map<String, String> vehiculos = getMapVehiculos(getJdbcTemplate());

        for (RutaDto ruta : map.values()) {
            LineaRutaDto[] lineas = ruta.getLineas().toArray(new LineaRutaDto[0]);

            configurarCliente(lineas, clientes);
            configurarNotificaciones(lineas, confirmaciones);
            configurarDocumentos(ruta);
            configurarIdentificadorMovil(ruta, vehiculos);
        }

        return map;
    }

    protected abstract void configurarCliente(LineaRutaDto[] lineas, Map<String, String> mapClientes);

    public void configurarNotificaciones(LineaRutaDto[] lineas, Map<String, List<SuscriptorDto>> confirmaciones) {

        for (int i = 0; i < lineas.length; i++) {
            List<String> emails = getEmailsConfirmarcionEntrega(lineas[i].getClienteCodigo(), confirmaciones);

            SuscriptorDto suscriptor = getSuscriptorOrden(getJdbcTemplate(), lineas[i].getClienteCodigo(),
                    lineas[i].getNumeroDocumentoEntrega());
            if (suscriptor != null && !suscriptor.getEmail().isEmpty()) {
                emails.add(suscriptor.getEmail());
            }
            SuscripcionDto suscripcion = new SuscripcionDto();
            suscripcion.setFinalizaRuta(emails);
            lineas[i].setCorreos(suscripcion);
        }

        for (int i = 0; i < lineas.length - 1; i++) {
            lineas[i].getCorreos().setSiguienteDestino(lineas[i + 1].getCorreos().getFinalizaRuta());
        }
    }

    public void configurarDocumentos(RutaDto ruta) {
        ruta.getDocumentos().add(new DocumentoDto("FACTURA", 1));
    }

    protected abstract void configurarIdentificadorMovil(RutaDto ruta, Map<String, String> vehiculos);

    public List<String> getEmailsConfirmarcionEntrega(String clienteCodigo,
            Map<String, List<SuscriptorDto>> emailsConfirmacion) {
        final List<String> emails = new ArrayList<>();

        List<SuscriptorDto> list = emailsConfirmacion.get(clienteCodigo);
        if (list != null) {
            list.forEach(a -> {
                emails.add(a.getEmail());
            });
        }

        return emails;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    public Map<String, String> getMapClientes(NamedParameterJdbcTemplate jdbcTemplate) {
        String query = " " + " SELECT " + "      a.codigo" + "     ,a.numero_identificacion " + " FROM crm.clientes a "
                + " ORDER BY " + "     a.codigo ";

        List<String[]> list = jdbcTemplate.query(query, (rs, rowNum) -> new String[] {
                rs.getString("codigo").toUpperCase(), rs.getString("numero_identificacion").toUpperCase() });

        Map<String, String> map = new HashMap<>();
        for (String[] item : list) {
            map.put(item[0], item[1]);
        }
        return map;
    }

    public Map<String, List<SuscriptorDto>> getMapSuscriptores(NamedParameterJdbcTemplate jdbcTemplate,
            String notificacionCodigo) {
        String query = "" + " SELECT DISTINCT " + "      a.id_cliente " + "     ,a.codigo " + "     ,b.contacto_email "
                + "     ,b.contacto_nombres " + "     ,b.contacto_telefonos " + " FROM crm.clientes a "
                + " INNER JOIN notificaciones.suscriptores b ON " + "    b.id_cliente = a.id_cliente "
                + " INNER JOIN notificaciones.suscriptores_notificaciones c ON "
                + "    c.id_suscriptor = b.id_suscriptor " + " INNER JOIN notificaciones.notificaciones d ON "
                + "    d.id_notificacion = c.id_notificacion " + " WHERE " + "    0 = 0 "
                + " AND d.codigo = :notificacionCodigo " + " ORDER BY " + "     a.codigo" + "    ,b.contacto_email";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("notificacionCodigo", notificacionCodigo);

        List<SuscriptorDto> rows = jdbcTemplate.query(query, parameters, (rs, rowNum) -> {
            return new SuscriptorDto(rs.getInt("id_cliente"), rs.getString("codigo"), rs.getString("contacto_email"),
                    rs.getString("contacto_nombres"), rs.getString("contacto_telefonos"));
        });

        Map<String, List<SuscriptorDto>> map = new HashMap<>();

        for (SuscriptorDto dto : rows) {
            if (!map.containsKey(dto.getClienteCodigo())) {
                map.put(dto.getClienteCodigo(), new ArrayList<SuscriptorDto>());
            }

            map.get(dto.getClienteCodigo()).add(dto);
        }

        return map;
    }

    private Map<String, String> getMapVehiculos(NamedParameterJdbcTemplate jdbcTemplate) {
        String query = " " + " SELECT " + "      a.Numero_Placa" + "     ,a.ID_Movil"
                + " FROM Tactic.piloto.Configuracion_Vehiculos a " + " ORDER BY " + "   a.Numero_Placa";

        List<String[]> list = jdbcTemplate.query(query, (rs, rowNum) -> new String[] {
                rs.getString("Numero_Placa").toUpperCase(), rs.getString("ID_Movil").toLowerCase() });

        Map<String, String> map = new HashMap<>();
        for (String[] item : list) {
            map.put(item[0], item[1]);
        }
        return map;
    }

    public static SuscriptorDto getSuscriptorOrden(NamedParameterJdbcTemplate jdbcTemplate, String clienteCodigo,
            String numeroDocumentoOrdenCliente) {
        String query = "" + "        SELECT " + "            a.id_cliente, " + "            a.codigo, "
                + "            b.destino_contacto_email, " + "            b.destino_contacto_nombres, "
                + "            b.destino_contacto_telefonos " + "        FROM crm.clientes a "
                + "        INNER JOIN ordenes.ordenes b ON " + "            b.id_cliente = a.id_cliente "
                + "        WHERE " + "            a.codigo = :clienteCodigo "
                + "        AND b.numero_documento_orden_cliente = :numeroDocumentoOrdenCliente ";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("clienteCodigo", clienteCodigo);
        parameters.put("numeroDocumentoOrdenCliente", numeroDocumentoOrdenCliente);

        List<SuscriptorDto> rows = jdbcTemplate.query(query, parameters, (rs, rowNum) -> {
            return new SuscriptorDto(rs.getInt("id_cliente"), rs.getString("codigo"),
                    rs.getString("destino_contacto_email"), rs.getString("destino_contacto_nombres"),
                    rs.getString("destino_contacto_telefonos"));
        });

        if (rows.size() > 0) {
            return rows.get(0);
        } else {
            return null;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    @Transactional(readOnly = false)
    protected void cargar(Map<String, RutaDto> map) {
        log.info("Begin cargar");
        List<RutaDto> rutas = new ArrayList<>(map.values());
        try {
            ResultadosProgramacionRutasDto result;
            result = send(getApiUrl(), getApiToken(), rutas);
            //TODO Generar rutas
            generarLogResultados(rutas, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("End cargar");
    }
    

    public static ResultadosProgramacionRutasDto send(String apiUrl, String apiToken, List<RutaDto> request)
            throws IOException, JsonGenerationException, JsonMappingException {

        MappingJackson2HttpMessageConverter mapping = new MappingJackson2HttpMessageConverter();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-Type", "application/json");
        headers.add("token", apiToken);

        HttpEntity<?> requestEntity = new HttpEntity<Object>(request, headers);
        log.info(mapping.getObjectMapper().writeValueAsString(request));

        try {
            ResultadosProgramacionRutasDto result = restTemplate.postForObject(apiUrl, requestEntity,
                    ResultadosProgramacionRutasDto.class);
            log.info(mapping.getObjectMapper().writeValueAsString(result));

            return result;
        } catch (HttpClientErrorException hcee) {
            log.error(hcee.getMessage());
            throw new RuntimeException(hcee);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected void generarLogResultados(List<RutaDto> rutas, ResultadosProgramacionRutasDto result) {
        Map<String, String> mapMovilVehiculo = new HashMap<>();
        for (RutaDto rutaDto : rutas) {
            String value = "";
            if (rutaDto.getLineas().size() > 0) {
                value = rutaDto.getLineas().get(0).getIdentificadorVehiculo();
            }
            mapMovilVehiculo.put(rutaDto.getIdentificadorMovil(), value);
        }

        for (ResultadoProgramacionRutaDto dto : result.getRutas()) {
            String key = mapMovilVehiculo.get(dto.getIdentificadorMovil());
            if (dto.getStatus().equalsIgnoreCase("OK")) {
                logInfo(key, "", dto.getStatus());
            } else {
                logError(key, "", dto.getError());
            }
        }
    }
}