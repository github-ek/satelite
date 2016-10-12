package com.tacticlogistics.clientes.dicermex.compras.planificacion.reglas;

import com.tacticlogistics.application.dto.common.MensajesDto;

public interface Regla<T> {
	MensajesDto validar(T object);
}
