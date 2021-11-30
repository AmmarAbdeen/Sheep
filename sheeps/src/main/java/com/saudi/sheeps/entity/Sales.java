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

@Table(name = "SALES")
public class Sales {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CREATION_Date")
	private LocalDateTime creationDate;
	
	@Expose
	@Column(name = "AMOUNT")
	private Double amount;
	
	@Expose
	@Column(name = "CODE")
	private String code;
	
	@Expose
	@Column(name = "COLOR")
	private String color;
	
	@Expose
	@Column(name = "TYPE")
	private String type;
	
	@Expose
	@Column(name = "BUYER")
	private String buyer;

	@Expose
	@Column(name = "DESCRIPTION")
	private String description;

}
