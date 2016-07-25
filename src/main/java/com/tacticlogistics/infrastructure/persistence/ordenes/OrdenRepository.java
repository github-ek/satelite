package com.tacticlogistics.infrastructure.persistence.ordenes;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.application.dto.oms.ConsultaOrdenesDto;
import com.tacticlogistics.domain.model.ordenes.EstadoOrdenType;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.presentation.api.wms.ingresos.dto.GridItemOrdenIngresoDto;

public interface OrdenRepository extends JpaRepository<Orden, Integer> {
    Orden findFirstByClienteIdAndNumeroDocumentoOrdenCliente(Integer clienteId,String numeroDocumentoOrdenCliente);
    
	@Query(""
			+ " SELECT a"
			+ " FROM Orden a, UsuarioClienteAssociation b"
			+ " WHERE a.cliente.id = b.cliente.id"
			+ " AND b.usuario.id = :usuarioId"
			+ " AND a.tipoServicio.id = :tipoServicioId"
			+ " AND ((:estadoOrden IS NULL) OR (a.estadoOrden = :estadoOrden)) "
			+ " AND ((:clienteId IS NULL) OR (a.cliente.id = :clienteId)) "
			+ " ORDER BY a.id DESC")
	List<Orden> findByUsuarioIdAndTipoServicioIdAndEstadoOrden(
			@Param("usuarioId") Integer usuarioId, 
			@Param("tipoServicioId") Integer tipoServicioId,
			@Param("estadoOrden") EstadoOrdenType estadoOrden,
			@Param("clienteId") Integer clienteId);

    @Query(""
            + " SELECT new com.tacticlogistics.application.dto.oms.ConsultaOrdenesDto"
            + " ("
            + "     a.id, "
            + "     a.tipoServicio.nombre, "
            + "     a.cliente.codigo, "
            + "     a.cliente.nombre,"
            + "     a.estadoOrden,"
            + "     a.numeroDocumentoOrdenCliente,"
            + "     b.numeroDocumentoConsolidadoCliente,"
            + "     f.codigo,"
            + "     f.nombre,"
            + "     g.nombre,"            
            + "     g.numeroIdentificacion,"
            + "     c.nombre,"
            + "     d.codigo,"
            + "     d.nombre,"
            + "     e.nombreAlterno,"
            + "     a.destinoDireccion.direccion,"
            + "     a.intervaloDeFechasSugerido.fechaMinima,"
            + "     a.intervaloDeFechasSugerido.fechaMaxima,"
            + "     a.intervaloDeHorasSugerido.horaMinima,"
            + "     a.intervaloDeHorasSugerido.horaMaxima, "

            + "     a.fechaActualizacion, "
            + "     a.usuarioActualizacion, "
            + "     a.fechaConfirmacion, "
            + "     a.usuarioConfirmacion, "
            + "     a.fechaAceptacion, "
            + "     a.usuarioAceptacion "
            + " )"
            + " FROM Orden a "
            + " LEFT JOIN a.consolidado b "
            + " LEFT JOIN a.destino c "
            + " LEFT JOIN a.bodegaDestino d "
            + " LEFT JOIN a.destinoDireccion.ciudad e "
            + " LEFT JOIN a.canal f "
            + " LEFT JOIN a.destinatario g "
            
            + " WHERE 0 = 0"
            + " AND a.tipoServicio.id = :tipoServicioId"
            + " AND ((:estadoOrden IS NULL) OR (a.estadoOrden = :estadoOrden)) "
            + " AND ((:clienteId IS NULL) OR (a.cliente.id = :clienteId)) "
            + " ORDER BY a.id DESC")
    Page<ConsultaOrdenesDto> findByUsuarioIdAndTipoServicioIdAndEstadoOrdenConPaginacion(
            @Param("tipoServicioId") Integer tipoServicioId,
            @Param("estadoOrden") EstadoOrdenType estadoOrden,
            @Param("clienteId") Integer clienteId,
            Pageable pageRequest);
    
    @Query(""
            + " SELECT new com.tacticlogistics.presentation.api.wms.ingresos.dto.GridItemOrdenIngresoDto"
            + " ("
            + "     a.id, "
            + "     a.cliente.codigo, "
            + "     a.estadoOrden,"
            + "     a.numeroDocumentoOrdenCliente,"
            + "     a.intervaloDeFechasSugerido.fechaMinima,"
            + "     a.intervaloDeFechasSugerido.fechaMaxima,"
            + "     a.intervaloDeHorasSugerido.horaMinima,"
            + "     a.intervaloDeHorasSugerido.horaMaxima, "

            + "     a.fechaActualizacion, "
            + "     a.usuarioActualizacion, "
            
            + "     a.fechaConfirmacion, "
            + "     a.usuarioConfirmacion, "
            
            + "     a.fechaAceptacion, "
            + "     a.usuarioAceptacion "
            + " )"
            + " FROM Orden a "
            + " WHERE 0 = 0"
            + " AND a.tipoServicio.id = :tipoServicioId"
            + " AND a.estadoOrden = :estadoOrden "
            //+ " AND a.bodegaDestino.id = :estadoOrden "
            + " ORDER BY a.id DESC")
    Page<GridItemOrdenIngresoDto> findByTipoServicioIdAndEstadoOrdenConPaginacion(
            @Param("tipoServicioId") Integer tipoServicioId,
            @Param("estadoOrden") EstadoOrdenType estadoOrden,
            Pageable pageRequest);
}
