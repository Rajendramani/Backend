package com.baas.userservice.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baas.userservice.model.CreateUserRequestModel;
import com.baas.userservice.model.User;
import com.baas.userservice.service.UserService;
import com.baas.userservice.shared.UserDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
public class UserController {
	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);

	@Autowired
	Environment env;

	@Autowired
	UserService userService;

	@GetMapping("/check/status")
	public String checkStatus() {
		log.info("Check status");
		return "Working on port " + env.getProperty("local.server.port");

	}

	@PostMapping("/create")
	public String createUser(@RequestBody CreateUserRequestModel user) {
		log.info("Create User");
		log.debug("User :{}", user);
		ModelMapper mdlmapper = new ModelMapper();
		UserDto userDto = mdlmapper.map(user, UserDto.class);
		userService.createUser(userDto);
		return "user create sucessfully";
	}
}
