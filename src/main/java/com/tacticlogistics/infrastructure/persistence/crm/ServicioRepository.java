package com.tacticlogistics.infrastructure.persistence.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.crm.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
	Servicio findByCodigoIgnoringCase(String codigo);

	@Query(""
	        + " SELECT c"
	        + " FROM Servicio c"
	        + " WHERE c.id IN"
	        + " ("
	        + " SELECT DISTINCT a.servicioId"
			+ " FROM ClienteServicioAssociation a"
			+ " WHERE a.clienteId = :clienteId"
			+ " AND a.codigoAlterno = :codigoAlterno"
			+ " )"
			+ " ORDER BY c.ordinal")
	Servicio findFisrtByClienteIdAndCodigoAlterno(
			@Param("clienteId") Integer clienteId,
			@Param("codigoAlterno") String codigoAlterno);

	
	@Query(""
	        + " SELECT c"
	        + " FROM Servicio c"
	        + " WHERE c.id IN"
	        + " ("
	        + " SELECT DISTINCT b.servicioId"
			+ " FROM UsuarioClienteAssociation a"
			+ " JOIN a.cliente.clienteServicioAssociation b"
			+ " WHERE a.usuario.id = :usuarioId"
			+ " )"
			+ " ORDER BY c.ordinal")
	List<Servicio> findByUsuarioId(
			@Param("usuarioId") Integer usuarioId);
}
