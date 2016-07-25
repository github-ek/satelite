package com.tacticlogistics.infrastructure.persistence.oms;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.oms.CausalSolicitudRevisionCliente;

public interface CausalSolicitudRevisionClienteRepository extends JpaRepository<CausalSolicitudRevisionCliente, Integer> {

}
