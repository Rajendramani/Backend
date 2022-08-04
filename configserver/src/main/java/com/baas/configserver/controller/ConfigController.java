package com.baas.configserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/config")
public class ConfigController {

	@Autowired
	Environment env;

	@GetMapping("/status/check")
	public String checkStatus() {
		log.info("Check status : {}",env.getProperty("token.expiration_time"));
		return "Token expiration_time " + env.getProperty("token.expiration_time");

	}
}
