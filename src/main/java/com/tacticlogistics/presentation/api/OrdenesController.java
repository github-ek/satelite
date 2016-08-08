package com.tacticlogistics.presentation.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.dto.oms.ConsultaOrdenesDto;
import com.tacticlogistics.application.dto.ordenes.DestinatarioDto;
import com.tacticlogistics.application.dto.ordenes.LineaOrdenDto;
import com.tacticlogistics.application.dto.ordenes.OrdenDto;
import com.tacticlogistics.application.services.crm.DestinatariosRemitentesApplicationService;
import com.tacticlogistics.application.services.crm.DestinosOrigenesApplicationService;
import com.tacticlogistics.application.services.geo.CiudadesApplicationService;
import com.tacticlogistics.application.services.ordenes.OrdenesApplicationService;
import com.tacticlogistics.application.services.seguridad.UsuarioApplicationService;
import com.tacticlogistics.application.services.wms.ProductosApplicationService;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.domain.model.ordenes.EstadoOrdenType;
import com.tacticlogistics.domain.model.ordenes.Orden;

@CrossOrigin
@RestController()
@RequestMapping("/ordenes")
public class OrdenesController {
    @Autowired
    private OrdenesApplicationService ordenesService;
    @Autowired
    private DestinatariosRemitentesApplicationService destinatariosRemitentesService;
    @Autowired
    private DestinosOrigenesApplicationService destinosOrigenesService;
    @Autowired
    private CiudadesApplicationService ciudadesService;
    @Autowired
    private ProductosApplicationService productosService;
    @Autowired
    private UsuarioApplicationService usuarioService;

