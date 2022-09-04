package com.baas.userservice.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.baas.userservice.model.AlbumResponseModel;

@FeignClient(name = "albums-ws")
public interface AlbumsProxy {

	@GetMapping("/users/{id}/albums")
	public List<AlbumResponseModel> userAlbums(@PathVariable String id);

}
