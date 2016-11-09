package com.tacticlogistics.application.etl.dicermex.despachos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tacticlogistics.application.etl.core.dto.RegistroDTO;
import com.tacticlogistics.application.etl.core.dto.RegistroDTO.Status;
import com.tacticlogistics.application.etl.core.extractors.FileExtractor;
import com.tacticlogistics.application.etl.core.extractors.readers.CharsetDetectorFileReaderBeta;
import com.tacticlogistics.application.etl.core.extractors.readers.Reader;
import com.tacticlogistics.application.etl.dicermex.despachos.dto.DespachoDTO;

@Component
public class DespachosETLFileExtractor extends FileExtractor<DespachoDTO> {
	private static final String NOTAS = "NOTAS";
	private static final String PREFIJO_ORDEN = "PREFIJO_ORDEN";
	private static final String NUMERO_ORDEN = "NUMERO_ORDEN";

	private static final String DESTINATARIO_IDENTIFICACION = "DESTINATARIO_IDENTIFICACION";
	private static final String DESTINATARIO_NOMBRE = "DESTINATARIO_NOMBRE";

	private static final String DESTINO_CODIGO = "DESTINO_CODIGO";
	private static final String DESTINO_NOMBRE = "DESTINO_NOMBRE";
	private static final String DESTINO_CONTACTO_TELEFONOS = "DESTINO_TELEFONOS";
	private static final String DESTINO_CONTACTO_EMAIL = "DESTINO_EMAIL";
	private static final String DESTINO_DIRECCION = "DESTINO_DIRECCION";
	private static final String CIUDAD_DESTINO_CODIGO = "DESTINO_CIUDAD";

	private static final String VALOR_RECAUDO = "VALOR_RECAUDO";
	private static final String DESTINATARIO_CANAL = "DESTINATARIO_CANAL";

	@Autowired
	private CharsetDetectorFileReaderBeta reader;

	@Autowired
	private Validator validator;

	@Override
	protected Reader getReader() {
		return reader;
	}

	@Override
	protected List<String> getCampos() {
		List<String> list = new ArrayList<>();

		list.add(CAMPO_IGNORAR);
		list.add(NOTAS);

		list.add(PREFIJO_ORDEN);
		list.add(NUMERO_ORDEN);

		list.add(DESTINATARIO_IDENTIFICACION);
		list.add(CAMPO_IGNORAR);
		list.add(DESTINATARIO_NOMBRE);

		list.add(DESTINO_CODIGO);
		list.add(DESTINO_NOMBRE);
		list.add(DESTINO_CONTACTO_TELEFONOS);
		list.add(DESTINO_CONTACTO_EMAIL);
		list.add(DESTINO_DIRECCION);
		list.add(CIUDAD_DESTINO_CODIGO);

		list.add(CAMPO_IGNORAR);
		list.add(VALOR_RECAUDO);
		list.add(DESTINATARIO_CANAL);

		return list;
	}

	@Override
	protected boolean seDebenIncluirEncabezados() {
		return true;
	}

	@Override
	protected String[] limpiarCampos(String[] campos) {
		campos = super.limpiarCampos(campos);

		for (int i = 0; i < campos.length; i++) {
			if (campos[i].equals("***")) {
				campos[i] = "";
			}
		}

		return campos;
	}

	@Override
	protected RegistroDTO<DespachoDTO> transformar(String registro, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {
		
		// @formatter:off
		DespachoDTO dto = DespachoDTO
				.builder()
				.notas(campos[mapNameToIndex.get(NOTAS)])
				.prefijoOrden(campos[mapNameToIndex.get(PREFIJO_ORDEN)])
				.numeroOrden(campos[mapNameToIndex.get(NUMERO_ORDEN)])
				.destinatarioIdentificacion(campos[mapNameToIndex.get(DESTINATARIO_IDENTIFICACION)])
				.destinatarioNombre(campos[mapNameToIndex.get(DESTINATARIO_NOMBRE)])
				.destinoCodigo(campos[mapNameToIndex.get(DESTINO_CODIGO)])
				.destinoNombre(campos[mapNameToIndex.get(DESTINO_NOMBRE)])
				.destinoContactoTelefonos(campos[mapNameToIndex.get(DESTINO_CONTACTO_TELEFONOS)])
				.destinoContactoEmails(campos[mapNameToIndex.get(DESTINO_CONTACTO_EMAIL)])
				.destinoDireccion(campos[mapNameToIndex.get(DESTINO_DIRECCION)])
				.ciudadDestinoCodigoAlterno(campos[mapNameToIndex.get(CIUDAD_DESTINO_CODIGO)])
				.valorRecaudo(campos[mapNameToIndex.get(VALOR_RECAUDO)])
				.canalCodigoAlterno(campos[mapNameToIndex.get(DESTINATARIO_CANAL)])
				.build();
		// @formatter:on

		Set<ConstraintViolation<DespachoDTO>> constraints = validator.validate(dto);
		
		StringBuffer sb = new StringBuffer();
		for (ConstraintViolation<DespachoDTO> e : constraints) {
			sb.append("[")
			.append(e.getPropertyPath())
			.append(",")
			.append(e.getInvalidValue())
			.append("]:")
			.append(e.getMessage())
			.append("\n");
		}
		
		// @formatter:off
		return RegistroDTO
				.<DespachoDTO>builder()
				.registro(registro)
				.estado(constraints.isEmpty()?Status.ACEPTADO:Status.ERROR)
				.causaError(sb.toString())
				.data(dto)
				.build();
		// @formatter:on
}

}
