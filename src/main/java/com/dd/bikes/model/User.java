package com.dd.bikes.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user_details")
@Entity
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	@NotBlank(message = "Name may not be empty")
	@Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
	private String name;

	@NotBlank(message = "Username is mandatory")
	@Column(unique = true)
	private String username;

	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "Password is mandatory")
	@Length(min = 6, message = "Password must be at least 6 characters long")
	private String password;

	private String gender;

	@NotNull(message = "Mobile number is mandatory")
	@Digits(integer = 10, fraction = 0, message = "Mobile number should be 10 digits")
	private Long mobile;

	@NotBlank(message = "Birthdate is mandatory")
	private String birthdate;

	@NotBlank(message = "Address is mandatory")
	private String address;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, updatable = false)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", nullable = false)
	private Date modifiedDate;

	@PrePersist
	protected void onCreate() {
		createdDate = new Date();
		modifiedDate = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		modifiedDate = new Date();
	}

}
