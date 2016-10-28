package com.tacticlogistics.infrastructure.persistence.oms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.oms.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Integer> {
	Orden findFirstByClienteIdAndNumeroOrden(Integer clienteId, String numeroOrden);

	// @formatter:off
	@Query("" 
	+ " SELECT a " 
	+ " FROM Orden a, "
	+ " UsuarioClienteAssociation b" 
	+ " WHERE a.cliente.id = b.cliente.id"
	+ " AND b.usuario.id = :usuarioId" 
	+ " AND a.servicio.id = :servicioId"
	+ " AND ((:estadoOrden IS NULL) OR (a.estadoOrden = :estadoOrden)) "
	+ " AND ((:clienteId IS NULL) OR (a.cliente.id = :clienteId)) " 
	+ " ORDER BY a.id DESC")
	List<Orden> findByUsuarioIdAndServicioIdAndEstadoOrden(
			@Param("usuarioId") Integer usuarioId,
			@Param("servicioId") Integer servicioId, 
			@Param("estadoOrden") EstadoOrdenType estadoOrden,
			@Param("clienteId") Integer clienteId);
	// @formatter:on


}
