package com.tacticlogistics.infrastructure.persistence.ingresos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.application.dto.ingresos.TipoNovedadIngresoInventario;

public interface TipoNovedadIngresoInventarioRepository extends JpaRepository<TipoNovedadIngresoInventario, Integer> {

}
