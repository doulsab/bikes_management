package com.dd.bikes.model;

import java.util.Date;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "bikes")
@Entity
@Data
@NoArgsConstructor
public class Bike {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String model;
	private String manufacturer;
	private int year;
	private double price;
	private String bikeType;
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
