package com.tacticlogistics.infrastructure.persistence.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.crm.DestinoOrigen;

public interface DestinoOrigenRepository extends JpaRepository<DestinoOrigen, Integer> {
	List<DestinoOrigen> findAllByDestinatarioRemitenteIdAndDireccionCiudadIdOrderByCodigoAscNombreAsc(Integer destinatarioRemitenteId,Integer ciudadId);

	List<DestinoOrigen> findAllByDestinatarioRemitenteIdAndDireccionCiudadIdAndDireccionDireccionOrderByCodigoAscNombreAsc(Integer destinatarioRemitenteId,Integer ciudadId,String direccion);

	List<DestinoOrigen> findAllByDestinatarioRemitenteIdAndDireccionDireccionOrderByCodigoAscNombreAsc(Integer destinatarioRemitenteId,String direccion);
}
