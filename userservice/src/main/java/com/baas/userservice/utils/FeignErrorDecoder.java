package com.baas.userservice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		switch (response.status()) {
		case 400:
			// Do something
			break;
		case 404: {
			if (methodKey.contains("userAlbums")) {
				log.debug("@FeignErrorDecoder 404");
				return new ResponseStatusException(HttpStatus.valueOf(response.status()));
			}
			break;
		}
		default:
			break;
		}
		return null;
	}

}
