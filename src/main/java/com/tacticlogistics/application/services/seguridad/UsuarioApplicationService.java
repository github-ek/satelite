package com.tacticlogistics.application.services.seguridad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.domain.model.common.RolUbicacionType;
import com.tacticlogistics.domain.model.common.UbicacionType;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.seguridad.Usuario;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.crm.TipoServicioRepository;
import com.tacticlogistics.infrastructure.persistence.seguridad.UsuarioRepository;

@Service
@Transactional(readOnly = true)
public class UsuarioApplicationService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TipoServicioRepository tipoServicioRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Object> findAllTipoServicioPorUsuario(Integer usuarioId) throws DataAccessException {
        List<Object> list = new ArrayList<>();

        List<TipoServicio> tipoServicioList = tipoServicioRepository.findByUsuarioId(usuarioId);
        tipoServicioList.forEach(a -> {
            Map<String, Object> o = new HashMap<String, Object>();

            o.put("id", a.getId());
            o.put("codigo", a.getCodigo());
            o.put("nombre", a.getNombre());

            o.put("admiteProductosPorLinea", a.isAdmiteProductosPorLinea());
            o.put("admitePaquetesPorLinea", a.isAdmitePaquetesPorLinea());

            RolUbicacionType rolUbicacionOrden = a.isRegistrarDestinoEnLaOrden() ? RolUbicacionType.DESTINO
                    : RolUbicacionType.ORIGEN;
            RolUbicacionType rolUbicacionLineaOrden = (rolUbicacionOrden == RolUbicacionType.DESTINO)
                    ? RolUbicacionType.ORIGEN : RolUbicacionType.DESTINO;
            UbicacionType tipoUbicacionOrden;
            UbicacionType tipoUbicacionLineaOrden;

            if (rolUbicacionOrden == RolUbicacionType.DESTINO) {
                tipoUbicacionOrden = a.isAdmiteBodegasComoDestino() ? UbicacionType.BODEGA : UbicacionType.DIRECCION;
            } else {
                tipoUbicacionOrden = a.isAdmiteBodegasComoOrigen() ? UbicacionType.BODEGA : UbicacionType.DIRECCION;
            }

            if (rolUbicacionLineaOrden == RolUbicacionType.DESTINO) {
                tipoUbicacionLineaOrden = a.isAdmiteBodegasComoDestino() ? UbicacionType.BODEGA
                        : UbicacionType.DIRECCION;
            } else {
                tipoUbicacionLineaOrden = a.isAdmiteBodegasComoOrigen() ? UbicacionType.BODEGA
                        : UbicacionType.DIRECCION;
            }

            o.put("rolUbicacionOrden", rolUbicacionOrden);
            o.put("rolUbicacionLineaOrden", rolUbicacionLineaOrden);
            o.put("tipoUbicacionOrden", tipoUbicacionOrden);
            o.put("tipoUbicacionLineaOrden", tipoUbicacionLineaOrden);

            list.add(o);
        });

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
                o.put("identificacionType", a.getIdentificacionType());
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
