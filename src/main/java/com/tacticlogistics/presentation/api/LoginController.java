package com.tacticlogistics.presentation.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.crm.ClienteDto;
import com.tacticlogistics.application.dto.geo.CiudadDto;
import com.tacticlogistics.application.dto.ingresos.LineaOrdenIngreso;
import com.tacticlogistics.application.dto.ingresos.LineaOrdenIngresoRepository;
import com.tacticlogistics.application.dto.ingresos.OrdenIngreso;
import com.tacticlogistics.application.dto.ingresos.OrdenIngresoRepository;
import com.tacticlogistics.application.dto.ingresos.RespuestaSaveOrdenIngreso;
import com.tacticlogistics.application.dto.ingresos.TipoNovedadIngresoInventario;
import com.tacticlogistics.application.dto.seguridad.RespuestaLoginDto;
import com.tacticlogistics.application.dto.seguridad.UserDto;
import com.tacticlogistics.application.dto.tms.TipoVehiculoDto;
import com.tacticlogistics.application.dto.tms.TransportadoraDto;
import com.tacticlogistics.application.dto.wms.BodegaDto;
import com.tacticlogistics.application.dto.wms.EstadoInventarioDto;
import com.tacticlogistics.application.dto.wms.ProductoDto;
import com.tacticlogistics.application.dto.wms.TipoNovedadIngresoInventarioDto;
import com.tacticlogistics.application.dto.wms.UoMDto;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.ClienteBodegaAssociation;
import com.tacticlogistics.domain.model.crm.ClienteCiudadAssociation;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.seguridad.Usuario;
import com.tacticlogistics.domain.model.seguridad.UsuarioBodegaAssociation;
import com.tacticlogistics.domain.model.tms.TipoVehiculo;
import com.tacticlogistics.domain.model.tms.Transportadora;
import com.tacticlogistics.domain.model.wms.Bodega;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.geo.CiudadRepository;
import com.tacticlogistics.infrastructure.persistence.ingresos.TipoNovedadIngresoInventarioRepository;
import com.tacticlogistics.infrastructure.persistence.seguridad.UsuarioRepository;
import com.tacticlogistics.infrastructure.persistence.tms.TipoVehiculoRepository;
import com.tacticlogistics.infrastructure.persistence.tms.TransportadoraRepository;
import com.tacticlogistics.infrastructure.persistence.wms.BodegaRepository;
import com.tacticlogistics.infrastructure.persistence.wms.ProductoRepository;

@RestController
public class LoginController {

    @Autowired
    CiudadRepository ciudadRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    BodegaRepository bodegaRepository;

    @Autowired
    TipoNovedadIngresoInventarioRepository tipoNovedadIngresoInventarioRepository;

    @Autowired
    TransportadoraRepository transportadoraRepository;

    @Autowired
    TipoVehiculoRepository tipoVehiculoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    OrdenIngresoRepository ordenRepository;

    @Autowired
    LineaOrdenIngresoRepository lineaOrdenRepository;

    @CrossOrigin
    @RequestMapping("/login")
    public RespuestaLoginDto Login(@RequestParam(value = "usuario", defaultValue = "") String usuario,
            @RequestParam(value = "pwd", defaultValue = "") String pwd) {

        boolean ok = false;
        String mensaje = "";
        UserDto user = null;
        List<BodegaDto> bodegas = new ArrayList<BodegaDto>(0);

        Usuario u = usuarioRepository.findByUsuarioIgnoringCase(usuario);
        if (u != null) {
            if (u.getPassword().equals(pwd)) {
                ok = true;
                user = new UserDto(u.getId(), u.getUsuario(), u.getPassword(), u.getNombres(), u.getApellidos(),
                        u.getEmail(), u.isActivo());

                for (UsuarioBodegaAssociation uba : u.getUsuarioBodegaAssociation()) {
                    Ciudad ciudad = ciudadRepository.getOne(uba.getBodega().getDireccion().getCiudadId());

                    bodegas.add(new BodegaDto(uba.getBodega().getId(), uba.getBodega().getCodigo(),
                            uba.getBodega().getNombre(),
                            new CiudadDto((ciudad == null) ? null : ciudad.getId(), (ciudad == null) ? null : "",
                                    (ciudad == null) ? null : "", (ciudad == null) ? null : "",
                                    (ciudad == null) ? null : true),
                            uba.getBodega().getDireccion().getDireccion(), uba.getBodega().isActivo(),
                            uba.isPredeterminada()));
                }
            } else {
                mensaje = "Error de autenticación";
            }
        } else {
            mensaje = "Usuario no existe";
        }

        return new RespuestaLoginDto(ok, mensaje, user, bodegas);
    }

