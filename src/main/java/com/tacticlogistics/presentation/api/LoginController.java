package com.tacticlogistics.presentation.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.crm.ClienteDto;
import com.tacticlogistics.application.dto.geo.CiudadDto;
import com.tacticlogistics.application.dto.seguridad.RespuestaLoginDto;
import com.tacticlogistics.application.dto.seguridad.UserDto;
import com.tacticlogistics.application.dto.tms.TipoVehiculoDto;
import com.tacticlogistics.application.dto.tms.TransportadoraDto;
import com.tacticlogistics.application.dto.wms.BodegaDto;
import com.tacticlogistics.application.dto.wms.EstadoInventarioDto;
import com.tacticlogistics.application.dto.wms.ProductoDto;
import com.tacticlogistics.application.dto.wms.UoMDto;
import com.tacticlogistics.domain.model.crm.Cliente;
import com.tacticlogistics.domain.model.crm.ClienteCiudadAssociation;
import com.tacticlogistics.domain.model.geo.Ciudad;
import com.tacticlogistics.domain.model.seguridad.Usuario;
import com.tacticlogistics.domain.model.seguridad.UsuarioBodegaAssociation;
import com.tacticlogistics.domain.model.tms.TipoVehiculo;
import com.tacticlogistics.domain.model.tms.Transportadora;
import com.tacticlogistics.domain.model.wms.Producto;
import com.tacticlogistics.infrastructure.persistence.crm.ClienteRepository;
import com.tacticlogistics.infrastructure.persistence.geo.CiudadRepository;
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
    TransportadoraRepository transportadoraRepository;

    @Autowired
    TipoVehiculoRepository tipoVehiculoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;


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
                    Ciudad ciudad = uba.getBodega().getDireccion().getCiudad();

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
