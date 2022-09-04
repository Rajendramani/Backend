package com.baas.albumservice.io.controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baas.albumservice.data.AlbumEntity;
import com.baas.albumservice.service.AlbumsService;
import com.baas.albumservice.ui.model.AlbumResponseModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users/{id}/albums")
@Slf4j
public class AlbumsController {

	@Autowired
	AlbumsService albumsService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, })
	public List<AlbumResponseModel> userAlbums(@PathVariable String id) {

		List<AlbumResponseModel> returnValue = new ArrayList<>();

		List<AlbumEntity> albumsEntities = albumsService.getAlbums(id);

		if (albumsEntities == null || albumsEntities.isEmpty()) {
			return returnValue;
		}

		Type listType = new TypeToken<List<AlbumResponseModel>>() {
		}.getType();

		returnValue = new ModelMapper().map(albumsEntities, listType);
		log.info("Returning " + returnValue.size() + " albums");
		return returnValue;
	}
}