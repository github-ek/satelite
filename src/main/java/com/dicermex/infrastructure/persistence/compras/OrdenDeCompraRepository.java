package com.dicermex.infrastructure.persistence.compras;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dicermex.domain.model.compras.OrdenDeCompra;

public interface OrdenDeCompraRepository extends JpaRepository<OrdenDeCompra, Integer> {
	OrdenDeCompra findFirstByOrdenClienteIdAndOrdenNumeroOrden(Integer clienteId, String numeroOrden);
}
