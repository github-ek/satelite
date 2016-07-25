package com.tacticlogistics.application.services.ordenes;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tacticlogistics.application.dto.etl.ETLLineaOrdenDto;
import com.tacticlogistics.application.dto.etl.ETLOrdenDto;
import com.tacticlogistics.application.dto.oms.ConsultaOrdenesDto;
import com.tacticlogistics.application.dto.ordenes.DestinatarioDto;
import com.tacticlogistics.application.dto.ordenes.LineaOrdenDto;
import com.tacticlogistics.application.dto.ordenes.OrdenDto;
import com.tacticlogistics.application.services.geo.CiudadesApplicationService;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.LineaOrdenViewModelAdpater;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.MensajeViewModelAdpater;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.OrdenDatosBodegaDestinoOrigenViewModelAdapter;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.OrdenDatosDestinoOrigenViewModelAdapter;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.OrdenDatosEntregaRecogidaViewModelAdapter;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.OrdenDatosFacturacionViewModelAdapter;
import com.tacticlogistics.application.services.ordenes.OrdenViewModelAdapter.OrdenDatosOtrosViewModelAdapter;
import com.tacticlogistics.domain.model.common.IdentificacionType;
import com.tacticlogistics.domain.model.common.TipoContenido;
import com.tacticlogistics.domain.model.common.UsoUbicacionType;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.Direccion;
import com.tacticlogistics.domain.model.common.valueobjects.OmsDireccion;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;
import com.tacticlogistics.domain.model.crm.DestinoOrigen;
import com.tacticlogistics.domain.model.crm.TipoFormaPago;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.ordenes.Consolidado;
import com.tacticlogistics.domain.model.ordenes.EstadoOrden;
import com.tacticlogistics.domain.model.ordenes.EstadoOrdenType;
import com.tacticlogistics.domain.model.ordenes.LineaOrden;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.domain.model.requerimientos.Requerimiento;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.Unidad;
import com.tacticlogistics.infrastructure.persistence.crm.CanalRepository;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.crm.DestinatarioRemitenteRepository;
import com.tacticlogistics.infrastructure.persistence.crm.DestinoOrigenRepository;
import com.tacticlogistics.infrastructure.persistence.crm.TipoFormaPagoRepository;
import com.tacticlogistics.infrastructure.persistence.crm.TipoServicioRepository;
import com.tacticlogistics.infrastructure.persistence.geo.CiudadRepository;
import com.tacticlogistics.infrastructure.persistence.ordenes.ConsolidadoRepository;
import com.tacticlogistics.infrastructure.persistence.ordenes.EstadoOrdenRepository;
import com.tacticlogistics.infrastructure.persistence.ordenes.OrdenRepository;
import com.tacticlogistics.infrastructure.persistence.ordenes.TipoContenidoRepository;
import com.tacticlogistics.infrastructure.persistence.wms.BodegaRepository;
import com.tacticlogistics.infrastructure.persistence.wms.ProductoRepository;
import com.tacticlogistics.infrastructure.persistence.wms.UnidadRepository;
import com.tacticlogistics.infrastructure.services.Basic;

@Service
@Transactional(readOnly = true)
public class OrdenesApplicationService {
    @Autowired
    private CiudadesApplicationService ciudadesService;

    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private EstadoOrdenRepository estadoOrdenRepository;
    @Autowired
    private TipoServicioRepository tipoServicioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ConsolidadoRepository consolidadoRepository;
    @Autowired
    private CanalRepository canalRepository;
    @Autowired
    private DestinatarioRemitenteRepository destinatarioRemitenteRepository;
    @Autowired
    private DestinoOrigenRepository destinoOrigenRepository;
    @Autowired
    private CiudadRepository ciudadRepository;
    @Autowired
    private BodegaRepository bodegaRepository;
    @Autowired
    private TipoFormaPagoRepository tipoFormaPagoRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UnidadRepository unidadRepository;
    @Autowired
    private TipoContenidoRepository tipoContenidoRepository;

