package com.saudi.sheeps.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlacesFeedDTO extends BaseDTO{
	
    private Long id;	

	private String amount;
	
	private String date;

	private PlacesDTO place;
	
	private FeedDTO feed;


}
