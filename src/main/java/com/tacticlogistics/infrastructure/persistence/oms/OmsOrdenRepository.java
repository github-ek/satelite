package com.tacticlogistics.infrastructure.persistence.oms;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.oms.OmsOrden;

public interface OmsOrdenRepository extends JpaRepository<OmsOrden, Integer> {

    @Query(""
            + " SELECT a"
            + " FROM OmsOrden a, UsuarioClienteAssociation b"
            + " WHERE a.cliente.id = b.cliente.id"
            + " AND b.usuario.id = :usuarioId"
            + " AND a.fechaConfirmacion >= :fechaDesde"
            + " ")
    List<OmsOrden> findByUsuarioIdAndFechaCreacion(
            @Param("usuarioId") Integer usuarioId, 
            @Param("fechaDesde") Date fechaDesde);
}
