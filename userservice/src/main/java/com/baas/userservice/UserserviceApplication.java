package com.baas.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@SpringBootApplication
@EnableEurekaClient
public class UserserviceApplication {

	public static void main(String[] args) {
		log.info("Application Context: UserserviceApplication");
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
