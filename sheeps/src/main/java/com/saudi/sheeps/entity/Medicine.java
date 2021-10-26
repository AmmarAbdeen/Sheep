package com.saudi.sheeps.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.google.gson.annotations.Expose;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder


@Table(name = "MEDICINE")
public class Medicine {
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CREATION_Date")
	private LocalDateTime creationDate;


	@Expose
	@Column(name = "NAME", unique = true,nullable = false)
	private String name;

	@Expose
	@Column(name = "COST", unique = true,nullable = false)
	private String cost;
	
	@Expose
	@Column(name = "QUANTITY", unique = true,nullable = false)
	private String quantity;

	@CreationTimestamp
	@Column(name = "EXPIRY_Date")
	private LocalDateTime expiryDate;
	
	@Expose
	@Column(name = "DESCRIPTION",  nullable = false)
	private String description;


}
