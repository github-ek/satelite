package com.tacticlogistics.presentation.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tacticlogistics.application.dto.common.MensajesDTO;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2511580590419334260L;

	public BadRequestException(String msg) {
		super(msg);
	}
	
	public BadRequestException(MensajesDTO<?> msg) {
		super(msg.toString());
	}
}