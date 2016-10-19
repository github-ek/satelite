package com.tacticlogistics.infrastructure.persistence.wms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.crm.ClienteBodegaAssociation;
import com.tacticlogistics.domain.model.wms.Bodega;

public interface BodegaRepository extends JpaRepository<Bodega, Integer> {
	Bodega findByCodigoIgnoringCase(String codigo);

	@Query(""
			+ " SELECT DISTINCT a"
			+ " FROM ClienteBodegaAssociation a"
			+ " WHERE :clienteId = :clienteId"
			+ "	AND a.clienteId = :clienteId"
			+ "	AND a.codigoAlterno = :codigoAlterno")
	ClienteBodegaAssociation findFirstByClienteIdAndCodigoAlterno(
			@Param("clienteId") Integer clienteId,
			@Param("codigoAlterno") String codigoAlterno);

	@Query(""
			+ " SELECT a"
			+ " FROM ClienteBodegaAssociation a"
			+ " WHERE :clienteId = :clienteId"
			+ "	AND a.clienteId = :clienteId"
			+ "	AND a.bodegaId = :bodegaId"
			+ "	AND a.estadoInventarioId = :estadoInventarioId"
			)
	List<ClienteBodegaAssociation> findByClienteIdAndBodegaIdAndEstadoInventarioId(
			@Param("clienteId") Integer clienteId,
			@Param("bodegaId") Integer bodegaId,
			@Param("estadoInventarioId") String estadoInventarioId);

}
