package com.tacticlogistics.application.tasks.etl.components.gws;

import static com.tacticlogistics.infrastructure.services.Basic.substringSafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.common.MensajesDTO;
import com.tacticlogistics.application.dto.etl.ETLDestinatarioDto;
import com.tacticlogistics.application.dto.etl.ETLDestinoOrigenDto;
import com.tacticlogistics.application.services.crm.DestinatariosApplicationService;
import com.tacticlogistics.application.services.crm.DestinosApplicationService;
import com.tacticlogistics.application.tasks.etl.components.ETLFlatFileStrategy;

@Component("GWS.MAESTROS")
public class GWSMaestroClientes extends ETLFlatFileStrategy<ETLDestinatarioDto> {

	private static final String DESTINATARIO_NUMERO_IDENTIFICACION = "destinatarioNumeroIdentificacion";
	private static final String DESTINATARIO_NOMBRE = "destinatarioNombre";
	private static final String DESTINATARIO_NOMBRE_COMERCIAL = "destinatarioNombreComercial";

	private static final String DESTINO_CONTACTO_NOMBRES = "destinoContactoNombres";
	private static final String DESTINO_CONTACTO_EMAIL = "destinoContactoEmail";
	private static final String DESTINO_CONTACTO_TELEFONOS = "destinoContactoTelefonos";

	private static final String CANAL_CODIGO_ALTERNO = "canalCodigoAlterno";

	private static final String DESTINO_CIUDAD_NOMBRE_ALTERNO = "destinoCiudadNombreAlterno";
	private static final String DESTINO_NOMBRE = "destinoNombre";
	private static final String DESTINO_DIRECCION = "destinoDireccion";
	private static final String DESTINATARIO_ACTIVO = "destinatarioActivo";

	private static Map<String, String> mapCanales = null;

	private static Map<String, String> mapCiudades = null;

	@Autowired
	private DestinatariosApplicationService destinatariosRemitentesServices;

	@Autowired
	private DestinosApplicationService destinosOrigenesServices;

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected String limpiar(String texto) {
		return super.limpiar(texto).replace('\r', ' ');
	}

