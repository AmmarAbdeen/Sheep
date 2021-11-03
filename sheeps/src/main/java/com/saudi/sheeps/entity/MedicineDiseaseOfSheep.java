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
@Table(name = "MEDICINE_DISEASE_OF_SHEEP")
public class MedicineDiseaseOfSheep {
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CREATION_Date")
	private LocalDateTime creationDate;

	@Column(name = "MEDICINE_ONSET")
	private LocalDate medicineOnset;
	
	@Column(name = "END_OF_MEDICINE")
	private LocalDate endOfMedicine;
	
	@Expose
	@Column(name = "QUANTITY",nullable = false)
	private Double quantity;
	
	@Expose
	@Column(name = "DISEASE",nullable = false)
	private String disease;
	
	@Expose
	@Column(name = "DESCRIPTION",  nullable = false)
	private String description;	
	
	@ManyToOne
	@JoinColumn(name = "SHEEP_ID")
	private Sheep sheep;
	
	@ManyToOne
	@JoinColumn(name = "MEDICINE_ID")
	private Medicine medicine;

	

}
