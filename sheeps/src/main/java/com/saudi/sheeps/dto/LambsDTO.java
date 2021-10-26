package com.saudi.sheeps.dto;

import com.saudi.sheeps.entity.Places;
import com.saudi.sheeps.entity.Sheep;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LambsDTO extends BaseDTO{

	private Long id;
	private Long code;
	private String color;
	private String birthDate;
	private int age;
	private String dateOfMating;
	private String named;
	private String weightOfBirth;
	private String weightOfWeaning;
	private String weightAtSale;
	private String weigthOfSheep;
	private String shape;
	private String status;
	private String advantages;
	private String disadvantages;
	private String type;
	private String notes;
	private Places places;
	private String fromBirthDate;
	private String toBirthDate;
	private SheepDTO sheepDTO;
	private String fromMatingDate;
	private String toMatingDate;
	
}
