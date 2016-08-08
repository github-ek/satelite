package com.tacticlogistics.application.services.oms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.common.CustomPage;
import com.tacticlogistics.application.dto.common.ItemGenerico;
import com.tacticlogistics.application.dto.oms.OmsOrdenDto;
import com.tacticlogistics.domain.model.crm.ClienteRequerimientoDistribucionAssociation;
import com.tacticlogistics.domain.model.crm.ClienteRequerimientoAlistamientoAssociation;
import com.tacticlogistics.domain.model.oms.CausalSolicitudRevisionCliente;
import com.tacticlogistics.domain.model.oms.CausalSolicitudRevisionPlaneacionLogistica;
import com.tacticlogistics.domain.model.oms.OmsCausalAnulacionOrden;
import com.tacticlogistics.domain.model.oms.OmsOrden;
import com.tacticlogistics.domain.model.ordenes.EstadoOrdenType;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.oms.CausalSolicitudRevisionClienteRepository;
import com.tacticlogistics.infrastructure.persistence.oms.CausalSolicitudRevisionPlaneacionLogisticaRepository;
import com.tacticlogistics.infrastructure.persistence.oms.OmsCausalAnulacionOrdenRepository;
import com.tacticlogistics.infrastructure.persistence.oms.OmsOrdenRepository;