    // ----------------------------------------------------------------------------------------------------------------
    // -- Gestionar Ordenes
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping("/estados-ordenes")
    public List<Object> getAllEstadoOrden() {
        List<Object> list = new ArrayList<>();

        try {
            list = ordenesService.findAllEstadoOrden();
            list.add(buildEstadoOrdenViewModelDummy());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/tipos_servicio-x-usuario")
    public List<Object> getAllTipoServicioPorUsuario(
            @RequestParam(value = "id_usuario", required = true) Integer usuarioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = usuarioService.findAllTipoServicioPorUsuario(usuarioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping(path = "/ordenes-x-fecha_confirmacion", method = RequestMethod.GET)
    public Map<String,Object> getAllOrdenPorUsuarioPorTipoServicioPorEstadoOrdenPorCliente(
            @RequestParam(value = "id_usuario", required = true) Integer usuarioId,
            @RequestParam(value = "fecha_desde", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam(value = "fecha_hasta", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) {
        Map<String,Object> list = new HashMap<>();

        try {
            list = ordenesService.findAllOrdenPorFechaConfirmacion(usuarioId, fechaDesde, fechaHasta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @RequestMapping(path = "/ordenes-x-tipo_servicio-x-estado-x-usuario", method = RequestMethod.GET)
    public List<OrdenDto> getAllOrdenPorUsuarioPorTipoServicioPorEstadoOrdenPorCliente(
            @RequestParam(value = "id_usuario", required = true) Integer usuarioId,
            @RequestParam(value = "id_tipo_servicio", required = true) Integer tipoServicioId,
            @RequestParam(value = "id_estado_orden", required = false) EstadoOrdenType estadoOrden,
            @RequestParam(value = "id_cliente", required = false) Integer clienteId) {
        List<OrdenDto> list = new ArrayList<>();

        try {
            list = ordenesService.findAllOrdenPorUsuarioPorTipoServicioPorEstadoOrden(usuarioId, tipoServicioId,
                    estadoOrden, clienteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping(path = "/ordenes-x-tipo_servicio-x-estado-x-usuario-con-paginacion", method = RequestMethod.GET)
    public Page<ConsultaOrdenesDto> getAllOrdenPorUsuarioPorTipoServicioPorEstadoOrdenPorClienteConPaginacion(
            @RequestParam(value = "id_usuario", required = true) Integer usuarioId,
            @RequestParam(value = "id_tipo_servicio", required = true) Integer tipoServicioId,
            @RequestParam(value = "id_estado_orden", required = false) EstadoOrdenType estadoOrden,
            @RequestParam(value = "id_cliente", required = false) Integer clienteId,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
            ) {
        
        try {
            return ordenesService.findAllOrdenPorUsuarioPorTipoServicioPorEstadoOrdenConPaginacion(usuarioId, tipoServicioId, estadoOrden, clienteId, pageNumber, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO EXCEPCION SI NO SE ENCUENTRA
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public OrdenDto getOrdenPorId(@PathVariable Integer id) {
        OrdenDto dto = null;

        try {
            dto = ordenesService.findOneOrdenPorId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Configuración
    // ----------------------------------------------------------------------------------------------------------------
    // TODO pasar esto a un servicio
    // TODO retornar los horarios
    @RequestMapping("/configuracion_orden-x-tipo_servicio")
    public Object[] getConfiguracionClientePorTipoServicio(
            @RequestParam(value = "id_tipo_servicio", required = true) Integer tipoServicioId,
            @RequestParam(value = "id_cliente", required = true) Integer clienteId) {
        Object[] list = new Object[0];

        try {
            list = ordenesService.findAllConfiguracionClientePorTipoServicio(clienteId, tipoServicioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Bill To
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping("/clientes-x-usuario")
    public List<Object> getAllClienteListPorUsuarioPorTipoServicio(
            @RequestParam(value = "id_usuario", required = true) Integer usuarioId,
            @RequestParam(value = "id_tipo_servicio", required = true) Integer tipoServicioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = usuarioService.findClientesPorUsuarioIdPorTipoServicioId(usuarioId, tipoServicioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/segmentos-x-cliente-x-tipo_servicio")
    public List<Object> getAllSegmentoPorClientePorTipoServicio(
            @RequestParam(value = "id_cliente", required = true) Integer clienteId,
            @RequestParam(value = "id_tipo_servicio", required = true) Integer tipoServicioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = destinatariosRemitentesService.findCanalesPorCliente(clienteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/destinatarios_remitentes-x-cliente")
    public List<Object> getAllDestinatarioRemitentePorClientePorSegmentoPorTipoServicio(
            @RequestParam(value = "id_cliente", required = true) Integer clienteId,
            @RequestParam(value = "id_segmento", required = true) Integer canalId,
            @RequestParam(value = "id_tipo_servicio", required = true) Integer tipoServicioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = destinatariosRemitentesService.findAllDestinatarioRemitentePorClientePorCanalPorTipoServicio(
                    clienteId, canalId, tipoServicioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/destinatarios_remitentes-x-cliente-x-texto")
    public List<Object> getAllDestinatarioRemitentePorClientePorSegmentoPorTipoServicioPorTexto(
            @RequestParam(value = "id_cliente", required = true) Integer clienteId,
            @RequestParam(value = "id_tipo_servicio", required = true) Integer tipoServicioId,
            @RequestParam(value = "id_segmento", required = false) Integer canalId,
            @RequestParam(value = "texto", required = false) String texto) {
        List<Object> list = new ArrayList<>();

        try {
            list = destinatariosRemitentesService.findAllDestinatarioRemitentePorClientePorCanalPorTipoServicio(
                    clienteId, canalId, tipoServicioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Ship To (Destino/Origen)
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping("/ciudades-x-destinatario_remitente")
    public List<Map<String,Object>> getAllCiudadPorDestinatarioRemitentePorTipoServicio(
            @RequestParam(value = "id_destinatario_remitente", required = true) Integer destinatarioRemitenteId,
            @RequestParam(value = "id_tipo_servicio", required = true) Integer tipoServicioId) {
        List<Map<String,Object>> list = new ArrayList<>();

        try {
            list = ciudadesService.findCiudadesPorDestinatarioRemitentePorTipoServicio(destinatarioRemitenteId,
                    tipoServicioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/destinos_origenes-x-destinatario_remitente-x-ciudad")
    public List<Map<String,Object>> getAllDestinoOrigenListPorDestinatarioRemitentePorCiudadPorTipoServicio(
            @RequestParam(value = "id_destinatario_remitente", required = true) Integer destinatarioRemitenteId,
            @RequestParam(value = "id_ciudad", required = true) Integer ciudadId,
            @RequestParam(value = "id_tipo_servicio", required = true) Integer tipoServicioId) {
        List<Map<String,Object>> list = new ArrayList<>();

        try {
            list = destinosOrigenesService.findDestinosOrigenesPorDestinatarioRemitentePorTipoServicioPorCiudad(
                    destinatarioRemitenteId, ciudadId, tipoServicioId);
        } catch (Exception e) {
            ;
            e.printStackTrace();
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Ship To (Bodega Destino/Bodega Origen)
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping("/ciudades-con-bodega-x-cliente")
    public List<Object> getAllCiudadPorClientePorTipoServicio(
            @RequestParam(value = "id_cliente", required = true) Integer clienteId,
            @RequestParam(value = "id_tipo_servicio", required = true) Integer tipoServicioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = ciudadesService.findAllCiudadPorClientePorTipoServicio(clienteId, tipoServicioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/bodegas-x-ciudad-x-cliente")
    public List<Object> getAllBodegaPorCliente(@RequestParam(value = "id_cliente", required = true) Integer clienteId,
            @RequestParam(value = "id_ciudad", required = true) Integer ciudadId,
            @RequestParam(value = "id_tipo_servicio", required = true) Integer tipoServicioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = ordenesService.findAllBodegaPorCliente(clienteId, ciudadId, tipoServicioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Lineas de Productos
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping("/productos-x-cliente")
    public List<Object> getAllProductoPorCliente(@RequestParam(value = "id_cliente", required = true) Integer clienteId,
            @RequestParam(value = "id_tipo_servicio", required = true) Integer tipoServicioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = productosService.findAllProductoPorCliente(clienteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/ciudades-x-producto")
    public List<Object> getAllBodegaPorProductoPorCiudad(
            @RequestParam(value = "id_producto", required = true) Integer productoId) {
        List<Object> list = new ArrayList<>();

        try {
            list = ciudadesService.findCiudadesPorProducto(productoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/bodegas-x-producto-x-ciudad")
    public List<Object> getAllBodegaPorProductoPorCiudad(
            @RequestParam(value = "id_producto", required = true) Integer productoId,
            @RequestParam(value = "id_ciudad", required = true) Integer ciudadId) {
        List<Object> list = new ArrayList<>();

        try {
            list = ordenesService.findAllBodegaPorProductoPorCiudad(productoId, ciudadId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/unidades-x-producto")
    public List<Object> getAllUnidadPorProducto(
            @RequestParam(value = "id_producto", required = true) Integer productoId) {
        List<Object> list = new ArrayList<>();

        try {
            list = productosService.findAllUnidadPorProducto(productoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Lineas de Paquetes
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping("/tipos_contenido")
    public List<Object> getAllTipoContenido() {
        List<Object> list = new ArrayList<>();

        try {
            list = ordenesService.findAllTipoContenido();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Otros
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping("/tipos_forma_pago-x-cliente-x-tipo_servicio")
    public List<Object> getAllTipoFormaPagoPorClientePorTipoServicio(
            @RequestParam(value = "id_cliente", required = true) Integer clienteId,
            @RequestParam(value = "id_tipo_servicio", required = true) Integer tipoServicioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = ordenesService.findAllTipoFormaPagoPorClientePorTipoServicio(clienteId, tipoServicioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Mensajes
    // ----------------------------------------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------------------------------------
    // -- Historico de Cambios
    // ----------------------------------------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------------------------------------
    // -- Save
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/saveDatosFacturacion", method = RequestMethod.POST)
    public Map<String, Object> save(@RequestBody DestinatarioDto[] models) {
        DestinatarioDto model = models[0];

        Map<String, Object> respuesta = new HashMap<>();
        MensajesDto mensajes = new MensajesDto();
        try {
            Orden orden = this.ordenesService.saveOrden(model);
            respuesta.put("orden", ordenesService.ordenToViewModel(orden));
            mensajes.addMensaje(SeveridadType.INFO, "");
        } catch (Exception e) {
            mensajes.addMensaje(e);
        }

        respuesta.put("mensajes", mensajes);
        return respuesta;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Map<String, Object> save(@RequestBody OrdenDto model) {

        Map<String, Object> respuesta = new HashMap<>();
        MensajesDto mensajes = new MensajesDto();
        try {
            Orden orden = this.ordenesService.saveOrden(model, model.getNuevoEstadoOrden());
            respuesta.put("orden", ordenesService.ordenToViewModel(orden));
            mensajes.addMensaje(SeveridadType.INFO, "");
        } catch (Exception e) {
            mensajes.addMensaje(e);
        }

        respuesta.put("mensajes", mensajes);
        return respuesta;
    }

    @RequestMapping(value = "/saveLineaOrden", method = RequestMethod.POST)
    public Map<String, Object> save(@RequestBody LineaOrdenDto[] models) {
        LineaOrdenDto model = models[0];

        Map<String, Object> respuesta = new HashMap<>();
        MensajesDto mensajes = new MensajesDto();
        try {
            Orden orden = this.ordenesService.saveLineaOrden(model);
            respuesta.put("orden", ordenesService.ordenToViewModel(orden));
            mensajes.addMensaje(SeveridadType.INFO, "");
        } catch (Exception e) {
            mensajes.addMensaje(e);
        }

        respuesta.put("mensajes", mensajes);
        return respuesta;
    }

    @RequestMapping("/{ordenId}/deleteLineaOrden/{lineaOrdenId}/{usuario}")
    public Map<String, Object> delete(@PathVariable Integer ordenId, @PathVariable Integer lineaOrdenId,
            @PathVariable String usuario) {

        Map<String, Object> respuesta = new HashMap<>();
        MensajesDto mensajes = new MensajesDto();
        try {
            Orden orden = this.ordenesService.deleteLineaOrden(ordenId, lineaOrdenId, usuario);
            respuesta.put("orden", ordenesService.ordenToViewModel(orden));
            mensajes.addMensaje(SeveridadType.INFO, "");
        } catch (Exception e) {
            mensajes.addMensaje(e);
        }

        respuesta.put("mensajes", mensajes);
        return respuesta;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Helpers
    // ----------------------------------------------------------------------------------------------------------------
    protected Object buildEstadoOrdenViewModelDummy() {
        Map<String, Object> o = new HashMap<String, Object>();

        o.put("id", null);
        o.put("nombre", "VER TODAS");
        o.put("ordinal", 99999);

        return o;
    }
}
