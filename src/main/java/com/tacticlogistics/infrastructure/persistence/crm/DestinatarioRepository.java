package com.tacticlogistics.infrastructure.persistence.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.crm.Destinatario;

public interface DestinatarioRepository extends JpaRepository<Destinatario, Integer> {
	Destinatario findByNumeroIdentificacion(String codigo);

	List<Destinatario> findAllByClienteIdAndCanalIdOrderByNombre(
			Integer clienteId, Integer canalId);
	
    @Query(""
            + " SELECT a"
            + " FROM Destinatario a"
            + " WHERE a.clienteId = :clienteId"
            + " AND ((:canalId IS NULL) OR (a.canalId = :canalId))"
            + " AND ((numeroIdentificacion LIKE :filtro) OR (nombre LIKE :filtro))"
            + " ORDER BY a.nombre")
    List<Destinatario> findByClienteIdAndCanalIdAndFiltro(
            @Param("clienteId") Integer clienteId,
            @Param("canalId") Integer canalId,
            @Param("filtro") String filtro);

	@Query(""
            + " SELECT a"
            + " FROM Destinatario a"
            + " WHERE a.clienteId = :clienteId"
            + " AND a.numeroIdentificacion = :numeroIdentificacion"
            + " ORDER BY a.nombre")
	Destinatario findByClienteIdAndNumeroIdentificacion(
        @Param("clienteId") Integer clienteId,
        @Param("numeroIdentificacion") String numeroIdentificacion);

}
