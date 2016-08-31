package com.tacticlogistics.application.services.ordenes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tacticlogistics.application.dto.ordenes.BodegaDestinoOrigenDto;
import com.tacticlogistics.application.dto.ordenes.DestinatarioDto;
import com.tacticlogistics.application.dto.ordenes.DestinoOrigenDto;
import com.tacticlogistics.application.dto.ordenes.EntregaRecogidaDto;
import com.tacticlogistics.application.dto.ordenes.LineaOrdenDto;
import com.tacticlogistics.application.dto.ordenes.OtrosDatosDto;
import com.tacticlogistics.domain.model.common.Mensaje;
import com.tacticlogistics.domain.model.common.valueobjects.Contacto;
import com.tacticlogistics.domain.model.common.valueobjects.Dimensiones;
import com.tacticlogistics.domain.model.common.valueobjects.Direccion;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;
import com.tacticlogistics.domain.model.crm.DestinoOrigen;
import com.tacticlogistics.domain.model.crm.Canal;
import com.tacticlogistics.domain.model.crm.TipoServicio;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.ordenes.CausalAnulacionOrden;
import com.tacticlogistics.domain.model.ordenes.Consolidado;
import com.tacticlogistics.domain.model.ordenes.LineaOrden;
import com.tacticlogistics.domain.model.ordenes.Orden;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.domain.model.wms.ProductoUnidadAssociation;
import com.tacticlogistics.infrastructure.persistence.geo.CiudadRepository;

@Service
public class OrdenViewModelAdapter {
    static class OrdenDatosFacturacionViewModelAdapter {

        protected static DestinatarioDto ordenToViewModel(Orden entity) {
            DestinatarioDto model = new DestinatarioDto();

            updateViewModelFromTipoServicio(model, entity.getTipoServicio());
            updateViewModelFromCliente(model, entity.getCliente());
            updateViewModelFromConsolidado(model, entity.getConsolidado());
            model.setNumeroOrden(entity.getNumeroOrden());
            model.setEstadoOrdenType(entity.getEstadoOrden());
            updateViewModelFromCausalAnulacion(model, entity.getCausalAnulacion());
            updateViewModelFromSegmento(model, entity.getCanal());
            updateViewModelFromDestinatarioRemitente(model, entity.getDestinatario());
            model.setCodigoAlternoDestinatario("");
            model.setNumeroIdentificacionAlternoDestinatario(entity.getDestinatarioNumeroIdentificacion());
            model.setNombreAlternoDestinatario(entity.getDestinatarioNombre());
            updateViewModelFromContacto(model, entity.getDestinatarioContacto());

            return model;
        }

        protected static void updateViewModelFromTipoServicio(DestinatarioDto model, TipoServicio entity) {
            if (entity != null) {
                model.setTipoServicio(entity.getId());
                model.setCodigoTipoServicio(entity.getCodigo());
                model.setNombreTipoServicio(entity.getNombre());
            } else {
                model.setTipoServicio(null);
                model.setCodigoTipoServicio("");
                model.setNombreTipoServicio("");
            }
        }

        protected static void updateViewModelFromCliente(DestinatarioDto model, Cliente entity) {
            if (entity != null) {
                model.setCliente(entity.getId());
                model.setCodigoCliente(entity.getCodigo());
                model.setNombreCliente(entity.getNombre());
            } else {
                model.setCliente(null);
                model.setCodigoCliente("");
                model.setNombreCliente("");
            }
        }

        // TODO
        protected static void updateViewModelFromConsolidado(DestinatarioDto model, Consolidado entity) {
            if (entity != null) {

            }
        }

        protected static void updateViewModelFromCausalAnulacion(DestinatarioDto model, CausalAnulacionOrden entity) {
            if (entity != null) {
                model.setCausalAnulacion(entity.getId());
                model.setCodigoCausalAnulacion(entity.getNombre());
                model.setNombreCausalAnulacion(entity.getNombre());
            } else {
                model.setCausalAnulacion(null);
                model.setCodigoCausalAnulacion("");
                model.setNombreCausalAnulacion("");
            }
        }

        protected static void updateViewModelFromSegmento(DestinatarioDto model, Canal entity) {
            if (entity != null) {
                model.setSegmento(entity.getId());
                model.setNombreSegmento(entity.getNombre());
            } else {
                model.setSegmento(null);
                model.setNombreSegmento("");
            }
        }

