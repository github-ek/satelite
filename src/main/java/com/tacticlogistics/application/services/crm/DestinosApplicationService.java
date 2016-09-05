package com.tacticlogistics.application.services.crm;

import static com.tacticlogistics.infrastructure.services.Basic.coalesce;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.crm.DestinoOrigenDto;
import com.tacticlogistics.application.dto.etl.ETLDestinoOrigenDto;
import com.tacticlogistics.application.services.geo.CiudadesApplicationService;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.OmsDireccion;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;
import com.tacticlogistics.domain.model.crm.DestinoOrigen;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.crm.DestinatarioRemitenteRepository;
import com.tacticlogistics.infrastructure.persistence.crm.DestinoOrigenRepository;
import com.tacticlogistics.infrastructure.persistence.geo.CiudadRepository;

@Service
@Transactional(readOnly = true)
public class DestinosOrigenesApplicationService {
    @Autowired
    private CiudadesApplicationService ciudadesService;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CiudadRepository ciudadRepository;
    @Autowired
    private DestinatarioRemitenteRepository destinatarioRemitenteRepository;
    @Autowired
    private DestinoOrigenRepository destinoOrigenRepository;

    public List<Map<String, Object>> findDestinosOrigenesPorDestinatarioRemitentePorTipoServicioPorCiudad(
            Integer destinatarioRemitenteId, Integer ciudadId, Integer tipoServicioId) throws DataAccessException {
        List<DestinoOrigen> entityList = destinoOrigenRepository
                .findAllByDestinatarioRemitenteIdAndDireccionCiudadIdOrderByCodigoAscNombreAsc(destinatarioRemitenteId,
                        ciudadId);

        List<Map<String, Object>> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(destinoOrigenToDto(a));
        });
        return list;
    }

    public List<Map<String, Object>> findDestinosOrigenesPorDestinatarioRemitentePorTipoServicioPorCiudad(
            Integer destinatarioRemitenteId, Integer ciudadId) throws DataAccessException {
        List<DestinoOrigen> entityList = destinoOrigenRepository
                .findAllByDestinatarioRemitenteIdAndDireccionCiudadIdOrderByCodigoAscNombreAsc(destinatarioRemitenteId,
                        ciudadId);

        List<Map<String, Object>> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(destinoOrigenToDto2(a));
        });
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Save
    // ----------------------------------------------------------------------------------------------------------------
    @Transactional(readOnly = false)
    public DestinoOrigen save(DestinoOrigenDto dto) throws DataAccessException {

        DestinoOrigen model = null;

        if (dto.getId() == null) {
            model = new DestinoOrigen();
        } else {
            model = destinoOrigenRepository.findOne(dto.getId());
        }

        if (dto.getDestinatarioRemitenteId() != null) {
            model.setDestinatarioRemitenteId(dto.getDestinatarioRemitenteId());
        }

        if (dto.getCiudadId() != null) {
            model.setDireccion(
                    new OmsDireccion(dto.getCiudadId(), (dto.getDireccion() == null ? "" : dto.getDireccion()),
                            (dto.getIndicacionesDireccion() == null ? "" : dto.getIndicacionesDireccion())));
        }

        model.setCodigo(dto.getCodigo() == null ? "" : dto.getCodigo());
        model.setNombre(dto.getNombre() == null ? "" : dto.getNombre());

        model.setContacto(new Contacto(dto.getContactoNombres(), dto.getContactoEmail(), dto.getContactoTelefonos()));

        model.setFechaActualizacion(new Date());
        model.setUsuarioActualizacion(dto.getUsuarioActualizacion());

        destinoOrigenRepository.save(model);

        return model;
    }
    
    @Transactional(readOnly = false)
    public DestinoOrigen save(ETLDestinoOrigenDto dto) throws DataAccessException {
        // StringBuffer errores = new StringBuffer();

        Cliente cliente = null;
        Ciudad ciudad = null;
        DestinatarioRemitente destinatario = null;

        cliente = clienteRepository.findByCodigoIgnoringCase(dto.getClienteCodigo());
        if (cliente == null) {
            throw new RuntimeException("(cliente == null)");
        }

        destinatario = destinatarioRemitenteRepository.findByClienteIdAndNumeroIdentificacion(cliente.getId(),
                dto.getNumeroIdentificacion());
        if (destinatario == null) {
            throw new RuntimeException("(destinatario == null)");
        }

        ciudad = ciudadRepository.findByNombreAlternoIgnoringCase(dto.getCiudadNombreAlterno());
        if (ciudad == null) {
            throw new RuntimeException("(ciudad == null)");
        }

        List<DestinoOrigen> list = destinoOrigenRepository
                .findAllByDestinatarioRemitenteIdAndDireccionDireccionOrderByCodigoAscNombreAsc(destinatario.getId(),
                        dto.getDireccion());

        DestinoOrigen model = null;

        if (list.size() <= 1) {
            if (list.isEmpty()) {
                model = new DestinoOrigen();
                model.setDestinatarioRemitenteId(destinatario.getId());
            } else {
                if (list.size() == 1) {
                    model = list.get(0);
                    return model;
                }
            }

            model.setCodigo(dto.getCodigo());
            model.setNombre(dto.getNombre());
            model.setDireccion(new OmsDireccion(ciudad.getId(), dto.getDireccion(), dto.getDireccionIndicaciones()));
            model.setContacto(
                    new Contacto(dto.getContactoNombres(), dto.getContactoEmail(), dto.getContactoTelefonos()));

            model.setFechaActualizacion(new Date());
            model.setUsuarioActualizacion(dto.getClienteCodigo());

            destinoOrigenRepository.save(model);
        }

        return model;
    }

    public Map<String, Object> destinoOrigenToDto(DestinoOrigen model) {
        Map<String, Object> o = new HashMap<String, Object>();

        String nombreAuxiliar = model.getNombre();
        if (nombreAuxiliar == null || "".equals(nombreAuxiliar)) {
            nombreAuxiliar = model.getDireccion().getDireccion();
        }

        o.put("id", model.getId());
        o.put("codigo", model.getCodigo());
        o.put("nombre", model.getNombre());
        o.put("nombreAuxiliar", nombreAuxiliar);
        o.put("direccion", ciudadesService.direccionToViewModel(model.getDireccion()));
        o.put("contacto", model.getContacto());

        return o;
    }

    public Map<String, Object> destinoOrigenToDto2(DestinoOrigen model) {
        Map<String, Object> o = new HashMap<String, Object>();

        StringBuffer sb = new StringBuffer();

        sb.append(coalesce(model.getNombre(), ""));
        if (!sb.toString().trim().isEmpty()) {
            sb.append(" - ");
        }
        sb.append(model.getDireccion().getDireccion());

        o.put("id", model.getId());
        o.put("nombreAuxiliar", sb.toString());
        o.put("codigo", model.getCodigo());
        o.put("nombre", model.getNombre());
        o.put("direccion", ciudadesService.direccionToDto(model.getDireccion()));
        o.put("contacto", model.getContacto());

        return o;
    }

    public DestinoOrigen dtoToNewDestinoOrigen(DestinoOrigenDto dto) {
        // DestinoOrigen model = new DestinoOrigen(
        // dto.getCodigo(),
        // dto.getNombre(),
        // new
        // DireccionEmbeddable(null,,dto.getDireccion(),dto.getIndicacionesDireccion())
        // //model.setId()
        // model.setCodigo("");
        // model.setNombre("");
        // model.setDireccion(new DireccionEmbeddable());
        // model.setContacto(new ContactoEmbeddable(this.getContactoNombres(),
        // this.getContactoEmail(), this.getContactoEmail()));
        // model.setActivo(true);
        // model.setFechaActualizacion(new Date());
        // model.setUsuarioActualizacion(model.getUsuarioActualizacion());
        return null;
    }
}
