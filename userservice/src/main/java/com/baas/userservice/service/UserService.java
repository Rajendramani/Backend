package com.baas.userservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.baas.userservice.shared.UserDto;
import com.baas.userservice.utils.GenericResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public interface UserService extends UserDetailsService {

	public GenericResponse createUser(UserDto userDetails);

	public UserDto getUserDetailsByEmail(String email);
	
	public UserDto getUserDetailsByUserId(String userId);
}
