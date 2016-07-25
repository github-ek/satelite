package com.tacticlogistics.infrastructure.persistence.tms;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tacticlogistics.domain.model.tms.TipoVehiculo;
import com.tacticlogistics.presentation.api.wms.ingresos.dto.ListItemTipoVehiculoDto;

public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Integer> {
    @Query(""
            + " SELECT new com.tacticlogistics.presentation.api.wms.ingresos.dto.ListItemTipoVehiculoDto("
            + "  a.id, "
            + "  a.codigo, "
            + "  a.nombre, "
            + "  a.requiereRemolque) "
            + " FROM TipoVehiculo a")
    List<ListItemTipoVehiculoDto> findAllListItemTipoVehiculoDto(Sort sort);   
}
