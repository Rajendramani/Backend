package com.baas.userservice.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baas.userservice.model.CreateUserRequestModel;
import com.baas.userservice.service.UserService;
import com.baas.userservice.shared.UserDto;
import com.baas.userservice.utils.GenericResponse;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	Environment env;

	@Autowired
	UserService userService;

	@GetMapping("/check/status")
	public String checkStatus() {
		log.info("UserController: Check status");
		return "Working on port " + env.getProperty("local.server.port");

	}

	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE }, 
			value = "/create")
	public ResponseEntity<GenericResponse> createUser(@RequestBody CreateUserRequestModel user) {
		//log.info("Create User");
		//log.debug("User :{}", user);
		GenericResponse genericResponse = new GenericResponse();
		ModelMapper mdlmapper = new ModelMapper();
		HttpStatus httpStatus = null;
		try {
			genericResponse = userService.createUser(mdlmapper.map(user, UserDto.class));
			if (genericResponse.isStatus()) {
				httpStatus = httpStatus.CREATED;
			} else {
				httpStatus = httpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO: handle exception
			//log.error("Error : {}", e.toString());
		}
		return new ResponseEntity<GenericResponse>(genericResponse, httpStatus);
	}
}
