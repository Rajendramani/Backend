package com.baas.userservice.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestModel {
	@NotNull(message = "Email cannot be null")
	@Email
	private String email;
	@NotNull(message = "Password cannot be null")
	@Size(min = 8, max = 16, message = "Password must be equal or greater than 8 characters and less than 16 characters")
	private String password;
}
