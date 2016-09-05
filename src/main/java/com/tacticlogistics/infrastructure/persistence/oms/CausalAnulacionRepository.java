package com.tacticlogistics.infrastructure.persistence.oms;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.oms.CausalAnulacion;

public interface CausalAnulacionRepository extends JpaRepository<CausalAnulacion, Integer> {

}
