package com.tacticlogistics.application.services.crm;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.crm.DestinatarioDto;
import com.tacticlogistics.application.dto.etl.ETLDestinatarioDto;
import com.tacticlogistics.domain.model.common.IdentificacionType;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.OmsDireccion;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.Destinatario;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.infrastructure.persistence.crm.CanalRepository;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.crm.DestinatarioRepository;
import com.tacticlogistics.infrastructure.persistence.geo.CiudadRepository;

@Service
@Transactional(readOnly = true)
public class DestinatariosApplicationService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CanalRepository canalRepository;
    @Autowired
    private CiudadRepository ciudadRepository;
    @Autowired
    private DestinatarioRepository destinatarioRepository;

    // ---------------------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------------------
    public List<Object> findAllDestinatarioPorClientePorCanalPorTipoServicio(Integer clienteId,
            Integer canalId, Integer tipoServicioId) throws DataAccessException {
        List<Destinatario> entityList = destinatarioRepository
                .findAllByClienteIdAndCanalIdOrderByNombre(clienteId, canalId);

        List<Object> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(destinatarioToDto(a));
        });
        return list;
    }

    public List<Map<String, Object>> findDestinatariosRemitentesPorClientePorTipoServicioPorCanalPorFiltro(
            Integer clienteId, Integer tipoServicioId, Integer canalId, String filtro) throws DataAccessException {

        filtro = coalesce(filtro, "");
        filtro = "%" + filtro + "%";

        List<Destinatario> entityList = destinatarioRepository
                .findByClienteIdAndCanalIdAndFiltro(clienteId, canalId, filtro);

        List<Map<String, Object>> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(miniMap(a));
        });
        return list;
    }

    // ---------------------------------------------------------------------------------------------------------
    @Transactional(readOnly = false)
    public Destinatario save(DestinatarioDto dto) throws DataAccessException {

        Destinatario model = null;

        if (dto.getId() == null) {
            model = new Destinatario();
        } else {
            model = destinatarioRepository.findOne(dto.getId());
        }

        if (model.getId() == null) {
            if (dto.getClienteId() != null) {
                model.setClienteId(dto.getClienteId());
            }
        }
        // TODO validar dto.getClienteId() != null
        // TODO
        model.setCanalId(0);
        model.setCodigo(dto.getCodigo() == null ? "" : dto.getCodigo());
        model.setNumeroIdentificacion(dto.getNumeroIdentificacion() == null ? "" : dto.getNumeroIdentificacion());
        model.setDigitoVerificacion(dto.getDigitoVerificacion() == null ? "" : dto.getDigitoVerificacion());
        model.setNombre(dto.getNombre() == null ? "" : dto.getNombre());
        model.setNombreComercial(dto.getNombreComercial() == null ? "" : dto.getNombreComercial());
        model.setContacto(new Contacto(dto.getContactoNombres(), dto.getContactoEmail(), dto.getContactoTelefonos()));
        model.setFechaActualizacion(new Date());
        model.setUsuarioActualizacion(dto.getUsuarioActualizacion());

        destinatarioRepository.save(model);

        return model;
    }

    @Transactional(readOnly = false)
    public Destinatario save(ETLDestinatarioDto dto) throws DataAccessException {
        // StringBuffer errores = new StringBuffer();

        Cliente cliente = null;
        Canal canal = null;
        Ciudad ciudad = null;
        Destinatario model = null;

        cliente = clienteRepository.findByCodigoIgnoringCase(dto.getClienteCodigo());
        if (cliente == null) {
            throw new RuntimeException("(cliente == null)");
        }

        model = destinatarioRepository.findByClienteIdAndNumeroIdentificacion(cliente.getId(),
                dto.getNumeroIdentificacion());

        if (model != null) {
            return model;
        }

        model = new Destinatario();
        model.setClienteId(cliente.getId());
        model.setNumeroIdentificacion(dto.getNumeroIdentificacion());

        canal = canalRepository.findByNombreIgnoringCase(dto.getCanalCodigoAlterno());
        if (canal == null) {
            throw new RuntimeException("(canal == null)");
        }

        // TODO Intentar buscar a la ciudad por codigo alterno
        ciudad = ciudadRepository.findByNombreAlternoIgnoringCase(dto.getCiudadNombreAlterno());
        Integer ciudadId = (ciudad != null) ? ciudad.getId() : null;

        model.setCanalId(canal.getId());
        model.setCodigo(dto.getCodigo());
        // TODO
        model.setDigitoVerificacion(dto.getDigitoVerificacion());
        model.setNombre(dto.getNombre());
        model.setNombreComercial(dto.getNombreComercial());
        model.setDireccion(new OmsDireccion(ciudadId, dto.getDireccion(), dto.getDireccionIndicaciones()));
        model.setContacto(new Contacto(dto.getContactoNombres(), dto.getContactoEmail(), dto.getContactoTelefonos()));
        model.setActivo(dto.isActivo());
        model.setFechaActualizacion(new Date());
        model.setUsuarioActualizacion(dto.getClienteCodigo());

        model = destinatarioRepository.save(model);

        return model;
    }

    // ---------------------------------------------------------------------------------------------------------
    public List<Object> findAllTipoIdentificacion() throws DataAccessException {
        List<Object> list = new ArrayList<>();
        list.add(IdentificacionType.NI);
        list.add(IdentificacionType.CC);
        return list;
    }

    public List<Object> findCanales() throws DataAccessException {
        List<Canal> entityList = canalRepository.findAll(new Sort(Sort.DEFAULT_DIRECTION, "nombre"));

        List<Object> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(canalToDto(a));
        });
        return list;
    }

    public List<Object> findCanalesPorCliente(Integer clienteId) throws DataAccessException {
        List<Canal> entityList = canalRepository.findAllByClienteId(clienteId);

        List<Object> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(canalToDto(a));
        });
        return list;
    }

    public Map<String, Object> canalToDto(Canal model) {
        Map<String, Object> o = new HashMap<String, Object>();

        o.put("id", model.getId());
        o.put("codigo", model.getCodigo());
        o.put("nombre", model.getNombre());

        return o;
    }

    public Map<String, Object> destinatarioToDto(Destinatario model) {
        Map<String, Object> o = new HashMap<String, Object>();

        o.put("id", model.getId());
        o.put("clienteId", model.getClienteId());

        o.put("canalId", model.getCanalId());

        o.put("codigo", model.getCodigo());
        o.put("numeroIdentificacion", model.getNumeroIdentificacion());
        o.put("digitoVerificacion", model.getDigitoVerificacion());
        o.put("nombre", model.getNombre());
        o.put("nombreComercial", model.getNombreComercial());
        // direccion
        o.put("contacto", model.getContacto());
        o.put("activo", model.isActivo());
        o.put("fechaActualizacion", model.getFechaActualizacion());
        o.put("usuarioActualizacion", model.getUsuarioActualizacion());

        return o;
    }

    public Map<String, Object> miniMap(Destinatario model) {
        Map<String, Object> o = new HashMap<String, Object>();

        o.put("id", model.getId());
        o.put("numeroIdentificacion", model.getNumeroIdentificacion());
        o.put("nombre", model.getNombre());

        return o;
    }

}
