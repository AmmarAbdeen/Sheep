package com.saudi.sheeps.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.saudi.sheeps.entity.Medicine;
import com.saudi.sheeps.entity.Sheep;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicineDiseaseOfSheepDTO extends BaseDTO {

	private Long id;

	private LocalDateTime creationDate;

	private String medicineOnset;

	private String endOfMedicine;

	private Double quantity;

	private String disease;

	private String description;

	private SheepDTO sheep;

	private MedicineDTO medicine;

}
