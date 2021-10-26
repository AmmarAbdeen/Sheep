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


@Table(name = "DISEASE")
public class Disease {
	
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
	@Column(name = "DESCRIPTION",  nullable = false)
	private String description;

}
