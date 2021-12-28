package com.baas.userservice.service;

import java.util.UUID;

import com.baas.userservice.shared.UserDto;

public class UserServiceImplementation implements UserService{

	@Override
	public UserDto createUser(UserDto userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		return null;
	}


}
