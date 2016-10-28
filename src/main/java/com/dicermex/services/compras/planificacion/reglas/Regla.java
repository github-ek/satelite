package com.dicermex.services.compras.planificacion.reglas;

import com.tacticlogistics.application.dto.common.MensajesDTO;

public interface Regla<T> {
	MensajesDTO<?> validar(T object);
}
