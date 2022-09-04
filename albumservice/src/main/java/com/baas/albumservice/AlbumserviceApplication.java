package com.baas.albumservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@SpringBootApplication
@EnableEurekaClient
public class AlbumserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlbumserviceApplication.class, args);
	}

}
