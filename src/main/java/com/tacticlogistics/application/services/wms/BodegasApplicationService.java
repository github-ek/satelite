package com.tacticlogistics.application.services.wms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.infrastructure.persistence.geo.CiudadRepository;
import com.tacticlogistics.infrastructure.persistence.wms.BodegaRepository;

@Service
@Transactional(readOnly = true)
public class BodegasApplicationService {
    @Autowired
    private BodegaRepository bodegaRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    public List<Object> findBodegasPorProductoPorCiudad(Integer productoId, Integer ciudadId)
            throws DataAccessException {
        List<Bodega> entityList = bodegaRepository.findAllByProductoIdAndCiudadId(productoId, ciudadId);

        List<Object> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(bodegaToViewModel(a));
        });
        return list;
    }

    protected Map<String, Object> bodegaToViewModel(Bodega entity) {
        Map<String, Object> o = new HashMap<String, Object>();

        if (entity != null) {
            if (entity.getDireccion().getCiudadId() != null) {
                Ciudad model = ciudadRepository.findOne(entity.getDireccion().getCiudadId());
                if (model != null) {
                    o.put("ciudadNombre", model.getNombreAlterno());
                }
            }

            o.put("id", entity.getId());
            o.put("codigo", entity.getCodigo());
            o.put("nombre", entity.getNombre());
            o.put("ciudadId", entity.getDireccion().getCiudadId());
            o.put("direccion", entity.getDireccion().getDireccion());
            o.put("indicacionesDireccion", entity.getDireccion().getIndicacionesDireccion());
            o.put("manejaRenta", entity.isManejaRenta());
        }

        return o;
    }

}
