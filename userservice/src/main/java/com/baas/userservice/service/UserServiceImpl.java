package com.baas.userservice.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baas.userservice.controller.UserController;
import com.baas.userservice.entity.UserEntity;
import com.baas.userservice.repository.UserRepository;
import com.baas.userservice.shared.UserDto;

@Service
public class UserServiceImpl implements UserService {

	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDto createUser(UserDto userDetails) {
		log.debug("UserDto :{}", userDetails.toString());
		userDetails.setEncryptedPassword("TEST123");
		ModelMapper mdlmapper = new ModelMapper();
		UserEntity userEntity = mdlmapper.map(userDetails, UserEntity.class);
		log.debug("UserEntity :{}", userEntity.toString());
		userRepository.save(userEntity);
		return null;
	}

}
