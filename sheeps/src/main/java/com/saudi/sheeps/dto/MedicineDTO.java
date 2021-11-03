package com.saudi.sheeps.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicineDTO extends BaseDTO{

	private Long id;

	private LocalDateTime creationDate;

	private String name;

	private String cost;

	private Double quantity;

	private String expiryDate;

	private String description;

}
