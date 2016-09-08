package com.tacticlogistics.presentation.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tacticlogistics.application.dto.common.MensajesDto;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	public BadRequestException(String msg) {
		super(msg);
	}
	
	public BadRequestException(MensajesDto msg) {
		super(msg.toString());
	}
}