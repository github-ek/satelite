package com.tacticlogistics.infrastructure.persistence.crm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.ClienteBodegaAssociation;
import com.tacticlogistics.domain.model.crm.ClienteCanalAssociation;
import com.tacticlogistics.domain.model.crm.ClienteRequerimientoAlmacenamientoAssociation;
import com.tacticlogistics.domain.model.crm.ClienteRequerimientoDistribucionAssociation;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByCodigoIgnoringCase(String codigo);
	
   @Query(""
            + " SELECT DISTINCT a.cliente"
            + " FROM UsuarioClienteAssociation a"
            + " JOIN a.cliente.clienteServicioAssociation b"
            + " WHERE a.usuario.id = :usuarioId"
            + " AND b.servicioId = :servicioId"
            + " ORDER BY a.cliente.codigo"
           )
	List<Cliente> findByUsuarioIdAndServicioId(
            @Param("usuarioId") Integer usuarioId, 
            @Param("servicioId") Integer servicioId);

	@Query(""
	        + " SELECT c "
	        + " FROM ClienteBodegaAssociation c "
	        + " WHERE c.clienteId IN "
	        + " ( "
	        + " SELECT DISTINCT a.id "
			+ " FROM Cliente a "
			+ " WHERE a.codigo = :clienteCodigo "
			+ " ) "
			+ " ORDER BY c.codigoAlterno ")   
	List<ClienteBodegaAssociation> findClienteBodegaAssociationByClienteCodigo(
           @Param("clienteCodigo") String clienteCodigo);

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
           + " AND a.servicioId = :servicioId"
           + " ORDER BY a.codigoAlterno"
          )
   List<ClienteRequerimientoDistribucionAssociation> findClienteRequerimientoDistribucionAssociationByClienteIdAndServicioId(
           @Param("clienteId") Integer clienteId,
           @Param("servicioId") Integer servicioId);

   @Query(""
           + " SELECT a"
           + " FROM ClienteRequerimientoAlmacenamientoAssociation a"
           + " WHERE a.clienteId = :clienteId"
           + " AND a.servicioId = :servicioId"
           + " ORDER BY a.codigoAlterno"
          )
   List<ClienteRequerimientoAlmacenamientoAssociation> findClienteRequerimientoAlmacenamientoAssociationByClienteIdAndServicioId(
           @Param("clienteId") Integer clienteId,
           @Param("servicioId") Integer servicioId);
}