        protected static void updateViewModelFromDestinatarioRemitente(DestinatarioDto model,
                DestinatarioRemitente entity) {
            if (entity != null) {
                model.setDestinatario(entity.getId());
                model.setCodigoDestinatario(entity.getCodigo());
                model.setNumeroIdentificacionDestinatario(entity.getNumeroIdentificacion());
                model.setNombreDestinatario(entity.getNombre());
                model.setNombreComercialDestinatario(entity.getNombreComercial());
            } else {
                model.setDestinatario(null);
                model.setCodigoDestinatario("");
                model.setNumeroIdentificacionDestinatario("");
                model.setNombreDestinatario("");
                model.setNombreComercialDestinatario("");
            }
        }

        protected static void updateViewModelFromContacto(DestinatarioDto model, Contacto entity) {
            if (entity != null) {
                model.setNombre(entity.getNombres());
                model.setEmail(entity.getEmail());
                model.setTelefonos(entity.getTelefonos());
            } else {
                model.setNombre("");
                model.setEmail("");
                model.setTelefonos("");
            }
        }

    }

    static class OrdenDatosDestinoOrigenViewModelAdapter {

        protected static DestinoOrigenDto ordenToViewModel(Orden entity, CiudadRepository ciudadRepository) {
            DestinoOrigenDto model = new DestinoOrigenDto();

            updateViewModelFromDestinoOrigen(model, entity.getDestino());
            updateViewModelFromDestinoDireccion(model, entity.getDestinoDireccion(), ciudadRepository);
            updateViewModelFromDestinoContacto(model, entity.getDestinoContacto());

            return model;
        }

        protected static BodegaDestinoOrigenDto createViewModelFromOrden2(Orden entity) {
            BodegaDestinoOrigenDto model = new BodegaDestinoOrigenDto();

            //updateViewModelFromBodega(model, entity.getBodegaDestino());

            return model;
        }

        protected static void updateViewModelFromDestinoDireccion(DestinoOrigenDto model, Direccion entity,
                CiudadRepository ciudadRepository) {
            if (entity != null) {
                model.setCiudadId(entity.getCiudad() == null ? null : entity.getCiudad().getId());
                model.setCiudadNombreAlterno("");
                model.setDireccion(entity.getDireccion());
                model.setIndicacionesDireccion(entity.getIndicacionesDireccion());

                if (entity.getCiudad() != null) {
                    Ciudad ciudad = ciudadRepository.findOne(entity.getCiudad().getId());
                    if (ciudad != null) {
                        model.setCiudadNombreAlterno(ciudad.getNombreAlterno());
                    }
                }
            }
        }

        protected static void updateViewModelFromDestinoContacto(DestinoOrigenDto model, Contacto entity) {
            if (entity != null) {
                model.setNombre(entity.getNombres());
                model.setContactoEmail(entity.getEmail());
                model.setContactoTelefonos(entity.getTelefonos());
            }
        }

        protected static void updateViewModelFromBodega(BodegaDestinoOrigenDto model, Bodega b) {
            if (b != null) {
                model.setBodegaId(b.getId());
                model.setBodegaCodigo(b.getCodigo());
                model.setBodegaNombre(b.getNombre());
            }
        }

        protected static void updateViewModelFromDestinoOrigen(DestinoOrigenDto model, DestinoOrigen entity) {
            if (entity != null) {
                model.setDestinoId(entity.getId());
                model.setDestinoCodigo(entity.getCodigo());
                model.setDestinoNombre(entity.getNombre());
            }
        }

    }

    static class OrdenDatosEntregaRecogidaViewModelAdapter {
        protected static EntregaRecogidaDto ordenToViewModel(Orden entity) {
            EntregaRecogidaDto model = new EntregaRecogidaDto();

            model.setFechaMinima(null);
            model.setFechaMaxima(null);
            model.setHoraMinima(null);
            model.setHoraMaxima(null);
            model.setFechaEntregaMaximaSegunPromesaServicio(null);

            model.setFechaMinima(entity.getFechaEntregaSugeridaMinima());
            model.setFechaMaxima(entity.getFechaEntregaSugeridaMaxima());
            model.setHoraMinima("");
            model.setHoraMaxima("");

            if (entity.getHoraEntregaSugeridaMinima() != null) {
                model.setHoraMinima(entity.getHoraEntregaSugeridaMinima().toString().substring(0, 5));
            }
            if (entity.getHoraEntregaSugeridaMaxima() != null) {
                model.setHoraMaxima(entity.getHoraEntregaSugeridaMaxima().toString().substring(0, 5));
            }

            return model;
        }
    }

