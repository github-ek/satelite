package com.tacticlogistics.infrastructure.persistence.tms;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.tms.Transportadora;

public interface TransportadoraRepository extends JpaRepository<Transportadora, Integer> {

}
