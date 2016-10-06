package com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes;


import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.CLIENTE_CODIGO;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.CLIENTE_RECOGE;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.CONFIRMAR_CITA;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.DESTINATARIO_CANAL_CODIGO;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.DESTINATARIO_IDENTIFICACION;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.DESTINATARIO_NOMBRE;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.FECHA_MAXIMA;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.FECHA_MINIMA;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.HORA_MAXIMA;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.HORA_MINIMA;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.ID_CARGA;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_BL;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_BODEGA_DESTINO_CODIGO;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_CANTIDAD_SOLICITADA;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_COSECHA;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_DESCRIPCION;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_DISTRIBUIDO;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_ESTADO_DESTINO;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_ESTAMPILLADO;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_FONDOCUENTA;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_IMPORTE;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_LOTE;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_NUTRICIONAL;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_ORIGEN;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_PINADO;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_PRODUCTO_CODIGO;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_SALUD;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.LINEA_SERIAL;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.NOTAS;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.NUMERO_ORDEN;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.ORIGEN_CIUDAD_NOMBRE_ALTERNO;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.ORIGEN_CONTACTO_EMAIL;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.ORIGEN_CONTACTO_NOMBRES;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.ORIGEN_CONTACTO_TELEFONOS;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.ORIGEN_DIRECCION;
import static com.tacticlogistics.application.tasks.etl.components.tactic.oms.ordenes.MacroExcelOrdenDtoAtributos.ORIGEN_NOMBRE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.etl.ETLLineaOrdenDto;
import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.application.services.ordenes.OrdenesApplicationService;
import com.tacticlogistics.application.tasks.etl.components.ETLOrdenesExcelFileStrategy;
import com.tacticlogistics.domain.model.ordenes.Orden;

@Component("TACTIC.OMS.EXCEL.INGRESOS")
public class OrdenesDeIngresoCargadasPorExcel extends ETLOrdenesExcelFileStrategy {
	protected static final String CODIGO_SERVICIO_RECIBOS_PRIMARIA = "COMPRAS";
	protected static final String CODIGO_CANAL_PREDETERMINADO = "OTROS";
	
    @Autowired
    private OrdenesApplicationService ordenesService;

    protected String getTipoServicioCodigo() {
        return CODIGO_SERVICIO_RECIBOS_PRIMARIA;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected List<String> getCamposEsperados() {
        List<String> list = new ArrayList<>();

		list.add(CLIENTE_CODIGO.toString());
		list.add(NUMERO_ORDEN.toString());
		list.add(CLIENTE_RECOGE.toString());
        
		list.add(DESTINATARIO_CANAL_CODIGO.toString());
		list.add(DESTINATARIO_IDENTIFICACION.toString());
		list.add(DESTINATARIO_NOMBRE.toString());
		
		list.add(ORIGEN_CIUDAD_NOMBRE_ALTERNO.toString());
		list.add(ORIGEN_DIRECCION.toString());
		list.add(ORIGEN_NOMBRE.toString());
		list.add(ORIGEN_CONTACTO_EMAIL.toString());
		list.add(ORIGEN_CONTACTO_NOMBRES.toString());
		list.add(ORIGEN_CONTACTO_TELEFONOS.toString());

		list.add(CONFIRMAR_CITA.toString());
		list.add(FECHA_MINIMA.toString());
		list.add(FECHA_MAXIMA.toString());
		list.add(HORA_MINIMA.toString());
		list.add(HORA_MAXIMA.toString());
		
        list.add(NOTAS.toString());

        list.add(LINEA_DESCRIPCION.toString());
        list.add(LINEA_PRODUCTO_CODIGO.toString());
        list.add(LINEA_CANTIDAD_SOLICITADA.toString());

        list.add(LINEA_BODEGA_DESTINO_CODIGO.toString());
        list.add(LINEA_ESTADO_DESTINO.toString());

		list.add(LINEA_LOTE.toString());
		list.add(LINEA_SERIAL.toString());
		list.add(LINEA_COSECHA.toString());
		list.add(LINEA_BL.toString());
		list.add(LINEA_FONDOCUENTA.toString());
		
		list.add(LINEA_DISTRIBUIDO.toString());
		list.add(LINEA_ESTAMPILLADO.toString());
		list.add(LINEA_IMPORTE.toString());
		list.add(LINEA_NUTRICIONAL.toString());
		list.add(LINEA_ORIGEN.toString());
		list.add(LINEA_SALUD.toString());
		list.add(LINEA_PINADO.toString());
        
        list.add(ID_CARGA.toString());

        return list;
    }

    @Override
    // ---------------------------------------------------------------------------------------------------------------------------------------
    protected String generarIdentificadorRegistro(String[] campos, Map<String, Integer> mapNameToIndex) {
        String value;

        value = getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex).trim();

        return value;
    }
    
