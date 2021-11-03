package com.saudi.sheeps.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder

@NoArgsConstructor
@AllArgsConstructor
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
	@Column(name = "NAME",nullable = false)
	private String name;

	@Expose
	@Column(name = "COST",nullable = false)
	private String cost;
	
	@Expose
	@Column(name = "QUANTITY",nullable = false)
	private Double quantity;

	@Column(name = "EXPIRY_Date")
	private LocalDate expiryDate;
	
	@Expose
	@Column(name = "DESCRIPTION",  nullable = false)
	private String description;


}
