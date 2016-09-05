package com.tacticlogistics.infrastructure.persistence.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.crm.Destino;

public interface DestinoRepository extends JpaRepository<Destino, Integer> {
	List<Destino> findAllByDestinatarioIdAndDireccionCiudadIdOrderByCodigoAscNombreAsc(Integer destinatarioId,Integer ciudadId);

	List<Destino> findAllByDestinatarioIdAndDireccionCiudadIdAndDireccionDireccionOrderByCodigoAscNombreAsc(Integer destinatarioId,Integer ciudadId,String direccion);

	List<Destino> findAllByDestinatarioIdAndDireccionDireccionOrderByCodigoAscNombreAsc(Integer destinatarioId,String direccion);
}
