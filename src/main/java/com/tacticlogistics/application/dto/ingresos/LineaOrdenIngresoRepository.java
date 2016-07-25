package com.tacticlogistics.application.dto.ingresos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LineaOrdenIngresoRepository extends CrudRepository<LineaOrdenIngreso, Integer> {
	List<LineaOrdenIngreso> findByIdOrden(Integer id);
}
