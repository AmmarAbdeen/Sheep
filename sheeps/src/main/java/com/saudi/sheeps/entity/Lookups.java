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
@Table(name = "LOOKUPS")
public class Lookups {
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CREATION_Date")
	private LocalDateTime creationDate;
	
	@Expose
	@Column(name = "CODE")
	private String code;
	
	@Expose
	@Column(name = "TYPE")
	private String type;
	
	@Expose
	@Column(name = "NAME_EN")
	private String nameEN;
	
	@Expose
	@Column(name = "NAME_AR")
	private String nameAR;

}