    static class OrdenDatosOtrosViewModelAdapter {
        protected static OtrosDatosDto ordenToViewModel(Orden entity) {
            OtrosDatosDto model = new OtrosDatosDto();

            model.setValorDeclarado(entity.getValorRecaudo());
            model.setNotas(entity.getNotasConfirmacion());

            return model;
        }
    }

    static class OrdenDatosBodegaDestinoOrigenViewModelAdapter {

        protected static BodegaDestinoOrigenDto ordenToViewModel(Orden entity) {
            BodegaDestinoOrigenDto model = new BodegaDestinoOrigenDto();
            // updateViewModelFromDestinoOrigen(model, entity.getDestino());
            // updateViewModelFromDestinoDireccion(model,
            // entity.getDestinoDireccion());
            // updateViewModelFromDestinoContacto(model,
            // entity.getDestinoContacto());

            return model;
        }

        /*
         * protected static void
         * updateViewModelFromDestinoDireccion(DestinoOrigenDto model, Direccion
         * entity) { if (entity != null) {
         * model.setCiudadId(entity.getCiudadId());
         * model.setCiudadNombreAlterno(""); if (entity.getCiudadId() != null) {
         * model.setCiudadNombreAlterno(entity.getCiudad().getNombreAlterno());
         * } else { }
         * 
         * model.setDireccion(entity.getDireccion());
         * model.setIndicacionesDireccion(entity.getIndicacionesDireccion()); }
         * }
         */
        protected static void updateViewModelFromDestinoContacto(DestinoOrigenDto model, Contacto entity) {
            if (entity != null) {
                model.setNombre(entity.getNombres());
                model.setContactoEmail(entity.getEmail());
                model.setContactoTelefonos(entity.getTelefonos());
            }
        }

        protected static void updateViewModelFromBodega(BodegaDestinoOrigenDto model, Bodega b) {
            if (b != null) {
                model.setBodegaId(b.getId());
                model.setBodegaCodigo(b.getCodigo());
                model.setBodegaNombre(b.getNombre());
            }
        }

        protected static void updateViewModelFromDestinoOrigen(DestinoOrigenDto model, DestinoOrigen entity) {
            if (entity != null) {
                model.setDestinoId(entity.getId());
                model.setDestinoCodigo(entity.getCodigo());
                model.setDestinoNombre(entity.getNombre());
            }
        }
    }

    static class LineaOrdenViewModelAdpater {
        protected static List<LineaOrdenDto> ordenToViewModel(Orden entity) {
            List<LineaOrdenDto> list = new ArrayList<>();
            entity.getLineas().forEach((a) -> {
                if (a.getId() != null) {
                    list.add(createViewModelFromOrden(a));
                }
            });

            Collections.sort(list, new Comparator<LineaOrdenDto>() {
                public int compare(LineaOrdenDto a, LineaOrdenDto b) {
                    return a.getNumeroItem().compareTo(b.getNumeroItem());
                }
            });

            return list;
        }

