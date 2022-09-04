package com.baas.albumservice.data;

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
public class AlbumEntity {
	private long id;
	private String albumId;
	private String userId;
	private String name;
	private String description;
}
