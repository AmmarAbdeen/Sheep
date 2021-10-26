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


@Table(name = "SHEEP_MOVEMENT")
public class SheepMovement {
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CREATION_Date")
	private LocalDateTime creationDate;
	
	@CreationTimestamp
	@Column(name = "MOVEMENT_Date")
	private LocalDateTime movementDate;
	
	@Expose
	@Column(name = "DURATION", unique = true,nullable = false)
	private String duration;
	
	@Expose
	@Column(name = "NOTES", unique = true,nullable = false)
	private String notes;

	@Expose
	@Column(name = "DESCRIPTION",  nullable = false)
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "SHEEP_ID")
	private Sheep sheep;
	
	@ManyToOne
	@JoinColumn(name = "LAMBS_ID")
	private Lambs lambs;
	
	
	@ManyToOne
	@JoinColumn(name = "PLACES_ID")
	private Places places;

}
