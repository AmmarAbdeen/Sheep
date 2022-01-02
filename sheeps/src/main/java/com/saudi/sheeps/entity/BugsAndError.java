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

@Table(name = "BUGS_AND_ERRORS")
public class BugsAndError {
	
	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "CREATION_Date")
	private LocalDateTime creationDate;
	
	@Expose
	@Column(name = "KEY")
	private String key;
	
	@Expose
	@Column(name = "VALUE")
	private String value;

	public BugsAndError(LocalDateTime creationDate, String key, String value) {
		super();
		this.creationDate = creationDate;
		this.key = key;
		this.value = value;
	}
	
	

}