    // ---------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void adicionar(String key, Map<String, ETLOrdenDto> map, String[] campos,
            Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {

        if (!map.containsKey(key)) {
            String value;
            LocalDate dateValue;
            LocalTime timeValue;

            ETLOrdenDto dto = new ETLOrdenDto();

            dto.setTipoServicioCodigo(getTipoServicioCodigo());
            dto.setTipoServicioCodigoAlterno("");

            
			// ---------------------------------------------------------------------------------------------------------
            value = getValorCampo(CLIENTE_CODIGO, campos, mapNameToIndex);
            dto.setClienteCodigo(value);

            value = getValorCampo(NUMERO_ORDEN, campos, mapNameToIndex);
            dto.setNumeroOrden(value);

			value = getValorCampo(CLIENTE_RECOGE, campos, mapNameToIndex);
			dto.setRequiereServicioDistribucion((NO.equalsIgnoreCase(value)));


			// ---------------------------------------------------------------------------------------------------------
			value = getValorCampo(DESTINATARIO_CANAL_CODIGO, campos, mapNameToIndex);
			if (value.isEmpty()) {
				value = CODIGO_CANAL_PREDETERMINADO;
			}
			dto.setCanalCodigoAlterno(value);

			value = getValorCampo(DESTINATARIO_IDENTIFICACION, campos, mapNameToIndex);
			dto.setDestinatarioNumeroIdentificacion(value);

			value = getValorCampo(DESTINATARIO_NOMBRE, campos, mapNameToIndex);
			dto.setDestinatarioNombre(value);

			
			// ---------------------------------------------------------------------------------------------------------
			value = getValorCampo(ORIGEN_CIUDAD_NOMBRE_ALTERNO, campos, mapNameToIndex);
			dto.setOrigenCiudadNombreAlterno(value);

			value = getValorCampo(ORIGEN_DIRECCION, campos, mapNameToIndex);
			dto.setOrigenDireccion(value);

			value = getValorCampo(ORIGEN_NOMBRE, campos, mapNameToIndex);
			dto.setOrigenNombre(value);

			value = getValorCampo(ORIGEN_CONTACTO_EMAIL, campos, mapNameToIndex);
			dto.setOrigenContactoEmail(value);

			value = getValorCampo(ORIGEN_CONTACTO_NOMBRES, campos, mapNameToIndex);
			dto.setOrigenContactoNombres(value);

			value = getValorCampo(ORIGEN_CONTACTO_TELEFONOS, campos, mapNameToIndex);
			dto.setOrigenContactoTelefonos(value);

			
			// ---------------------------------------------------------------------------------------------------------
			value = getValorCampo(CONFIRMAR_CITA, campos, mapNameToIndex);
			dto.setRequiereConfirmacionCitaRecogida((SI.equalsIgnoreCase(value)));

			value = getValorCampo(FECHA_MAXIMA, campos, mapNameToIndex);
			dateValue = getValorCampoFecha(key, FECHA_MAXIMA, value, getFormatoFechaCorta());
			dto.setFechaRecogidaSugeridaMaxima(dateValue);

			value = getValorCampo(FECHA_MINIMA, campos, mapNameToIndex);
			if (!value.isEmpty()) {
				dateValue = getValorCampoFecha(key, FECHA_MINIMA, value, getFormatoFechaCorta());
			} else {
				dateValue = dto.getFechaRecogidaSugeridaMaxima();
			}
			dto.setFechaRecogidaSugeridaMinima(dateValue);

			value = getValorCampo(HORA_MINIMA, campos, mapNameToIndex);
			timeValue = getValorCampoHora(key, HORA_MINIMA, value, getFormatoHoraHH());
			dto.setHoraRecogidaSugeridaMinima(timeValue);

			value = getValorCampo(HORA_MAXIMA, campos, mapNameToIndex);
			timeValue = getValorCampoHora(key, HORA_MAXIMA, value, getFormatoHoraHH());
			dto.setHoraRecogidaSugeridaMaxima(timeValue);


			// ---------------------------------------------------------------------------------------------------------
            value = getValorCampo(NOTAS, campos, mapNameToIndex);
            dto.setNotasConfirmacion(value);


            // ---------------------------------------------------------------------------------------------------------
            value = getValorCampoUsuarioConfirmacion(campos, mapNameToIndex);
            dto.setUsuarioConfirmacion(value);


            // ---------------------------------------------------------------------------------------------------------
            map.put(key, dto);
        }
    }

