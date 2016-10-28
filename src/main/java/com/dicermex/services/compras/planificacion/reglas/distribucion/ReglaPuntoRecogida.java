package com.dicermex.services.compras.planificacion.reglas.distribucion;

import com.dicermex.services.compras.planificacion.reglas.Regla;
import com.tacticlogistics.application.dto.common.MensajesDTO;
import com.tacticlogistics.domain.model.oms.Orden;

public class ReglaPuntoRecogida implements Regla<Orden> {

	@Override
	public MensajesDTO<?> validar(Orden object) {
		MensajesDTO<?> mensajes = new MensajesDTO<>();
		
		return mensajes;
	}

}
