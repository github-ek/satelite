package com.tacticlogistics.infrastructure.persistence.oms;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.oms.OmsCausalAnulacionOrden;

public interface OmsCausalAnulacionOrdenRepository extends JpaRepository<OmsCausalAnulacionOrden, Integer> {

}
