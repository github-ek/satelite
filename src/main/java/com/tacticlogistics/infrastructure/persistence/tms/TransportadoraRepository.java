package com.tacticlogistics.infrastructure.persistence.tms;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tacticlogistics.domain.model.tms.Transportadora;
import com.tacticlogistics.presentation.api.wms.ingresos.dto.ListItemTransportadoraDto;

public interface TransportadoraRepository extends JpaRepository<Transportadora, Integer> {
    
    @Query(""
            + " SELECT new com.tacticlogistics.presentation.api.wms.ingresos.dto.ListItemTransportadoraDto("
            + "  a.id, "
            + "  a.codigo, "
            + "  a.nombre, "
            + "  a.codigo) "
            + " FROM Transportadora a")
    List<ListItemTransportadoraDto> findAllListItemTransportadoraDto(Sort sort);
    //
}
