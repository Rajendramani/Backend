package com.baas.userservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, name = "Id")
	private Long id;
	@Column(nullable = false, length = 50, name = "FirstName")
	private String firstName;
	@Column(nullable = false, length = 50, name = "LastName")
	private String lastName;
	@Column(nullable = false, length = 120, unique = true, name = "Email")
	private String email;
	@Column(nullable = false, length = 50, name = "UserId")
	private String userId;
	@Column(nullable = false, unique = true, name = "EncryptedPassword")
	private String encryptedPassword;

}
