package com.tacticlogistics.infrastructure.persistence.ordenes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.oms.EstadoOrdenType;
import com.tacticlogistics.domain.model.ordenes.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Integer> {
	Orden findFirstByClienteIdAndNumeroOrden(Integer clienteId, String numeroOrden);

	
	@Query("" + " SELECT a" + " FROM Orden a, UsuarioClienteAssociation b" + " WHERE a.cliente.id = b.cliente.id"
			+ " AND b.usuario.id = :usuarioId" + " AND a.tipoServicio.id = :tipoServicioId"
			+ " AND ((:estadoOrden IS NULL) OR (a.estadoOrden = :estadoOrden)) "
			+ " AND ((:clienteId IS NULL) OR (a.cliente.id = :clienteId)) " + " ORDER BY a.id DESC")
	List<Orden> findByUsuarioIdAndTipoServicioIdAndEstadoOrden(@Param("usuarioId") Integer usuarioId,
			@Param("tipoServicioId") Integer tipoServicioId, @Param("estadoOrden") EstadoOrdenType estadoOrden,
			@Param("clienteId") Integer clienteId);

}
