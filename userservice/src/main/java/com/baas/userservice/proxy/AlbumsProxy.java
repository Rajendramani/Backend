package com.baas.userservice.proxy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.baas.userservice.model.AlbumResponseModel;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;


@FeignClient(name = "albums-ws")
public interface AlbumsProxy {

	
	//@CircuitBreaker(name = "albums-ws", fallbackMethod = "getAlbumsFallback")
	@GetMapping("/albums/{id}")
	@CircuitBreaker(name = "albums-ws", fallbackMethod = "getAlbumsFallback")
	public List<AlbumResponseModel> userAlbums(@PathVariable String id);

	default List<AlbumResponseModel> getAlbumsFallback(String id, Throwable ex) {
		System.out.println("ID:" + id);
		System.out.println("Exception: " + ex);
		return new ArrayList<>();
	}
} 
