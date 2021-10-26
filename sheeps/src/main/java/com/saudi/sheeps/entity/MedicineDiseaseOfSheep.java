package com.saudi.sheeps.entity;

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

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder


@Table(name = "MEDICINE_DISEASE_OF_SHEEP")
public class MedicineDiseaseOfSheep {
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CREATION_Date")
	private LocalDateTime creationDate;

	@CreationTimestamp
	@Column(name = "MEDICINE_ONSET")
	private LocalDateTime medicineOnset;
	
	@CreationTimestamp
	@Column(name = "END_OF_MEDICINE")
	private LocalDateTime endOfMedicine;
	
	@Expose
	@Column(name = "QUANTITY", unique = true,nullable = false)
	private String quantity;
	
	@Expose
	@Column(name = "DURATION", unique = true, nullable = false)
	private Long duration;
	
	@Expose
	@Column(name = "DESCRIPTION",  nullable = false)
	private String description;	
	
	@ManyToOne
	@JoinColumn(name = "SHEEP_ID")
	private Sheep sheep;

	@ManyToOne
	@JoinColumn(name = "DISEASE_ID")
	private Disease disease;
	
	@ManyToOne
	@JoinColumn(name = "MEDICINE_ID")
	private Medicine medicine;

	

}
