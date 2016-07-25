package com.tacticlogistics.infrastructure.persistence.oms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.oms.OmsOrden;
import com.tacticlogistics.domain.model.ordenes.EstadoOrdenType;

public interface OmsOrdenRepository extends JpaRepository<OmsOrden, Integer> {

    @Query(""
            + " SELECT a"
            + " FROM OmsOrden a, UsuarioClienteAssociation b"
            + " WHERE a.cliente.id = b.cliente.id"
            + " AND b.usuario.id = :usuarioId"
            + " AND a.tipoServicio.id = :tipoServicioId"
            + " AND ((:clienteId IS NULL) OR (a.cliente.id = :clienteId)) "
            + " AND ((:estadoOrden IS NULL) OR (a.estadoOrden = :estadoOrden)) "
            + " ")
    Page<OmsOrden> findByUsuarioIdAndTipoServicioIdAndEstadoOrden(
            @Param("usuarioId") Integer usuarioId, 
            @Param("tipoServicioId") Integer tipoServicioId,
            @Param("clienteId") Integer clienteId,
            @Param("estadoOrden") EstadoOrdenType estadoOrden,
            Pageable pageRequest);
}
