package com.baas.userservice.service;

import org.springframework.stereotype.Service;

import com.baas.userservice.shared.UserDto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public interface UserService {
	public UserDto createUser(UserDto userDetails);
}
