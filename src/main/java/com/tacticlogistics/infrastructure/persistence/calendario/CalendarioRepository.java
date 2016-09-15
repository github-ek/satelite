package com.tacticlogistics.infrastructure.persistence.calendario;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tacticlogistics.domain.model.calendario.Calendario;

public interface CalendarioRepository extends JpaRepository<Calendario, Integer> {

    @Query(""
            + " SELECT a"
            + " FROM Calendario a"
            + " WHERE a.fecha >= :fechaDesde AND a.fecha <= :fechaHasta "
            + " ORDER BY a.fecha")
    List<Calendario> findSemana(
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta
            );

}
