package com.tacticlogistics.infrastructure.persistence.wms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.wms.ProductoUnidadAssociation;
import com.tacticlogistics.domain.model.wms.Unidad;

public interface UnidadRepository extends JpaRepository<Unidad, Integer> {
	Unidad findByCodigoIgnoringCase(String codigo);
	
	List<Unidad> findAllByOrderByOrdinalAsc();
			
	//TODO
	@Query(""
			+ " SELECT a"
			+ " FROM ProductoUnidadAssociation a"
			+ " WHERE "
			+ "		a.producto.id = :productoId"
			+ " AND a.producto.activo = true"
			+ " AND a.unidad.activo = true"
			+ " ORDER BY a.nivel")
	List<ProductoUnidadAssociation> findAllByProductoId(
			@Param("productoId") Integer productoId);
}
