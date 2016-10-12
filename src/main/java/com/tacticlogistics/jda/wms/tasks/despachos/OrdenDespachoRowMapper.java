package com.tacticlogistics.jda.wms.tasks.despachos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class OrdenDespachoRowMapper implements RowMapper<OrdenDespacho> {

	@Override
	public OrdenDespacho mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrdenDespacho oc = new OrdenDespacho();
		oc.setIdOrden(rs.getInt("id_orden"));
		oc.setNumeroOrden("TC-" + rs.getString("id_orden") + '-' + rs.getString("numero_orden"));
		oc.setTipoOrden("RET");
		oc.setDireccionDespacho("TACTICBG01_11001");
		oc.setDireccionDespachoId("A000000001");
		oc.setDireccionEnvio("TACTICBG01_11001");
		oc.setDireccionEnvioId("A000000001");
		oc.setDireccionFacturacion("TACTICBG01_11001");
		oc.setDireccionFacturacionId("A000000001");
		oc.setCodigoCliente(rs.getString("codigo_alterno_wms"));
		oc.setBodega(rs.getString("bodega_origen_codigo"));
		
		LineaOrdenDespacho ocLinea = new LineaOrdenDespacho();
		ocLinea.setNumeroOrden(oc.getNumeroOrden());
		ocLinea.setBodega(oc.getBodega());
		ocLinea.setNumeroLinea(rs.getInt("numero_item"));
		ocLinea.setCantidadSolicitad(rs.getInt("cantidad_solicitada"));
		ocLinea.setCodigoProducto(rs.getString("codigo_producto"));
		ocLinea.setCodigoCliente(rs.getString("codigo_alterno_wms"));
		ocLinea.setEstadoInventario(
				rs.getString("id_estado_inventario_origen") != "" ? rs.getString("id_estado_inventario_origen") : "A");
		ocLinea.setLote(rs.getString("lote"));
		ocLinea.setEstampillado(rs.getString("requerimiento_estampillado"));
		ocLinea.setTransportista("GENERAL");
		ocLinea.setNivelServicio("GENERAL");
		
		oc.getLineas().add(ocLinea);
		
		return oc;
	}

}
