package com.saudi.sheeps.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
public class FeedLookupsDTO  extends BaseDTO{

	private Long id;

	private LocalDateTime creationDate;
	
	private String name;
	
	private String benefitOfIt;
	
	private String calcium;
	
	private String dryAmount;
	
	private String phosphorous;
	

	private String protein;
	
	private String energyMj;
}
