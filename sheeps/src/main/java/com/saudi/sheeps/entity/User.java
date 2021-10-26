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


@Table(name = "USER")
public class User {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CREATION_Date")
	private LocalDateTime creationDate;
	
	@Expose
	@Column(name = "FULL_NAME", unique = true,nullable = false)
	private Long fullName;
	
	@Expose
	@Column(name = "EMAIL", unique = true,nullable = false)
	private Long email;
	
	@Expose
	@Column(name = "USER_NAME", unique = true,nullable = false)
	private Long userName;
	
	@Expose
	@Column(name = "PASSWORD", unique = true,nullable = false)
	private Long password;
	
	@Expose
	@Column(name = "MOBILE_NUMBER", unique = true,nullable = false)
	private Long mobileNumber;
	
	@Expose
	@Column(name = "NATIONAL_ID", unique = true,nullable = false)
	private Long nationalId;


}
