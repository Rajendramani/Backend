package com.baas.userservice.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baas.userservice.controller.UserController;
import com.baas.userservice.entity.UserEntity;
import com.baas.userservice.repository.UserRepository;
import com.baas.userservice.shared.UserDto;
import com.baas.userservice.utils.GenericResponse;

@Service
public class UserServiceImpl implements UserService {

	private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public GenericResponse createUser(UserDto userDetails) {
		log.debug("UserDto :{}", userDetails.toString());
		GenericResponse genericResponse = new GenericResponse();
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		ModelMapper mdlmapper = new ModelMapper();
		UserEntity userEntity = mdlmapper.map(userDetails, UserEntity.class);
		log.debug("UserEntity :{}", userEntity.toString());
		userRepository.save(userEntity);
		if (userEntity != null) {
			genericResponse.setStatus(true);
			genericResponse.setMessage("User details save successfully");
			genericResponse.setData(userEntity);
		} else {
			genericResponse.setStatus(false);
			genericResponse.setMessage("User details cannot save");
		}
		return genericResponse;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);
		if (userEntity == null)
			throw new UsernameNotFoundException(username);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		return new ModelMapper().map(userEntity, UserDto.class);
	}

}
