package com.tacticlogistics.infrastructure.persistence.wms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.wms.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	List<Producto> findAllByClienteIdAndActivoOrderByNombreLargo(Integer clienteId,boolean activo);
	
	Producto findByClienteCodigoAndCodigo(String clienteCodigo,String productoCodigo);
	
	Producto findByClienteIdAndCodigo(Integer clienteId,String productoCodigo);
	
	Producto findByClienteIdAndCodigoAlterno(Integer clienteId,String productoCodigoAlterno);
}