@Service
@Transactional(readOnly = true)
public class OmsApplicationService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CausalSolicitudRevisionClienteRepository causalSolicitudRevisionClienteRepository;

    @Autowired
    private CausalSolicitudRevisionPlaneacionLogisticaRepository causalSolicitudRevisionPlaneacionLogisticaRepository;

    @Autowired
    private OmsCausalAnulacionOrdenRepository causalAnulacionOrdenRepository;

    @Autowired
    private OmsOrdenRepository ordenRepository;

    public OmsOrdenDto findOneOrdenPorId(Integer id) throws DataAccessException {
        OmsOrden orden = ordenRepository.findOne(id);
        OmsOrdenDto dto = null;

        if (orden != null) {
            dto = map(orden);
        }

        return dto;
    }

    public CustomPage<String[]> findOrdenesByUsuarioIdAndTipoServicioIdAndEstadoOrden(Integer usuarioId,
            Integer tipoServicioId, Integer clienteId, EstadoOrdenType estadoOrden, int start, int length)
            throws DataAccessException {
//        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        List<String[]> list = new ArrayList<>();
//
//        Pageable pageRequest = createPageRequest(start, length);
//        Page<OmsOrden> searchResultPage = ordenRepository.findByUsuarioIdAndTipoServicioIdAndEstadoOrden(usuarioId,
//                tipoServicioId, clienteId, estadoOrden, pageRequest);
//
//        searchResultPage.getContent().forEach(a -> {
//            List<String> map = new ArrayList<>();
//
//            OmsOrdenDto dto = map(a);
//
//            Date fmin = dto.getFechaSugeridaEntregaMinima();
//            Date fmax = dto.getFechaSugeridaEntregaMaxima();
//
//            if ((dto.getFechaPlaneadaEntregaMinima() != null) || (dto.getFechaPlaneadaEntregaMaxima() != null)) {
//                fmin = dto.getFechaPlaneadaEntregaMinima();
//                fmax = dto.getFechaPlaneadaEntregaMaxima();
//            }
//
//            map.add(dto.getId().toString());
//            map.add(dto.getEstadoOrden().toString());
//            map.add(dto.getClienteCodigo());
//            map.add(dto.getNumeroDocumentoOrdenCliente());
//
//            if (!dto.getCanalCodigo().isEmpty()) {
//                map.add(dto.getCanalCodigo());
//            } else {
//                map.add(dto.getCanalCodigoAlterno());
//            }
//            if (!dto.getDestinatarioNombre().isEmpty()) {
//                map.add(dto.getDestinatarioNombre());
//            } else {
//                map.add(dto.getDestinatarioNombreAlterno());
//            }
//            if (!dto.getDestinatarioNumeroIdentificacion().isEmpty()) {
//                map.add(dto.getDestinatarioNumeroIdentificacion());
//            } else {
//                map.add(dto.getDestinatarioNumeroIdentificacionAlterno());
//            }
//            if (!dto.getDestinoCiudadNombre().isEmpty()) {
//                map.add(dto.getDestinoCiudadNombre());
//            } else {
//                map.add(dto.getDestinoCiudadNombreAlterno());
//            }
//            map.add(dto.getDestinoDireccion());
//            map.add((fmin == null) ? "" : sdf.format(fmin));
//            map.add((fmax == null) ? "" : sdf.format(fmax));
//
//            list.add(map.toArray(new String[0]));
//        });
//
//        CustomPage<String[]> page = new CustomPage<>(searchResultPage.getTotalElements(), list);
//
//        return page;
    	return null;
    }

    private Pageable createPageRequest(int start, int length) {
        start = (start < 0) ? 0 : start;
        length = (length < 0) ? 10 : length;
        return new PageRequest(start, length);
    }

    public List<Map<String, Object>> findRequerimientosDistribucionPorClientePorTipoServicioDestinatarioRemitente(
            Integer clienteId, Integer tipoServicioId, Integer destinatarioRemitenteId) throws DataAccessException {
        List<ClienteRequerimientoDistribucionAssociation> entityList = clienteRepository
                .findClienteRequerimientoDistribucionAssociationByClienteIdAndTipoServicioId(clienteId, tipoServicioId);

        List<Map<String, Object>> list = new ArrayList<>();
        entityList.forEach(a -> {
            Map<String, Object> map = new HashMap<>();
            map.put("codigoAlterno", a.getCodigoAlterno());
            map.put("requerimientoDistribucionId", a.getRequerimientoDistribucionId());
            map.put("descripcion", a.getDescripcion());
            list.add(map);
        });
        return list;
    }

    public List<Map<String, Object>> findRequerimientosAlistamientoPorClientePorTipoServicioDestinatarioRemitente(
            Integer clienteId, Integer tipoServicioId, Integer destinatarioRemitenteId) throws DataAccessException {
        List<ClienteRequerimientoAlistamientoAssociation> entityList = clienteRepository
                .findClienteRequerimientoAlistamientoAssociationByClienteIdAndTipoServicioId(clienteId, tipoServicioId);

        List<Map<String, Object>> list = new ArrayList<>();
        entityList.forEach(a -> {
            Map<String, Object> map = new HashMap<>();
            map.put("codigoAlterno", a.getCodigoAlterno());
            map.put("requerimientoAlistamientoId", a.getRequerimientoAlistamientoId());
            map.put("descripcion", a.getDescripcion());
            list.add(map);
        });
        return list;
    }

    public List<ItemGenerico<Integer>> findCausalesSolicitudRevisionCliente() throws DataAccessException {
        List<CausalSolicitudRevisionCliente> entityList = causalSolicitudRevisionClienteRepository
                .findAll(new Sort("ordinal"));

        List<ItemGenerico<Integer>> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(new ItemGenerico<Integer>(a.getId(), a.getCodigo(), a.getDescripcion()));
        });
        return list;
    }

    public List<ItemGenerico<Integer>> findCausalesSolicitudRevisionPlaneacionLogistica() throws DataAccessException {
        List<CausalSolicitudRevisionPlaneacionLogistica> entityList = causalSolicitudRevisionPlaneacionLogisticaRepository
                .findAll(new Sort("ordinal"));

        List<ItemGenerico<Integer>> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(new ItemGenerico<Integer>(a.getId(), a.getCodigo(), a.getDescripcion()));
        });
        return list;
    }

    public List<ItemGenerico<Integer>> findCausalesAnulacionOrden() throws DataAccessException {
        List<OmsCausalAnulacionOrden> entityList = causalAnulacionOrdenRepository.findAll(new Sort("ordinal"));

        List<ItemGenerico<Integer>> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(new ItemGenerico<Integer>(a.getId(), a.getCodigo(), a.getDescripcion()));
        });
        return list;
    }

    protected OmsOrdenDto map(OmsOrden orden) {
        ModelMapper modelMapper = new ModelMapper();

        OmsOrdenDto dto;
        dto = modelMapper.map(orden, OmsOrdenDto.class);

        if (orden.getTipoServicio() != null) {
            dto.setTipoServicioNombre(orden.getTipoServicio().getNombre());
        }

        dto.setClienteCodigo(orden.getClienteCodigo());
        if (orden.getCliente() != null) {
            dto.setClienteNombre(orden.getCliente().getNombre());
        }

        if (orden.getNumeroConsolidado() != null) {
            dto.setNumeroDocumentoConsolidadoCliente(orden.getNumeroConsolidado());
        }

        if (orden.getCiudadDestino() != null) {
            dto.setDestinoCiudadNombre(orden.getCiudadDestino().getNombreAlterno());
        }

        if (orden.getCiudadOrigen() != null) {
            dto.setDestinoCiudadNombre(orden.getCiudadOrigen().getNombreAlterno());
        }

        dto.setCanalCodigoAlterno(orden.getCanalCodigoAlterno());
        if (orden.getCanal() != null) {
            dto.setCanalCodigo(orden.getCanal().getCodigo());
            dto.setCanalNombre(orden.getCanal().getNombre());
        }

        if (orden.getDestinatario() != null) {
            dto.setDestinatarioNombre(orden.getDestinatarioNombre());
            dto.setDestinatarioNumeroIdentificacion(orden.getDestinatarioNumeroIdentificacion());
        }

        if (orden.getDestino() != null) {
            dto.setDestinoNombre(orden.getDestinoNombre());
        }

        if (orden.getOrigen() != null) {
            dto.setDestinoNombre(orden.getDestinoNombre());
        }

        return dto;
    }

}