        public static void updateLineaOrdenFromViewModel(LineaOrden model, LineaOrdenDto dto) {

            // todo ver paquetes

            model.setCantidadSolicitada(dto.getCantidad());
            model.setLote(dto.getLote());
            model.setValorDeclaradoPorUnidad(dto.getValorDeclaradoPorUnidad());
            model.setNotas(dto.getNotas() == null ? "" : dto.getNotas());

            model.setProductoCodigoAlterno(dto.getCodigoProductoAlterno() == null ? "" : dto.getCodigoProductoAlterno());
            model.setUnidadCodigoAlterno(dto.getCodigoUnidadAlterno() == null ? "" : dto.getCodigoUnidadAlterno());
            model.setBodegaOrigenCodigoAlterno(dto.getCodigoBodegaAlterno() == null ? "" : dto.getCodigoBodegaAlterno());

            if (model.getProducto() != null) {
                Optional<ProductoUnidadAssociation> pua = model.getProducto().getProductoUnidadAssociation().stream()
                        .filter(a -> a.getUnidad().equals(model.getUnidad())).findFirst();

                model.setDescripcion(model.getProducto().getNombreLargo());
                model.setProductoCodigo(model.getProducto().getCodigo());
                model.setUnidadCodigo((model.getUnidad() != null) ? model.getUnidad().getCodigo() : "");
                if (pua.isPresent()) {
                    model.setDimensiones(new Dimensiones(pua.get().getLargo(), pua.get().getAncho(), pua.get().getAlto(),
                            pua.get().getPesoBruto()));
                }
            } else {
                model.setDescripcion(dto.getDescripcion());
                model.setProductoCodigo("");
                model.setUnidadCodigo("");
                model.setDimensiones(new Dimensiones(dto.getLargoPorUnidad(), dto.getAnchoPorUnidad(), dto.getAltoPorUnidad(),dto.getPesoBrutoPorUnidad()));
            }

            //model.setOrigenContacto(new Contacto((dto.getNombre() == null ? "" : dto.getNombre()),
            //(dto.getEmail() == null ? "" : dto.getEmail()),
            //(dto.getTelefonos() == null ? "" : dto.getTelefonos())));
            //model.setOrigenCodigoAlterno(dto.getOrigenCodigoAlterno() == null ? "" : dto.getOrigenCodigoAlterno());
            //model.setOrigenNombreAlterno(dto.getOrigenNombreAlterno() == null ? "" : dto.getOrigenNombreAlterno());

            //if (model.getOrigen() != null) {
            //    Direccion direcccion = model.getOrigen().getDireccion();
            //    
            //    model.setOrigenDireccion(new Direccion(direcccion.getCiudad(), direcccion.getDireccion(),
            //            direcccion.getIndicacionesDireccion()));
            //} else {
            //}

            // Set<MensajeEmbeddable> mensajes = new
            // HashSet<MensajeEmbeddable>();
            model.setFechaActualizacion(new Date());
            model.setUsuarioActualizacion(dto.getUsuario());
        }

        protected static LineaOrdenDto createViewModelFromOrden(LineaOrden model) {
            LineaOrdenDto dto = new LineaOrdenDto();

            dto.setIdOrden(model.getOrden().getId());
            dto.setIdLineaOrden(model.getId());
            dto.setNumeroItem(model.getNumeroItem());

            dto.setDescripcion(model.getDescripcion());
            dto.setCantidad(model.getCantidadSolicitada());
            dto.setValorDeclaradoPorUnidad(model.getValorDeclaradoPorUnidad());

            if (model.getProducto() != null) {
                dto.setProducto(model.getProducto().getId());

            }
            dto.setCodigoProducto(model.getProductoCodigo());
            dto.setNombreProducto(model.getDescripcion());
            dto.setCodigoProductoAlterno(model.getProductoCodigoAlterno());
            dto.setNombreProductoAlterno("");

            if (model.getUnidad() != null) {
                dto.setUnidad(model.getUnidad().getId());
                dto.setNombreUnidad(model.getUnidad().getNombre());
            }
            dto.setCodigoUnidad(model.getUnidadCodigo());
            dto.setCodigoUnidadAlterno(model.getUnidadCodigoAlterno());

            dto.cambiarDimensiones(model.getLargoPorUnidad(), model.getAnchoPorUnidad(), model.getAltoPorUnidad(),
                    model.getPesoBrutoPorUnidad());

            if (model.getBodegaOrigen() != null) {
                dto.setBodega(model.getBodegaOrigen().getId());
                dto.setCodigoBodega(model.getBodegaOrigen().getCodigo());
                dto.setNombreBodega(model.getBodegaOrigen().getNombre());
            }
            dto.setCodigoBodegaAlterno(model.getBodegaOrigenCodigoAlterno());

            // TODO ubicaion origen
            // private Integer origen;
            // private String origenCodigo;
            // private String origenNombre;
            // private String origenCodigoAlterno;
            // private String origenNombreAlterno;
            // private Integer ciudad;
            // private String ciudadNombreAlterno;
            // private String direccion;
            // private String indicacionesDireccion;
            // private BigDecimal longitud;
            // private BigDecimal latitud;
            // private String direccionEstandarizada;
            // private String nombre;
            // private String email;
            // private String telefonos;

            dto.setLote(model.getLote()); // TODO model.disponibilidad = 0;
            dto.setDisponibilidad(0);
            dto.setNotas(model.getNotas());

            // private Integer tipoContenido;
            // private String tipoContenidoCodigo;
            // private String tipoContenidoNombre;
            // private String tipoContenidoCodigoAlterno;

            dto.setFechaActualizacion(model.getFechaActualizacion());
            dto.setUsuario(model.getUsuarioActualizacion());

            return dto;
        }
    }

