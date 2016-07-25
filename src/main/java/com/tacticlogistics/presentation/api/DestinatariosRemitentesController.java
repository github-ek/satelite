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

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.dto.crm.DestinatarioRemitenteDto;
import com.tacticlogistics.application.dto.oms.OmsDestinatarioRemitenteDto;
import com.tacticlogistics.application.services.crm.DestinatariosRemitentesApplicationService;
import com.tacticlogistics.domain.model.common.SeveridadType;
import com.tacticlogistics.domain.model.crm.DestinatarioRemitente;

@CrossOrigin
@RestController()
@RequestMapping("/destinatarios_remitentes")
public class DestinatariosRemitentesController {
	@Autowired
	private DestinatariosRemitentesApplicationService destinatariosRemitentesService;
	
	@RequestMapping("/tipos_identificacion")
	public List<Object> getAllTipoIdentificacion() {
		List<Object> list = new ArrayList<>();

		try {
			list = destinatariosRemitentesService.findAllTipoIdentificacion();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping("/segmentos")
	public List<Object> getAllSegmento() {
		List<Object> list = new ArrayList<>();

		try {
			list = destinatariosRemitentesService.findCanales();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ----------------------------------------------------------------------------------------------------------------
    // -- Get One
    // ----------------------------------------------------------------------------------------------------------------
    @RequestMapping(path = "/{id}", method = { RequestMethod.GET, RequestMethod.POST })
    public OmsDestinatarioRemitenteDto getPorId(
            @PathVariable Integer id) {
        OmsDestinatarioRemitenteDto dto = null;

        try {
            dto = destinatariosRemitentesService.findOnePorId(id);
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
	public Map<String, Object> save(@RequestBody DestinatarioRemitenteDto dto) {
		Map<String, Object> respuesta = new HashMap<>();
		MensajesDto mensajes = new MensajesDto();
		try {
			DestinatarioRemitente model = this.destinatariosRemitentesService.save(dto);
			respuesta.put("destinatarioRemitente", this.destinatariosRemitentesService.destinatarioRemitenteToDto(model));
			mensajes.addMensaje(SeveridadType.INFO, "");
		} catch (Exception e) {
			mensajes.addMensaje(e);
		}

		respuesta.put("mensajes", mensajes);
		return respuesta;
	}
}
