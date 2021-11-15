package com.saudi.sheeps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardDTO {
	
	private String type;
	private double value;
	
	public DashboardDTO(String type, double value) {
		this.type = type;
		this.value = value;
	}
	
	public DashboardDTO() {
		
	}
	
	

}
