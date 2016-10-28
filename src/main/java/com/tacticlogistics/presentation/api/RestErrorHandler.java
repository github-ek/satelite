package com.tacticlogistics.presentation.api;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tacticlogistics.application.dto.common.MensajesDTO;
import com.tacticlogistics.domain.model.common.SeveridadType;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestErrorHandler {
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MensajesDTO<?> processValidationError(MethodArgumentNotValidException ex) {
		log.debug("Handling form validation error");

		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		return processFieldErrors(fieldErrors);
	}

	private MensajesDTO<?> processFieldErrors(List<FieldError> fieldErrors) {
		MensajesDTO<?> dto = new MensajesDTO<>();

		for (FieldError fieldError : fieldErrors) {
			String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
			log.debug("Adding error message: {} to field: {}", localizedErrorMessage, fieldError.getField());
			dto.add(SeveridadType.ERROR, localizedErrorMessage, fieldError.getObjectName(), fieldError.getField(),
					fieldError.getRejectedValue());
		}

		return dto;
	}

	private String resolveLocalizedErrorMessage(FieldError fieldError) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

		// If a message was not found, return the most accurate field error code
		// instead.
		// You can remove this check if you prefer to get the default error
		// message.
		boolean debug = true;
		if (debug) {
			if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
				String[] fieldErrorCodes = fieldError.getCodes();
				localizedErrorMessage = fieldErrorCodes[0];
			}
		}
		return localizedErrorMessage;
	}
}
