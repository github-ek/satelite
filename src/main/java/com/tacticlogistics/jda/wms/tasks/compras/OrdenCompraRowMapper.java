package com.tacticlogistics.jda.wms.tasks.compras;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class OrdenCompraRowMapper implements RowMapper<OrdenCompra> {

	@Override
	public OrdenCompra mapRow(ResultSet rs, int rowNum) throws SQLException {
		OrdenCompra oc = new OrdenCompra();
		oc.setIdOrden(rs.getInt("id_orden"));
		oc.setNumeroOrden("TC-" + rs.getString("id_orden") + '-' + rs.getString("numero_orden"));
		oc.setProveedor(rs.getString("codigo_alterno_wms"));
		oc.setClienteId(rs.getString("codigo_alterno_wms"));
		oc.setBodega(rs.getString("bodega_destino_codigo"));
		oc.setTipoOrden("REC");
		oc.setEstadoOrden("OPEN");

		LineaOrdenCompra ocLinea = new LineaOrdenCompra();
		ocLinea.setNumeroLinea(rs.getInt("numero_item"));
		ocLinea.setCantidadEsperada(rs.getInt("cantidad_solicitada"));
		ocLinea.setCodigoProducto(rs.getString("codigo_producto"));
		ocLinea.setEstadoInventarioEsperado(rs.getString("id_estado_inventario_destino") != ""
				? rs.getString("id_estado_inventario_destino") : "A");
		ocLinea.setLote(rs.getString("lote"));
		ocLinea.setEstampillado(rs.getString("requerimiento_estampillado"));

		oc.addLinea(ocLinea);

		return oc;
	}

}
