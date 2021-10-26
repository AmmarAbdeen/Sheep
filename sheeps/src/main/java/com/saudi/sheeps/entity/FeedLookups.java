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
@Table(name = "FEED_LOOKUPS")
public class FeedLookups {
	
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
	@Column(name = "BENEFIT_OF_IT",nullable = false)
	private String benefitOfIt;
	
	@Expose
	@Column(name = "CALCIUM",nullable = false)
	private String calcium;
	
	@Expose
	@Column(name = "DRY_AMOUNT",nullable = false)
	private String dryAmount;
	
	@Expose
	@Column(name = "PHOSPHOROUS",nullable = false)
	private String phosphorous;
	
	@Expose
	@Column(name = "PROTEIN",nullable = false)
	private String protein;
	
	@Expose
	@Column(name = "ENERGY_MJ",nullable = false)
	private String energyMj;

}