	@Override
	@Transactional(readOnly = false)
	protected void cargar(Map<String, ETLDestinatarioDto> map, MensajesDTO<?> mensajes) {
		for (ETLDestinatarioDto dto : map.values()) {
			try {
				destinatariosRemitentesServices.save(dto);
				logInfo(mensajes, "OK", "", "", dto.getNumeroIdentificacion());
				for (ETLDestinoOrigenDto d : dto.getDestinosOrigenes()) {
					try {
						destinosOrigenesServices.save(d);
					} catch (Exception e) {
						StringBuffer sb = new StringBuffer();
						sb.append(dto.getNumeroIdentificacion()).append("-").append(dto.getNombre()).append("-")
								.append(d.getCiudadNombreAlterno()).append("-").append(d.getDireccion()).append("-");

						logError(mensajes, e.getMessage(), "", "", sb.toString());
					}
				}
			} catch (Exception e) {
				logError(mensajes, e.getMessage(), "", "", dto.getNumeroIdentificacion());
			}
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected List<String> getCamposEsperados() {
		List<String> list = new ArrayList<>();

		list.add(DESTINATARIO_NUMERO_IDENTIFICACION);
		list.add(DESTINATARIO_NOMBRE);
		list.add(DESTINATARIO_NOMBRE_COMERCIAL);

		list.add(DESTINO_CONTACTO_NOMBRES);
		list.add(DESTINO_CONTACTO_EMAIL);
		list.add(DESTINO_CONTACTO_TELEFONOS);

		list.add(CANAL_CODIGO_ALTERNO);

		list.add(DESTINO_CIUDAD_NOMBRE_ALTERNO);
		list.add(DESTINO_NOMBRE);
		list.add(DESTINO_DIRECCION);
		list.add(DESTINATARIO_ACTIVO);

		return list;
	}

	@Override
	protected boolean generarEncabezadoConLosNombresDeLosCamposEsperados() {
		return true;
	}

	@Override
	protected String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex) {
		return getValorCampo(DESTINATARIO_NUMERO_IDENTIFICACION, campos, mapNameToIndex);
	}

	@Override
	protected void adicionar(String key, Map<String, ETLDestinatarioDto> map, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName, MensajesDTO<?> mensajes) {

		if (!map.containsKey(key)) {
			String value;

			ETLDestinatarioDto dto = new ETLDestinatarioDto();

			dto.setClienteCodigo(this.getClienteCodigoAlterno());

			value = getValorCampo(CANAL_CODIGO_ALTERNO, campos, mapNameToIndex);
			value = decodeCanalCodigoAlterno(value);
			dto.setSegmentoCodigoAlterno(value);

			dto.setTipoIdentificacionCodigoAlterno("NI");

			value = getValorCampo(DESTINATARIO_NUMERO_IDENTIFICACION, campos, mapNameToIndex);
			value = substringSafe(value, 0, 20);
			dto.setNumeroIdentificacion(value);

			value = getValorCampo(DESTINATARIO_NOMBRE, campos, mapNameToIndex);
			value = substringSafe(value, 0, 100);
			dto.setNombre(value);

			value = getValorCampo(DESTINATARIO_NOMBRE_COMERCIAL, campos, mapNameToIndex);
			value = substringSafe(value, 0, 100);
			dto.setNombreComercial(value);

			value = getValorCampo(DESTINATARIO_ACTIVO, campos, mapNameToIndex);
			dto.setActivo("N".equals(value));

			map.put(key, dto);
		}
	}

	@Override
	protected void modificar(String key, Map<String, ETLDestinatarioDto> map, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName, MensajesDTO<?> mensajes) {
		if (map.containsKey(key)) {
			String value;

			ETLDestinatarioDto destinatario = map.get(key);

			ETLDestinoOrigenDto dto = new ETLDestinoOrigenDto();

			dto.setClienteCodigo(destinatario.getClienteCodigo());
			dto.setNumeroIdentificacion(destinatario.getNumeroIdentificacion());

			value = getValorCampo(DESTINO_NOMBRE, campos, mapNameToIndex);
			value = substringSafe(value, 0, 100);
			dto.setNombre(value);

			value = decodeCiudadNombreAlterno(getValorCampo("destinoCiudadNombreAlterno", campos, mapNameToIndex));
			dto.setCiudadNombreAlterno(value);

			value = getValorCampo(DESTINO_DIRECCION, campos, mapNameToIndex);
			value = substringSafe(value, 0, 150);
			dto.setDireccion(value);

			value = getValorCampo(DESTINO_CONTACTO_NOMBRES, campos, mapNameToIndex);
			value = substringSafe(value, 0, 100);
			dto.setContactoNombres(value);

			value = getValorCampo(DESTINO_CONTACTO_EMAIL, campos, mapNameToIndex);
			value = substringSafe(value, 0, 100);
			dto.setContactoEmail(value);

			value = getValorCampo(DESTINO_CONTACTO_TELEFONOS, campos, mapNameToIndex);
			value = substringSafe(value, 0, 50);
			dto.setContactoTelefonos(value);

			destinatario.getDestinosOrigenes().add(dto);
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------
	protected String getClienteCodigoAlterno() {
		return "GWS";
	}

	public static String decodeCanalCodigoAlterno(String value) {
		if (value == null) {
			value = "";
		} else {
			value = value.trim();
		}

		Map<String, String> m = getMapCanales();

		value = (m.containsKey(value)) ? m.get(value) : "PREDETERMINADO";

		return value;
	}

	public static String decodeCiudadNombreAlterno(String value) {
		if (value == null) {
			value = "";
		} else {
			value = value.trim();
		}

		Map<String, String> m = getMapCiudades();

		value = (m.containsKey(value)) ? m.get(value) : value;

		return value;
	}

	// TODO getMapCanales() {
	protected static Map<String, String> getMapCanales() {
		if (mapCanales == null) {
			Map<String, String> m;
			m = new HashMap<>();

			m.put("ON TRADE", "CLIENTE FINAL");
			m.put("OFF", "CLIENTE FINAL");
			m.put("EMPLEADOS", "INTERCOMPAÑIA");
			m.put("INSTITUCIONALES", "CLIENTE FINAL");
			m.put("EXTERIOR", "CLIENTE FINAL");
			m.put("CADENAS", "CADENAS");
			m.put("DISTRIBUIDORES NAL", "DISTRIBUIDORES");
			m.put("DISTRIBUIDORES REGIO", "DISTRIBUIDORES");
			m.put("SUBDISTRIBUIDORES", "DISTRIBUIDORES");

			mapCanales = m;
		}

		return mapCanales;
	}

	// TODO getMapCiudades() {
	private static Map<String, String> getMapCiudades() {
		if (mapCiudades == null) {
			Map<String, String> m;
			m = new HashMap<>();

			m.put("B/QUILLA", "BARRANQUILLA");
			m.put("BOGOTA", "BOGOTA");
			m.put("BUCARAMANGA", "BUCARAMANGA");
			m.put("C/GENA", "CARTAGENA");
			m.put("CALI", "CALI");
			m.put("MEDELLIN", "MEDELLIN");
			m.put("MONTERIA", "MONTERIA");
			m.put("ST/MARTA", "SANTA MARTA");
			m.put("STORE", "");
			m.put("V/DUPAR", "VALLEDUPAR");

			mapCiudades = m;
		}

		return mapCiudades;
	}
}
