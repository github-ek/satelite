package com.tacticlogistics.presentation.api.cpr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.dto.cpr.CorteOrdenesPorFechaDto;
import com.tacticlogistics.application.dto.cpr.RealizarCorteDto;
import com.tacticlogistics.domain.model.common.SeveridadType;

@CrossOrigin
@RestController()
@RequestMapping("/cpr")
public class CortesController {

    @RequestMapping("/moviles")
    public Map<String, String> getMoviles() {
        Map<String, String> dto = new HashMap<>();
        dto.put("tactic_01", "UFW316");
        dto.put("tactic_02", "SYM743");
        dto.put("tactic_03", "WCV487");
        dto.put("tactic_04", "WEP478");
        dto.put("tactic_05", "TGW883");
        dto.put("tactic_06", "WHO811");
        dto.put("tactic_07", "WHO681");
        dto.put("tactic_08", "WCZ865");
        dto.put("tactic_09", "WHO600");
        dto.put("tactic_10", "WLM767");
        dto.put("tactic_11", "CIY589");
        dto.put("tactic_12", "SKM594");
        dto.put("tactic_13", "SOP407");
        dto.put("tactic_14", "THV229");
        dto.put("tactic_15", "TZQ889");
        dto.put("tactic_16", "VEQ774");
        dto.put("tactic_17", "WLK993");
        dto.put("tactic_18", "WFL353");
        dto.put("tactic_19", "SZV832");
        dto.put("tactic_20", "SLJ659");
        dto.put("tactic_21", "TSX904");
        dto.put("tactic_22", "WNM661");
        dto.put("tactic_23", "SZP728");
        dto.put("tactic_24", "CPA");
        dto.put("tactic_25", "XCW033");
        dto.put("tactic_26", "SPX377");
        dto.put("tactic_27", "SZY058");
        dto.put("tactic_28", "TFQ811");
        dto.put("tactic_29", "TLN655");
        dto.put("tactic_30", "SPX869");
        dto.put("tactic_31", "SNT821");
        dto.put("tactic_32", "TNH024");
        dto.put("tactic_33", "SNP447");
        dto.put("tactic_34", "TIY443");
        dto.put("tactic_35", "SIN522");
        dto.put("tactic_36", "CFR862");
        dto.put("tactic_37", "SOD968");
        dto.put("tactic_38", "TJW995");

        return dto;
    }

    @RequestMapping(value = "/moviles/save", method = RequestMethod.POST)
    public Map<String, Object> saveMovil(@RequestParam(value = "id_movil", required = true) String movilId,
            @RequestParam(value = "numero_placa", required = true) String numeroPlaca) {

        Map<String, Object> respuesta = new HashMap<>();
        MensajesDto mensajes = new MensajesDto();
        try {
            // Orden orden = this.ordenesService.saveOrden(model);
            // respuesta.put("orden", ordenesService.ordenToViewModel(orden));
            mensajes.addMensaje(SeveridadType.INFO, "");
        } catch (Exception e) {
            mensajes.addMensaje(e);
        }

        respuesta.put("mensajes", mensajes);
        return respuesta;
    }

    @RequestMapping(value = "/cortes/corte-ordenes-x-fecha", method = RequestMethod.POST)
    public Map<String, Object> solicitarEnvioCorteOrdenesPorFecha(@RequestBody CorteOrdenesPorFechaDto dto) {

        Map<String, Object> respuesta = new HashMap<>();
        MensajesDto mensajes = new MensajesDto();
        try {
            // Orden orden = this.ordenesService.saveOrden(model);
            // respuesta.put("orden", ordenesService.ordenToViewModel(orden));
            mensajes.addMensaje(SeveridadType.INFO, "");
        } catch (Exception e) {
            mensajes.addMensaje(e);
        }

        respuesta.put("mensajes", mensajes);
        return respuesta;
    }
    
    @RequestMapping(value = "/cortes/corte", method = RequestMethod.POST)
    public Map<String, Object> realizarCorte(@RequestBody RealizarCorteDto dto) {

        Map<String, Object> respuesta = new HashMap<>();
        MensajesDto mensajes = new MensajesDto();
        try {
            // Orden orden = this.ordenesService.saveOrden(model);
            // respuesta.put("orden", ordenesService.ordenToViewModel(orden));
            mensajes.addMensaje(SeveridadType.INFO, "");
        } catch (Exception e) {
            mensajes.addMensaje(e);
        }

        respuesta.put("ordenesNoConciliadas", new ArrayList<String>());
        respuesta.put("mensajes", mensajes);
        return respuesta;
    }
    
}
