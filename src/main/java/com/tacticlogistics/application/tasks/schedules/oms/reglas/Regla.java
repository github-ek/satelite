package com.tacticlogistics.application.tasks.schedules.oms.reglas;

import com.tacticlogistics.application.dto.common.MensajesDto;

public interface Regla<T> {
	MensajesDto validar(T object);
}
