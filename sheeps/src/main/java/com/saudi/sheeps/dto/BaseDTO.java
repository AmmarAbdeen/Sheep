package com.saudi.sheeps.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDTO implements Serializable{
	
	private static final long serialVersionUID = 1438091567511041189L;
	
	private String encryptedData;
	private Class<?> encryptedDataType;
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	

}
