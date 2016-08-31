package com.tacticlogistics.infrastructure.persistence.ordenes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.ordenes.EstadoOrden;
import com.tacticlogistics.domain.model.oms.EstadoOrdenType;

public interface EstadoOrdenRepository extends JpaRepository<EstadoOrden, EstadoOrdenType> {
	List<EstadoOrden> findAllByOrderByOrdinalAsc();
}
