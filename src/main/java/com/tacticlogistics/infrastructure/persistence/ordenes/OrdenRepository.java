package com.tacticlogistics.infrastructure.persistence.ordenes;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.presentation.api.wms.ingresos.dto.GridItemOrdenIngresoDto;

public interface OrdenRepository extends JpaRepository<Orden, Integer> {
	Orden findFirstByClienteIdAndNumeroOrden(Integer clienteId, String numeroOrden);

	
	@Query("" + " SELECT a" + " FROM Orden a, UsuarioClienteAssociation b" + " WHERE a.cliente.id = b.cliente.id"
			+ " AND b.usuario.id = :usuarioId" + " AND a.tipoServicio.id = :tipoServicioId"
			+ " AND ((:estadoOrden IS NULL) OR (a.estadoOrden = :estadoOrden)) "
			+ " AND ((:clienteId IS NULL) OR (a.cliente.id = :clienteId)) " + " ORDER BY a.id DESC")
	List<Orden> findByUsuarioIdAndTipoServicioIdAndEstadoOrden(@Param("usuarioId") Integer usuarioId,
			@Param("tipoServicioId") Integer tipoServicioId, @Param("estadoOrden") EstadoOrdenType estadoOrden,
			@Param("clienteId") Integer clienteId);

	@Query("" + " SELECT new com.tacticlogistics.presentation.api.wms.ingresos.dto.GridItemOrdenIngresoDto" + " ("
			+ "     a.id, " 
			+ "     a.cliente.codigo, " 
			+ "     a.estadoOrden," 
			+ "     a.numeroOrden,"
			+ "     a.fechaEntregaSugeridaMinima," 
			+ "     a.fechaEntregaSugeridaMaxima,"
			+ "     a.horaEntregaSugeridaMinima," 
			+ "     a.horaEntregaSugeridaMaxima, "

			+ "     a.fechaActualizacion, " 
			+ "     a.usuarioActualizacion, "

			+ "     a.fechaConfirmacion, " 
			+ "     a.usuarioConfirmacion, "

			+ "     a.fechaAceptacion, " 
			+ "     a.usuarioAceptacion " + " )" 
			+ " FROM Orden a " 
			+ " WHERE 0 = 0"
			+ " AND a.tipoServicio.id = :tipoServicioId" 
			+ " AND a.estadoOrden = :estadoOrden "
			// + " AND a.bodegaDestino.id = :estadoOrden "
			+ " ORDER BY a.id DESC")
	Page<GridItemOrdenIngresoDto> findByTipoServicioIdAndEstadoOrdenConPaginacion(
			@Param("tipoServicioId") Integer tipoServicioId, @Param("estadoOrden") EstadoOrdenType estadoOrden,
			Pageable pageRequest);
}