    @CrossOrigin
    @RequestMapping("/clientes-x-bodega")
    public List<ClienteDto> getClientesPorBodega(@RequestParam(value = "bodega", defaultValue = "") String codigo) {

        List<ClienteDto> list = new ArrayList<ClienteDto>(0);

        Bodega bodega = bodegaRepository.findByCodigoIgnoringCase(codigo);
        if (bodega != null) {

            Comparator<ClienteBodegaAssociation> by = (a, b) -> Integer.compare(a.getClienteId(), b.getClienteId());

            bodega.getBodegaClienteAssociation().stream().sorted(by)
                    .forEachOrdered((c) ->{
                        Cliente cliente  = clienteRepository.findOne(c.getClienteId());
                        list.add(new ClienteDto(
                                cliente.getId(), 
                                cliente.getCodigo(),
                                cliente.getNumeroIdentificacion(), 
                                cliente.getDigitoVerificacion(),
                                cliente.getNombre(), 
                                cliente.isActivo()));    
                    } 
                    );
        }
        return list;
    }

    @CrossOrigin
    @RequestMapping("/ciudades-x-cliente")
    public List<CiudadDto> getCiudadesPorCliente(@RequestParam(value = "cliente", defaultValue = "") String codigo) {

        List<CiudadDto> list = new ArrayList<CiudadDto>(0);

        Cliente cliente = clienteRepository.findByCodigoIgnoringCase(codigo);
        if (cliente != null) {

            Comparator<ClienteCiudadAssociation> by = (a, b) -> Integer.compare(
                    a.getCiudadId(),
                    b.getCiudadId());

            cliente.getClienteCiudadAssociation().stream().sorted(by).forEachOrdered((c) -> {
                Ciudad ciudad = ciudadRepository.getOne(c.getCiudadId());
                if(ciudad != null){
                    list.add(new CiudadDto(
                            ciudad.getId(), 
                            ciudad.getCodigo(),
                            ciudad.getNombreAlterno(), 
                            ciudad.getNombreAlterno(), 
                            ciudad.isActivo()));
                }
            });
        }
        return list;
    }

    @CrossOrigin
    @RequestMapping("/ciudades")
    public List<CiudadDto> getCiudades() {
        List<CiudadDto> list = new ArrayList<CiudadDto>(0);

        List<Ciudad> listCiudad = new ArrayList<>();

        for (Ciudad ciudad : ciudadRepository.findAll()) {
            listCiudad.add(ciudad);
        }

        Comparator<Ciudad> by = (a, b) -> Integer.compare(a.getOrdinal(), b.getOrdinal());

        listCiudad.stream().sorted(by).forEachOrdered((c) -> list.add(
                new CiudadDto(c.getId(), c.getCodigo(), c.getNombreAlterno(), c.getNombreAlterno(), c.isActivo())));

        return list;
    }

    @CrossOrigin
    @RequestMapping("/productos-x-cliente-x-bodega")
    public List<ProductoDto> getProductosPorClientePorBodega(
            @RequestParam(value = "cliente", defaultValue = "") String cliente,
            @RequestParam(value = "bodega", defaultValue = "") String bodega) {

        List<ProductoDto> list = new ArrayList<>();
        Cliente c = clienteRepository.findByCodigoIgnoringCase(cliente);
        if (c != null) {
            List<Producto> productos = productoRepository.findAllByClienteIdAndActivoOrderByNombreLargo(c.getId(),
                    true);

            Comparator<Producto> by = (a, b) -> a.getNombreLargo().compareTo(b.getNombreLargo());

            productos.stream().sorted(by).forEachOrdered((p) -> {
                p.getProductoBodegaAssociation().forEach((q) -> {
                    if (q.getBodega().getCodigo().equals(bodega)) {
                        // list.add(new ProductoViewModel(p.getId(), cliente,
                        // bodega, p.getCodigo(),p.getCodigo() + ", " +
                        // p.getNombreLargo(), p.getNombreLargo()));
                        list.add(new ProductoDto(p.getId(), cliente, p.getCodigo(),
                                p.getCodigo() + ", " + p.getNombreLargo(), p.getCodigo(), p.getNombreLargo()));

                    }
                });
            });
        }

        return list;
    }

