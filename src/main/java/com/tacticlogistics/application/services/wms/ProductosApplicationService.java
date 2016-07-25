package com.tacticlogistics.application.services.wms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.wms.ProductoDto;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.ProductoUnidadAssociation;
import com.tacticlogistics.domain.model.wms.Unidad;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.wms.ProductoRepository;
import com.tacticlogistics.infrastructure.persistence.wms.UnidadRepository;

@Service
@Transactional(readOnly = true)
public class ProductosApplicationService{
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UnidadRepository unidadRepository;

    public List<Object> findAllUnidades() throws DataAccessException {
        List<Unidad> entityList = unidadRepository.findAllByOrderByOrdinalAsc();

        List<Object> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(unidadToDto(a));
        });
        return list;
    }

    public List<Object> findAllProductoPorCliente(Integer clienteId) throws DataAccessException {
        List<Producto> entityList = productoRepository.findAllByClienteIdAndActivoOrderByNombreLargo(clienteId, true);

        List<Object> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(productoToDto(a));
        });
        return list;
    }

    public List<Object> findAllUnidadPorProducto(Integer productoId) throws DataAccessException {
        List<ProductoUnidadAssociation> entityList = unidadRepository.findAllByProductoId(productoId);

        List<Object> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(productoUnidadAssociationToDto(a));
        });
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Save
    // ----------------------------------------------------------------------------------------------------------------
    @Transactional(readOnly = false)
    public Producto save(ProductoDto dto) throws DataAccessException {
        Producto model = null;

        if (dto.getId() == null) {
            model = new Producto();
            model.setExisteEnWms(false);
            model.setActivo(true);
        } else {
            model = productoRepository.findOne(dto.getId());
        }

        if (!model.isExisteEnWms()) {
            if (model.getId() == null) {
                if (dto.getClienteId() != null) {
                    model.setCliente(clienteRepository.getOne(dto.getClienteId()));
                }
            }
            model.setCodigo(dto.getCodigoAlterno() == null ? "" : dto.getCodigoAlterno());
            model.setCodigoAlterno(dto.getCodigoAlterno()== null ? "": dto.getCodigoAlterno());
            model.setNombre("");
            model.setNombreLargo(dto.getNombreAlterno()== null ? "": dto.getNombreAlterno());
            /*
            int i = 1;
            for (ProductoUnidadDto pu : dto.getUnidades()) {
                if (pu.getUnidadId() != null) {
                    ProductoUnidadAssociation pua = new ProductoUnidadAssociation();
                    pua.setProducto(model);
                    pua.setUnidad(unidadRepository.getOne(pu.getUnidadId()));
                    
                    pua.setNivel(i);
                    pua.setUnidadBase(i == 1);
                    pua.setFactorConversion(i == 1 ? 1 : pu.getFactorConversion());
                    pua.setLargo(pu.getLargo());
                    pua.setAncho(pu.getAncho());
                    pua.setAlto(pu.getAlto());
                    pua.setPesoBruto(pu.getPesoBruto());
                    pua.setHabilitadaEnOrdenesDeIngreso(i < 3);
                    pua.setHabilitadaEnOrdenesDeSalida(i < 3);
                    pua.setPredeterminadaEnOrdenesDeIngreso(false);
                    pua.setPredeterminadaEnOrdenesDeSalida(false);
                    pua.setValorAproximado(i == 1 ? dto.getValorAproximado() : null);

                    model.getProductoUnidadAssociation().add(pua);
                }
                i++;
            }
            */

            model.setFechaActualizacion(new Date());
            model.setUsuarioActualizacion(dto.getUsuarioActualizacion());

            productoRepository.save(model);
        }

        return model;

    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------------------
    public Map<String, Object> productoToDto(Producto model) {
        Map<String, Object> o = new HashMap<String, Object>();

        StringBuffer sb = new StringBuffer();
        sb.append((model.getCodigo() == null) ? "" : model.getCodigo()).append(" - ")
                .append((model.getNombreLargo() == null) ? "" : model.getNombreLargo());

        o.put("id", model.getId());
        o.put("codigo", model.getCodigo());
        o.put("codigoAlterno", model.getCodigoAlterno());
        o.put("nombre", sb.toString());
        o.put("nombreLargo", model.getNombreLargo());
        o.put("existeEnWms", model.isExisteEnWms());

        return o;
    }

    protected Map<String, Object> unidadToDto(Unidad model) {
        Map<String, Object> o = new HashMap<String, Object>();

        o.put("id", model.getId());
        o.put("codigo", model.getCodigo());
        o.put("nombre", model.getNombre());
        o.put("descripcion", model.getDescripcion());
        o.put("ordinal", model.getOrdinal());

        return o;
    }

    protected Map<String, Object> productoUnidadAssociationToDto(ProductoUnidadAssociation model) {
        Map<String, Object> o = new HashMap<String, Object>();

        o.put("id", model.getUnidad().getId());
        o.put("codigo", model.getUnidad().getCodigo());
        o.put("nombre", model.getUnidad().getNombre());
        o.put("nombreAlterno", model.getUnidad().getNombre());
        o.put("nivel", model.getNivel());
        o.put("unidadBase", model.isUnidadBase());
        o.put("factorConversion", model.getFactorConversion());
        o.put("largo", model.getLargo());
        o.put("ancho", model.getAncho());
        o.put("alto", model.getAlto());
        o.put("pesoBruto", model.getPesoBruto());
        o.put("habilitadaEnOrdenesDeIngreso", model.isHabilitadaEnOrdenesDeIngreso());
        o.put("predeterminadaEnOrdenesDeIngreso", model.isPredeterminadaEnOrdenesDeIngreso());
        o.put("habilitadaEnOrdenesDeSalida", model.isHabilitadaEnOrdenesDeSalida());
        o.put("predeterminadaEnOrdenesDeSalida", model.isPredeterminadaEnOrdenesDeSalida());
        o.put("valorAproximado", model.getValorAproximado());

        return o;
    }
}
