package com.saudi.sheeps.dto;

import java.time.LocalDateTime;
import com.saudi.sheeps.entity.Lambs;
import com.saudi.sheeps.entity.Places;
import com.saudi.sheeps.entity.Sheep;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SheepMovementDTO extends BaseDTO {
	
    private Long id;
	
	private LocalDateTime creationDate;
	
	private String date;
	
	private String outDate;
	
	private String fromDate;
	
	private String toDate;
			
	private String notes;
	
	private String description;
	
	private SheepDTO sheep;
	
	private LambsDTO lamb;
	
	private PlacesDTO place;

}
