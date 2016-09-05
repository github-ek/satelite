package com.tacticlogistics.infrastructure.persistence.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;

public interface DestinatarioRemitenteRepository extends JpaRepository<DestinatarioRemitente, Integer> {
	DestinatarioRemitente findByNumeroIdentificacion(String codigo);

	List<DestinatarioRemitente> findAllByClienteIdAndCanalIdOrderByNombre(
			Integer clienteId, Integer canalId);
	
    @Query(""
            + " SELECT a"
            + " FROM DestinatarioRemitente a"
            + " WHERE a.clienteId = :clienteId"
            + " AND ((:canalId IS NULL) OR (a.canalId = :canalId))"
            + " AND ((numeroIdentificacion LIKE :filtro) OR (nombre LIKE :filtro))"
            + " ORDER BY a.nombre")
    List<DestinatarioRemitente> findByClienteIdAndCanalIdAndFiltro(
            @Param("clienteId") Integer clienteId,
            @Param("canalId") Integer canalId,
            @Param("filtro") String filtro);

	@Query(""
            + " SELECT a"
            + " FROM DestinatarioRemitente a"
            + " WHERE a.clienteId = :clienteId"
            + " AND a.numeroIdentificacion = :numeroIdentificacion"
            + " ORDER BY a.nombre")
	DestinatarioRemitente findByClienteIdAndNumeroIdentificacion(
        @Param("clienteId") Integer clienteId,
        @Param("numeroIdentificacion") String numeroIdentificacion);

}