    @CrossOrigin
    @RequestMapping("/transportadoras")
    public List<TransportadoraDto> getTransportadoras() {
        List<TransportadoraDto> list = new ArrayList<TransportadoraDto>(0);

        List<Transportadora> list2 = new ArrayList<>();

        for (Transportadora c : transportadoraRepository.findAll()) {
            list2.add(c);
        }

        Comparator<Transportadora> by = (a, b) -> a.getCodigo().compareTo(b.getCodigo());

        list2.stream().sorted(by).forEachOrdered(
                (c) -> list.add(new TransportadoraDto(c.getId(), c.getCodigo(), c.getNombre(), c.isActivo())));

        return list;
    }

    @CrossOrigin
    @RequestMapping("/tipos-vehiculos")
    public List<TipoVehiculoDto> getTiposVehiculos() {
        List<TipoVehiculoDto> list = new ArrayList<TipoVehiculoDto>(0);

        List<TipoVehiculo> list2 = new ArrayList<>();

        for (TipoVehiculo c : tipoVehiculoRepository.findAll()) {
            list2.add(c);
        }

        Comparator<TipoVehiculo> by = (a, b) -> a.getCodigo().compareTo(b.getCodigo());

        list2.stream().sorted(by).forEachOrdered((c) -> list.add(
                new TipoVehiculoDto(c.getId(), c.getCodigo(), c.getNombre(), c.isRequiereRemolque(), c.isActivo())));

        return list;
    }

    @CrossOrigin
    @RequestMapping("/unidades-x-producto-x-cliente")
    public List<UoMDto> getUnidadesPorClientePorProducto(
            @RequestParam(value = "cliente", defaultValue = "") String cliente,
            @RequestParam(value = "producto", defaultValue = "") String producto) {

        Producto p = productoRepository.findByClienteCodigoAndCodigo(cliente, producto);

        List<UoMDto> l = new ArrayList<UoMDto>();
        if (p != null) {
            p.getProductoUnidadAssociation().forEach((u) -> {
                l.add(new UoMDto(0L, u.getUnidad().getCodigo(), u.getUnidad().getCodigo(), 1, true));
            });
        }

        return l;
    }

    @CrossOrigin
    @RequestMapping("/estados-inventario-x-producto-x-cliente")
    public List<EstadoInventarioDto> getEstadosInventarioPorClientePorProducto(
            @RequestParam(value = "cliente", defaultValue = "") String codigo,
            @RequestParam(value = "producto", defaultValue = "") String producto) {

        List<EstadoInventarioDto> list = new ArrayList<EstadoInventarioDto>(0);
        /*
         * Cliente cliente = clienteRepository.findByCodigoIgnoringCase(codigo);
         * if (cliente != null) {
         * 
         * Comparator<EstadoInventario> by = (a, b) ->
         * Integer.compare(a.getOrdinal(), b.getOrdinal());
         * 
         * cliente.getEstadosInventario().stream().sorted(by)
         * .forEachOrdered((c) -> list.add(new
         * EstadoInventarioViewModel(c.getId(), c.getCodigo(), c.getNombre(),
         * c.getOrdinal(), c.isActivo()))); }
         */
        return list;
    }