    static class MensajeViewModelAdpater {
        protected static List<Mensaje> ordenToViewModel(Orden entity) {
            List<Mensaje> list = new ArrayList<>();
            entity.getMensajes().forEach((a) -> {
                list.add(a);
            });

            Collections.sort(list, new Comparator<Mensaje>() {
                public int compare(Mensaje a, Mensaje b) {
                    return a.getSeveridad().compareTo(b.getSeveridad());
                }
            });

            return list;
        }

        public static void updateOrdenFromViewModel(LineaOrden entity, LineaOrdenDto model, Producto producto) {
            /*
             * entity.setConsistente(false); entity.setCantidad(model.cantidad);
             * entity.setLote(model.lote); entity.setNotas(model.notas);
             * entity.setFechaActualizacion(model.getFechaActualizacion());
             * entity.setUsuarioActualizacion(model.getUsuario());
             * 
             * //
             * -----------------------------------------------------------------
             * ----------------------------------------
             * entity.setCodigoProducto(model.codigoProducto);
             * entity.setCodigoAlternoProducto(model.codigoProductoAlterno);
             * entity.setDescripcion(model.nombreProducto);
             * 
             * entity.setCodigoUnidad(model.codigoUnidad);
             * entity.setCodigoAlternoUnidad(model.codigoUnidadAlterno); //
             * -----------------------------------------------------------------
             * ---------------------------------------- if
             * (entity.getLargoPorUnidad() == null) {
             * entity.setLargoPorUnidad(new BigDecimal(0));
             * entity.setAnchoPorUnidad(new BigDecimal(0));
             * entity.setAltoPorUnidad(new BigDecimal(0));
             * entity.setPesoBrutoPorUnidad(new BigDecimal(0));
             * entity.setValorDeclaradoPorUnidad(null); }
             * 
             * //
             * -----------------------------------------------------------------
             * ---------------------------------------- if (producto != null) {
             * entity.setCodigoProducto(producto.getCodigo()); //
             * entity.setCodigoAlternoProducto(producto.getCodigoAlterno());
             * entity.setDescripcion(producto.getNombreLargo());
             * 
             * Optional<ProductoUnidadAssociation> pua =
             * producto.getProductoUnidadAssociation().stream() .filter(a ->
             * a.getUnidad().getId().equals(model.unidad)).findFirst();
             * 
             * if (pua.isPresent()) {
             * entity.setCodigoUnidad(pua.get().getUnidad().getCodigo()); //
             * TODO // entity.setCodigoAlternoUnidad(a.get); if
             * (pua.get().getLargo().floatValue() > 0.0) {
             * entity.setLargoPorUnidad(pua.get().getLargo());
             * entity.setAnchoPorUnidad(pua.get().getAncho());
             * entity.setAltoPorUnidad(pua.get().getAlto());
             * entity.setPesoBrutoPorUnidad(pua.get().getPesoBruto());
             * entity.setValorDeclaradoPorUnidad(pua.get().getValorAproximado())
             * ; } } }
             */
        }

        protected static LineaOrdenDto createViewModelFromOrden(LineaOrden entity) {
            LineaOrdenDto model = new LineaOrdenDto();
            /*
             * 
             * 
             * model.idLineaOrden = entity.getId(); model.idOrden =
             * entity.getOrden().getId(); model.numeroItem =
             * entity.getNumeroItem();
             * 
             * if (entity.getProducto() != null) { model.producto =
             * entity.getProducto().getId(); } model.codigoProducto =
             * entity.getCodigoProducto(); model.codigoProductoAlterno =
             * entity.getCodigoAlternoProducto(); model.nombreProducto =
             * entity.getDescripcion();
             * 
             * model.cantidad = entity.getCantidad();
             * 
             * if (entity.getUnidad() != null) { model.unidad =
             * entity.getUnidad().getId(); model.nombreUnidad =
             * entity.getUnidad().getNombre(); } model.codigoUnidad =
             * entity.getCodigoUnidad(); model.codigoUnidadAlterno =
             * entity.getCodigoAlternoUnidad();
             * 
             * if (entity.getBodegaOrigen() != null) { model.bodega =
             * entity.getBodegaOrigen().getId(); model.codigoBodega =
             * entity.getBodegaOrigen().getCodigo(); model.nombreBodega =
             * entity.getBodegaOrigen().getNombre(); }
             * 
             * model.lote = entity.getLote(); // TODO model.disponibilidad = 0;
             * model.notas = entity.getNotas();
             * 
             * model.setFechaActualizacion(entity.getFechaActualizacion());
             * model.setUsuario(entity.getUsuarioActualizacion());
             */
            return model;
        }
    }
}