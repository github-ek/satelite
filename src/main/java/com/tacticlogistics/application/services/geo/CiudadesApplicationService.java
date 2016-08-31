package com.tacticlogistics.application.services.geo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.domain.model.common.valueobjects.Direccion;
import com.tacticlogistics.domain.model.common.valueobjects.OmsDireccion;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.infrastructure.persistence.geo.CiudadRepository;

@Service
@Transactional(readOnly = true)
public class CiudadesApplicationService {
    @Autowired
    private CiudadRepository ciudadRepository;

    @Cacheable(value = "ciudades")
    public List<Object> findAllCiudad() throws DataAccessException {
//        List<Ciudad> entityList = ciudadRepository.findByOrderByOrdinalAsc();
//
//        List<Object> list = new ArrayList<>();
//        entityList.forEach(a -> {
//            list.add(ciudadToDto(a));
//        });
//        return list;
    	return new ArrayList<>(); 
    }

    public List<Map<String, Object>> findCiudadesPorDestinatarioRemitentePorTipoServicio(
            Integer destinatarioRemitenteId, Integer tipoServicioId) throws DataAccessException {
//        List<Ciudad> entityList = ciudadRepository
//                .findByDestinatarioRemitenteIdAndTipoServicioId(destinatarioRemitenteId);
//
//        List<Map<String, Object>> list = new ArrayList<>();
//        entityList.forEach(a -> {
//            list.add(ciudadToDto(a));
//        });
//        return list;
    	return new ArrayList<>();
    }

    public List<Object> findAllCiudadPorClientePorTipoServicio(Integer clienteId, Integer tipoServicioId)
            throws DataAccessException {
//        List<Object> list = new ArrayList<>();
//
//        List<Ciudad> entityList = ciudadRepository.findByClienteIdAndTipoServicioId(clienteId, tipoServicioId);
//
//        entityList.forEach(a -> {
//            list.add(ciudadToDto(a));
//        });
//        return list;
    	return new ArrayList<>();
    }

    public List<Object> findCiudadesPorProducto(Integer productoId) throws DataAccessException {
//        List<Ciudad> entityList = ciudadRepository.findByProductoId(productoId);

//        List<Object> list = new ArrayList<>();
//        entityList.forEach(a -> {
//            list.add(ciudadToDto(a));
//        });
//        return list;
        return new ArrayList<>();
    }

    public Map<String, Object> ciudadToDto(Ciudad entity) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("id", entity.getId());
        map.put("codigo", entity.getCodigo());
        map.put("nombreAlterno", entity.getNombreAlterno());
        map.put("ordinal", entity.getOrdinal());

        return map;
    }

    public Map<String, Object> ciudadToViewModel(Integer ciudadId) {
        Map<String, Object> o = null;

        if (ciudadId != null) {
            Ciudad entity = ciudadRepository.findOne(ciudadId);
            o = ciudadToDto(entity);
        }

        return o;
    }

    public Map<String, Object> direccionToViewModel(Direccion entity) {
        Integer ciudadId = entity.getCiudad() == null ? null : entity.getCiudad().getId();

        return direccionToViewModel(
                new OmsDireccion(ciudadId, entity.getDireccion(), entity.getIndicacionesDireccion()));
    }

    public Map<String, Object> direccionToViewModel(OmsDireccion entity) {
        Map<String, Object> o = new HashMap<String, Object>();

        o.put("ciudad", entity.getCiudadId());
        o.put("direccion", entity.getDireccion());
        o.put("indicacionesDireccion", entity.getIndicacionesDireccion());
        if (entity.getLongitud() != null) {
            o.put("longitud", entity.getLongitud().toPlainString());
        }
        if (entity.getLatitud() != null) {
            o.put("latitud", entity.getLatitud().toPlainString());
        }
        o.put("direccionEstandarizada", entity.getDireccionEstandarizada());

        return o;
    }

    public Map<String, Object> direccionToDto(OmsDireccion entity) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("ciudadId", entity.getCiudadId());
        if (entity.getCiudadId() != null) {
            Ciudad ciudad = ciudadRepository.findOne(entity.getCiudadId());
            map.put("ciudadCodigo", ciudad.getCodigo());
            map.put("ciudadNombreAlterno", ciudad.getNombreAlterno());
        }
        map.put("direccion", entity.getDireccion());
        map.put("indicacionesDireccion", entity.getIndicacionesDireccion());
        map.put("direccionEstandarizada", entity.getDireccionEstandarizada());

        return map;
    }

}