    @CrossOrigin
    @RequestMapping("/tipos-novedades-ingreso-x-producto-x-cliente")
    public List<TipoNovedadIngresoInventarioDto> getTiposNovedadesIngresoInventarioPorClientePorProducto(
            @RequestParam(value = "cliente", defaultValue = "") String codigo,
            @RequestParam(value = "producto", defaultValue = "") String producto) {
        List<TipoNovedadIngresoInventarioDto> list = new ArrayList<TipoNovedadIngresoInventarioDto>(0);

        List<TipoNovedadIngresoInventario> list2 = new ArrayList<>();

        for (TipoNovedadIngresoInventario c : tipoNovedadIngresoInventarioRepository.findAll()) {
            list2.add(c);
        }

        Comparator<TipoNovedadIngresoInventario> by = (a, b) -> Integer.compare(a.getOrdinal(), b.getOrdinal());

        list2.stream().sorted(by).forEachOrdered((c) -> list.add(new TipoNovedadIngresoInventarioDto(c.getId(),
                c.getCodigo(), c.getNombre(), c.getOrdinal(), c.isActivo())));

        return list;
    }

    @CrossOrigin
    @RequestMapping(value = "/ordenes-ingreso/save", method = RequestMethod.POST)
    @Transactional
    public RespuestaSaveOrdenIngreso saveOrdenesDeIngreso(@RequestBody OrdenIngreso[] orden) {
        if (orden.length > 0) {
            OrdenIngreso o = orden[0];

            o.Recepcionar();
            ordenRepository.save(o);
            for (LineaOrdenIngreso lo : o.productos) {
                lo.idOrden = o.id;
                lo.setCantidadEsperada(lo.getCantidadEsperada() == null ? 0 : lo.getCantidadEsperada());
                lo.setCantidadRecibida(lo.getCantidadRecibida() == null ? 0 : lo.getCantidadRecibida());
                lo.setCantidadAverias(lo.getCantidadAverias() == null ? 0 : lo.getCantidadAverias());
                lo.setCantidadSobrante(lo.getCantidadSobrante() == null ? 0 : lo.getCantidadSobrante());
                lo.setCantidadFaltantes(lo.getCantidadFaltantes() == null ? 0 : lo.getCantidadFaltantes());
                lineaOrdenRepository.save(lo);
            }

            System.out.println(o.toString());
            return new RespuestaSaveOrdenIngreso(true, o.id, o.hash);
        }
        return new RespuestaSaveOrdenIngreso(false, null, null);
    }

    @CrossOrigin
    @RequestMapping("/ordenes-ingreso")
    public OrdenIngreso getOrdenIngresoPorIdPorToken(@RequestParam(value = "id", defaultValue = "") String id,
            @RequestParam(value = "token", defaultValue = "") String token) {

        OrdenIngreso o = ordenRepository.findOneByIdAndHash(Integer.parseInt(id), Integer.parseInt(token));

        for (LineaOrdenIngreso lo : o.productos) {
            Producto p = productoRepository.findByClienteCodigoAndCodigo(o.getCliente(), lo.getProducto());
            lo.setNombreProducto(p.getNombreLargo());

            // lo.setCantidadEsperada(lo.getCantidadEsperada()==null?0:lo.getCantidadEsperada());
            // lo.setCantidadRecibida(lo.getCantidadRecibida()==null?0:lo.getCantidadRecibida());
            // lo.setCantidadAverias(lo.getCantidadAverias()==null?0:lo.getCantidadAverias());
            // lo.setCantidadSobrante(lo.getCantidadSobrante()==null?0:lo.getCantidadSobrante());
            // lo.setCantidadFaltantes(lo.getCantidadFaltantes()==null?0:lo.getCantidadFaltantes());
        }

        return o;
    }

    protected FileOutputStream createOutputStream(File imageFile) throws FileNotFoundException {

        // if (o.getImagenIngreso() != null) {
        /*
         * String imageDataBytes =
         * o.getImagenIngreso().substring(o.getImagenIngreso().indexOf( ",") +
         * 1);
         * 
         * final Path destination = Paths.get("C:\\ab\\test.jpg"); try(final
         * InputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(
         * imageDataBytes.getBytes()))){ java.nio.file.Files.copy(in,
         * destination,StandardCopyOption.REPLACE_EXISTING); } catch
         * (IOException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); }
         * 
         * System.out.println(orden[0].toString());
         */
        // }

        return new FileOutputStream("C:\\ab\\test.jpg");
    }
}
