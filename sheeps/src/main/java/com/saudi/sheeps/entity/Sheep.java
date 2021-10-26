package com.saudi.sheeps.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "SHEEP")
public class Sheep {
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Column(name = "CREATION_Date")
	private LocalDateTime creationDate;

	@Expose
	@Column(name = "CODE", nullable = false)
	private Long code;

	@Expose
	@Column(name = "COLOR",  nullable = false)
	private String color;

	//@CreationTimestamp
	@Column(name = "BIRTHDATE")
	private LocalDate birthDate;
		
	//@CreationTimestamp
	@Column(name = "ARRIVALDATE")
	private LocalDate arrivalDate;

	@Expose
	@Column(name = "NAMED")
	private String named;

	@Expose
	@Column(name = "WEIGHT", nullable = false)
	private String weight;

	@Expose
	@Column(name = "shape")
	private String shape;

	@Expose
	@Column(name = "STATUS", nullable = false)
	private String status;

	@Expose
	@Column(name = "ADVANTAGES")
	private String advantages;

	@Expose
	@Column(name = "DISADVANTAGES")
	private String disadvantages;

	@Expose
	@Column(name = "TYPE", nullable = false)
	private String type;

	@Expose
	@Column(name = "NOTES")
	private String notes;
	
	@ManyToOne
	@JoinColumn(name = "PLACES_ID")
	private Places places;
	

}
