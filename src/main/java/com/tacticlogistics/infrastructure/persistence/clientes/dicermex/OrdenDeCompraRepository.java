package com.tacticlogistics.infrastructure.persistence.clientes.dicermex;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.clientes.dicermex.OrdenDeCompra;

public interface OrdenDeCompraRepository extends JpaRepository<OrdenDeCompra, Integer> {

}
