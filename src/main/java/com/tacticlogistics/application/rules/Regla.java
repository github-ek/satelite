package com.tacticlogistics.application.rules;

import com.tacticlogistics.application.dto.common.MensajesDTO;

public interface Regla<T> {
	MensajesDTO<?> validar(T object);
}
