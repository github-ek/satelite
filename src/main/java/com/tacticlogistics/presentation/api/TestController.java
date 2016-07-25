package com.tacticlogistics.presentation.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController()
@RequestMapping("/test")
public class TestController {
	@RequestMapping(value = "/tactic/Tasks", produces = MediaType.APPLICATION_XML_VALUE,method = RequestMethod.POST)
	public String Task(@RequestBody String model,HttpEntity<String> httpEntity) {
		System.out.println(model);
		return model;
	}
}
