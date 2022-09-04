package com.baas.userservice.service.data;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.baas.userservice.model.AlbumResponseModel;

public interface AlbumsServiceClient {
	public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}
