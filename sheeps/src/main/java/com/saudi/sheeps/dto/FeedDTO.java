package com.saudi.sheeps.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedDTO extends BaseDTO {

	private Long id;

	private String name;

	private String weight;

	private Double quantity;

	private String storageDate;
	
	private String price;

}
