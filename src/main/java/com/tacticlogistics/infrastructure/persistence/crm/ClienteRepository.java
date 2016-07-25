package com.tacticlogistics.infrastructure.persistence.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.ClienteCanalAssociation;
import com.tacticlogistics.domain.model.crm.ClienteRequerimientoDistribucionAssociation;
import com.tacticlogistics.domain.model.crm.ClienteRequerimientoMaquilaAssociation;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByCodigoIgnoringCase(String codigo);
	
   @Query(""
            + " SELECT DISTINCT a.cliente"
            + " FROM UsuarioClienteAssociation a"
            + " JOIN a.cliente.clienteTipoServicioAssociation b"
            + " WHERE a.usuario.id = :usuarioId"
            + " AND b.tipoServicioId = :tipoServicioId"
            + " ORDER BY a.cliente.codigo"
           )
	List<Cliente> findByUsuarioIdAndTipoServicioId(
            @Param("usuarioId") Integer usuarioId, 
            @Param("tipoServicioId") Integer tipoServicioId);

   @Query(""
           + " SELECT a"
           + " FROM ClienteCanalAssociation a"
           + " WHERE a.clienteId = :clienteId"
           + " ORDER BY a.codigoAlterno"
          )
   List<ClienteCanalAssociation> findClienteCanalAssociationByClienteId(
           @Param("clienteId") Integer clienteId);

   @Query(""
           + " SELECT a"
           + " FROM ClienteRequerimientoDistribucionAssociation a"
           + " WHERE a.clienteId = :clienteId"
           + " AND a.tipoServicioId = :tipoServicioId"
           + " ORDER BY a.codigoAlterno"
          )
   List<ClienteRequerimientoDistribucionAssociation> findClienteRequerimientoDistribucionAssociationByClienteIdAndTipoServicioId(
           @Param("clienteId") Integer clienteId,
           @Param("tipoServicioId") Integer tipoServicioId);

   @Query(""
           + " SELECT a"
           + " FROM ClienteRequerimientoMaquilaAssociation a"
           + " WHERE a.clienteId = :clienteId"
           + " AND a.tipoServicioId = :tipoServicioId"
           + " ORDER BY a.codigoAlterno"
          )
   List<ClienteRequerimientoMaquilaAssociation> findClienteRequerimientoMaquilaAssociationByClienteIdAndTipoServicioId(
           @Param("clienteId") Integer clienteId,
           @Param("tipoServicioId") Integer tipoServicioId);
}
