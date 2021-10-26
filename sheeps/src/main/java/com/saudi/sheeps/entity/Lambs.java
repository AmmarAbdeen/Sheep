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
@Table(name = "LAMBS")
public class Lambs {
	

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
	@Column(name = "COLOR", nullable = false)
	private String color;

	//@CreationTimestamp
	@Column(name = "BIRTHDATE", nullable = false)
	private LocalDate birthDate;
	
	@Expose
	@Column(name = "BIRTH_WEIGHT", nullable = false)
	private String birthWeight;
	
	@Expose
	@Column(name = "BIRTH_WEIGHT_SHEEP")
	private String birthWeightSheep;

	@Expose
	@Column(name = "WEANING_WEIGHT")
	private String weaningWeight;
	
	@Expose
	@Column(name = "SELLING_WEIGHT")
	private String	sellingWeight;
	
	@Expose
	@Column(name = "NAMED")
	private String named;

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

	
	//@CreationTimestamp
	@Column(name = "MATING_DATE", nullable = false)
	private LocalDate matingDate;
	

	@Expose
	@Column(name = "TYPE", nullable = false)
	private String type;

	@Expose
	@Column(name = "NOTES")
	private String notes;
	
	@ManyToOne
	@JoinColumn(name = "SHEEP_ID")
	private Sheep sheep;
	
	@ManyToOne
	@JoinColumn(name = "PLACES_ID")
	private Places places;

}
