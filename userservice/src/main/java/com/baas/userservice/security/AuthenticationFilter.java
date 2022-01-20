package com.baas.userservice.security;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.baas.userservice.model.LoginRequestModel;
import com.baas.userservice.service.UserService;
import com.baas.userservice.shared.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UserService userService;
	private Environment environment;

	@Autowired
	public AuthenticationFilter(UserService userService, Environment environment,
			AuthenticationManager authenticationManager) {
		super();
		log.info("AuthenticationFilter");
		this.userService = userService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		log.info("attemptAuthentication");
		try {
			LoginRequestModel creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
			log.debug("LoginRequestModel : {}", creds);
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (Exception e) {
			log.error("Exception : {}", e);
			throw new RuntimeException();
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		log.info("successfulAuthentication");
		String userName = ((User) authResult.getPrincipal()).getUsername();
		UserDto userDetails = userService.getUserDetailsByEmail(userName);
		log.debug("UserName : {}", userName);
		log.debug("UserDetails : {}", userDetails);

		String token = Jwts.builder().setSubject(userDetails.getUserId())
				.setExpiration(new Date(
						System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret")).compact();
		log.debug("Token : {}", token);
		response.addHeader("token", token);
		response.addHeader("userId", userDetails.getUserId());
	}

}
