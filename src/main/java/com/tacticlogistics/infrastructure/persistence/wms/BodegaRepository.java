package com.tacticlogistics.infrastructure.persistence.wms;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.presentation.api.wms.ingresos.dto.ListItemBodegaDto;

public interface BodegaRepository extends JpaRepository<Bodega, Integer> {
	Bodega findByCodigoIgnoringCase(String codigo);
	
	@Query(""
			+ " SELECT DISTINCT b.bodega"
			+ " FROM Producto a"
			+ " JOIN a.productoBodegaAssociation b"
			+ " WHERE :tipoServicioId = :tipoServicioId"
			+ "	AND a.cliente.id = :clienteId"
			+ " AND a.activo = true"
			+ " AND b.bodega.activo = true"
			+ " AND b.bodega.direccion.ciudadId = :ciudadId"
			+ " ORDER BY b.bodega.codigo")
	List<Bodega> findAllByClienteIdAndCiudadIdAndTipoServicioId(
			@Param("clienteId") Integer clienteId,
			@Param("ciudadId") Integer ciudadId,
			@Param("tipoServicioId") Integer tipoServicioId);

	@Query(""
			+ " SELECT DISTINCT a.bodega"
			+ " FROM ProductoBodegaAssociation a"
			+ " WHERE a.producto.id = :productoId"
			+ " AND a.producto.activo = true"
			+ " AND a.bodega.activo = true"
			+ " ORDER BY a.bodega.codigo")
	List<Bodega> findAllByProductoId(
			@Param("productoId") Integer productoId);
	
	@Query(""
			+ " SELECT DISTINCT a.bodega"
			+ " FROM ProductoBodegaAssociation a"
			+ " WHERE a.producto.id = :productoId"
			+ " AND a.producto.activo = true"
			+ " AND a.bodega.direccion.ciudadId = :ciudadId"
			+ " AND a.bodega.activo = true"
			+ " ORDER BY a.bodega.codigo")
	List<Bodega> findAllByProductoIdAndCiudadId(
			@Param("productoId") Integer productoId,
			@Param("ciudadId") Integer ciudadId);	
	
   @Query(""
            + " SELECT DISTINCT a"
            + " FROM Bodega a"
            + " WHERE a.direccion.ciudadId = :ciudadId"
            + " AND a.activo = true"
            + " ORDER BY a.codigo")
    List<Bodega> findByCiudadId(
            @Param("ciudadId") Integer ciudadId);   

   @Query(""
           + " SELECT new com.tacticlogistics.presentation.api.wms.ingresos.dto.ListItemBodegaDto("
           + "  a.bodega.id, "
           + "  a.bodega.codigo, "
           + "  a.bodega.nombre,"
           + "  b.nombreAlterno,"
           + "  a.bodega.direccion.direccion)"
           + " FROM UsuarioBodegaAssociation a"
           + " ,Ciudad b "
           + " WHERE 0 = 0"
           + " AND b.id = a.bodega.direccion.ciudadId"
           + " AND a.usuario.id = :usuarioId")
   List<ListItemBodegaDto> findByUsuarioId(
           @Param("usuarioId") Integer usuarioId,Sort sort);   
}
