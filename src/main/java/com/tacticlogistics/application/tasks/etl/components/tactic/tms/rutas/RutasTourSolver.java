package com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas.dto.DocumentoDto;
import com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas.dto.LineaRutaDto;
import com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas.dto.RutaDto;
import com.tacticlogistics.application.tasks.etl.components.tactic.tms.rutas.dto.SuscriptorDto;


public class RutasTourSolver  {

	public void configurarNotificaciones(LineaRutaDto[] lineas, Map<String, List<SuscriptorDto>> confirmaciones) {

//		for (int i = 0; i < lineas.length; i++) {
//			List<String> emails = getEmailsConfirmarcionEntrega(lineas[i].getClienteCodigo(), confirmaciones);
//
//			SuscriptorDto suscriptor = getSuscriptorOrden(getJdbcTemplate(), lineas[i].getClienteCodigo(),
//					lineas[i].getNumeroDocumentoEntrega());
//			if (suscriptor != null && !suscriptor.getEmail().isEmpty()) {
//				emails.add(suscriptor.getEmail());
//			}
//			SuscripcionDto suscripcion = new SuscripcionDto();
//			suscripcion.setFinalizaRuta(emails);
//			lineas[i].setCorreos(suscripcion);
//		}

		for (int i = 0; i < lineas.length - 1; i++) {
			lineas[i].getCorreos().setSiguienteDestino(lineas[i + 1].getCorreos().getFinalizaRuta());
		}
	}

	public void configurarDocumentos(RutaDto ruta) {
		ruta.getDocumentos().add(new DocumentoDto("FACTURA", 1));
	}


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


	public static SuscriptorDto getSuscriptorOrden(NamedParameterJdbcTemplate jdbcTemplate, String clienteCodigo,
			String numeroOrden) {
		String query = "" + "        SELECT " + "            a.id_cliente, " + "            a.codigo, "
				+ "            b.destino_contacto_email, " + "            b.destino_contacto_nombres, "
				+ "            b.destino_contacto_telefonos " + "        FROM crm.clientes a "
				+ "        INNER JOIN ordenes.ordenes b ON " + "            b.id_cliente = a.id_cliente "
				+ "        WHERE " + "            a.codigo = :clienteCodigo "
				+ "        AND b.numero_documento_orden_cliente = :numeroOrden ";

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("clienteCodigo", clienteCodigo);
		parameters.put("numeroOrden", numeroOrden);

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

}
