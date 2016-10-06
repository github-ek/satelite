package com.tacticlogistics.infrastructure.persistence.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.crm.TipoServicio;

public interface TipoServicioRepository extends JpaRepository<TipoServicio, Integer> {
	TipoServicio findByCodigoIgnoringCase(String codigo);

	@Query(""
	        + " SELECT c"
	        + " FROM TipoServicio c"
	        + " WHERE c.id IN"
	        + " ("
	        + " SELECT DISTINCT a.tipoServicioId"
			+ " FROM ClienteTipoServicioAssociation a"
			+ " WHERE a.clienteId = :clienteId"
			+ " AND a.codigoAlterno = :codigoAlterno"
			+ " )"
			+ " ORDER BY c.ordinal")
	TipoServicio findFisrtByClienteIdAndCodigoAlterno(
			@Param("clienteId") Integer clienteId,
			@Param("codigoAlterno") String codigoAlterno);

	
	@Query(""
	        + " SELECT c"
	        + " FROM TipoServicio c"
	        + " WHERE c.id IN"
	        + " ("
	        + " SELECT DISTINCT b.tipoServicioId"
			+ " FROM UsuarioClienteAssociation a"
			+ " JOIN a.cliente.clienteTipoServicioAssociation b"
			+ " WHERE a.usuario.id = :usuarioId"
			+ " )"
			+ " ORDER BY c.ordinal")
	List<TipoServicio> findByUsuarioId(
			@Param("usuarioId") Integer usuarioId);
}
