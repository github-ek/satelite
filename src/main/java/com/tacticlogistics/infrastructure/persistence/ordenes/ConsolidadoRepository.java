package com.tacticlogistics.infrastructure.persistence.ordenes;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.ordenes.Consolidado;

public interface ConsolidadoRepository extends JpaRepository<Consolidado, Integer> {

    Consolidado findByClienteIdAndNumeroDocumentoConsolidadoClienteIgnoringCase(Integer clienteId,String codigo);
}
