package com.tacticlogistics.presentation.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tacticlogistics.application.dto.common.MensajesDTO;
import com.tacticlogistics.application.dto.crm.DestinatarioDto;
import com.tacticlogistics.application.dto.oms.OmsDestinatarioDto;
import com.tacticlogistics.application.services.crm.DestinatariosApplicationService;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.domain.model.crm.Destinatario;

@CrossOrigin
@RestController()
@RequestMapping("/destinatarios_remitentes")
public class DestinatariosRemitentesController {
	@Autowired
	private DestinatariosApplicationService destinatariosService;
	
	@RequestMapping("/tipos_identificacion")
	public List<Object> getAllTipoIdentificacion() {
		List<Object> list = new ArrayList<>();

		try {
			list = destinatariosService.findAllTipoIdentificacion();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping("/segmentos")
	public List<Object> getAllSegmento() {
		List<Object> list = new ArrayList<>();

		try {
			list = destinatariosService.findCanales();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ----------------------------------------------------------------------------------------------------------------
    // -- Get One
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping(path = "/{id}", method = { RequestMethod.GET, RequestMethod.POST })
    public OmsDestinatarioDto getPorId(
            @PathVariable Integer id) {
        OmsDestinatarioDto dto = null;

        try {
            dto = null;
        } catch (Exception e) {
            // TODO e.printStackTrace()
            e.printStackTrace();
        }
        return dto;
    }

	// ----------------------------------------------------------------------------------------------------------------
	// -- Save
	// ----------------------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Map<String, Object> save(@RequestBody DestinatarioDto dto) {
		Map<String, Object> respuesta = new HashMap<>();
		MensajesDTO<?> mensajes = new MensajesDTO<>();
		try {
			Destinatario model = this.destinatariosService.save(dto);
			respuesta.put("destinatario", this.destinatariosService.destinatarioToDto(model));
			mensajes.add(SeveridadType.INFO, "");
		} catch (Exception e) {
			mensajes.add(e);
		}

		respuesta.put("mensajes", mensajes);
		return respuesta;
	}
}
