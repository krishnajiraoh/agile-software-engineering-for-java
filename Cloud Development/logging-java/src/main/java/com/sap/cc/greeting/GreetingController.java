package com.sap.cc.greeting;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
public class GreetingController {

	private GreetingService service;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public GreetingController(GreetingService service) {
		this.service = service;
	}

	@GetMapping("/hello")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		MDC.put("path", name);
		String greeting = getGreeting("Hello", name);
		MDC.clear();
		return greeting;
	}

	@GetMapping("/howdy")
	public String deprecatedGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		MDC.put("path", "howdy");
		logger.warn("Deprecated endpoint used.");
		String greeting = getGreeting("Howdy", name);
		MDC.clear();
		return greeting;
	}

	private String getGreeting(String greeting, String name) {
		try {
			return service.createGreetingMessage(greeting, name);
		} catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		} finally {
		}
	}

}
