package com.dicermex.services.compras.erp.alertas.dto;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class OrdenDeCompraValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {

		return OrdenDeCompraDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
		//OrdenDeCompraDTO p = (OrdenDeCompraDTO) target;
//		if (p.getCentroOperacion().length() < 0) {
//			errors.rejectValue("age", "negativevalue");
//		} else if (p.getCentroOperacion().length() > 110) {
//			errors.rejectValue("age", "too.darn.old");
//		}
	}

}
