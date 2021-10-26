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


@Table(name = "PAYMENTS")
public class Payments {
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CREATION_Date")
	private LocalDateTime creationDate;
	
	@Expose
	@Column(name = "AMOUNT",  nullable = false)
	private String amount;
	
	@Expose
	@Column(name = "NAMED",  nullable = false)
	private String named;
	
	@Expose
	@Column(name = "NOTES",  nullable = false)
	private String notes;
	
	@Expose
	@Column(name = "DESCRIPTION",  nullable = false)
	private String description;

}