    // ----------------------------------------------------------------------------------------------------------------
    // -- Gestionar Ordenes
    // ----------------------------------------------------------------------------------------------------------------
    public List<Object> findAllEstadoOrden() throws DataAccessException {
        List<EstadoOrden> entityList = estadoOrdenRepository.findAllByOrderByOrdinalAsc();

        List<Object> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(estadoOrdenToViewModel(a));
        });
        return list;
    }

    public List<OrdenDto> findAllOrdenPorUsuarioPorTipoServicioPorEstadoOrden(Integer usuarioId, Integer tipoServicioId,
            EstadoOrdenType estadoOrdenId, Integer clienteId) throws DataAccessException {
        List<Orden> entityList = ordenRepository.findByUsuarioIdAndTipoServicioIdAndEstadoOrden(usuarioId,
                tipoServicioId, estadoOrdenId, clienteId);

        List<OrdenDto> list = new ArrayList<>();
        entityList.forEach((orden) -> {
            boolean incluir = false;
            if (orden.getFechaConfirmacion() == null) {
                incluir = true;
            } else {
                Date desde = Basic.addDays(Basic.truncate(new Date()), -8);
                if (orden.getFechaConfirmacion().after(desde)) {
                    incluir = true;
                }
            }
            if (incluir) {
                list.add(ordenToViewModel(orden));
            }
        });
        return list;
    }

    public Page<ConsultaOrdenesDto> findAllOrdenPorUsuarioPorTipoServicioPorEstadoOrdenConPaginacion(Integer usuarioId,
            Integer tipoServicioId, EstadoOrdenType estadoOrdenId, Integer clienteId, Integer pageNumber,
            Integer pageSize) throws DataAccessException {

        Pageable pageRequest = createPageRequest(pageNumber, pageSize);

        Page<ConsultaOrdenesDto> searchResultPage = ordenRepository
                .findByUsuarioIdAndTipoServicioIdAndEstadoOrdenConPaginacion(
                        // usuarioId,
                        tipoServicioId, estadoOrdenId, clienteId, pageRequest);

        return searchResultPage;
    }

    private Pageable createPageRequest(int start, int length) {
        start = (start < 0) ? 0 : start;
        length = (length < 0) ? 10 : length;
        return new PageRequest(start, length);
    }

    static Page<ConsultaOrdenesDto> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<Orden> source) {
        final List<ConsultaOrdenesDto> dtos = new ArrayList<>();

        source.getContent().forEach(a -> {

            ConsultaOrdenesDto dto = map(a);

            Date fmin = dto.getFechaSugeridaEntregaMinima();
            Date fmax = dto.getFechaSugeridaEntregaMaxima();

            if ((dto.getFechaPlaneadaEntregaMinima() != null) || (dto.getFechaPlaneadaEntregaMaxima() != null)) {
                fmin = dto.getFechaPlaneadaEntregaMinima();
                fmax = dto.getFechaPlaneadaEntregaMaxima();
            }

            dtos.add(dto);
        });

        return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
    }

    static protected ConsultaOrdenesDto map(Orden orden) {
        PropertyMap<Orden, ConsultaOrdenesDto> ordenMap = new PropertyMap<Orden, ConsultaOrdenesDto>() {
            protected void configure() {
                map().setDestinoDireccion(source.getDestinoDireccion().getDireccion());
            }
        };

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(ordenMap);

        ConsultaOrdenesDto dto;
        dto = modelMapper.map(orden, ConsultaOrdenesDto.class);

        /*
         * if (orden.getTipoServicio() != null) {
         * dto.setTipoServicioNombre(orden.getTipoServicio().getNombre()); }
         * 
         * if (orden.getCliente() != null) {
         * dto.setClienteCodigo(model.getCodigo());
         * dto.setClienteNombre(model.getNombre()); }
         * 
         * if (orden.getConsolidadoId() != null) { Consolidado model =
         * consolidadoRepository.findOne(orden.getConsolidadoId()); if (model !=
         * null) { dto.setNumeroDocumentoConsolidadoCliente(model.
         * getNumeroDocumentoConsolidadoCliente()); } }
         * 
         * if (orden.getCanalId() != null) { Canal model =
         * canalRepository.findOne(orden.getCanalId()); if (model != null) {
         * dto.setCanalCodigo(model.getCodigo());
         * dto.setCanalNombre(model.getNombre()); } }
         * 
         * if (orden.getDestinatarioId() != null) { DestinatarioRemitente model
         * = destinatarioRemitenteRepository.findOne(orden.getDestinatarioId());
         * if (model != null) { dto.setDestinatarioCodigo(model.getCodigo());
         * dto.setDestinatarioNombre(model.getNombre());
         * dto.setDestinatarioNumeroIdentificacion(model.getNumeroIdentificacion
         * ()); } }
         * 
         * if (orden.getDestinatarioDireccion().getCiudadId() != null) { Ciudad
         * model =
         * ciudadRepository.findOne(orden.getDestinatarioDireccion().getCiudadId
         * ()); if (model != null) {
         * dto.setDestinatarioCiudadNombre(model.getNombreAlterno()); } }
         * 
         * if (orden.getDestinoId() != null) { DestinoOrigen model =
         * destinoOrigenRepository.findOne(orden.getDestinoId()); if (model !=
         * null) { dto.setDestinoCodigo(model.getCodigo());
         * dto.setDestinoNombre(model.getNombre()); } }
         * 
         * if (orden.getBodegaDestinoId() != null) { Bodega model =
         * bodegaRepository.findOne(orden.getBodegaDestinoId()); if (model !=
         * null) { dto.setDestinoCodigo(model.getCodigo());
         * dto.setDestinoNombre(model.getNombre()); } }
         * 
         * if (orden.getDestinoDireccion().getCiudadId() != null) { Ciudad model
         * =
         * ciudadRepository.findOne(orden.getDestinoDireccion().getCiudadId());
         * if (model != null) {
         * dto.setDestinoCiudadNombre(model.getNombreAlterno()); } }
         */
        return dto;
    }

    public OrdenDto findOneOrdenPorId(Integer id) throws DataAccessException {
        return ordenToViewModel(ordenRepository.findOne(id));
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Configuración
    // ----------------------------------------------------------------------------------------------------------------
    // TODO corregir implementacion
    public Object[] findAllConfiguracionClientePorTipoServicio(Integer clienteId, Integer tipoServicioId)
            throws DataAccessException {
        Cliente cliente = clienteRepository.findOne(clienteId);

        Map<String, Collection<Map<String, Object>>> config = new HashMap<String, Collection<Map<String, Object>>>(0);

        if (cliente != null) {
            if (cliente.isActivo()) {
                Map<Integer, Map<String, Object>> m = new HashMap<Integer, Map<String, Object>>(0);

                cliente.getRequerimientoClienteAssociation().forEach((a) -> {
                    if (a.getTipoServicio().isActivo() && a.getTipoServicio().getId().equals(tipoServicioId)) {
                        Requerimiento requerimiento = a.getRequerimiento();
                        if (requerimiento.isActivo()) {
                            if (!m.containsKey(requerimiento.getId())) {
                                Map<String, Object> o = new HashMap<String, Object>();

                                o.put("id", requerimiento.getId());
                                o.put("codigo", requerimiento.getCodigo());
                                o.put("nombre", requerimiento.getNombre());
                                o.put("ordinal", 0);
                                o.put("incluirSiempre", a.isIncluirSiempre());

                                o.put("requerido", true);
                                o.put("puedePersonalizarOpcionesDeArchivosAdjuntos",
                                        a.getPermisos().isPuedePersonalizarOpcionesDeArchivosAdjuntos());
                                o.put("puedePersonalizarOpcionesParaDispositivosMoviles",
                                        a.getPermisos().isPuedePersonalizarOpcionesParaDispositivosMoviles());
                                o.put("puedePersonalizarOpcionesParaRequerimientosDeCumplimiento",
                                        a.getPermisos().isPuedePersonalizarOpcionesParaRequerimientosDeCumplimiento());
                                o.put("puedePersonalizarOpcionesParaRequerimientosDocumentales",
                                        a.getPermisos().isPuedePersonalizarOpcionesParaRequerimientosDocumentales());

                                o.put("numeroMinimoDeArchivosAdjuntos",
                                        a.getOpciones().getNumeroMinimoDeArchivosAdjuntos());
                                o.put("numeroMaximoDeArchivosAdjuntos",
                                        a.getOpciones().getNumeroMaximoDeArchivosAdjuntos());
                                o.put("tamanoMaximoPorArchivoAdjuntos",
                                        a.getOpciones().getTamanoMaximoPorArchivoAdjuntos());

                                o.put("habilitarOpcionParaAdjuntarArchivosEnDispositivosMoviles",
                                        a.getOpciones().isHabilitarOpcionParaAdjuntarArchivosEnDispositivosMoviles());

                                o.put("textoPreguntaRequerimientoDeCumplimiento",
                                        a.getOpciones().getTextoPreguntaRequerimientoDeCumplimiento());
                                o.put("requiereNotasEnCasoDeConformidad",
                                        a.getOpciones().isRequiereNotasEnCasoDeConformidad());
                                o.put("requiereNotasEnCasoDeNoConformidad",
                                        a.getOpciones().isRequiereNotasEnCasoDeNoConformidad());

                                o.put("requiereNumeroDeReferenciaDelDocumento",
                                        a.getOpciones().isRequiereNumeroDeReferenciaDelDocumento());
                                o.put("requiereDocumentoFisico", a.getOpciones().isRequiereDocumentoFisico());

                                m.put(requerimiento.getId(), o);
                            }
                        }
                    }
                });

                config.put("requerimientosDocumentales", m.values());

                Map<Integer, Map<String, Object>> n = new HashMap<Integer, Map<String, Object>>(0);

                Map<String, Object> o;

                o = new HashMap<String, Object>();
                o.put("codigo", "AM");
                o.put("horaMinima", "05:30");
                o.put("horaMaxima", "12:00");
                n.put(1, o);

                o = new HashMap<String, Object>();
                o.put("codigo", "PM");
                o.put("horaMinima", "13:00");
                o.put("horaMaxima", "19:00");
                n.put(2, o);

                config.put("jornadas", n.values());
            }

        }

        return config.entrySet().toArray();
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Ship To (Bodega Destino/Bodega Origen)
    // ----------------------------------------------------------------------------------------------------------------
    public List<Object> findAllBodegaPorCliente(Integer clienteId, Integer ciudadId, Integer tipoServicioId)
            throws DataAccessException {
        List<Object> list = new ArrayList<>();

        List<Bodega> entityList = bodegaRepository.findAllByClienteIdAndCiudadIdAndTipoServicioId(clienteId, ciudadId,
                tipoServicioId);

        entityList.forEach(a -> {
            list.add(bodegaToViewModel(a));
        });
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Lineas de Productos
    // ----------------------------------------------------------------------------------------------------------------
    public List<Object> findAllBodegaPorProductoPorCiudad(Integer productoId, Integer ciudadId)
            throws DataAccessException {
        List<Bodega> entityList = bodegaRepository.findAllByProductoIdAndCiudadId(productoId, ciudadId);

        List<Object> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(bodegaToViewModel(a));
        });
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Lineas de Paquetes
    // ----------------------------------------------------------------------------------------------------------------
    public List<Object> findAllTipoContenido() throws DataAccessException {
        List<TipoContenido> entityList = tipoContenidoRepository.findAllByOrderByOrdinalAsc();

        List<Object> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(tipoContenidoToViewModel(a));
        });

        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Otros
    // ----------------------------------------------------------------------------------------------------------------
    public List<Object> findAllTipoFormaPagoPorClientePorTipoServicio(Integer clienteId, Integer tipoServicioId)
            throws DataAccessException {
        List<TipoFormaPago> entityList = tipoFormaPagoRepository.findAll();

        List<Object> list = new ArrayList<>();
        entityList.forEach(a -> {
            list.add(tipoFormaPagoToViewModel(a));
        });
        return list;
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------------------
    @Transactional(readOnly = false)
    public Orden saveOrden(DestinatarioDto dto) throws DataAccessException {
        OrdenDto ordenDto = new OrdenDto();
        ordenDto.setDatosFacturacion(dto);
        ordenDto.setUsuarioActualizacion(dto.getUsuario());

        return saveOrden(ordenDto, EstadoOrdenType.NO_CONFIRMADA);
    }

    @Transactional(readOnly = false)
    public Orden saveOrden(OrdenDto dto, EstadoOrdenType nuevoEstadoOrdenType) throws DataAccessException {
        Orden model = null;

        if (dto.getIdOrden() == null) {
            model = new Orden();
            model.setTipoServicio(tipoServicioRepository.getOne(dto.getDatosFacturacion().getTipoServicio()));
            model.setCliente(clienteRepository.getOne(dto.getDatosFacturacion().getCliente()));
        } else {
            model = ordenRepository.findOne(dto.getIdOrden());
        }

        // -------------------------------------------------------------------------------------------------------------------
        if (notEqualId(dto.getDatosFacturacion().getTipoServicio(),
                (model.getTipoServicio() == null) ? null : model.getTipoServicio().getId())) {

        }
        if (notEqualId(dto.getDatosFacturacion().getCliente(),
                (model.getCliente() == null) ? null : model.getCliente().getId())) {

        }

        model.setNumeroDocumentoOrdenCliente(dto.getDatosFacturacion().getNumeroDocumentoOrdenCliente());

        // -------------------------------------------------------------------------------------------------------------------
        // Consolidado consolidado;
        // CausalAnulacionOrden causalAnulacion;

        // -------------------------------------------------------------------------------------------------------------------
        if (notEqualId(dto.getDatosFacturacion().getSegmento(),
                (model.getSegmento() == null) ? null : model.getSegmento().getId())) {
            model.setCanal(canalRepository.getOne(dto.getDatosFacturacion().getSegmento()));
        }
        if (notEqualId(dto.getDatosFacturacion().getDestinatario(),
                (model.getDestinatario() == null) ? null : model.getDestinatario().getId())) {
            model.setDestinatario(destinatarioRemitenteRepository.getOne(dto.getDatosFacturacion().getDestinatario()));
        }

        model.cambiarDatosAlternosDestinatarioRemitente(dto.getDatosFacturacion().getCodigoAlternoSegmento(),
                dto.getDatosFacturacion().getNumeroIdentificacionAlternoDestinatario(),
                dto.getDatosFacturacion().getCodigoAlternoDestinatario(),
                dto.getDatosFacturacion().getNombreAlternoDestinatario());

        model.setDestinatarioContacto(new Contacto(dto.getDatosFacturacion().getNombre(),
                dto.getDatosFacturacion().getEmail(), dto.getDatosFacturacion().getTelefonos()));

        // -------------------------------------------------------------------------------------------------------------------
        if (notEqualId(dto.getDestinoOrigen().getDestinoId(),
                (model.getDestino() == null) ? null : model.getDestino().getId())) {
            if (dto.getDestinoOrigen().getDestinoId() != null) {
                model.setDestino(destinoOrigenRepository.getOne(dto.getDestinoOrigen().getDestinoId()));
            } else {
                model.setDestino(null);
            }
        }

        model.cambiarDatosAlternosDestino(dto.getDestinoOrigen().getDestinoCodigoAlterno(),
                dto.getDestinoOrigen().getDestinoNombreAlterno());

        // -------------------------------------------------------------------------------------------------------------------
        if (notEqualId(dto.getBodegaDestinoOrigen().getBodegaId(),
                (model.getBodegaDestino() == null) ? null : model.getBodegaDestino().getId())) {
            if (dto.getBodegaDestinoOrigen().getBodegaId() != null) {
                model.setBodegaDestino(bodegaRepository.getOne(dto.getBodegaDestinoOrigen().getBodegaId()));
            } else {
                model.setBodegaDestino(null);
            }
        }

        model.cambiarDatosAlternosBodegaDestino(dto.getBodegaDestinoOrigen().getBodegaCodigoAlterno(),
                dto.getBodegaDestinoOrigen().getBodegaNombreAlterno());

        // -------------------------------------------------------------------------------------------------------------------
        // TODO HOMOLOGA4R PARTA BODEGAS, PERMITIR QUE LA BODEGA RECIBA SOLO LA
        // CIUDAD DESTINO
        // Ciudad ciudad = null;
        Integer ciudadId = null;
        if (model.getDestinoDireccion() != null) {
            if (model.getDestinoDireccion().getCiudad() != null) {
                if (model.getDestinoDireccion().getCiudad().getId() != null) {
                    // ciudad = model.getDestinoDireccion().getCiudad();
                    ciudadId = model.getDestinoDireccion().getCiudad().getId();
                }
            }
        }

        if (notEqualId(dto.getDestinoOrigen().getCiudadId(), ciudadId)) {
            if (dto.getDestinoOrigen().getCiudadId() != null) {
                ciudadId = dto.getDestinoOrigen().getCiudadId();
            }
        }

        Ciudad ciudad = null;
        if (ciudadId != null) {
            ciudad = ciudadRepository.getOne(ciudadId);
        }

        model.setDestinoDireccion(new Direccion(ciudad, dto.getDestinoOrigen().getDireccion(),
                dto.getDestinoOrigen().getIndicacionesDireccion()));

        model.setDestinoContacto(new Contacto(dto.getDestinoOrigen().getContactoNombres(),
                dto.getDestinoOrigen().getContactoEmail(), dto.getDestinoOrigen().getContactoTelefonos()));

        // -------------------------------------------------------------------------------------------------------------------
        Time horaMinima = null;
        if (dto.getDatosEntregaRecogida().getHoraMinima() != null) {
            try {
                horaMinima = Time.valueOf(dto.getDatosEntregaRecogida().getHoraMinima() + ":00");
            } catch (IllegalArgumentException e) {

            }
        }
        // -------------------------------------------------------------------------------------------------------------------
        Time horaMaxima = null;
        if (dto.getDatosEntregaRecogida().getHoraMaxima() != null) {
            try {
                horaMaxima = Time.valueOf(dto.getDatosEntregaRecogida().getHoraMaxima() + ":00");
            } catch (IllegalArgumentException e) {

            }
        }

        model.cambiarCitaSugerida(dto.getDatosEntregaRecogida().getFechaMinima(),
                dto.getDatosEntregaRecogida().getFechaMaxima(), horaMinima, horaMaxima);

        // -------------------------------------------------------------------------------------------------------------------
        if (notEqualId(dto.getDatosOtros().getTipoFormaPago(),
                (model.getTipoFormaPago() == null) ? null : model.getTipoFormaPago().getId())) {
            if (dto.getDatosOtros().getTipoFormaPago() != null) {
                model.setTipoFormaPago(tipoFormaPagoRepository.getOne(dto.getDatosOtros().getTipoFormaPago()));
            } else {
                model.setTipoFormaPago(null);
            }
        }

        model.setValorDeclarado(dto.getDatosOtros().getValorDeclarado());
        model.setRequiereMaquila(dto.getDatosOtros().isRequiereMaquila());
        model.setNotas(dto.getDatosOtros().getNotas());

        // TODO Aparte de los cargue por PDF donde mas se haria una
        // actualización de este tipo
        for (LineaOrdenDto m : dto.getLineas()) {
            updateLineaOrdenFromViewModel(model, m);
        }

        model.cambiarEstado(nuevoEstadoOrdenType, dto.getUsuarioActualizacion());

        return ordenRepository.save(model);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------------------------------------------------
    @Transactional(readOnly = false)
    public Orden saveLineaOrden(LineaOrdenDto model) throws DataAccessException {
        Orden entity = ordenRepository.findOne(model.getIdOrden());

        if (entity == null) {
            throw new RuntimeException("No se encontro la orden con id=" + model.getIdOrden());
        }

        updateLineaOrdenFromViewModel(entity, model);

        ordenRepository.save(entity);

        return entity;
    }

    @Transactional(readOnly = false)
    public Orden deleteLineaOrden(Integer ordenId, Integer lineaOrdenId, String usuario) throws DataAccessException {
        Orden entity = ordenRepository.findOne(ordenId);

        if (entity == null) {
            throw new RuntimeException("No se encontro la orden con id=" + ordenId);
        }

        LineaOrden e = entity.getLinea(lineaOrdenId);

        if (e == null) {
            throw new RuntimeException(
                    "No se encontro la linea con id = " + lineaOrdenId + ",de la orden con id=" + ordenId);
        }

        entity.removeLinea(e);

        ordenRepository.save(entity);

        return entity;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Homologar
    // ----------------------------------------------------------------------------------------------------------------
    @Transactional(readOnly = false)
    public Orden saveOrden2(ETLOrdenDto dto) throws DataAccessException {
        StringBuffer errores = new StringBuffer();
        TipoServicio tipoServicio = null;
        Cliente cliente = null;
        Consolidado consolidado = null;
        Ciudad ciudadDestino = null;
        Ciudad ciudadOrigen = null;

        Canal canal = null;
        DestinatarioRemitente destinatarioRemitente = null;
        List<DestinoOrigen> destinos = null;
        DestinoOrigen destino = null;
        Bodega bodegaDestino = null;

        cliente = clienteRepository.findByCodigoIgnoringCase(dto.getClienteCodigo());
        if (cliente == null) {
            throw new RuntimeException("(cliente == null)");
        }

        tipoServicio = tipoServicioRepository.findByCodigoIgnoringCase(dto.getTipoServicioCodigo());
        if (tipoServicio == null) {
            throw new RuntimeException("(tipoServicio == null)");
        }

        // ------------------------------------------------------------------------------------------------
        Orden model = null;

        model = ordenRepository.findFirstByClienteIdAndNumeroDocumentoOrdenCliente(cliente.getId(),
                dto.getNumeroOrden());
        if (model != null) {
            switch (model.getEstadoOrden()) {
            case ANULADA:
                ordenRepository.delete(model.getId());
                break;
            default:
                return null;
            }
        }

        // ------------------------------------------------------------------------------------------------
        if (!dto.getNumeroConsolidado().isEmpty()) {
            consolidado = consolidadoRepository.findByClienteIdAndNumeroDocumentoConsolidadoClienteIgnoringCase(
                    cliente.getId(), dto.getNumeroConsolidado());
            if (consolidado == null) {
                consolidado = CrearConsolidado(dto, cliente);
            }
        }

        // ------------------------------------------------------------------------------------------------
        // TODO INTENTAR BUSCAR POR LA HOMOLOGACION DEL CLIENTE PRIMERO
        if (!dto.getDestinoCiudadNombreAlterno().isEmpty()) {
            ciudadDestino = ciudadRepository.findByNombreAlternoIgnoringCase(dto.getDestinoCiudadNombreAlterno());
            if (ciudadDestino == null) {
                ciudadDestino = ciudadRepository.findByCodigo(dto.getDestinoCiudadNombreAlterno());
            }
        }

        // ------------------------------------------------------------------------------------------------
        // TODO INTENTAR BUSCAR POR LA HOMOLOGACION DEL CLIENTE PRIMERO
        if (!dto.getOrigenCiudadNombreAlterno().isEmpty()) {
            ciudadOrigen = ciudadRepository.findByNombreAlternoIgnoringCase(dto.getOrigenCiudadNombreAlterno());
            if (ciudadOrigen == null) {
                ciudadOrigen = ciudadRepository.findByCodigo(dto.getOrigenCiudadNombreAlterno());
            }
        }

        // ------------------------------------------------------------------------------------------------
        // Buscar Canal independiente del maestro

        // ------------------------------------------------------------------------------------------------
        if (!dto.getDestinatarioNumeroIdentificacion().isEmpty()) {
            destinatarioRemitente = destinatarioRemitenteRepository
                    .findByClienteIdAndNumeroIdentificacion(cliente.getId(), dto.getDestinatarioNumeroIdentificacion());
            if (destinatarioRemitente == null) {
                destinatarioRemitente = CrearDestintarioRemitente(dto, cliente);
            }
        }

        if (destinatarioRemitente != null) {
            canal = canalRepository.findOne(destinatarioRemitente.getCanalId());

            // Solo si la informacion de contacto del destinatario no se ha
            // personalizado en el dto, solo entoces
            // se usa la del destinatario
            if (dto.getDestinatarioContactoNombres().isEmpty() && dto.getDestinatarioContactoEmail().isEmpty()
                    && dto.getDestinatarioContactoTelefonos().isEmpty()) {
                dto.setDestinatarioContactoNombres(destinatarioRemitente.getContacto().getNombres());
                dto.setDestinatarioContactoEmail(destinatarioRemitente.getContacto().getEmail());
                dto.setDestinatarioContactoTelefonos(destinatarioRemitente.getContacto().getTelefonos());
            }
        }

        // ------------------------------------------------------------------------------------------------
        if (destinatarioRemitente != null && ciudadDestino != null) {
            destinos = destinoOrigenRepository
                    .findAllByDestinatarioRemitenteIdAndDireccionCiudadIdAndDireccionDireccionOrderByCodigoAscNombreAsc(
                            destinatarioRemitente.getId(), ciudadDestino.getId(), dto.getDestinoDireccion());

            if (destinos.isEmpty()) {
                destino = CrearDestinoOrigen(dto, destinatarioRemitente, ciudadDestino);
            } else if (destinos.size() > 1) {
                errores.append("ADVERTENCIA: Se detectaron " + destinos.size() + " destinos con esta misma dirección.");
            } else {
                destino = destinos.get(0);
            }

            if (destino != null) {
                // Solo si la informacion de contacto del destino no se ha
                // personalizado en el dto, solo entoces
                // se usa la del destino
                if (dto.getDestinoContactoNombres().isEmpty() && dto.getDestinoContactoEmail().isEmpty()
                        && dto.getDestinoContactoTelefonos().isEmpty()) {
                    dto.setDestinoContactoNombres(destino.getContacto().getNombres());
                    dto.setDestinoContactoEmail(destino.getContacto().getEmail());
                    dto.setDestinoContactoTelefonos(destino.getContacto().getTelefonos());
                }
            }
        }

        // ------------------------------------------------------------------------------------------------
        // TODO QUITAR
        if (!dto.getBodegaDestinoCodigo().isEmpty()) {
            bodegaDestino = bodegaRepository.findByCodigoIgnoringCase(dto.getBodegaDestinoCodigo());
        } else if (!dto.getBodegaDestinoCodigoAlterno().isEmpty()) {
            bodegaDestino = bodegaRepository.findByCodigoIgnoringCase(dto.getBodegaDestinoCodigoAlterno());
        }

        // ------------------------------------------------------------------------------------------------
        model = new Orden();
        model.setNumeroDocumentoOrdenCliente(dto.getNumeroOrden());
        model.setConsolidado(consolidado);

        model.setCliente(cliente);
        // model.setClienteCodigo(cliente.getCodigo());

        model.setTipoServicio(tipoServicio);
        // model.setTipoServicioCodigoAlterno(dto.getTipoServicioCodigoAlterno());
        // requiere_servicio_distribucion
        // requiere_servicio_alistamiento

        model.setDestinoDireccion(
                new Direccion(ciudadDestino, dto.getDestinoDireccion(), dto.getDestinoIndicaciones()));
        // destino_ciudad_nombre_alterno

        // id_ciudad_origen
        // origen_ciudad_nombre_alterno
        // origen_direccion
        // origen_indicaciones

        // requiere_confirmacion_cita_entrega
        model.cambiarCitaSugerida(dto.getEntregaSugeridaMinima(), dto.getFechaEntregaSugeridaMaxima(),
                dto.getHoraEntregaSugeridaMinima(), dto.getHoraEntregaSugeridaMaxima());
        // hora_entrega_sugerida_minima_adicional
        // hora_entrega_sugerida_maxima_adicional
        model.cambiarCitaPlanificada(dto.getEntregaSugeridaMinima(), dto.getFechaEntregaSugeridaMaxima(),
                dto.getHoraEntregaSugeridaMinima(), dto.getHoraEntregaSugeridaMaxima());

        // requiere_confirmacion_cita_recogida
        // fecha_recogida_sugerida_minima
        // fecha_recogida_sugerida_maxima
        // hora_recogida_sugerida_minima
        // hora_recogida_sugerida_maxima
        // fecha_recogida_planificada_minima
        // fecha_recogida_planificada_maxima
        // hora_recogida_planificada_minima
        // hora_recogida_planificada_maxima

        model.setCanal(canal);
        // canal_codigo_alterno
        model.setDestinatario(destinatarioRemitente);
        model.cambiarDatosAlternosDestinatarioRemitente(dto.getCanalCodigoAlterno(),
                dto.getDestinatarioNumeroIdentificacion(), "", dto.getDestinatarioNombre());
        model.setDestinatarioContacto(new Contacto(dto.getDestinatarioContactoNombres(),
                dto.getDestinatarioContactoEmail(), dto.getDestinatarioContactoTelefonos()));

        model.setDestino(destino);
        model.cambiarDatosAlternosDestino("", dto.getDestinoNombre());
        model.setDestinoContacto(new Contacto(dto.getDestinoContactoNombres(), dto.getDestinoContactoEmail(),
                dto.getDestinoContactoTelefonos()));

        // id_origen
        // origen_nombre
        // origen_contacto_email
        // origen_contacto_nombres
        // origen_contacto_telefonos

        // notas_requerimientos_distribucion
        // notas_requerimientos_alistamiento
        // Listas de requerimientos
        model.setValorDeclarado(dto.getValorRecaudo());

        model.setNotas(dto.getNotasConfirmacion());

        // TODO QUITAR
        model.setBodegaDestino(bodegaDestino);
        model.cambiarDatosAlternosBodegaDestino(dto.getBodegaDestinoCodigoAlterno(), "");

        for (ETLLineaOrdenDto linea : dto.getLineas()) {
            saveLineaOrden2(dto, linea, model);
        }

        model.cambiarEstado(EstadoOrdenType.CONFIRMADA, dto.getUsuarioConfirmacion());
        model.modificado(dto.getUsuarioConfirmacion());
        // fecha_creacion
        // usuario_creacion

        model = ordenRepository.save(model);

        return model;
    }

    @Transactional(readOnly = false)
    protected void saveLineaOrden2(ETLOrdenDto dtoOrden, ETLLineaOrdenDto dto, Orden orden) {
        LineaOrden model = null;
        Producto producto = null;
        Unidad unidad = null;
        Bodega bodegaOrigen = null;
        Bodega bodegaDestino = null;

        model = new LineaOrden(null);
        orden.addLinea(model);

        // ----------------------------------------------------------------------------------------------------
        if (!dto.getProductoCodigo().isEmpty()) {
            producto = productoRepository.findByClienteIdAndCodigo(orden.getCliente().getId(), dto.getProductoCodigo());
        } else if (!dto.getProductoCodigoAlterno().isEmpty()) {
            producto = productoRepository.findByClienteIdAndCodigo(orden.getCliente().getId(),
                    dto.getProductoCodigoAlterno());
            if (producto == null) {
                producto = productoRepository.findByClienteIdAndCodigoAlterno(orden.getCliente().getId(),
                        dto.getProductoCodigoAlterno());
            }
        }
        if (producto != null) {
            dto.setProductoCodigo(producto.getCodigo());
            dto.setDescripcion(producto.getNombreLargo());
        }

        // ----------------------------------------------------------------------------------------------------
        // TODO BUSCAR EN LA HOMOLOGACION
        if (!dto.getUnidadCodigo().isEmpty()) {
            unidad = unidadRepository.findByCodigoIgnoringCase(dto.getUnidadCodigo());
        } else if (!dto.getUnidadCodigoAlterno().isEmpty()) {
            unidad = unidadRepository.findByCodigoIgnoringCase(dto.getUnidadCodigoAlterno());
            if (unidad == null) {
                unidad = unidadRepository.findByClienteIdAndByCodigoAlterno(orden.getCliente().getId(),
                        dto.getUnidadCodigoAlterno());
            }
        }
        if (unidad != null) {
            dto.setUnidadCodigo(unidad.getCodigo());
        }

        // ----------------------------------------------------------------------------------------------------
        // TODO BUSCAR EN LA HOMOLOGACION
        if (!dto.getBodegaOrigenCodigo().isEmpty()) {
            bodegaOrigen = bodegaRepository.findByCodigoIgnoringCase(dto.getBodegaOrigenCodigo());
        } else if (!dto.getBodegaOrigenCodigoAlterno().isEmpty()) {
            bodegaOrigen = bodegaRepository.findByCodigoIgnoringCase(dto.getBodegaOrigenCodigoAlterno());
            if (bodegaOrigen == null) {
                // TODO usar los alias de bodegas
            }
        }
        if (bodegaOrigen != null) {
            dto.setBodegaOrigenCodigo(bodegaOrigen.getCodigo());
        }

        // ----------------------------------------------------------------------------------------------------
        // TODO BUSCAR EN LA HOMOLOGACION
        if (!dto.getBodegaDestinoCodigo().isEmpty()) {
            bodegaDestino = bodegaRepository.findByCodigoIgnoringCase(dto.getBodegaDestinoCodigo());
        } else if (!dto.getBodegaDestinoCodigoAlterno().isEmpty()) {
            bodegaDestino = bodegaRepository.findByCodigoIgnoringCase(dto.getBodegaDestinoCodigoAlterno());
            if (bodegaDestino == null) {
                // TODO usar los alias de bodegas
            }
        }
        if (bodegaDestino != null) {
            dto.setBodegaDestinoCodigo(bodegaDestino.getCodigo());
        }

        // ----------------------------------------------------------------------------------------------------
        model.setDescripcion(dto.getDescripcion());
        model.setCantidad(dto.getCantidadSolicitada());
        // cantidad_planificada

        model.setProducto(producto);
        model.setCodigoProducto(dto.getProductoCodigo());
        model.setCodigoAlternoProducto(dto.getProductoCodigoAlterno());

        model.setUnidad(unidad);
        model.setCodigoUnidad(dto.getUnidadCodigo());
        model.setCodigoAlternoUnidad(dto.getUnidadCodigoAlterno());

        // id_tipo_contenido
        // tipo_contenido_codigo
        // tipo_contenido_codigo_alterno

        // TODO dimensiones y pesos
        // alto_por_unidad
        // ancho_por_unidad
        // largo_por_unidad
        // peso_bruto_por_unidad

        model.setBodegaOrigen(bodegaOrigen);
        // bodega_origen_codigo
        model.setBodegaOrigenCodigoAlterno(dto.getBodegaOrigenCodigoAlterno());
        model.setOrigenNombreAlterno(dto.getEstadoInventarioOrigen());
        // id_estado_inventario_origen

        // id_bodega_destino
        // bodega_destino_codigo
        model.setOrigenCodigoAlterno(dto.getBodegaDestinoCodigoAlterno());
        // id_estado_inventario_destino
        model.setBodegaOrigenNombreAlterno(dto.getEstadoInventarioDestino());

        model.setLote(dto.getLote());
        // serial
        // cosecha
        model.setOrigenContacto(new Contacto(dto.getRequerimientoEstampillado(), dto.getRequerimientoBl(),
                dto.getRequerimientoFondoCuenta()));
        // requerimiento_estampillado
        // requerimiento_salud
        // requerimiento_importe
        // requerimiento_distribuido
        // requerimiento_nutricional
        // requerimiento_bl
        // requerimiento_fondo_cuenta
        // requerimiento_origen

        model.setPredistribucionDestinoFinal(dto.getPredistribucionDestinoFinal());
        model.setPredistribucionRotulo(dto.getPredistribucionRotulo());
        model.setValorDeclaradoPorUnidad(dto.getValorDeclaradoPorUnidad());

        model.setNotas(dto.getNotas());

        model.setFechaActualizacion(new Date());
        model.setUsuarioActualizacion(dtoOrden.getUsuarioConfirmacion());

        // TODO QUITAR
        model.setOrigen(null);
    }

    // ----------------------------------------------------------------------------------------------------------------
    private Consolidado CrearConsolidado(ETLOrdenDto dto, Cliente cliente) {
        Consolidado model = new Consolidado();

        model.setCliente(cliente);
        model.setNumeroDocumentoConsolidadoCliente(dto.getNumeroConsolidado());
        model.setFechaActualizacion(new Date());
        model.setUsuarioActualizacion(dto.getClienteCodigo());

        return consolidadoRepository.save(model);
    }

    protected DestinatarioRemitente CrearDestintarioRemitente(ETLOrdenDto dto, Cliente cliente) {
        DestinatarioRemitente model = null;
        if (dto.getDestinatarioNumeroIdentificacion().isEmpty()) {
            return model;
        }

        model = new DestinatarioRemitente();

        model.setClienteId(cliente.getId());
        // TODO
        model.setCanalId(0);

        model.setCodigo("");
        model.setIdentificacionType(IdentificacionType.NI);
        model.setNumeroIdentificacion(dto.getDestinatarioNumeroIdentificacion());
        model.setDigitoVerificacion("");

        model.setNombre(dto.getDestinatarioNombre());
        model.setNombreComercial(dto.getDestinatarioNombre());

        model.setDireccion(new OmsDireccion(null, "", ""));

        model.setContacto(new Contacto(dto.getDestinatarioContactoNombres(), dto.getDestinatarioContactoEmail(),
                dto.getDestinatarioContactoTelefonos()));

        model.setFechaActualizacion(new Date());
        model.setUsuarioActualizacion(dto.getUsuarioConfirmacion());

        return destinatarioRemitenteRepository.save(model);
    }

    protected DestinoOrigen CrearDestinoOrigen(ETLOrdenDto dto, DestinatarioRemitente destinatarioRemitente,
            Ciudad ciudad) {
        DestinoOrigen model = null;

        if (ciudad == null || dto.getDestinoDireccion().isEmpty()) {
            return model;
        }

        model = new DestinoOrigen();
        model.setDestinatarioRemitenteId(destinatarioRemitente.getId());
        model.setCodigo("");
        model.setNombre(dto.getDestinoNombre());
        model.setUsoUbicacionType(UsoUbicacionType.PUNTO_RUTA);

        model.setDireccion(new OmsDireccion(ciudad.getId(), dto.getDestinoDireccion(), dto.getDestinoIndicaciones()));
        model.setContacto(new Contacto(dto.getDestinoContactoNombres(), dto.getDestinoContactoEmail(),
                dto.getDestinoContactoTelefonos()));

        model.setFechaActualizacion(new Date());
        model.setUsuarioActualizacion(dto.getUsuarioConfirmacion());

        return destinoOrigenRepository.save(model);
    }

    // ----------------------------------------------------------------------------------------------------------------
    public void updateLineaOrdenFromViewModel(Orden entity, LineaOrdenDto model) {
        LineaOrden e = null;

        if (model.getIdLineaOrden() == null) {
            e = new LineaOrden(null);
            entity.addLinea(e);
        } else {
            e = entity.getLinea(model.getIdLineaOrden());
        }

        if (notEqualId(model.getProducto(), (e.getProducto() == null) ? null : e.getProducto().getId())) {
            e.setProducto(productoRepository.findOne(model.getProducto()));
        }
        if (notEqualId(model.getUnidad(), (e.getUnidad() == null) ? null : e.getUnidad().getId())) {
            e.setUnidad(unidadRepository.findOne(model.getUnidad()));
        }
        if (notEqualId(model.getBodega(), (e.getBodegaOrigen() == null) ? null : e.getBodegaOrigen().getId())) {
            e.setBodegaOrigen(bodegaRepository.getOne(model.getBodega()));
        }
        if (notEqualId(model.getOrigen(), (e.getOrigen() == null) ? null : e.getOrigen().getId())) {
            e.setOrigen(destinoOrigenRepository.getOne(model.getOrigen()));
        }
        if (notEqualId(model.getTipoContenido(),
                (e.getTipoContenido() == null) ? null : e.getTipoContenido().getId())) {
            e.setTipoContenido(tipoContenidoRepository.getOne(model.getTipoContenido()));
        }

        LineaOrdenViewModelAdpater.updateLineaOrdenFromViewModel(e, model);

        entity.modificado(e.getUsuarioActualizacion());
    }

    protected boolean notEqualId(Integer a, Integer b) {
        if (a == null) {
            return (b != null);
        }
        return !a.equals(b);
    }

    // -------------------------------------------------------------------------------------------------------------------------------------------------
    protected Map<String, Object> estadoOrdenToViewModel(EstadoOrden entity) {
        Map<String, Object> o = new HashMap<String, Object>();

        o.put("id", entity.getEstadoOrden());
        o.put("nombre", entity.getDescripcion());
        o.put("ordinal", entity.getOrdinal());

        return o;
    }

    protected Map<String, Object> bodegaToViewModel(Bodega entity) {
        Map<String, Object> o = new HashMap<String, Object>();

        o.put("id", entity.getId());
        o.put("codigo", entity.getCodigo());
        o.put("nombre", entity.getNombre());
        o.put("direccion", ciudadesService.direccionToViewModel(entity.getDireccion()));
        o.put("manejaRenta", entity.isManejaRenta());

        return o;
    }

    protected Map<String, Object> tipoContenidoToViewModel(TipoContenido entity) {
        Map<String, Object> o = new HashMap<String, Object>();

        o.put("id", entity.getId());
        o.put("codigo", entity.getCodigo());
        o.put("nombre", entity.getNombre());

        return o;
    }

    protected Map<String, Object> tipoFormaPagoToViewModel(TipoFormaPago entity) {
        Map<String, Object> o = new HashMap<String, Object>();

        o.put("id", entity.getId());
        o.put("codigo", entity.getCodigo());
        o.put("nombre", entity.getNombre());

        return o;
    }

    public OrdenDto ordenToViewModel(Orden entity) {
        if (entity == null) {
            return null;
        }
        System.out.println(Locale.getDefault().toString());

        OrdenDto model = new OrdenDto();

        model.setIdOrden(entity.getId());
        // ---------------------------------------------------------------------------------------------------------
        model.setDatosFacturacion(OrdenDatosFacturacionViewModelAdapter.ordenToViewModel(entity));
        model.setDestinoOrigen(OrdenDatosDestinoOrigenViewModelAdapter.ordenToViewModel(entity, ciudadRepository));
        model.setBodegaDestinoOrigen(OrdenDatosBodegaDestinoOrigenViewModelAdapter.ordenToViewModel(entity));
        model.setDatosEntregaRecogida(OrdenDatosEntregaRecogidaViewModelAdapter.ordenToViewModel(entity));
        model.setDatosOtros(OrdenDatosOtrosViewModelAdapter.ordenToViewModel(entity));
        // ---------------------------------------------------------------------------------------------------------
        model.setLineas(LineaOrdenViewModelAdpater.ordenToViewModel(entity));
        model.setMensajes(MensajeViewModelAdpater.ordenToViewModel(entity));
        // TODO CAMBIOS
        // TODO DOCUMENTOS
        // TODO REQUERIMIENTOS

        // ---------------------------------------------------------------------------------------------------------
        model.setFechaConfirmacion(entity.getFechaConfirmacion());
        model.setUsuarioConfirmacion(entity.getUsuarioConfirmacion());

        model.setFechaAceptacion(entity.getFechaAceptacion());
        model.setUsuarioAceptacion(entity.getUsuarioAceptacion());

        model.setFechaActualizacion(entity.getFechaActualizacion());
        model.setUsuarioActualizacion(entity.getUsuarioActualizacion());

        model.setNuevoEstadoOrden(entity.getEstadoOrden());

        return model;
    }

}
