package com.saudi.sheeps.dto;

import java.io.Serializable;

import com.google.gson.Gson;



public class BaseDTO implements Serializable{
	
	private static final long serialVersionUID = 1438091567511041189L;
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	

}
