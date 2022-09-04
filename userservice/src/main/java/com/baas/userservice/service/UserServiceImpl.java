package com.baas.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.naming.directory.AttributeInUseException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.baas.userservice.controller.UserController;
import com.baas.userservice.entity.UserEntity;
import com.baas.userservice.model.AlbumResponseModel;
import com.baas.userservice.proxy.AlbumsProxy;
import com.baas.userservice.repository.UserRepository;
import com.baas.userservice.shared.UserDto;
import com.baas.userservice.utils.GenericResponse;

import feign.FeignException.FeignClientException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	Environment env;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	AlbumsProxy albumsProxy;

	@Override
	public GenericResponse createUser(UserDto userDto) {
		log.debug("UserDto :{}", userDto.toString());
		GenericResponse genericResponse = new GenericResponse();
		userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		ModelMapper mdlmapper = new ModelMapper();
		// UserEntity userEntity = mdlmapper.map(userDto, UserEntity.class);
		UserEntity userEntity = new UserEntity().builder().email(userDto.getEmail())
				.encryptedPassword(userDto.getEncryptedPassword()).firstName(userDto.getFirstName())
				.lastName(userDto.getLastName()).userId(userDto.getUserId()).build();
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
		log.info("loadUserByUsername");
		UserEntity userEntity = userRepository.findByEmail(username);
		log.debug("UserEntity : {}", userEntity);
		if (userEntity == null)
			throw new UsernameNotFoundException(username);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		log.info("getUserDetailsByEmail");
		UserEntity userEntity = userRepository.findByEmail(email);
		log.debug("UserEntity : {}", userEntity);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);
		return new ModelMapper().map(userEntity, UserDto.class);
	}

	@Override
	public UserDto getUserDetailsByUserId(String userId) {
		log.info("getUsedId");
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (null == userEntity)
			throw new UsernameNotFoundException("User Not Found");
		UserDto dto = new ModelMapper().map(userEntity, UserDto.class);
		String url = String.format(env.getProperty("albums.url"), userId);
		log.debug("URL: {}", url);
		// RestTemplate Communication
		/*
		 * ResponseEntity<List<AlbumResponseModel>> albumlistResponse =
		 * restTemplate.exchange(url, HttpMethod.GET, null, new
		 * ParameterizedTypeReference<List<AlbumResponseModel>>() { });
		 * 
		 * List<AlbumResponseModel> albumList = albumlistResponse.getBody();
		 */

		// Feign Client
		try {
			List<AlbumResponseModel> albumList = albumsProxy.userAlbums(userId);
			log.debug("albumList: {}", albumList);
			dto.setAlbums(albumList);
		} catch (FeignClientException e) {
			// TODO: handle exception
			log.error("FeignException: {}", e);
		}
		return dto;
	}

}
