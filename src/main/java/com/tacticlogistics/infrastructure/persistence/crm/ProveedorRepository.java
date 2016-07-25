package com.tacticlogistics.infrastructure.persistence.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
	List<Proveedor> findAllByClienteOrderByNombre(Cliente cliente);
}
