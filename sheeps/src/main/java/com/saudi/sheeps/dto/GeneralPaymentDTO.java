package com.saudi.sheeps.dto;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralPaymentDTO extends BaseDTO {
	
	private Long id;

	private String creationDate;
	
    private Double amount;
	
	private String named;
	
	private String notes;

	private String description;
	

}
