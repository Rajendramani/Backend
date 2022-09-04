package com.baas.albumservice.ui.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlbumResponseModel {
	private String albumId;
	private String userId;
	private String name;
	private String description;
}
