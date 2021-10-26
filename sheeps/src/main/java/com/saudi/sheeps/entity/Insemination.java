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


@Table(name = "INSEMINATION")
public class Insemination {
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CREATION_Date")
	private LocalDateTime creationDate;
	
	@CreationTimestamp
	@Column(name = "MATING_DATE")
	private LocalDateTime matingDate;
	
	@Expose
	@Column(name = "STATUS", unique = true,nullable = false)
	private String status;

	@Expose
	@Column(name = "NOTES",  nullable = false)
	private String notes;
	
	@Expose
	@Column(name = "CHECKUP_COST",  nullable = false)
	private String checkupCost;
	
	@CreationTimestamp
	@Column(name = "CHECKUP_Date")
	private LocalDateTime checkupDate;
	
	@ManyToOne
	@JoinColumn(name = "SHEEP_ID")
	private Sheep sheep;
	


}
