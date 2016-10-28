package com.tacticlogistics.infrastructure.persistence.geo;

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
}
