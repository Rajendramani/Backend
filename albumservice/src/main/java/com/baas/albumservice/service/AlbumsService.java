package com.baas.albumservice.service;

import java.util.List;

import com.baas.albumservice.data.AlbumEntity;

public interface AlbumsService {
    List<AlbumEntity> getAlbums(String userId);
}
