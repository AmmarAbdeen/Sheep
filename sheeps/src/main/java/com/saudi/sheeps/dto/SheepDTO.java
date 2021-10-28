package com.saudi.sheeps.dto;

import java.time.LocalDateTime;

import com.saudi.sheeps.entity.Places;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SheepDTO extends BaseDTO{
	private Long id;	
	private Long code;
	private String color;
	private String birthDate;
	private int age;
	private String arrivalDate;
	private String named;
	private String weight;
	private String shape;
	private String status;
	private String advantages;
	private String disadvantages;
	private String type;
	private String notes;
	private PlacesDTO place;
	private String fromBirthDate;
	private String toBirthDate;
	private String fromArrivalDate;
	private String toArrivalDate;
	@Override
	public String toString() {
		return "code=" + code + ", color=" + color;
	}
	
	
	
}
