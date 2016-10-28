package com.tacticlogistics.application.tasks.etl.components.dicermex;

import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.DESTINATARIO_CANAL_CODIGO_ALTERNO;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.DESTINATARIO_IDENTIFICACION;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.DESTINATARIO_NOMBRE;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.DESTINO_CIUDAD_NOMBRE_ALTERNO;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.DESTINO_CONTACTO_EMAIL;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.DESTINO_CONTACTO_TELEFONOS;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.DESTINO_DIRECCION;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.DESTINO_NOMBRE;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.FECHA_MAXIMA;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.FECHA_MINIMA;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.HORA_MAXIMA;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.HORA_MINIMA;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.NOTAS;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.NUMERO_ORDEN;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.PREFIJO_NUMERO_ORDEN;
import static com.tacticlogistics.application.tasks.etl.OrdenDtoAtributos.REQUERIMIENTOS_DISTRIBUCION;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.common.MensajesDTO;
import com.tacticlogistics.application.dto.etl.ETLLineaOrdenDto;
import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.application.services.oms.OrdenesApplicationService;
import com.tacticlogistics.application.tasks.etl.components.ETLFlatFileStrategy;
import com.tacticlogistics.application.tasks.etl.readers.CharsetDetectorFileReader;
import com.tacticlogistics.application.tasks.etl.readers.Reader;
import com.tacticlogistics.domain.model.calendario.Calendario;
import com.tacticlogistics.domain.model.calendario.DiaDeSemanaType;
import com.tacticlogistics.domain.model.crm.ClienteBodegaAssociation;
import com.tacticlogistics.domain.model.oms.Orden;
import com.tacticlogistics.infrastructure.persistence.calendario.CalendarioRepository;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.services.Basic;

@Component("DICERMEX.")
public class DICERMEXFacturas extends ETLFlatFileStrategy<ETLOrdenDto> {
	private static final Logger log = (Logger) LoggerFactory.getLogger(DICERMEXFacturas.class);

	private static final Pattern PATTERN = Pattern.compile("(?i:(.*)ESBOrdenesDeDespacho(Parcial)?_(\\d+)\\.txt)");

	@Autowired
	private CharsetDetectorFileReader reader;

	@Autowired
	private OrdenesApplicationService ordenesService;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private DICERMEXLineasFactura lineasOrdenStrategy;

	// ---------------------------------------------------------------------------------------------------------------------------------------
	protected String getClienteCodigo() {
		return "DICERMEX";
	}

	protected String getServicioCodigoTraslado() {
		return "TRASLADO";
	}

	protected String getServicioCodigoAlternoTraslado() {
		return "TRS";
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
	protected boolean preProcesarArchivo(MensajesDTO<?> mensajes) {
		File archivo = getArchivoLineas();
		boolean procesar = archivo.exists();

		if (!procesar) {
			log.warn("No se encontró el archivo {}", archivo.getName());
		}

		if (procesar) {
			procesar = procesar && super.preProcesarArchivo(mensajes);
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
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName, MensajesDTO<?> mensajes) {

		if (!map.containsKey(key)) {
			String value;

			ETLOrdenDto dto = new ETLOrdenDto();

			dto.setClienteCodigo(getClienteCodigo());

			value = getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex);
			value = Basic.substringSafe(value, 0, 3).replaceAll("-", "");
			dto.setServicioCodigoAlterno(value);

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
				cambiarDatosFechaHoraMinimaMaximaEntrega(mensajes,key, value, dto);
			} else {
				if (value.matches("FECHA MINIMA \\d{8} FECHA MAXIMA \\d{8}.*")) {
					cambiarDatosFechaMinimaMaximaEntrega(mensajes,key, value, dto);
				} else {
					if (value.matches(
							"((ENTREGAR|LUNES|MARTES|MIERCOLES|JUEVES|VIERNES|SABADO|DOMINGO)\\s){0,1}((A|P)(\\.*)M(\\.*)).*")) {
						inferirFechaHoraEntrega(mensajes,key, value, dto);
					}
				}
			}

			dto.setUsuarioConfirmacion(getClienteCodigo());

			map.put(key, dto);
		}
	}

	@Override
	protected void modificar(String key, Map<String, ETLOrdenDto> map, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName, MensajesDTO<?> mensajes) {
	}

	@Override
	protected Map<String, ETLOrdenDto> preCargar(Map<String, ETLOrdenDto> map, MensajesDTO<?> mensajes) {
		map = super.preCargar(map, mensajes);

		Map<String, List<ETLLineaOrdenDto>> lineas = null;

		lineasOrdenStrategy.setDirectorioEntrada(this.getDirectorioEntrada());
		lineasOrdenStrategy.setDirectorioSalida(this.getDirectorioSalida());
		lineasOrdenStrategy.setDirectorioProcesados(this.getDirectorioProcesados());
		lineasOrdenStrategy.setDirectorioErrores(this.getDirectorioErrores());

		lineas = lineasOrdenStrategy.procesarLineas(getArchivoLineas(),mensajes);

		for (String key : map.keySet()) {
			if (lineas.containsKey(key)) {
				map.get(key).getLineas().clear();
				map.get(key).getLineas().addAll(lineas.get(key));
			}
		}

		lineasOrdenStrategy.setDirectorioEntrada(null);
		lineasOrdenStrategy.setDirectorioSalida(null);
		lineasOrdenStrategy.setDirectorioProcesados(null);
		lineasOrdenStrategy.setDirectorioErrores(null);

		final List<ClienteBodegaAssociation> bodegas = clienteRepository
				.findClienteBodegaAssociationByClienteCodigo(this.getClienteCodigo());

		List<ETLOrdenDto> list = map.entrySet().stream()
				.filter(a -> getServicioCodigoAlternoTraslado().equals(a.getValue().getServicioCodigoAlterno()))
				.map(a -> a.getValue()).collect(Collectors.toCollection(ArrayList::new));

		for (ETLOrdenDto dto : list) {
			boolean traslado = dto.getLineas().stream().anyMatch(a -> {
				if (!a.getBodegaDestinoCodigoAlterno().isEmpty()) {
					boolean origen = bodegas.stream()
							.anyMatch(b -> b.getCodigoAlterno().equals(a.getBodegaOrigenCodigoAlterno()));
					boolean destino = bodegas.stream()
							.anyMatch(b -> b.getCodigoAlterno().equals(a.getBodegaDestinoCodigoAlterno()));
					return origen && destino;
				}
				return false;
			});

			if (traslado) {
				dto.setServicioCodigo(this.getServicioCodigoTraslado());
				dto.setDestinoCiudadNombreAlterno("");
				dto.setDestinoDireccion("");
				dto.setDestinoNombre("");
				dto.setDestinoContactoTelefonos("");
				dto.setDestinoContactoEmail("");
			}
		}

		return map;
	}

