package com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.almacenamiento;

import com.tacticlogistics.application.dto.common.MensajesDto;
import com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas.Regla;
import com.tacticlogistics.domain.model.ordenes.Orden;

public class ReglaBodegaOrigen implements Regla<Orden> {

	@Override
	public MensajesDto validar(Orden object) {
		MensajesDto mensajes = new MensajesDto();
		
		return mensajes;
	}

}
