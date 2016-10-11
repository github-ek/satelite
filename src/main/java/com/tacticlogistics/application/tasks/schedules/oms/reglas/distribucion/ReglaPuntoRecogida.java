package com.tacticlogistics.application.tasks.schedules.oms.reglas.distribucion;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.application.tasks.schedules.oms.reglas.Regla;
import com.tacticlogistics.domain.model.ordenes.Orden;

public class ReglaPuntoRecogida implements Regla<Orden> {

	@Override
	public MensajesDto validar(Orden object) {
		MensajesDto mensajes = new MensajesDto();
		
		return mensajes;
	}

}
