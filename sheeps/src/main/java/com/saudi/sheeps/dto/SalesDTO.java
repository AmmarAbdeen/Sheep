package com.saudi.sheeps.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesDTO extends BaseDTO {

	private Long id;

	private String creationDate;
	
    private Double amount;
	
    private String code;
	
	private String color;

	private String type;

	private String buyer;

	private String description;
}