	@Override
	@Transactional(readOnly = false)
	protected void cargar(Map<String, ETLOrdenDto> map, MensajesDTO<?> mensajes) {
		for (Entry<String, ETLOrdenDto> entry : map.entrySet()) {
			ETLOrdenDto dto = entry.getValue();
			try {
				Orden orden = ordenesService.saveOrdenDespachosSecundaria(dto);
				if (orden != null) {
					logInfo(mensajes, "OK", "", "", dto.getNumeroOrden());
				} else {
					logWarning(mensajes,
							"Una  solicitud para el mismo cliente con el mismo numero ya se encuentra registrada.","", "", 
							dto.getNumeroOrden());
				}
			} catch (Exception e) {
				logError(mensajes, e.getMessage(), "", "", dto.getNumeroOrden());
			}
		}
	}

	@Override
	protected void postProcesarDirectorio() {
		this.diasCalendario = null;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	private void cambiarDatosFechaHoraMinimaMaximaEntrega(MensajesDTO<?> mensajes, String key, String value,
			ETLOrdenDto dto) {
		LocalDate dateValue;
		LocalTime timeValue;

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
			logParseException(mensajes, key, FECHA_MAXIMA, value, "", "");
		}
		dto.setFechaEntregaSugeridaMaxima(dateValue);
		dto.setFechaEntregaSugeridaMinima(dto.getFechaEntregaSugeridaMaxima());

		timeValue = null;
		try {
			timeValue = Basic.toHora(campos[0], null, getFormatoHoraHHmm());
		} catch (ParseException e) {
			logParseException(mensajes, key, HORA_MINIMA, value, "", "");
		}
		dto.setHoraEntregaSugeridaMinima(timeValue);

		timeValue = null;
		try {
			timeValue = Basic.toHora(campos[1], null, getFormatoHoraHHmm());
		} catch (ParseException e) {
			logParseException(mensajes, key, HORA_MAXIMA, value, "", "");
		}
		dto.setHoraEntregaSugeridaMaxima(timeValue);
	}

	private void cambiarDatosFechaMinimaMaximaEntrega(MensajesDTO<?> mensajes, String key, String value,
			ETLOrdenDto dto) {
		LocalDate dateValue;

		value = value.substring(0, 43);
		value = value.replaceAll("FECHA MINIMA ", "").replaceAll("FECHA MAXIMA ", "").replaceAll("  ", " ").trim();

		String[] campos = value.split(" ");

		dateValue = null;
		try {
			dateValue = Basic.toFecha(campos[1], null, getFormatoFechaCorta());
		} catch (ParseException e) {
			logParseException(mensajes, key, FECHA_MAXIMA, value, "", "");
		}
		dto.setFechaEntregaSugeridaMaxima(dateValue);

		dateValue = null;
		try {
			dateValue = Basic.toFecha(campos[0], null, getFormatoFechaCorta());
		} catch (ParseException e) {
			logParseException(mensajes, key, FECHA_MINIMA, value, "", "");
		}
		dto.setFechaEntregaSugeridaMinima(dateValue);
	}

	private void inferirFechaHoraEntrega(MensajesDTO<?> mensajes, String key, String value, ETLOrdenDto dto) {
		value = value.replaceAll("\\.", "").replaceAll("ENTREGAR", "").replaceAll(" ", " ").trim();

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

		LocalTime timeValue;

		timeValue = null;
		try {
			timeValue = Basic.toHora(horaMinima, null, getFormatoHoraHHmm());
		} catch (ParseException e) {
			logParseException(mensajes, key, HORA_MINIMA, value, "", "");
		}
		dto.setHoraEntregaSugeridaMinima(timeValue);

		timeValue = null;
		try {
			timeValue = Basic.toHora(horaMaxima, null, getFormatoHoraHHmm());
		} catch (ParseException e) {
			logParseException(mensajes, key, HORA_MAXIMA, value, "", "");
		}
		dto.setHoraEntregaSugeridaMaxima(timeValue);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Autowired
	private CalendarioRepository calendarioRepository;

	private List<Calendario> diasCalendario = null;

	public List<Calendario> getDiasCalendario() {
		if (diasCalendario == null) {
			LocalDate fechaDesde = LocalDate.now().plusDays(1);
			LocalDate fechaHasta = fechaDesde.plusDays(6);

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
	private DateTimeFormatter formatoFechaCorta = null;

	@Override
	protected DateTimeFormatter getFormatoFechaCorta() {
		if (formatoFechaCorta == null) {
			formatoFechaCorta = DateTimeFormatter.ofPattern("yyyyMMdd");
		}
		return formatoFechaCorta;
	}
}