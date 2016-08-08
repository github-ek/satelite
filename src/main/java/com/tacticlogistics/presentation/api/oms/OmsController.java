package com.tacticlogistics.presentation.api.oms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.common.CustomPage;
import com.tacticlogistics.application.dto.common.ItemGenerico;
import com.tacticlogistics.application.dto.oms.OmsOrdenDto;
import com.tacticlogistics.application.services.crm.ClientesApplicationService;
import com.tacticlogistics.application.services.crm.DestinatariosRemitentesApplicationService;
import com.tacticlogistics.application.services.crm.DestinosOrigenesApplicationService;
import com.tacticlogistics.application.services.geo.CiudadesApplicationService;
import com.tacticlogistics.application.services.oms.OmsApplicationService;
import com.tacticlogistics.application.services.seguridad.UsuarioApplicationService;
import com.tacticlogistics.application.services.wms.BodegasApplicationService;
import com.tacticlogistics.application.services.wms.ProductosApplicationService;
import com.tacticlogistics.domain.model.ordenes.EstadoOrdenType;

@CrossOrigin
@RestController
@RequestMapping("/oms/ordenes")
public class OmsController {

    @Autowired
    private UsuarioApplicationService usuariosService;

    @Autowired
    private ClientesApplicationService clientesService;

    @Autowired
    private DestinatariosRemitentesApplicationService destinatariosRemitentesService;

    @Autowired
    private CiudadesApplicationService ciudadesService;

    @Autowired
    private DestinosOrigenesApplicationService destinosOrigenesService;

    @Autowired
    private ProductosApplicationService productosService;

    @Autowired
    private BodegasApplicationService bodegasService;

    @Autowired
    private OmsApplicationService omsService;

    // ----------------------------------------------------------------------------------------------------------------
    // -- CRUD
    // ----------------------------------------------------------------------------------------------------------------
    
