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

    public List<Object> findAllTipoServicioPorUsuario(Integer usuarioId) throws DataAccessException {
        List<Object> list = new ArrayList<>();

//        list<tiposervicio> tiposerviciolist = tiposerviciorepository.findbyusuarioid(usuarioid);
//        tiposerviciolist.foreach(a -> {
//            map<string, object> o = new hashmap<string, object>();
//
//            o.put("id", a.getid());
//            o.put("codigo", a.getcodigo());
//            o.put("nombre", a.getnombre());
//
//            o.put("admiteproductosporlinea", a.isadmiteproductosporlinea());
//            o.put("admitepaquetesporlinea", a.isadmitepaquetesporlinea());
//
//            rolubicaciontype rolubicacionorden = a.isregistrardestinoenlaorden() ? rolubicaciontype.destino
//                    : rolubicaciontype.origen;
//            rolubicaciontype rolubicacionlineaorden = (rolubicacionorden == rolubicaciontype.destino)
//                    ? rolubicaciontype.origen : rolubicaciontype.destino;
//            ubicaciontype tipoubicacionorden;
//            ubicaciontype tipoubicacionlineaorden;
//
//            if (rolubicacionorden == rolubicaciontype.destino) {
//                tipoubicacionorden = a.isadmitebodegascomodestino() ? ubicaciontype.bodega : ubicaciontype.direccion;
//            } else {
//                tipoubicacionorden = a.isadmitebodegascomoorigen() ? ubicaciontype.bodega : ubicaciontype.direccion;
//            }
//
//            if (rolubicacionlineaorden == rolubicaciontype.destino) {
//                tipoubicacionlineaorden = a.isadmitebodegascomodestino() ? ubicaciontype.bodega
//                        : ubicaciontype.direccion;
//            } else {
//                tipoubicacionlineaorden = a.isadmitebodegascomoorigen() ? ubicaciontype.bodega
//                        : ubicaciontype.direccion;
//            }
//
//            o.put("rolubicacionorden", rolubicacionorden);
//            o.put("rolubicacionlineaorden", rolubicacionlineaorden);
//            o.put("tipoubicacionorden", tipoubicacionorden);
//            o.put("tipoubicacionlineaorden", tipoubicacionlineaorden);
//
//            list.add(o);
//        });

        return list;
    }
    // TODO Implementar consulta en repositorio
    public List<Object> findClientesPorUsuarioIdPorTipoServicioId(Integer usuarioId, Integer tipoServicioId)
            throws DataAccessException {
        List<Object> list = new ArrayList<>();

        Usuario usuario = usuarioRepository.findOne(usuarioId);

        if (usuario != null) {
            List<Cliente> clientes = clienteRepository.findByUsuarioIdAndTipoServicioId(usuarioId, tipoServicioId);

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
