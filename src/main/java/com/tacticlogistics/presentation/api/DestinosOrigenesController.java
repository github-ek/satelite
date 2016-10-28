package com.tacticlogistics.presentation.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.common.MensajesDTO;
import com.tacticlogistics.application.dto.crm.DestinoDto;
import com.tacticlogistics.application.services.crm.DestinatariosApplicationService;
import com.tacticlogistics.application.services.crm.DestinosApplicationService;
import com.tacticlogistics.application.services.geo.CiudadesApplicationService;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.domain.model.crm.Destino;

@CrossOrigin
@RestController()
@RequestMapping("/destinos_origenes")
public class DestinosOrigenesController {
    @Autowired
    private DestinatariosApplicationService destinatarioService;
    @Autowired
    private CiudadesApplicationService ciudadesService;
    @Autowired
    private DestinosApplicationService destinosOrigenesService;

    @RequestMapping("/segmentos-x-cliente")
    public List<Object> getAllSegmentoPorCliente(
            @RequestParam(value = "id_cliente", required = true) Integer clienteId) {
        List<Object> list = new ArrayList<>();

        try {
            list = destinatarioService.findCanalesPorCliente(clienteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/destinatarios_remitentes-x-cliente-x-segmento")
    public List<Object> getAllDestinatarioPorClientePorCanal(
            @RequestParam(value = "id_cliente", required = true) Integer clienteId,
            @RequestParam(value = "id_segmento", required = true) Integer canalId) {
        List<Object> list = new ArrayList<>();

        try {
            list = destinatarioService
                    .findAllDestinatarioPorClientePorCanalPorServicio(clienteId, canalId, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("/ciudades")
    public List<Object> getAllCiudad() {
        List<Object> list = new ArrayList<>();

        try {
            list = ciudadesService.findAllCiudad();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // -- Save
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Map<String, Object> save(@RequestBody DestinoDto dto) {
        Map<String, Object> respuesta = new HashMap<>();
        MensajesDTO<?> mensajes = new MensajesDTO<>();
        try {
            Destino model = this.destinosOrigenesService.save(dto);
            respuesta.put("destinoOrigen", this.destinosOrigenesService.destinoOrigenToDto(model));
            mensajes.add(SeveridadType.INFO, "");
        } catch (Exception e) {
            mensajes.add(e);
        }

        respuesta.put("mensajes", mensajes);
        return respuesta;
    }
}
