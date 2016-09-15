package com.tacticlogistics.infrastructure.persistence.tms;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.tms.TipoVehiculo;

public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Integer> {

}
