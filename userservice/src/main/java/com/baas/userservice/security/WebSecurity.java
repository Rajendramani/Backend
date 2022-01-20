package com.baas.userservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.baas.userservice.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	Environment environment;
	
	@Autowired
	UserService usersService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("configure websecurity");
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/users/**")
		.hasIpAddress(environment.getProperty("gatewayIP"))
		.and()
		.addFilter(getAuthenticationFilter());
		http.headers().frameOptions().disable();
		log.debug("HTTP : {}", http);
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		log.info("getAuthenticationFilter");
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, environment, authenticationManager());
		//authenticationFilter.setAuthenticationManager(authenticationManager());
		authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		return authenticationFilter;
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("configure");
		auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);	
	}
}
