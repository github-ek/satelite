package com.tacticlogistics.application.dto.ingresos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OrdenIngresoRepository extends CrudRepository<OrdenIngreso, Integer> {
	List<OrdenIngreso> findByEstado(EstadoOrdenIngresoType estado);
	OrdenIngreso findOneByIdAndHash(Integer Id,Integer hash);
}
