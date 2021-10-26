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


@Table(name = "SHEEP_DISEASE")
public class SheepDisease {
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CREATION_Date")
	private LocalDateTime creationDate;

	@CreationTimestamp
	@Column(name = "DISEASE_ONSET")
	private LocalDateTime diseaseOnset;
	
	@CreationTimestamp
	@Column(name = "END_OF_DISEASE")
	private LocalDateTime endOfDisease;
	
	@Expose
	@Column(name = "DESCRIPTION")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "SHEEP_ID")
	private Sheep sheep;

	@ManyToOne
	@JoinColumn(name = "DISEASE_ID")
	private Disease disease;

	
	

}