    // Listar Ordenes
    @RequestMapping(path = "/ordenes-x-usuario-x-tipo_servicio-x-cliente-x-estado_orden", method = { RequestMethod.GET, RequestMethod.POST })
    public CustomPage<String[]> getOrdenesPorUsuarioIdAndTipoServicioIdAndEstadoOrden(
            @RequestParam(required = true) Integer usuarioId, @RequestParam(required = true) Integer tipoServicioId,
            @RequestParam(required = false) Integer clienteId,
            @RequestParam(required = false) EstadoOrdenType estadoOrdenId, @RequestParam(required = true) int start,
            @RequestParam(required = true) int length) {

        CustomPage<String[]> page = new CustomPage<>(0L, null);
        try {
            page = omsService.findOrdenesByUsuarioIdAndTipoServicioIdAndEstadoOrden(usuarioId, tipoServicioId,
                    clienteId, estadoOrdenId, start, length);
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return page;
    }

    // Get Orden
    @RequestMapping(path = "/{id}", method = { RequestMethod.GET, RequestMethod.POST })
    public OmsOrdenDto getOrdenPorId(
            @PathVariable Integer id) {
        OmsOrdenDto dto = null;

        try {
            dto = omsService.findOneOrdenPorId(id);
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return dto;
    }

    // TODO Get Configuracion x Tipo Servicio
    // @RequestMapping("/configuracion_orden-x-tipo_servicio")
    // public Object[] getConfiguracionClientePorTipoServicio(
    // @RequestParam(value = "id_tipo_servicio", required = true) Integer
    // tipoServicioId,
    // @RequestParam(value = "id_cliente", required = true) Integer clienteId) {
    // Object[] list = new Object[0];
    //
    // try {
    // list =
    // ordenesService.findAllConfiguracionClientePorTipoServicio(clienteId,
    // tipoServicioId);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return list;
    // }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Bill To
    // ----------------------------------------------------------------------------------------------------------------
    // Clientes habilitados para el usuario actual y un tipo de servicio
    @RequestMapping(value = "/clientes-x-usuario-x-tipo_servicio", method = RequestMethod.GET)
    public List<Object> getClientesPorUsuarioPorTipoServicio(
            @RequestParam(required = true) Integer usuarioId,
            @RequestParam(required = true) Integer tipoServicioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = usuariosService.findClientesPorUsuarioIdPorTipoServicioId(usuarioId, tipoServicioId);
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // Canales habilitados para un cliente y tipo de servicio
    @RequestMapping(value = "/canales-x-cliente-x-tipo_servicio", method = RequestMethod.GET)
    public List<Map<String, Object>> getCanalesPorClientePorTipoServicio(
            @RequestParam(required = true) Integer clienteId, 
            @RequestParam(required = true) Integer tipoServicioId) {
        List<Map<String, Object>> list = new ArrayList<>();

        try {
            list = clientesService.findCanalesPorCliente(clienteId);
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // Destinatarios/Remitentes para un cliente, tipo de servicio y filtro
    @RequestMapping(value = "/destinatarios_remitentes-x-cliente-x-tipo_servicio-x-canal-x-filtro", method = RequestMethod.GET)
    public List<Map<String, Object>> getDestinatariosRemitentesPorClientePorTipoServicioPorCanalPorFiltro(
            @RequestParam(required = true) Integer clienteId, 
            @RequestParam(required = true) Integer tipoServicioId,
            @RequestParam(required = false) Integer canalId,
            @RequestParam(required = false, defaultValue = "") String filtro) {

        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = destinatariosRemitentesService.findDestinatariosRemitentesPorClientePorTipoServicioPorCanalPorFiltro(
                    clienteId, tipoServicioId, canalId, filtro);
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Ship To (Destino)
    // ----------------------------------------------------------------------------------------------------------------
    // Ciudades habilitadas para un destinatario y tipo de servicio
    @RequestMapping(value = "/ciudades-x-destinatario_remitente-x-tipo_servicio", method = RequestMethod.GET)
    public List<Map<String, Object>> getCiudadesPorDestinatarioRemitentePorTipoServicio(
            @RequestParam(required = true) Integer destinatarioRemitenteId,
            @RequestParam(required = true) Integer tipoServicioId) {

        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = ciudadesService.findCiudadesPorDestinatarioRemitentePorTipoServicio(destinatarioRemitenteId,
                    tipoServicioId);
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // Destinos habilitados para un destinatario, ciudad y tipo de servicio
    @RequestMapping("/destinos_origenes-x-destinatario_remitente-x-tipo_servicio-x-ciudad")
    public List<Map<String, Object>> getDestinosOrigenesPorDestinatarioRemitentePorTipoServicioPorCiudad(
            @RequestParam(required = true) Integer destinatarioRemitenteId,
            @RequestParam(required = true) Integer tipoServicioId, @RequestParam(required = true) Integer ciudadId) {

        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = destinosOrigenesService.findDestinosOrigenesPorDestinatarioRemitentePorTipoServicioPorCiudad(
                    destinatarioRemitenteId, ciudadId);
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // TODO Malla horaria x cliente, canal, destinatario, ciudad, destino

    // ----------------------------------------------------------------------------------------------------------------
    // -- Ship To (Bodega Destino)
    // ----------------------------------------------------------------------------------------------------------------
    // TODO Ciudades con bodegas habilitadas para un cliente y tipo de servicio
    // @RequestMapping("/ciudades-con-bodega-x-cliente")
    // public List<Object> getAllCiudadPorClientePorTipoServicio(
    // @RequestParam(value = "id_cliente", required = true) Integer clienteId,
    // @RequestParam(value = "id_tipo_servicio", required = true) Integer
    // tipoServicioId) {
    // List<Object> list = new ArrayList<>();
    //
    // try {
    // list = ciudadesService.findAllCiudadPorClientePorTipoServicio(clienteId,
    // tipoServicioId);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return list;
    // }

    // TODO Bodegas habilitados para un cliente, ciudad y tipo de servicio
    // @RequestMapping("/bodegas-x-ciudad-x-cliente")
    // public List<Object> getAllBodegaPorCliente(@RequestParam(value =
    // "id_cliente", required = true) Integer clienteId,
    // @RequestParam(value = "id_ciudad", required = true) Integer ciudadId,
    // @RequestParam(value = "id_tipo_servicio", required = true) Integer
    // tipoServicioId) {
    // List<Object> list = new ArrayList<>();
    //
    // try {
    // list = ordenesService.findAllBodegaPorCliente(clienteId, ciudadId,
    // tipoServicioId);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return list;
    // }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Requerimientos
    // ----------------------------------------------------------------------------------------------------------------
    // Requerimientos de Distribución x cliente x tipo de servicio x
    // destinatario remitente
    @RequestMapping("/requerimientos_distribucion-x-cliente-x-tipo_servicio-x-destinatario_remitente")
    public List<Map<String, Object>> getRequerimientosDistribucionPorClientePorTipoServicioDestinatarioRemitente(
            @RequestParam(required = true) Integer clienteId, @RequestParam(required = true) Integer tipoServicioId,
            @RequestParam(required = true) Integer destinatarioRemitenteId) {

        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = omsService.findRequerimientosDistribucionPorClientePorTipoServicioDestinatarioRemitente(clienteId,
                    tipoServicioId, destinatarioRemitenteId);
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // Requerimientos de Alistamiento x cliente x tipo de servicio x destinatario
    // remitente
    @RequestMapping("/requerimientos_alistamiento-x-cliente-x-tipo_servicio-x-destinatario_remitente")
    public List<Map<String, Object>> getRequerimientosAlistamientoPorClientePorTipoServicioDestinatarioRemitente(
            @RequestParam(required = true) Integer clienteId, @RequestParam(required = true) Integer tipoServicioId,
            @RequestParam(required = true) Integer destinatarioRemitenteId) {

        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = omsService.findRequerimientosAlistamientoPorClientePorTipoServicioDestinatarioRemitente(clienteId,
                    tipoServicioId, destinatarioRemitenteId);
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Lineas de Productos
    // ----------------------------------------------------------------------------------------------------------------
    // Productos x cliente y tipo de servicio
    @RequestMapping("/productos-x-cliente-x-tipo_servicio")
    public List<Object> getProductosPorClientePorTipoServicio(@RequestParam(required = true) Integer clienteId,
            @RequestParam(required = true) Integer tipoServicioId) {
        List<Object> list = new ArrayList<>();

        try {
            list = productosService.findAllProductoPorCliente(clienteId);
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // Unidades x producto
    @RequestMapping("/unidades-x-producto")
    public List<Object> getUnidadesPorProducto(@RequestParam(required = true) Integer productoId) {
        List<Object> list = new ArrayList<>();

        try {
            list = productosService.findAllUnidadPorProducto(productoId);
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // Ciudades donde existen bodegas habilitadas para un producto
    @RequestMapping("/ciudades-x-producto")
    public List<Object> getCiudadesPorProductoPorCiudad(@RequestParam(required = true) Integer productoId) {
        List<Object> list = new ArrayList<>();

        try {
            list = ciudadesService.findCiudadesPorProducto(productoId);
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // Bodegas habilitadas para un producto y ciudad
    @RequestMapping("/bodegas-x-producto-x-ciudad")
    public List<Object> getBodegasPorProductoPorCiudad(@RequestParam(required = true) Integer productoId,
            @RequestParam(required = true) Integer ciudadId) {
        List<Object> list = new ArrayList<>();

        try {
            list = bodegasService.findBodegasPorProductoPorCiudad(productoId, ciudadId);
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Causales
    // ----------------------------------------------------------------------------------------------------------------
    // Causales de Solicitud de Revision cuando lo solicte el cliente
    @RequestMapping("/causales_solicitud_revision_cliente")
    public List<ItemGenerico<Integer>> getCausalesSolicitudRevisionCliente() {
        List<ItemGenerico<Integer>> list = new ArrayList<>();

        try {
            list = omsService.findCausalesSolicitudRevisionCliente();
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // Causales de Solicitud de Revision cuando lo solicite planeación
    @RequestMapping("/causales_solicitud_revision_planeacion")
    public List<ItemGenerico<Integer>> getCausalesSolicitudRevisionPlaneacion() {
        List<ItemGenerico<Integer>> list = new ArrayList<>();

        try {
            list = omsService.findCausalesSolicitudRevisionPlaneacionLogistica();
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // Causales de anulación
    @RequestMapping("/causales_anulacion_orden")
    public List<ItemGenerico<Integer>> getCausalesAnulacionOrden() {
        List<ItemGenerico<Integer>> list = new ArrayList<>();

        try {
            list = omsService.findCausalesAnulacionOrden();
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Operaciones de Modificacion
    // ----------------------------------------------------------------------------------------------------------------
    // TODO Guardar
    //
    // id_orden
    // concurrency_version

    // id_tipo_servicio, id_cliente, id_origen_orden INSERT

    // Bill To
    // numero_documento_orden_cliente, id_consolidado,
    // id_canal, id_destinatario_remitente_destinatario
    // direccion, contacto,
    // id_estado_solicitud_actualizacion_datos_contacto_destinatario

    // Ship To
    // id_ciudad_destino, requiere_servicio_distribucion

    // Ship To (Destino)
    // id_destino_origen_destino
    // direccion, contacto,
    // id_estado_solicitud_actualizacion_datos_contacto_destino

    // Ship To (Bodega Destino)
    // id_bodega_destino

    // fecha_sugerida_entrega (minima,maxima)
    // hora_sugerida_entrega (minima,maxima,minima_adicional,maxima_adicional)

    // requerimientos_distribucion, notas_requerimientos_distribucion
    // requerimientos_alistamiento, notas_requerimientos_alistamiento

    // TODO Add Linea
    // id_orden,id_linea_orden(null)
    // descripcion, cantidad_solicitada, cantidad_planificada
    // id_producto, id_unidad, id_estado_producto, dimensiones, valores, notas
    // id_ciudad_bodega_origen, id_bodega_origen, lote, serial

    // TODO Update Linea
    // id_orden,id_linea_orden
    // descripcion, cantidad_solicitada, cantidad_planificada
    // id_producto, id_unidad, id_estado_producto, dimensiones, valores, notas
    // id_ciudad_bodega_origen, id_bodega_origen, lote, serial

    // TODO Remove Linea
    // id_orden,id_linea_orden

    // TODO CONFIRMAR
    // TODO APROBAR
    // TODO SOLICITAR REVISION (CLIENTE)

    // TODO SOLICITAR REVISION (PLANIFICACION)
    // TODO INICIAR PLANEACION
    // TODO FINALIZAR PLANEACION

    // TODO ANULAR

}
