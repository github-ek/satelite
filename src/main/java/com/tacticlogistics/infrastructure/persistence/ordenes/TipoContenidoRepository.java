package com.tacticlogistics.infrastructure.persistence.ordenes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.common.TipoContenido;

public interface TipoContenidoRepository extends JpaRepository<TipoContenido, Integer> {
	List<TipoContenido> findAllByOrderByOrdinalAsc();
}
