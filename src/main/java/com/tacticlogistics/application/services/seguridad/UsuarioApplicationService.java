package com.tacticlogistics.application.services.seguridad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.seguridad.Usuario;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.seguridad.UsuarioRepository;

@Service
@Transactional(readOnly = true)
public class UsuarioApplicationService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Object> findAllServicioPorUsuario(Integer usuarioId) throws DataAccessException {
        List<Object> list = new ArrayList<>();

        return list;
    }
    // TODO Implementar consulta en repositorio
    public List<Object> findClientesPorUsuarioIdPorServicioId(Integer usuarioId, Integer servicioId)
            throws DataAccessException {
        List<Object> list = new ArrayList<>();

        Usuario usuario = usuarioRepository.findOne(usuarioId);

        if (usuario != null) {
            List<Cliente> clientes = clienteRepository.findByUsuarioIdAndServicioId(usuarioId, servicioId);

            clientes.forEach(a -> {
                Map<String, Object> o = new HashMap<String, Object>();

                o.put("id", a.getId());
                o.put("numeroIdentificacion", a.getNumeroIdentificacion());
                o.put("digitoVerificacion", a.getDigitoVerificacion());
                o.put("codigo", a.getCodigo());
                o.put("nombre", a.getNombre());
                o.put("nombreComercial", a.getNombreComercial());

                list.add(o);
            });

        }

        return list;
    }
}
