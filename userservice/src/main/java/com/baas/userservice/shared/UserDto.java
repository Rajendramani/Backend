package com.baas.userservice.shared;

import java.io.Serializable;

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
public class UserDto{
private String firstName;
private String lastName;
private String email;
private String password;
//private String userId;
private String encryptedPassword;
}
