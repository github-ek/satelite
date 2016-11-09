package com.tacticlogistics.application.etl.gws.ordenes;

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
import com.tacticlogistics.application.etl.gws.ordenes.dto.OrdenDTO;

@Component
public class OrdenesFileExtractor extends FileExtractor<OrdenDTO> {
	private static final String SERVICIO_CODIGO_ALTERNO = "SERVICIO_CODIGO_ALTERNO";
	private static final String NUMERO_ORDEN_COMPRA = "NUMERO_ORDEN_COMPRA";
	private static final String PREFIJO_ORDEN = "PREFIJO_ORDEN";
	private static final String NUMERO_ORDEN = "NUMERO_ORDEN";

	private static final String DESTINATARIO_IDENTIFICACION = "DESTINATARIO_IDENTIFICACION";
	private static final String DESTINATARIO_CANAL_CODIGO_ALTERNO = "DESTINATARIO_CANAL_CODIGO_ALTERNO";

	private static final String DESTINO_NOMBRE = "DESTINO_NOMBRE";
	private static final String DESTINO_DIRECCION = "DESTINO_DIRECCION";
	private static final String CIUDAD_DESTINO_CODIGO_ALTERNO = "CIUDAD_DESTINO_CODIGO_ALTERNO";

	private static final String FECHA_MINIMA = "FECHA_MINIMA";
	private static final String FECHA_MAXIMA = "FECHA_MAXIMA";
	private static final String HORA_MINIMA = "HORA_MINIMA";
	private static final String HORA_MAXIMA = "HORA_MAXIMA";

	private static final String NOTAS = "NOTAS";

	private static final String LINEA_PRODUCTO_CODIGO = "LINEA_PRODUCTO_CODIGO";
	private static final String LINEA_DESCRIPCION = "LINEA_DESCRIPCION";
	private static final String LINEA_CANTIDAD_SOLICITADA = "LINEA_CANTIDAD_SOLICITADA";
	private static final String LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO = "LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO";
	private static final String LINEA_BODEGA_DESTINO_CODIGO_ALTERNO = "LINEA_BODEGA_DESTINO_CODIGO_ALTERNO";
	private static final String LINEA_VALOR_DECLARADO_POR_UNIDAD = "LINEA_VALOR_DECLARADO_POR_UNIDAD";

	private static final String LINEA_PREDISTRIBUCION_DESTINO_FINAL = "LINEA_PREDISTRIBUCION_DESTINO_FINAL";
	private static final String LINEA_PREDISTRIBUCION_ROTULO = "LINEA_PREDISTRIBUCION_ROTULO";

	private static final String REQUIERE_RECAUDO = "REQUIERE_RECAUDO";
	private static final String VALOR_RECAUDO = "VALOR_RECAUDO";

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

		list.add(SERVICIO_CODIGO_ALTERNO);
		list.add(NUMERO_ORDEN_COMPRA);
		list.add(PREFIJO_ORDEN);
		list.add(NUMERO_ORDEN);

		list.add(DESTINATARIO_IDENTIFICACION);
		list.add(DESTINATARIO_CANAL_CODIGO_ALTERNO);

		list.add(DESTINO_NOMBRE);
		list.add(DESTINO_DIRECCION);
		list.add(CIUDAD_DESTINO_CODIGO_ALTERNO);

		list.add(CAMPO_IGNORAR);
		list.add(FECHA_MINIMA);
		list.add(FECHA_MAXIMA);
		list.add(HORA_MINIMA);
		list.add(HORA_MAXIMA);

		list.add(NOTAS);

		list.add(LINEA_PRODUCTO_CODIGO);
		list.add(LINEA_DESCRIPCION);
		list.add(LINEA_CANTIDAD_SOLICITADA);
		list.add(LINEA_BODEGA_ORIGEN_CODIGO_ALTERNO);
		list.add(LINEA_BODEGA_DESTINO_CODIGO_ALTERNO);
		list.add(LINEA_VALOR_DECLARADO_POR_UNIDAD);

		list.add(LINEA_PREDISTRIBUCION_DESTINO_FINAL);
		list.add(LINEA_PREDISTRIBUCION_ROTULO);

		list.add(REQUIERE_RECAUDO);
		list.add(VALOR_RECAUDO);

		return list;
	}

	@Override
	protected boolean seDebenIncluirEncabezados() {
		return true;
	}

	@Override
	protected boolean ignorarRegistroDespuesDeSerSeparadoPorCampos(String[] campos,
			Map<String, Integer> mapNameToIndex) {
		if (super.ignorarRegistroDespuesDeSerSeparadoPorCampos(campos, mapNameToIndex)) {
			return true;
		}
		
		return ("".equals(campos[mapNameToIndex.get(NUMERO_ORDEN)]));
	}

	@Override
	protected RegistroDTO<OrdenDTO> transformar(String registro, String[] campos,
			Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {

		// @formatter:off
		OrdenDTO dto = OrdenDTO
				.builder()
				.notas(campos[mapNameToIndex.get(NOTAS)])
				.prefijoOrden(campos[mapNameToIndex.get(PREFIJO_ORDEN)])
				.numeroOrden(campos[mapNameToIndex.get(NUMERO_ORDEN)])
				.destinatarioIdentificacion(campos[mapNameToIndex.get(DESTINATARIO_IDENTIFICACION)])
				.build();
		// @formatter:on

		Set<ConstraintViolation<OrdenDTO>> constraints = validator.validate(dto);

		StringBuffer sb = new StringBuffer();
		for (ConstraintViolation<OrdenDTO> e : constraints) {
			sb.append("[").append(e.getPropertyPath()).append(",").append(e.getInvalidValue()).append("]:")
					.append(e.getMessage()).append("\n");
		}

		// @formatter:off
		return RegistroDTO
				.<OrdenDTO>builder()
				.registro(registro)
				.estado(constraints.isEmpty()?Status.ACEPTADO:Status.ERROR)
				.causaError(sb.toString())
				.data(dto)
				.build();
		// @formatter:on
	}

}
