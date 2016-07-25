package com.tacticlogistics.infrastructure.persistence.crm;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.crm.TipoFormaPago;

public interface TipoFormaPagoRepository extends JpaRepository<TipoFormaPago, Integer> {
	TipoFormaPago findByCodigoIgnoringCase(String codigo);
}
