package com.tacticlogistics.application.etl.dicermex.despachos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.tacticlogistics.application.etl.core.dto.RegistroDTO;
import com.tacticlogistics.application.etl.core.dto.RegistroDTO.Status;
import com.tacticlogistics.application.etl.core.extractors.FileExtractor;
import com.tacticlogistics.application.etl.core.extractors.readers.CharsetDetectorFileReaderBeta;
import com.tacticlogistics.application.etl.core.extractors.readers.Reader;
import com.tacticlogistics.application.etl.dicermex.despachos.dto.DespachoDTO;
import com.tacticlogistics.application.etl.dicermex.despachos.dto.DespachoLineaDTO;

public class DespachosLineasETLFileExtractor extends FileExtractor<DespachoLineaDTO> {
	private static final String PREFIJO_ORDEN = "PREFIJO_ORDEN";
	private static final String NUMERO_ORDEN = "NUMERO_ORDEN";
	private static final String PRODUCTO_CODIGO = "PRODUCTO_CODIGO";
	private static final String CANTIDAD_SOLICITADA = "CANTIDAD_SOLICITADA";
	private static final String BODEGA_ORIGEN_CODIGO_ALTERNO = "BODEGA_ORIGEN_CODIGO_ALTERNO";
	private static final String BODEGA_DESTINO_CODIGO_ALTERNO = "BODEGA_DESTINO_CODIGO_ALTERNO";

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

		list.add(PREFIJO_ORDEN);
		list.add(NUMERO_ORDEN);

		list.add(PRODUCTO_CODIGO);
		list.add(CANTIDAD_SOLICITADA);
		list.add(CAMPO_IGNORAR);
		list.add(BODEGA_ORIGEN_CODIGO_ALTERNO);
		list.add(BODEGA_DESTINO_CODIGO_ALTERNO);

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
	protected RegistroDTO<DespachoLineaDTO> transformar(String registro, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {
		// @formatter:off
		DespachoLineaDTO dto = DespachoLineaDTO
				.builder()
				.prefijoOrden(campos[mapNameToIndex.get(PREFIJO_ORDEN)])
				.numeroOrden(campos[mapNameToIndex.get(NUMERO_ORDEN)])
				.productoCodigo(campos[mapNameToIndex.get(PRODUCTO_CODIGO)])
				.cantidadSolicitada(campos[mapNameToIndex.get(CANTIDAD_SOLICITADA)])
				.bodegaOrigenCodigoAlterno(campos[mapNameToIndex.get(BODEGA_ORIGEN_CODIGO_ALTERNO)])
				.bodegaDestinoCodigoAlterno(campos[mapNameToIndex.get(BODEGA_DESTINO_CODIGO_ALTERNO)])
				.build();
		// @formatter:on

		Set<ConstraintViolation<DespachoLineaDTO>> constraints = validator.validate(dto);

		StringBuffer sb = new StringBuffer();
		for (ConstraintViolation<DespachoLineaDTO> e : constraints) {
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
				.<DespachoLineaDTO>builder()
				.registro(registro)
				.estado(constraints.isEmpty()?Status.ACEPTADO:Status.ERROR)
				.causaError(sb.toString())
				.data(dto)
				.build();
		// @formatter:on
	}

}
