package com.tacticlogistics.infrastructure.persistence.oms;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.oms.OmsConsolidado;

public interface OmsConsolidadoRepository extends JpaRepository<OmsConsolidado, Integer> {

}
