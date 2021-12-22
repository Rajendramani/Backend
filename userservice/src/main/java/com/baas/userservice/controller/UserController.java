package com.baas.userservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baas.userservice.model.User;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);

	@Autowired
	private Environment env;

	@GetMapping("/check/status")
	public String checkStatus() {
		log.info("Check status");
		return "Working on port " + env.getProperty("local.server.port");

	}

	@PostMapping("/create")
	public String createUser(@RequestBody User user) {
		log.info("Create User");
		return "user create sucessfully";
	}
}