    @Override
    protected void modificar(String key, Map<String, ETLOrdenDto> map, String[] campos,
            Map<String, Integer> mapNameToIndex, Map<Integer, String> mapIndexToName) {
		if (map.containsKey(key)) {
			String value;
			Integer integerValue;

			ETLLineaOrdenDto dto = new ETLLineaOrdenDto();

			value = getValorCampo(LINEA_DESCRIPCION, campos, mapNameToIndex);
			dto.setDescripcion(value);

			value = getValorCampo(LINEA_PRODUCTO_CODIGO.toString(), campos, mapNameToIndex);
			value = value.replaceFirst("^[`'´]", "");
			dto.setProductoCodigo(value);

			value = getValorCampo(LINEA_CANTIDAD_SOLICITADA.toString(), campos, mapNameToIndex);
			integerValue = getValorCampoDecimal(key, LINEA_CANTIDAD_SOLICITADA.toString(), value, getFormatoEntero());
			dto.setCantidadSolicitada(integerValue);

			value = getValorCampo(LINEA_BODEGA_DESTINO_CODIGO, campos, mapNameToIndex);
			dto.setBodegaDestinoCodigo(value);

			value = getValorCampo(LINEA_ESTADO_DESTINO, campos, mapNameToIndex);
			dto.setEstadoInventarioDestino(value);


			// ---------------------------------------------------------------------------------------------------------

			// ---------------------------------------------------------------------------------------------------------
			value = getValorCampo(LINEA_LOTE, campos, mapNameToIndex);
			dto.setLote(value);

			value = getValorCampo(LINEA_SERIAL, campos, mapNameToIndex);
			dto.setSerial(value);

			value = getValorCampo(LINEA_COSECHA, campos, mapNameToIndex);
			dto.setCosecha(value);

			value = getValorCampo(LINEA_BL, campos, mapNameToIndex);
			dto.setRequerimientoBl(value);

			value = getValorCampo(LINEA_FONDOCUENTA, campos, mapNameToIndex);
			dto.setRequerimientoFondoCuenta(value);

			value = getValorCampo(LINEA_DISTRIBUIDO, campos, mapNameToIndex);
			dto.setRequerimientoDistribuido(value);
			
			value = getValorCampo(LINEA_ESTAMPILLADO, campos, mapNameToIndex);
			dto.setRequerimientoEstampillado(value);

			value = getValorCampo(LINEA_IMPORTE, campos, mapNameToIndex);
			dto.setRequerimientoImporte(value);

			value = getValorCampo(LINEA_NUTRICIONAL, campos, mapNameToIndex);
			dto.setRequerimientoNutricional(value);

			value = getValorCampo(LINEA_ORIGEN, campos, mapNameToIndex);
			dto.setRequerimientoOrigen(value);

			value = getValorCampo(LINEA_SALUD, campos, mapNameToIndex);
			dto.setRequerimientoSalud(value);

			value = getValorCampo(LINEA_PINADO, campos, mapNameToIndex);
			dto.setRequerimientoPinado(value);

			map.get(key).getLineas().add(dto);
		}
    }

    @Override
    @Transactional(readOnly = false)
    protected void cargar(Map<String, ETLOrdenDto> map) {
        for (Entry<String, ETLOrdenDto> entry : map.entrySet()) {
            ETLOrdenDto dto = entry.getValue();
            try {
                Orden orden = ordenesService.saveOrdenReciboPrimaria(dto);
                if(orden != null){
                    logInfo(dto.getNumeroOrden(), "", "OK");
                }else{
                    logWarning(dto.getNumeroOrden(), "", "Una  solicitud para el mismo cliente con el mismo numero ya se encuentra registrada.");
                }
            } catch (Exception e) {
                logError(dto.getNumeroOrden(), "", e.getMessage());
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    protected String getValorCampoUsuarioConfirmacion(String[] campos, Map<String, Integer> mapNameToIndex) {
        String value = getValorCampo(ID_CARGA, campos, mapNameToIndex).replace("INGRESOS_", "");
        String list[] = getArchivo().getName().split("_");
        if (list.length >= 3) {
            value = list[2] + "(" + value + ")";
        }
        return value;
    }

}
