package com.tacticlogistics.presentation.api.wms.ingresos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.domain.model.ordenes.EstadoOrdenType;
import com.tacticlogistics.infrastructure.persistence.ordenes.OrdenRepository;
import com.tacticlogistics.infrastructure.persistence.tms.TipoVehiculoRepository;
import com.tacticlogistics.infrastructure.persistence.tms.TransportadoraRepository;
import com.tacticlogistics.infrastructure.persistence.wms.BodegaRepository;
import com.tacticlogistics.presentation.api.wms.ingresos.dto.GridItemOrdenIngresoDto;
import com.tacticlogistics.presentation.api.wms.ingresos.dto.ListItemBodegaDto;
import com.tacticlogistics.presentation.api.wms.ingresos.dto.ListItemTipoVehiculoDto;
import com.tacticlogistics.presentation.api.wms.ingresos.dto.ListItemTransportadoraDto;
import com.tacticlogistics.presentation.api.wms.ingresos.dto.LlegadaDto;

@CrossOrigin
@RestController
@RequestMapping("/wms/ingresos")
public class IngresosController {

    @Autowired
    private BodegaRepository bodegaRepository;

    @Autowired
    private TransportadoraRepository transportadoraRepository;

    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    @RequestMapping(path = "/bodegas", method = RequestMethod.GET)
    public List<ListItemBodegaDto> getBodegasPorUsuarioId(@RequestParam(required = true) Integer usuarioId) {

        List<ListItemBodegaDto> list;

        try {
            Sort sort = new Sort(Sort.Direction.ASC, "bodega.codigo");
            list = bodegaRepository.findByUsuarioId(usuarioId, sort);
        } catch (RuntimeException e) {
            list = new ArrayList<>();
        }
        return list;
    }

    @RequestMapping(value = "/transportadoras", method = RequestMethod.GET)
    public List<ListItemTransportadoraDto> getTransportadoras() throws DataAccessException {
        List<ListItemTransportadoraDto> list;

        try {
            Sort sort = new Sort(Sort.Direction.ASC, "codigo");
            list = transportadoraRepository.findAllListItemTransportadoraDto(sort);
        } catch (RuntimeException e) {
            list = new ArrayList<>();
        }
        return list;
    }

    @RequestMapping(value = "/tipos_vehiculos", method = RequestMethod.GET)
    public List<ListItemTipoVehiculoDto> getTiposVehiculo() throws DataAccessException {
        List<ListItemTipoVehiculoDto> list;

        try {
            Sort sort = new Sort(Sort.Direction.ASC, "requiereRemolque").and(new Sort(Sort.Direction.ASC, "codigo"));
            list = tipoVehiculoRepository.findAllListItemTipoVehiculoDto(sort);
        } catch (RuntimeException e) {
            list = new ArrayList<>();
        }
        return list;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<GridItemOrdenIngresoDto> getOrdenesDeIngresoProgramadasPorBodegaId(
            @RequestParam(required = true) Integer bodegaId) {

        Page<GridItemOrdenIngresoDto> page;

        try {
            Pageable pageRequest = new PageRequest(0, 1000);
            page = ordenRepository.findByTipoServicioIdAndEstadoOrdenConPaginacion(3, EstadoOrdenType.ACEPTADA,
                    pageRequest);
        } catch (RuntimeException e) {
            page = null;
        }
        return page;
    }

    @RequestMapping(value = "/llegadas/{id}", method = RequestMethod.GET)
    public LlegadaDto getLlegada(@PathVariable Integer id) {
        LlegadaDto dto = new LlegadaDto();

        dto.setId(id);
        dto.setTransportadoraId(0);
        dto.setTipoVehiculoId(1);
        dto.setNumeroPlacaVehiculo("UVX-345");
        dto.setNumeroPlacaRemolque("UVY-300");
        dto.setFechaNotificacionDeLlegada(new Date());
        dto.setFechaRegistroDeLlegada(dto.getFechaRegistroDeLlegada());
        dto.setConductorNumeroIdentificacion("77777777");
        dto.setConductorNombres("PEPITO");
        dto.setConductorApellidos("PEREZ");
        dto.setConductorTelefono("300-999-0000");
        dto.setUsuarioActualizacion("usuario");

        return dto;
    }

    @RequestMapping(value = "/llegadas/{id}", method = RequestMethod.PUT)
    public MensajesDto registrarLlegada(@PathVariable Integer id, @RequestBody LlegadaDto dto) {
        MensajesDto mensajes = new MensajesDto();

        mensajes.setData(dto);
        mensajes.addMensaje(SeveridadType.INFO, "");

        // try {
        // Orden orden = this.ordenesService.saveOrden(model,
        // model.getNuevoEstadoOrden());
        // respuesta.put("orden", ordenesService.ordenToViewModel(orden));
        // mensajes.addMensaje(SeveridadType.INFO, "");
        // } catch (Exception e) {
        // mensajes.addMensaje(e);
        // }

        return mensajes;
    }

}
