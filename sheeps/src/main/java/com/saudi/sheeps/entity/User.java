package com.saudi.sheeps.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "USER")
public class User {
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;
	
	@Expose
	@Column(name = "FULL_NAME",nullable = false)
	private String fullName;
	
	@Expose
	@Column(name = "EMAIL",nullable = false)
	private String email;
	
	@Expose
	@Column(name = "USER_NAME",nullable = false)
	private String userName;
	
	@Expose
	@Column(name = "PASSWORD",nullable = false)
	private String password;
	
	@Expose
	@Column(name = "MOBILE_NUMBER",nullable = false)
	private String mobileNumber;
	
	@Expose
	@Column(name = "NATIONAL_ID")
	private String nationalId;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_PRIVILEGES", joinColumns = {
			@JoinColumn(referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(referencedColumnName = "ID") })
	private List<Privileges> privilege;

}
