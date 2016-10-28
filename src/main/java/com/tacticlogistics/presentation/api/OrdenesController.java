package com.tacticlogistics.presentation.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.services.crm.DestinatariosApplicationService;
import com.tacticlogistics.application.services.crm.DestinosApplicationService;
import com.tacticlogistics.application.services.geo.CiudadesApplicationService;
import com.tacticlogistics.application.services.seguridad.UsuarioApplicationService;
import com.tacticlogistics.application.services.wms.ProductosApplicationService;

@CrossOrigin
@RestController()
@RequestMapping("/ordenes")
public class OrdenesController {
    @Autowired
    private DestinatariosApplicationService destinatariosRemitentesService;
    @Autowired
    private DestinosApplicationService destinosService;
    @Autowired
    private CiudadesApplicationService ciudadesService;
    @Autowired
    private ProductosApplicationService productosService;
    @Autowired
    private UsuarioApplicationService usuarioService;

    // ----------------------------------------------------------------------------------------------------------------
    // -- Gestionar Ordenes
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping("/tipos_servicio-x-usuario")
    public List<Object> getAllServicioPorUsuario(
            @RequestParam(value = "id_usuario", required = true) Integer usuarioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = usuarioService.findAllServicioPorUsuario(usuarioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

 

    // ----------------------------------------------------------------------------------------------------------------
    // -- Bill To
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping("/clientes-x-usuario")
    public List<Object> getAllClienteListPorUsuarioPorServicio(
            @RequestParam(value = "id_usuario", required = true) Integer usuarioId,
            @RequestParam(value = "id_servicio", required = true) Integer servicioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = usuarioService.findClientesPorUsuarioIdPorServicioId(usuarioId, servicioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/canales-x-cliente-x-servicio")
    public List<Object> getAllCanalesPorClientePorServicio(
            @RequestParam(value = "id_cliente", required = true) Integer clienteId,
            @RequestParam(value = "id_servicio", required = true) Integer servicioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = destinatariosRemitentesService.findCanalesPorCliente(clienteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/destinatarios_remitentes-x-cliente")
    public List<Object> getAllDestinatarioPorClientePorCanalPorServicio(
            @RequestParam(value = "id_cliente", required = true) Integer clienteId,
            @RequestParam(value = "id_segmento", required = true) Integer canalId,
            @RequestParam(value = "id_servicio", required = true) Integer servicioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = destinatariosRemitentesService.findAllDestinatarioPorClientePorCanalPorServicio(
                    clienteId, canalId, servicioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/destinatarios_remitentes-x-cliente-x-texto")
    public List<Object> getAllDestinatarioPorClientePorCanalPorServicioPorTexto(
            @RequestParam(value = "id_cliente", required = true) Integer clienteId,
            @RequestParam(value = "id_servicio", required = true) Integer servicioId,
            @RequestParam(value = "id_segmento", required = false) Integer canalId,
            @RequestParam(value = "texto", required = false) String texto) {
        List<Object> list = new ArrayList<>();

        try {
            list = destinatariosRemitentesService.findAllDestinatarioPorClientePorCanalPorServicio(
                    clienteId, canalId, servicioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Ship To (Destino/Origen)
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping("/ciudades-x-destinatario_remitente")
    public List<Map<String,Object>> getAllCiudadPorDestinatarioPorServicio(
            @RequestParam(value = "id_destinatario_remitente", required = true) Integer destinatarioId,
            @RequestParam(value = "id_servicio", required = true) Integer servicioId) {
        List<Map<String,Object>> list = new ArrayList<>();

        try {
            list = ciudadesService.findCiudadesPorDestinatarioPorServicio(destinatarioId,
                    servicioId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/destinos_origenes-x-destinatario_remitente-x-ciudad")
    public List<Map<String,Object>> getAllDestinoOrigenListPorDestinatarioPorCiudadPorServicio(
            @RequestParam(value = "id_destinatario_remitente", required = true) Integer destinatarioId,
            @RequestParam(value = "id_ciudad", required = true) Integer ciudadId,
            @RequestParam(value = "id_servicio", required = true) Integer servicioId) {
        List<Map<String,Object>> list = new ArrayList<>();

        try {
            list = destinosService.findDestinosPorDestinatarioPorServicioPorCiudad(
                    destinatarioId, ciudadId, servicioId);
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
    public List<Object> getAllCiudadPorClientePorServicio(
            @RequestParam(value = "id_cliente", required = true) Integer clienteId,
            @RequestParam(value = "id_servicio", required = true) Integer servicioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = ciudadesService.findAllCiudadPorClientePorServicio(clienteId, servicioId);
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
            @RequestParam(value = "id_servicio", required = true) Integer servicioId) {
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

    // ----------------------------------------------------------------------------------------------------------------
    // -- Otros
    // ----------------------------------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------------
    // -- Mensajes
    // ----------------------------------------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------------------------------------
    // -- Historico de Cambios
    // ----------------------------------------------------------------------------------------------------------------

    // ----------------------------------------------------------------------------------------------------------------
    // -- Save
    // ----------------------------------------------------------------------------------------------------------------

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
