package com.saudi.sheeps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeDTO extends BaseDTO{
	
	private Long id;

	private String creationDate;
	
    private Double amount;
    
    private String source;

	private String description;

}
