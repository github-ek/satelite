package com.tacticlogistics.infrastructure.persistence.geo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.geo.Ciudad;

public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {
    Ciudad findByCodigo(String codigo);

    Ciudad findByNombreAlternoIgnoringCase(String nombreAlterno);
    
    @Query(""
            + " SELECT c"
            + " FROM Ciudad c"
            + " WHERE c.activo = true"
            + " AND c.id IN "
            + " ("
            
            + " SELECT DISTINCT a.ciudadId"
            + " FROM ClienteCiudadAssociation a"
            + " WHERE a.clienteId = :clienteId"
            + " AND a.nombreAlterno = :nombreAlterno"
            
            + " )")
	Ciudad findTop1ByClienteIdAndNombreAlterno(
			@Param("clienteId") Integer clienteId,
			@Param("nombreAlterno") String nombreAlterno);

//    @Query("" 
//	    + " SELECT a" 
//	    + " FROM Ciudad a" 
//	    + " WHERE a.activo = true" 
//	    + " ORDER BY a.ordinal")
//    List<Ciudad> findByOrderByOrdinalAsc();
//
//    @Query("" 
//            + " SELECT DISTINCT b" 
//            + " FROM DestinoOrigen a, Ciudad b"
//            + " WHERE a.direccion.ciudadId = b.id"
//            + " AND a.destinatarioRemitenteId = :destinatarioRemitenteId" 
//            + " ORDER BY b.ordinal")
//    List<Ciudad> findByDestinatarioRemitenteIdAndTipoServicioId(
//            @Param("destinatarioRemitenteId") Integer destinatarioRemitenteId);
//
//    @Query(""
//        + " SELECT c"
//        + " FROM Ciudad c"
//        + " WHERE :tipoServicioId = :tipoServicioId"
//        + " AND c.activo = true"
//        + " AND c.id IN "
//        + " ("
//        
//        + " SELECT DISTINCT b.bodega.direccion.ciudadId"
//        + " FROM Producto a"
//        + " JOIN a.productoBodegaAssociation b"
//        + " WHERE a.cliente.id = :clienteId"
//        + " AND a.activo = true"
//        + " AND b.bodega.activo = true"
//        
//        + " )"
//        + " ORDER BY c.ordinal")
//	List<Ciudad> findByClienteIdAndTipoServicioId(
//			@Param("clienteId") Integer clienteId,
//			@Param("tipoServicioId") Integer tipoServicioId);
//
//    @Query("" 
//            + " SELECT c"
//            + " FROM Ciudad c"
//            + " WHERE c.activo = true"
//            + " AND c.id IN "
//            + " ("
//            
//            + " SELECT DISTINCT a.bodega.direccion.ciudadId"
//            + " FROM ProductoBodegaAssociation a"
//            + " WHERE a.producto.id = :productoId"
//            + " AND a.producto.activo = true"
//            + " AND a.bodega.activo = true"
//            
//            + " )"
//            + " ORDER BY c.ordinal")
//    List<Ciudad> findByProductoId(@Param("productoId") Integer productoId);
//
//    @Query("" 
//            + " SELECT DISTINCT c" 
//            + " FROM Bodega a" 
//            + " , Ciudad c" 
//            + " WHERE a.activo = true"
//            + " AND c.id = a.direccion.ciudadId" 
//            + " ORDER BY c.ordinal")
//    List<Ciudad> findConBodegas();
}
