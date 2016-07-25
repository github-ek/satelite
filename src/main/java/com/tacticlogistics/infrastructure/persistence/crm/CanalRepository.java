package com.tacticlogistics.infrastructure.persistence.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.crm.Canal;

public interface CanalRepository extends JpaRepository<Canal, Integer> {

	@Query(""
			+ " SELECT b"
			+ " FROM Canal b"
			+ " WHERE b.activo = true "
			+ " AND b.id IN "
			+ " ("

			+ " SELECT DISTINCT a.canalId"
            + " FROM DestinatarioRemitente a"
            + " WHERE a.clienteId = :clienteId "
            + " AND a.activo = true"
			
			+ " )"
			+ " ORDER BY b.ordinal")
	List<Canal> findAllByClienteId(
			@Param("clienteId") Integer clienteId);
	
	Canal findByNombreIgnoringCase(String nombre);
}
